package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class AddressDaoImpl implements AddressDao {
    private final String SQL_INSERT_ADDRESS = "INSERT INTO [dbo].[Address](region, city,area, street,house, flat) VALUES(?, ?, ?, ?, ?, ?)";
    private final String SQL_SELECT_ALL_ADDRESSES = "SELECT * FROM [dbo].[Address]";
    private final String SQL_SELECT_ADDRESS_BY_ID = "SELECT * FROM [dbo].[Address] WHERE id = ?";
    private final String SQL_SELECT_ADDRESS_BY_STREET = "SELECT * FROM [dbo].[Address] WHERE street = ?";

    private final String SQL_DELETE_ALL_ADDRESSES = "DELETE FROM [dbo].[Address]";
    private final String SQL_DELETE_ADDRESS_BY_ID = "DELETE FROM [dbo].[Address] WHERE id = ?";
    private Connection conn;

    public AddressDaoImpl(Connection conn) {
        this.conn = conn;
    }
    @Override
    public long insert(Address record) throws DaoException {
        long id = -1;
        try(PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_ADDRESS, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1,record.getRegion());

            preparedStatement.setString(2,record.getCity());
            if(record.getArea() != null) {
                preparedStatement.setString(3, record.getArea());
            }
            else{
                preparedStatement.setNull(3,NULL);
            }
            preparedStatement.setString(4,record.getStreet());
            preparedStatement.setInt(5,record.getHouse());
            if(record.getFlat() != null) {
                preparedStatement.setInt(6, record.getFlat());
            }
            else{
                preparedStatement.setNull(6,NULL);
            }
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                id = rs.getLong(1);
            }
        }
        catch (SQLException e){
            throw  new DaoException(e);
        }
        return id;
    }

    @Override
    public List<Address> selectAll() throws DaoException {
        List<Address> addressList = new ArrayList<Address>();
        try(PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_ALL_ADDRESSES, Statement.RETURN_GENERATED_KEYS)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Address a = new Address();
                a.setRegion(resultSet.getString("region"));
                a.setCity(resultSet.getString("city"));
                a.setArea(resultSet.getString("area"));
                a.setStreet(resultSet.getString("street"));
                a.setHouse(resultSet.getInt("house"));
                a.setFlat(resultSet.getInt("flat"));
                addressList.add(a);
            }
        }
        catch (SQLException e){
            throw new DaoException(e);
        }
        return addressList;
    }

    @Override
    public Address select(long id) throws DaoException {
        Address a = null;
        try(PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_ADDRESS_BY_ID, Statement.RETURN_GENERATED_KEYS)){
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                a = new Address();
                a.setRegion(resultSet.getString("region"));
                a.setCity(resultSet.getString("city"));
                a.setArea(resultSet.getString("area"));
                a.setStreet(resultSet.getString("street"));
                a.setHouse(resultSet.getInt("house"));
                a.setFlat(resultSet.getInt("flat"));
            }
        }
        catch (SQLException e){
            throw new DaoException(e);
        }
        return a;
    }
    @Override
    public ArrayList<Address> selectByStreet(String street) throws DaoException {
        return null;
    }

    @Override
    public ArrayList<Address> selectByCity(String street) throws DaoException {
        return null;
    }

    @Override
    public ArrayList<Address> selectByRegion(String region) throws DaoException {
        return null;
    }

    @Override
    public ArrayList<Address> selectByArea(String area) throws DaoException {
        return null;
    }

    @Override
    public boolean update(Address record) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Address record) throws DaoException {
        return delete(record.getId());
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try {
            PreparedStatement statement = conn.prepareStatement(SQL_DELETE_ADDRESS_BY_ID);
            statement.setLong(1, id);
            boolean isDeleted = statement.executeUpdate() > 0;
            statement.close();

            return isDeleted;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean deleteAllRecords() throws DaoException {
        try {
            PreparedStatement statement = conn.prepareStatement(SQL_DELETE_ALL_ADDRESSES);

            boolean isDeleted = statement.executeUpdate() > 0;
            statement.close();

            return isDeleted;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


}
