package by.fpmibsu.bielrent.model.dao;

import by.fpmibsu.bielrent.model.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.entity.Filter;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilterDaoImpl implements FilterDao {
    private static final String SQL_SELECT_FILTER_BY_LISTING_ID = "SELECT * FROM [dbo].[Filter] WHERE listingId = ?";
    private static final String SQL_SELECT_ALL_FILTERS = "SELECT * FROM [dbo].[Filter]";
    private static final String SQL_SELECT_FILTER_BY_ID = "SELECT * FROM [dbo].[Filter] WHERE id = ?";
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

    private static final FilterDaoImpl INSTANCE = new FilterDaoImpl();
    Logger logger = org.apache.log4j.Logger.getLogger(FilterDaoImpl.class);

    public FilterDaoImpl() {
    }

    public static FilterDaoImpl getInstance() {
        return INSTANCE;
    }

    public void buildFilter(Filter filter, ResultSet resultSet) throws SQLException {
        filter.setId(resultSet.getLong("id"));
        filter.setBedroomCount(resultSet.getInt("bedroomCount"));
        filter.setRoomCount(resultSet.getInt("roomCount"));
        filter.setBalconyCount(resultSet.getInt("balconyCount"));
        filter.setBuildYear(resultSet.getInt("buildYear"));
        filter.setFloorCount(resultSet.getInt("floorCount"));
        filter.setRentalPeriodStart(resultSet.getDate("rentalPeriodStart").toLocalDate());
        filter.setRentalPeriodEnd(resultSet.getDate("rentalPeriodEnd").toLocalDate());
        filter.setPriceMonthly(resultSet.getLong("priceMonthly"));
        filter.setSquareArea(resultSet.getDouble("squareArea"));
        filter.setHasBathroom(resultSet.getBoolean("hasBathroom"));
        filter.setHasFurniture(resultSet.getBoolean("hasFurniture"));
        filter.setHasWifi(resultSet.getBoolean("hasWifi"));
        filter.setHasWashingMachine(resultSet.getBoolean("hasWashingMachine"));
        filter.setHasElevator(resultSet.getBoolean("hasElevator"));
        filter.setListingId(resultSet.getLong("listingId"));
    }

    public long insert(Filter record, Connection conn) throws DaoException {
        try (PreparedStatement statement
                     = conn.prepareStatement(SQL_INSERT_FILTER, Statement.RETURN_GENERATED_KEYS)) {
            long id = -1;
            statement.setInt(1, record.getRoomCount());
            statement.setInt(2, record.getFloorCount());
            statement.setInt(3, record.getBedroomCount());
            statement.setDouble(4, record.getSquareArea());
            statement.setInt(5, record.getBalconyCount());
            statement.setInt(6, record.getBuildYear());
            statement.setDate(7,
                    Date.valueOf(record.getRentalPeriodStart()));
            statement.setDate(8,
                    Date.valueOf(record.getRentalPeriodEnd()));
            statement.setLong(9, record.getPriceMonthly());
            statement.setBoolean(10, record.getHasBathroom());
            statement.setBoolean(11, record.getHasWifi());
            statement.setBoolean(12, record.getHasWashingMachine());
            statement.setBoolean(13, record.getHasFurniture());
            statement.setBoolean(14, record.getHasElevator());
            statement.setLong(15, record.getListingId());
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

    public boolean update(Filter record, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_FILTER)) {
            statement.setInt(1, record.getRoomCount());
            statement.setInt(2, record.getFloorCount());
            statement.setInt(3, record.getBedroomCount());
            statement.setDouble(4, record.getSquareArea());
            statement.setInt(5, record.getBalconyCount());
            statement.setInt(6, record.getBuildYear());
            statement.setDate(7,
                    (Date) Date.from(record.getRentalPeriodStart()
                            .atStartOfDay(ZoneId.of("Europe/Minsk"))
                            .toInstant()));
            statement.setDate(8,
                    (Date) Date.from(record.getRentalPeriodStart()
                            .atStartOfDay(ZoneId.of("Europe/Minsk"))
                            .toInstant()));
            statement.setLong(9, record.getPriceMonthly());
            statement.setBoolean(10, record.getHasBathroom());
            statement.setBoolean(11, record.getHasWifi());
            statement.setBoolean(12, record.getHasWashingMachine());
            statement.setBoolean(13, record.getHasFurniture());
            statement.setBoolean(14, record.getHasElevator());
            statement.setLong(15, record.getListingId());
            statement.setLong(16, record.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("filter wasn't updated\n");
            throw new DaoException(e);
        }
    }

    public boolean delete(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_DELETE_FILTER_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("filter wasn't deleted\n");
            throw new DaoException(e);
        }
    }

    public Filter selectByListingId(long listingId, Connection conn) throws DaoException {
        try (PreparedStatement statement
                     = conn.prepareStatement(SQL_SELECT_FILTER_BY_LISTING_ID)) {
            statement.setLong(1, listingId);
            ResultSet rs = statement.executeQuery();

            Filter filter = null;
            if (rs.next()) {
                filter = new Filter();
                buildFilter(filter, rs);
            }

            return filter;
        } catch (SQLException e) {
            logger.error("filter wasn't selected by id\n");
            throw new DaoException(e);
        }
    }

    public List<Filter> selectAll(Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL_FILTERS)) {
            ResultSet resultSet = statement.executeQuery();

            List<Filter> filters = new ArrayList<>();
            while (resultSet.next()) {
                Filter filter = new Filter();
                buildFilter(filter, resultSet);

                filters.add(filter);
            }
            return filters;
        } catch (SQLException e) {
            logger.error("filters werent selected\n");
            throw new DaoException(e);
        }
    }

    public Optional<Filter> select(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_FILTER_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            Filter filter = null;
            if (resultSet.next()) {
                filter = new Filter();
                buildFilter(filter, resultSet);
            }

            return Optional.ofNullable(filter);
        } catch (SQLException e) {
            logger.error("filter wasn't selected by id\n");
            throw new RuntimeException(e);
        }
    }

    @Override
    public long insert(Filter record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return insert(record, conn);
        } catch (SQLException e) {
            logger.error("filter wasn't inserted\n");
            throw new DaoException(e);
        }
    }

    @Override
    public List<Filter> selectAll() throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectAll(conn);
        } catch (SQLException e) {
            logger.error("filters werent selected\n");
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Filter> select(long id) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return select(id, conn);
        } catch (SQLException e) {
            logger.error("filter wasn't selected by id\n");
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Filter record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return update(record, conn);
        } catch (SQLException e) {
            logger.error("filter wasn't updated\n");
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Filter record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return delete(record.getId(), conn);
        } catch (SQLException e) {
            logger.error("filter wasn't deleted\n");
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return delete(id, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Filter selectByListingId(long listingId) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectByListingId(listingId, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}



