package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.Filter;
import by.fpmibsu.bielrent.entity.Role;
import by.fpmibsu.bielrent.entity.User;

import java.sql.*;
import java.util.List;

import static java.sql.Types.NULL;

public class FilterDaoAbstract implements FilterDao {
    private final String SQL_INSERT_FILTER = "INSERT INTO Filter(roomCount,floorCount, bedroomCount,squareArea, balconyCount, buildYear, " +
            ", rentalPeriodStart, rentalPeriodEnd, priceMonthly, hasBathroom, hasWifi, hasWashingMachine, hasFurniture, hasElevator, listingId)"
            + " VALUES(?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQL_SELECT_ALL_FILTERS = "SELECT * FROM Filter";
    private final String SQL_SELECT_FILTER_BY_ID = "SELECT * FROM [dbo].[User] WHERE id = ?";
    private final String SQL_UPDATE_FILTER = "UPDATE Filter SET roomCount = ?,floorCount = ?, bedroomCount = ?,squareArea = ?," +
            " balconyCount = ?, buildYear = ?, rentalPeriodStart= ?, rentalPeriodEnd = ?, priceMonthly = ?, hasBathroom = ?, hasWifi = ?," +
            " hasWashingMachine = ?, hasFurniture = ?, hasElevator = ?, listingId = ?";
    private final String SQL_DELETE_FILTER_BY_ID = "DELETE FROM [dbo].[User] WHERE id = ?";

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
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL_FILTERS)) {
            List<Filter> filters = null;
            Filter filter = null;
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {


                filter.setId(resultSet.getLong("id"));
                filter.setRoomCount(resultSet.getInt("roomCount"));
                filter.setFloorCount(resultSet.getInt("floorCount"));
                filter.setBedroomCount(resultSet.getInt("bedroomCount"));
                filter.setSquareArea(resultSet.getDouble("squareArea"));
                filter.setBalconyCount(resultSet.getInt("balconyCount"));
                filter.setBuildYear(resultSet.getInt("buildYear"));
                filter.setRentalPeriodStart(resultSet.getDate("rentalPeriodStart"));
                filter.setRentalPeriodEnd(resultSet.getDate("rentalPeriodEnd"));
                filter.setPriceMonthly(resultSet.getInt("priceMonthly"));
                filter.setHasBathroom(resultSet.getBoolean("hasBathroom"));
                filter.setHasWifi(resultSet.getBoolean("hasWifi"));
                filter.setHasWashingMachine(resultSet.getBoolean("hasWashingMachine"));
                filter.setHasFurniture(resultSet.getBoolean("hasFurniture"));
                filter.setHasElevator(resultSet.getBoolean("hasElevator"));
                ListingDao ld = new ListingDaoImpl(conn);
                filter.setListing(ld.select(resultSet.getLong("listingId")));

                filters.add(filter);
            }
            return filters;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public Filter select(long id) throws DaoException{
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_FILTER_BY_ID)) {
            Filter filter = null;
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                filter.setId(id);
                filter.setRoomCount(resultSet.getInt("roomCount"));
                filter.setFloorCount(resultSet.getInt("floorCount"));
                filter.setBedroomCount(resultSet.getInt("bedroomCount"));
                filter.setSquareArea(resultSet.getDouble("squareArea"));
                filter.setBalconyCount(resultSet.getInt("balconyCount"));
                filter.setBuildYear(resultSet.getInt("buildYear"));
                filter.setRentalPeriodStart(resultSet.getDate("rentalPeriodStart"));
                filter.setRentalPeriodEnd(resultSet.getDate("rentalPeriodEnd"));
                filter.setPriceMonthly(resultSet.getInt("priceMonthly"));
                filter.setHasBathroom(resultSet.getBoolean("hasBathroom"));
                filter.setHasWifi(resultSet.getBoolean("hasWifi"));
                filter.setHasWashingMachine(resultSet.getBoolean("hasWashingMachine"));
                filter.setHasFurniture(resultSet.getBoolean("hasFurniture"));
                filter.setHasElevator(resultSet.getBoolean("hasElevator"));
                ListingDao ld = new ListingDaoImpl(conn);
                filter.setListing(ld.select(resultSet.getLong("listingId")));
            }
            return filter;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public boolean update(Filter record) throws DaoException{
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_FILTER)) {
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



