package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class AddressDaoImpl implements AddressDao {
    private final String SQL_INSERT_ADDRESS = "INSERT INTO [dbo].[Address](regionNumber, city, districtAdministrative" +
            ", districtMicro, street, houseNumber)" +
            " VALUES(?, ?, ?, ?, ?,?)";
    private final String SQL_SELECT_ALL_ADDRESSES = "SELECT * FROM [dbo].[Address]";
    private final String SQL_SELECT_ADDRESS_BY_ID = "SELECT * FROM [dbo].[Address] WHERE id = ?";
    private final String SQL_DELETE_ADDRESS_BY_ID = "DELETE FROM [dbo].[Address] WHERE id = ?";
    private Connection conn;

    public AddressDaoImpl(Connection conn) {
        this.conn = conn;
    }
    @Override
    public long insert(Address record) throws DaoException {
        long id = -1;
        try(PreparedStatement statement = conn.prepareStatement(SQL_INSERT_ADDRESS, Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1,record.getRegionNumber());

            statement.setString(2,record.getCity());
            if(record.getDistrictAdministrative() != null) {
                statement.setString(3, record.getDistrictAdministrative());
            }
            else{
                statement.setNull(3,NULL);
            }
            if(record.getDistrictMicro() != null) {
                statement.setString(4, record.getDistrictMicro());
            }
            else{
                statement.setNull(4,NULL);
            }
            statement.setString(5,record.getStreet());
            statement.setInt(6,record.getHouseNumber());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
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
                a.setId(resultSet.getLong("id"));
                a.setRegionNumber(resultSet.getInt("regionNumber"));
                a.setCity(resultSet.getString("city"));
                a.setDistrictAdministrative(resultSet.getString("districtAdministrative"));
                a.setDistrictMicro(resultSet.getString("districtMicro"));
                a.setStreet(resultSet.getString("street"));
                a.setHouseNumber(resultSet.getInt("houseNumber"));

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
        Address addr = null;
        try(PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ADDRESS_BY_ID, Statement.RETURN_GENERATED_KEYS)){
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                addr = new Address();
                addr.setId(id);
                addr.setRegionNumber(resultSet.getInt("regionNumber"));
                addr.setCity(resultSet.getString("city"));
                addr.setDistrictAdministrative(resultSet.getString("districtAdministrative"));
                addr.setDistrictMicro(resultSet.getString("districtMicro"));
                addr.setStreet(resultSet.getString("street"));
                addr.setHouseNumber(resultSet.getInt("houseNumber"));
            }
        }
        catch (SQLException e){
            throw new DaoException(e);
        }
        return addr;
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
        try (PreparedStatement statement = conn.prepareStatement(SQL_DELETE_ADDRESS_BY_ID)){
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
