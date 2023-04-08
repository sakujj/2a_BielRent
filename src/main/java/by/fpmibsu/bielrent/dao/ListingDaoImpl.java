package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.connectionpool.ConnectionPoolImpl;
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

            if (record.getPropertyType() == null
                    || record.getDescription() == null
                    || record.getDescription().codePoints().count() > 1000) {
                return id;
            }

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

    public Listing select(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_LISTING_BY_ID)) {
            conn.setAutoCommit(false);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            Listing listing = null;
            if (resultSet.next()) {
                listing = new Listing();
                initListing(conn, resultSet, listing);
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

    public List<Listing> selectAll(Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL_LISTINGS)) {
            conn.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery();

            List<Listing> listings = new ArrayList<>();
            while (resultSet.next()) {
                Listing listing = new Listing();
                initListing(conn, resultSet, listing);
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

    private void initListing(Connection conn, ResultSet resultSet, Listing listing) throws DaoException, SQLException {
        buildListingPartly(listing, resultSet);
        listing.setAddress(AddressDaoImpl.getInstance()
                .selectAddressWORefs(resultSet.getLong("addressId"), conn));
        listing.setUser(UserDaoImpl.getInstance()
                .selectWORefs(resultSet.getLong("userId"), conn));
        listing.setPhotos(PhotoDaoImpl.getInstance()
                .selectWORefsByListingId(listing.getId(), conn));
        switch (listing.getPropertyType()) {
            case HOUSE:
                listing.setFilter(HouseFilterDaoImpl.getInstance()
                        .selectWORefsByListingId(listing.getId(), conn));
                break;
            case FLAT:
                listing.setFilter(FlatFilterDaoImpl.getInstance()
                        .selectWORefsByListingId(listing.getId(), conn));
                break;
        }
    }

    protected Listing selectWORefs(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_LISTING_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            Listing listing = null;
            if (resultSet.next()) {
                listing = new Listing();
                buildListingPartly(listing, resultSet);
            }

            return listing;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    protected List<Listing> selectWORefsByUserId(long userId, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_LISTINGS_BY_USER_ID)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            List<Listing> listings = new ArrayList<>();
            while (resultSet.next()) {
                Listing listing = new Listing();
                buildListingPartly(listing, resultSet);
                listings.add(listing);
            }

            return listings;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    protected List<Listing> selectWORefsByAddressId(long addressId, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_LISTINGS_BY_ADDRESS_ID)) {
            statement.setLong(1, addressId);
            ResultSet resultSet = statement.executeQuery();

            List<Listing> listings = new ArrayList<>();
            while (resultSet.next()) {
                Listing listing = new Listing();
                buildListingPartly(listing, resultSet);
                listings.add(listing);
            }

            return listings;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(Listing record, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_LISTING_BY_ID)) {
            if (record.getPropertyType() == null
                    || record.getDescription() == null
                    || record.getDescription().codePoints().count() > 1000) {
                return false;
            }

            statement.setString(1, record.getPropertyType().toString());
            statement.setLong(2, record.getUser().getId());
            statement.setLong(3, record.getAddress().getId());

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

    private void buildListingPartly(Listing listing, ResultSet resultSet) throws DaoException {
        try {
            listing.setId(resultSet.getLong("id"));
            listing.setDescription(resultSet.getString("description"));
            listing.setPropertyType(PropertyType.valueOf(resultSet.getString("propertyTypeName")));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
//try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
//        } catch (SQLException e) {
//        throw new DaoException(e);
//        }