package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.Listing;
import by.fpmibsu.bielrent.entity.PropertyType;
import by.fpmibsu.bielrent.entity.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListingDaoImpl implements  ListingDao{
    String SQL_INSERT_LISTING = "INSERT INTO Listing(propertyTypeName, userId, addressId, description) VALUES (?, ?, ?, ?)";
    String SQL_SELECT_LISTING_BY_ID = "SELECT * FROM Listing where id = ?";
    String SQL_SELECT_ALL_LISTINGS = "SELECT * FROM Listing";
    String SQL_DELETE_LISTING_BY_ID = "DELETE FROM Listing where id = ?";
    String SQL_UPDATE_LISTING_BY_ID = "UPDATE Listing SET propertyTypeName = ?, userId = ?, addressId = ?,filterId = ?, description = ? ";
    Connection conn;
    public ListingDaoImpl(Connection c){
        conn = c;
    }
    @Override
    public long insert(Listing record) throws DaoException {
        try (PreparedStatement statement
                     = conn.prepareStatement(SQL_INSERT_LISTING, Statement.RETURN_GENERATED_KEYS)) {
            long id = -1;

            statement.setString(1, record.getPropertyType().toString());
            statement.setLong(2, record.getUser().getId());
            statement.setLong(3, record.getAddress().getId());

            statement.setString(4, record.getDescription());

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }
            return id;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Listing> selectAll() throws DaoException {

        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL_LISTINGS)) {
            List<Listing> listings = new ArrayList<>();
            //statement.setLong(1, id);
            Listing listing = new Listing();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                listing.setId(resultSet.getLong("id"));
                listing.setPropertyType(PropertyType.valueOf(resultSet.getString(resultSet.getString("propertyTypeName"))));
                UserDao ud = new UserDaoImpl(conn);
                listing.setUser(ud.select(resultSet.getInt("userId")));
                AddressDao ad = new AddressDaoImpl(conn);
                listing.setAddress(ad.select(resultSet.getLong("addressId")));

                listing.setDescription(resultSet.getString("description"));
                listings.add(listing);
            }
            return listings;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Listing select(long id) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_LISTING_BY_ID)) {
            statement.setLong(1, id);
            Listing listing = null;
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                listing.setId(resultSet.getLong("id"));
                listing.setPropertyType(PropertyType.valueOf(resultSet.getString(resultSet.getString("propertyTypeName"))));
                UserDao ud = new UserDaoImpl(conn);
                listing.setUser(ud.select(resultSet.getInt("userId")));
                AddressDao ad = new AddressDaoImpl(conn);
                listing.setAddress(ad.select(resultSet.getLong("addressId")));

                listing.setDescription(resultSet.getString("description"));
            }
            return listing;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Listing record) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_LISTING_BY_ID)) {

            statement.setString(1,record.getPropertyType().toString());
            statement.setLong(2,record.getUser().getId());
            statement.setLong(3,record.getAddress().getId());

            statement.setString(4, record.getDescription());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Listing record) throws DaoException {
        return delete(record.getId());
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try(PreparedStatement statement = conn.prepareStatement(SQL_DELETE_LISTING_BY_ID)){
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
