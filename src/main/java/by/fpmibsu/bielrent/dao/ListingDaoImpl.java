package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.entity.*;

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

    public Optional<Listing> select(long id, Connection conn) throws DaoException {
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
            return Optional.ofNullable(listing);
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
            listing.setFilterId(HouseFilterDaoImpl.getInstance().selectByListingId(id, conn).get().getId());
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

    /**
     * Selects ORM listings from database, that meet specified query
     * @param query data to filter entities (ids other than Listing.id doesn't mean a thing)
     * @param topN how many entities to select
     * @param idToStartFrom Listing.id to start selection from
     * @return List of ORM listings that meet specified query
     * @throws DaoException
     */
    public List<ListingORM> selectTopNByListingData(ListingQuery query, long topN, long idToStartFrom) throws DaoException {
        Address addressQ = query.getAddress();
        Filter filterQ = query.getFilter();

        String sqlQuery = "SELECT TOP (?) * FROM " +
                " Listing l " +
                " LEFT JOIN Filter f ON f.listingId = l.id " +
                " LEFT JOIN FlatFilter ff ON ff.filterId = f.id " +
                " LEFT JOIN HouseFilter hf ON hf.filterId = f.id " +
                " LEFT JOIN Address a ON l.addressId = a.id " +
                " LEFT JOIN [dbo].[User] u ON l.userId = u.id " +
                " WHERE l.id >= ? ";
        List<Object> params = new ArrayList<>();

        params.add(topN);
        params.add(idToStartFrom);

        if (query.getPropertyType() == PropertyType.FLAT) {
            sqlQuery += " AND propertyTypeName <> ? ";
            params.add(PropertyType.HOUSE.toString());
        } else if (query.getPropertyType() == PropertyType.HOUSE) {
            sqlQuery += " AND propertyTypeName <> ? ";
            params.add(PropertyType.FLAT.toString());
        }

        if (addressQ != null) {
            if (addressQ.getStreet() != null) {
                sqlQuery += " AND street = ? ";
                params.add(addressQ.getStreet());
            }
            if (addressQ.getCity() != null) {
                sqlQuery += " AND city = ? ";
                params.add(addressQ.getCity());
            }
            if (addressQ.getDistrictAdministrative() != null) {
                sqlQuery += " AND districtAdministrative = ? ";
                params.add(addressQ.getDistrictAdministrative());
            }
            if (addressQ.getDistrictMicro() != null) {
                sqlQuery += " AND districtMicro = ? ";
                params.add(addressQ.getDistrictMicro());
            }
            if (addressQ.getRegionNumber() != null) {
                sqlQuery += " AND regionNumber = ? ";
                params.add(addressQ.getRegionNumber());
            }
            if (addressQ.getHouseNumber() != null) {
                sqlQuery += " AND houseNumber = ? ";
                params.add(addressQ.getHouseNumber());
            }
        }

        if (filterQ != null) {
            if (filterQ.getBedroomCount() != null) {
                sqlQuery += " AND bedroomCount = ? ";
                params.add(filterQ.getBedroomCount());
            }
            if (filterQ.getBalconyCount() != null) {
                sqlQuery += " AND balconyCount = ? ";
                params.add(filterQ.getBalconyCount());
            }
            if (filterQ.getRoomCount() != null) {
                sqlQuery += " AND roomCount = ? ";
                params.add(filterQ.getRoomCount());
            }
            if (filterQ.getFloorCount() != null) {
                sqlQuery += " AND floorCount = ? ";
                params.add(filterQ.getFloorCount());
            }

            if (filterQ.getHasBathroom() != null) {
                sqlQuery += " AND hasBathroom = ? ";
                params.add(filterQ.getHasBathroom());
            }
            if (filterQ.getHasFurniture() != null) {
                sqlQuery += " AND hasFurniture = ? ";
                params.add(filterQ.getHasFurniture());
            }
            if (filterQ.getHasWifi() != null) {
                sqlQuery += " AND hasWifi = ? ";
                params.add(filterQ.getHasWifi());
            }
            if (filterQ.getHasElevator() != null) {
                sqlQuery += " AND hasElevator = ? ";
                params.add(filterQ.getHasElevator());
            }
            if (filterQ.getHasWashingMachine() != null) {
                sqlQuery += " AND hasWashingMachine = ? ";
                params.add(filterQ.getHasWashingMachine());
            }

            if (filterQ.getBuildYear() != null) {
                sqlQuery += " AND buildYear = ? ";
                params.add(filterQ.getBuildYear());
            }
            if (filterQ.getSquareArea() != null) {
                sqlQuery += " AND squareArea = ? ";
                params.add(filterQ.getSquareArea());
            }

            if (filterQ.getRentalPeriodStart() != null) {
                sqlQuery += " AND rentalPeriodStart >= ? ";
                params.add(Date.valueOf(filterQ.getRentalPeriodStart()));
            }
            if (filterQ.getRentalPeriodEnd() != null) {
                sqlQuery += " AND rentalPeriodEnd <= ? ";
                params.add(Date.valueOf(filterQ.getRentalPeriodEnd()));
            }

        }

        if (query.getPriceFrom() != null) {
            sqlQuery += " AND priceMonthly >= ? ";
            params.add(query.getPriceFrom());
        }
        if (query.getPriceTo() != null) {
            sqlQuery += " AND priceMonthly <= ? ";
            params.add(query.getPriceTo());
        }
        System.out.println(params);

        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            try {
                PreparedStatement statement = conn.prepareStatement(sqlQuery);


                conn.setAutoCommit(false);
                for (int i = 0; i < params.size(); i++) {
                    statement.setObject(i + 1, params.get(i));
                    System.out.println(params.get(i));
                }

                List<ListingORM> list = new ArrayList<>();

                AddressDaoImpl addressDao = AddressDaoImpl.getInstance();
                UserDaoImpl userDao = UserDaoImpl.getInstance();
                FlatFilterDaoImpl flatFilterDao = FlatFilterDaoImpl.getInstance();
                HouseFilterDaoImpl houseFilterDao = HouseFilterDaoImpl.getInstance();
                PhotoDaoImpl photoDao = PhotoDaoImpl.getInstance();

                System.out.println(sqlQuery);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    ListingORM listingORM = new ListingORM();

                    Address address = new Address();
                    addressDao.buildAddress(address, rs);
                    listingORM.setAddress(address);

                    User user = new User();
                    userDao.buildUser(user, rs);
                    listingORM.setUser(user);


                    listingORM.setPropertyType(PropertyType.valueOf(rs.getString("propertyTypeName")));

                    if (listingORM.getPropertyType() == PropertyType.FLAT) {
                        FlatFilter flatFilter = new FlatFilter();
                        flatFilterDao.buildFlatFilter(flatFilter, rs);
                        listingORM.setFilter(flatFilter);
                    } else if (listingORM.getPropertyType() == PropertyType.HOUSE) {
                        HouseFilter houseFilter = new HouseFilter();
                        houseFilterDao.buildHouseFilter(houseFilter, rs);
                        listingORM.setFilter(houseFilter);
                    }

                    listingORM.setId(rs.getLong("id"));
                    listingORM.setDescription(rs.getString("description"));

                    List<Photo> photos = photoDao.selectAllByListingId(listingORM.getId());
                    listingORM.setPhotos(photos);

                    list.add(listingORM);
                }

                conn.commit();
                return list;

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
    public Optional<Listing> select(long id) throws DaoException {
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