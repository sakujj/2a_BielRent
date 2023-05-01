package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListingDaoImpl implements ListingDao {
    String SQL_INSERT_LISTING
            = "INSERT INTO " +
            "Listing(" +
            "propertyTypeName, " +
            "userId, " +
            "addressId, " +
            "description) " +
            "VALUES (?, ?, ?, ?)";
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
            "description = ? ";
    String SQL_SELECT_LISTINGS_BY_USER_ID
            = "SELECT * " +
            "FROM dbo.[Listing] " +
            "WHERE userId = ?";
    String SQL_SELECT_LISTINGS_BY_ADDRESS_ID
            = "SELECT * " +
            "FROM dbo.[Listing] " +
            "WHERE addressId = ? ";
    private static final ListingDaoImpl INSTANCE = new ListingDaoImpl();

    private ListingDaoImpl() {
    }

    public static ListingDaoImpl getInstance() {
        return INSTANCE;
    }

    public long insert(Listing record, Connection conn) throws DaoException {
        try (PreparedStatement statement
                     = conn.prepareStatement(SQL_INSERT_LISTING, Statement.RETURN_GENERATED_KEYS)) {
            long id = -1;

            statement.setString(1, record.getPropertyType().toString());
            statement.setLong(2, record.getUserId());
            statement.setLong(3, record.getAddressId());
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

    public Listing select(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_LISTING_BY_ID)) {
            conn.setAutoCommit(false);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            Listing listing = null;
            if (resultSet.next()) {
                listing = new Listing();
                buildListingWOFilterId(listing, resultSet);
                this.setFilterIdForListing(id, conn, listing);
            }

            conn.commit();
            return listing;
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

    private void setFilterIdForListing(long id, Connection conn, Listing listing) throws DaoException {
        if (listing.getPropertyType() == PropertyType.HOUSE) {
            listing.setFilterId(HouseFilterDaoImpl.getInstance().selectByListingId(id, conn).getId());
        } else if (listing.getPropertyType() == PropertyType.FLAT) {
            listing.setFilterId(FlatFilterDaoImpl.getInstance().selectByListingId(id, conn).getId());
        }
    }

    public List<Listing> selectAll(Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL_LISTINGS)) {
            conn.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery();

            List<Listing> listings = new ArrayList<>();
            while (resultSet.next()) {
                Listing listing = new Listing();
                buildListingWOFilterId(listing, resultSet);
                this.setFilterIdForListing(listing.getId(), conn, listing);
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

    public List<Listing> selectAllByUserId(long userId, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_LISTINGS_BY_USER_ID)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            List<Listing> listings = new ArrayList<>();
            while (resultSet.next()) {
                Listing listing = new Listing();
                buildListingWOFilterId(listing, resultSet);
                this.setFilterIdForListing(listing.getId(), conn, listing);
                listings.add(listing);
            }

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
            statement.setLong(1, addressId);
            ResultSet resultSet = statement.executeQuery();
            List<Listing> listings = new ArrayList<>();
            while (resultSet.next()) {
                Listing listing = new Listing();
                buildListingWOFilterId(listing, resultSet);
                this.setFilterIdForListing(listing.getId(), conn, listing);
                listings.add(listing);
            }

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
            statement.setString(1, record.getPropertyType().toString());
            statement.setLong(2, record.getUserId());
            statement.setLong(3, record.getAddressId());
            statement.setString(4, record.getDescription());

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
            throw new DaoException(e);
        }
    }

    @Override
    public List<Listing> selectAllByAddressId(long addressId) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectAllByAddressId(addressId, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public long insert(Listing record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return insert(record, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Listing> selectAll() throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectAll(conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Listing select(long id) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return select(id, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Listing record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return update(record, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Listing record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return delete(record.getId(), conn);
        } catch (SQLException e) {
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

    private void buildListingWOFilterId(Listing listing, ResultSet resultSet) throws DaoException {
        try {
            listing.setId(resultSet.getLong("id"));
            listing.setDescription(resultSet.getString("description"));
            listing.setPropertyType(PropertyType.valueOf(resultSet.getString("propertyTypeName")));
            listing.setAddressId(resultSet.getLong("addressId"));
            listing.setUserId(resultSet.getLong("userId"));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
//try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
//        } catch (SQLException e) {
//        throw new DaoException(e);
//        }