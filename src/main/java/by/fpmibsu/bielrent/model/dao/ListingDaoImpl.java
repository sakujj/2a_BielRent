package by.fpmibsu.bielrent.model.dao;

import by.fpmibsu.bielrent.model.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.req.AddressReq;
import by.fpmibsu.bielrent.model.dto.req.FilterReq;
import by.fpmibsu.bielrent.model.entity.*;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListingDaoImpl implements ListingDao {
    String SQL_INSERT_LISTING
            = "INSERT INTO " +
            "Listing(" +
            "propertyTypeName, " +
            "userId, " +
            "addressId, " +
            "description, " +
            " name) " +
            "VALUES (?, ?, ?, ?, ?)";
    String SQL_SELECT_LISTING_BY_ID
            = "SELECT * " +
            "FROM Listing " +
            "WHERE id = ?";
    String SQL_SELECT_ALL_LISTINGS
            = "SELECT * " +
            "FROM Listing";
    String SQL_DELETE_LISTING_BY_ID
            = "DELETE " +
            "FROM Listing " +
            "WHERE id = ?";
    String SQL_UPDATE_LISTING_BY_ID
            = "UPDATE Listing " +
            "SET " +
            "propertyTypeName = ?, " +
            "userId = ?, " +
            "addressId = ?," +
            "filterId = ?, " +
            "description = ?, " +
            "name = ? ";
    String SQL_SELECT_LISTINGS_BY_USER_ID
            = "SELECT * " +
            "FROM dbo.[Listing] " +
            "WHERE userId = ?";
    String SQL_SELECT_LISTINGS_BY_ADDRESS_ID
            = "SELECT * " +
            "FROM dbo.[Listing] " +
            "WHERE addressId = ? ";
    private static final ListingDaoImpl INSTANCE = new ListingDaoImpl();
    Logger logger = LogManager.getLogger(FilterDaoImpl.class);

    private ListingDaoImpl() {
    }

    public static ListingDaoImpl getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public int countListings() {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(id) AS cnt FROM Listing");
            rs.next();
            return rs.getInt("cnt");
        } catch (SQLException e) {
            logger.error("listings werent counted");
            throw new DaoException(e);
        }
    }

    public long insert(Listing record, Connection conn) throws DaoException {
        try (PreparedStatement statement
                     = conn.prepareStatement(SQL_INSERT_LISTING, Statement.RETURN_GENERATED_KEYS)) {
            long id = -1;

            statement.setString(1, record.getPropertyTypeName().toString());
            statement.setLong(2, record.getUserId());
            statement.setLong(3, record.getAddressId());
            statement.setString(4, record.getDescription());
            statement.setString(5, record.getName());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }

            return id;
        } catch (SQLException e) {
            logger.error("listing wasnt inserted");
            throw new DaoException(e);
        }
    }

    public Optional<Listing> select(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_LISTING_BY_ID)) {
            conn.setAutoCommit(false);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            Listing listing = null;
            if (resultSet.next()) {
                listing = new Listing();
                buildListing(listing, resultSet);
            }

            conn.commit();
            return Optional.ofNullable(listing);
        } catch (SQLException e) {
            logger.error("listing wasnt added");
            try {
                conn.rollback();

            } catch (SQLException ex) {
                logger.error("rollback failed");
                throw new DaoException(ex);
            }
            throw new DaoException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error(e);
                throw new DaoException(e);
            }
        }
    }

    public List<Listing> selectAll(Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL_LISTINGS)) {
            conn.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery();

            List<Listing> listings = new ArrayList<>();
            var filterDao = FilterDaoImpl.getInstance();
            while (resultSet.next()) {
                Listing listing = new Listing();
                buildListing(listing, resultSet);
                listings.add(listing);
            }

            conn.commit();
            return listings;
        } catch (SQLException e) {
            logger.error(e);
            try {
                conn.rollback();
            } catch (SQLException ex) {
                logger.error("rollback failed");
                throw new DaoException(ex);
            }
            throw new DaoException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error(e);
                throw new DaoException(e);
            }
        }
    }

    public List<Listing> selectAllByUserId(long userId, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_LISTINGS_BY_USER_ID)) {
            conn.setAutoCommit(false);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            List<Listing> listings = new ArrayList<>();
            var filterDao = FilterDaoImpl.getInstance();
            while (resultSet.next()) {
                Listing listing = new Listing();
                buildListing(listing, resultSet);
                listings.add(listing);
            }

            conn.commit();
            return listings;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
            throw new DaoException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }

    public List<Listing> selectAllByAddressId(long addressId, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_LISTINGS_BY_ADDRESS_ID)) {
            conn.setAutoCommit(false);
            statement.setLong(1, addressId);
            ResultSet resultSet = statement.executeQuery();

            List<Listing> listings = new ArrayList<>();
            var filterDao = FilterDaoImpl.getInstance();
            while (resultSet.next()) {
                Listing listing = new Listing();
                buildListing(listing, resultSet);
                listings.add(listing);
            }

            conn.commit();
            return listings;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
            throw new DaoException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }

    public boolean update(Listing record, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_LISTING_BY_ID)) {
            statement.setString(1, record.getPropertyTypeName().toString());
            statement.setLong(2, record.getUserId());
            statement.setLong(3, record.getAddressId());
            statement.setString(4, record.getDescription());
            statement.setString(5, record.getName());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_DELETE_LISTING_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public List<Listing> selectAllByUserId(long userId) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectAllByUserId(userId, conn);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Listing> selectAllByAddressId(long addressId) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectAllByAddressId(addressId, conn);
        } catch (SQLException e) {logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public long insert(Listing record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return insert(record, conn);
        } catch (SQLException e) {logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Listing> selectAll() throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectAll(conn);
        } catch (SQLException e) {logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Listing> select(long id) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return select(id, conn);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Listing record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return update(record, conn);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Listing record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return delete(record.getId(), conn);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return delete(id, conn);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    private void buildListing(Listing listing, ResultSet resultSet) throws DaoException {
        try {
            listing.setId(resultSet.getLong("id"));
            listing.setName(resultSet.getString("name"));
            listing.setDescription(resultSet.getString("description"));
            listing.setPropertyTypeName(PropertyType.valueOf(resultSet.getString("propertyTypeName")));
            listing.setAddressId(resultSet.getLong("addressId"));
            listing.setUserId(resultSet.getLong("userId"));
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
}
