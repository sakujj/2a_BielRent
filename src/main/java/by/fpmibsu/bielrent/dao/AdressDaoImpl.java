package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.Adress;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class AdressDaoImpl implements AdressDao{
    private final String SQL_INSERT_ADRESS = "INSERT INTO [dbo].[Adress](region, city,area, street,house, flat) VALUES(?, ?, ?, ?, ?, ?)";
    private final String SQL_SELECT_ALL_ADRESSES = "SELECT * FROM [dbo].[Adress]";
    private final String SQL_SELECT_ADRESS_BY_ID = "SELECT * FROM [dbo].[Adress] WHERE id = ?";
    private final String SQL_SELECT_ADRESS_BY_STREET = "SELECT * FROM [dbo].[Adress] WHERE street = ?";

    private final String SQL_DELETE_ALL_ADRESSES = "DELETE FROM [dbo].[Adress]";
    private final String SQL_DELETE_ADRESS_BY_ID = "DELETE FROM [dbo].[Adress] WHERE id = ?";
    private Connection conn;

    public AdressDaoImpl(Connection conn) {
        this.conn = conn;
    }
    @Override
    public long insert(Adress record) throws DaoException {
        long id = -1;
        try(PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_ADRESS, Statement.RETURN_GENERATED_KEYS)){
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
    public List<Adress> selectAll() throws DaoException {
        List<Adress> adressList = new ArrayList<Adress>();
        try(PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_ALL_ADRESSES, Statement.RETURN_GENERATED_KEYS)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Adress a = new Adress();
                a.setRegion(resultSet.getString("region"));
                a.setCity(resultSet.getString("city"));
                a.setArea(resultSet.getString("area"));
                a.setStreet(resultSet.getString("street"));
                a.setHouse(resultSet.getInt("house"));
                a.setFlat(resultSet.getInt("flat"));
                adressList.add(a);
            }
        }
        catch (SQLException e){
            throw new DaoException(e);
        }
        return adressList;
    }

    @Override
    public Adress select(long id) throws DaoException {
        Adress a = null;
        try(PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_ADRESS_BY_ID, Statement.RETURN_GENERATED_KEYS)){
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                a = new Adress();
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
    public ArrayList<Adress> selectByStreet(String street) throws DaoException {
        return null;
    }

    @Override
    public ArrayList<Adress> selectByCity(String street) throws DaoException {
        return null;
    }

    @Override
    public ArrayList<Adress> selectByRegion(String region) throws DaoException {
        return null;
    }

    @Override
    public ArrayList<Adress> selectByArea(String area) throws DaoException {
        return null;
    }

    @Override
    public boolean update(Adress record) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Adress record) throws DaoException {
        return delete(record.getId());
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try {
            PreparedStatement statement = conn.prepareStatement(SQL_DELETE_ADRESS_BY_ID);
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
            PreparedStatement statement = conn.prepareStatement(SQL_DELETE_ALL_ADRESSES);

            boolean isDeleted = statement.executeUpdate() > 0;
            statement.close();

            return isDeleted;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


}
