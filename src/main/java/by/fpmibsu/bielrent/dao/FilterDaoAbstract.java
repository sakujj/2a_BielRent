package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.Filter;


import java.sql.*;
import java.util.List;

import static java.sql.Types.NULL;

public class FilterDaoAbstract implements FilterDao {
    private final String SQL_INSERT_FILTER = "INSERT INTO " +
            "dbo.[Filter](" +
            "roomCount," +
            "floorCount, " +
            "bedroomCount," +
            "squareArea, " +
            "balconyCount, " +
            "buildYear, " +
            "rentalPeriodStart, " +
            "rentalPeriodEnd, " +
            "priceMonthly, " +
            "hasBathroom, " +
            "hasWifi, " +
            "hasWashingMachine, " +
            "hasFurniture, " +
            "hasElevator, " +
            "listingId)"
            + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQL_UPDATE_FILTER
            = "UPDATE dbo.[Filter] SET " +
            "roomCount = ?," +
            "floorCount = ?, " +
            "bedroomCount = ?," +
            "squareArea = ?," +
            " balconyCount = ?, " +
            "buildYear = ?, " +
            "rentalPeriodStart= ?, " +
            "rentalPeriodEnd = ?, " +
            "priceMonthly = ?, " +
            "hasBathroom = ?, " +
            "hasWifi = ?," +
            " hasWashingMachine = ?," +
            " hasFurniture = ?, " +
            "hasElevator = ?, " +
            "listingId = ? " +
            "WHERE id = ?";
    private final String SQL_DELETE_FILTER_BY_ID = "DELETE FROM [dbo].[Filter] WHERE id = ?";

    private Connection conn;

    public FilterDaoAbstract(Connection c) {
        conn = c;
    }

    @Override
    public long insert(Filter record) throws DaoException {
        try (PreparedStatement statement
                     = conn.prepareStatement(SQL_INSERT_FILTER, Statement.RETURN_GENERATED_KEYS)) {
            long id = -1;
            statement.setInt(1, record.getRoomCount());
            statement.setInt(2, record.getFloorCount());
            statement.setInt(3, record.getBedroomCount());
            statement.setDouble(4, record.getSquareArea());
            statement.setInt(5, record.getBalconyCount());
            statement.setInt(6, record.getBuildYear());
            statement.setDate(7, record.getRentalPeriodStart());
            statement.setDate(8, record.getRentalPeriodEnd());
            statement.setLong(9, record.getPriceMonthly());
            statement.setBoolean(10, record.getHasBathroom());
            statement.setBoolean(11, record.getHasWifi());
            statement.setBoolean(12, record.getHasWashingMachine());
            statement.setBoolean(13, record.getHasFurniture());
            statement.setBoolean(14, record.getHasElevator());
            if(record.getListing() == null){
                statement.setNull(15, NULL);
            }
            else {
                statement.setLong(15, record.getListing().getId());
            }
            statement.executeUpdate();
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
    public List<Filter> selectAll() throws DaoException{
        throw new UnsupportedOperationException("selectAll() is not supported in FilterDaoAbstract");
    }

    @Override
    public Filter select(long id) throws DaoException{
        throw new UnsupportedOperationException("select(long) is not supported in FilterDaoAbstract");
    }

    @Override
    public boolean update(Filter record) throws DaoException{
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_FILTER)) {
            statement.setInt(1, record.getRoomCount());
            statement.setInt(2, record.getFloorCount());
            statement.setInt(3, record.getBedroomCount());
            statement.setDouble(4, record.getSquareArea());
            statement.setInt(5, record.getBalconyCount());
            statement.setInt(6, record.getBuildYear());
            statement.setDate(7, record.getRentalPeriodStart());
            statement.setDate(8, record.getRentalPeriodEnd());
            statement.setLong(9, record.getPriceMonthly());
            statement.setBoolean(10, record.getHasBathroom());
            statement.setBoolean(11, record.getHasWifi());
            statement.setBoolean(12, record.getHasWashingMachine());
            statement.setBoolean(13, record.getHasFurniture());
            statement.setBoolean(14, record.getHasElevator());
            if(record.getListing() == null){
                statement.setNull(15, NULL);
            }
            else {
                statement.setLong(15, record.getListing().getId());
            }
            statement.setLong(16, record.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public boolean delete(Filter record) throws DaoException{
        return delete(record.getId());
    }
    @Override
    public boolean delete(long id) throws DaoException{
        try(PreparedStatement statement = conn.prepareStatement(SQL_DELETE_FILTER_BY_ID)){
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}



