package by.fpmibsu.bielrent.model.service;

import by.fpmibsu.bielrent.model.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.model.dao.*;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.req.AddressReq;
import by.fpmibsu.bielrent.model.dto.req.FilterReq;
import by.fpmibsu.bielrent.model.dto.req.ListingOrmReq;
import by.fpmibsu.bielrent.model.dto.req.ListingReq;
import by.fpmibsu.bielrent.model.dto.resp.ListingOrmResp;
import by.fpmibsu.bielrent.model.dtomapper.ListingMapper;
import by.fpmibsu.bielrent.model.dtomapper.ListingOrmMapper;
import by.fpmibsu.bielrent.model.dtovalidator.ListingValidator;
import by.fpmibsu.bielrent.model.dtovalidator.ValidationException;
import by.fpmibsu.bielrent.model.entity.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListingOrmService {
    public static ListingOrmService getInstance() {
        return INSTANCE;
    }

    private static final ConnectionPoolImpl connPool = ConnectionPoolImpl.getInstance();
    private static final ListingMapper listingMapper = ListingMapper.getInstance();
    private static final ListingOrmMapper listingOrmMapper = ListingOrmMapper.getInstance();
    private static final ListingDaoImpl listingDao = ListingDaoImpl.getInstance();
    private static final FilterDaoImpl filterDao = FilterDaoImpl.getInstance();
    private static final AddressDaoImpl addressDao = AddressDaoImpl.getInstance();
    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static final PhotoDaoImpl photoDao = PhotoDaoImpl.getInstance();
    private static final AtomicInteger listingCount = new AtomicInteger(listingDao.countListings());
    private static final AddressService addressService = AddressService.getInstance();
    private static final PhotoService photoService = PhotoService.getInstance();
    private static final FilterService filterService = FilterService.getInstance();
    private static final UserService userService = UserService.getInstance();
    private static final ListingService listingService = ListingService.getInstance();

    private static final ListingValidator listingValidator = ListingValidator.getInstance();
    private Logger logger = LogManager.getLogger(AddressService.class);
    private static final ListingOrmService INSTANCE = new ListingOrmService();

    public List<ListingOrm> getListingsByUserId(Long userId) throws DaoException {
        String sqlQuery = "SELECT * FROM " +
                " Listing l " +
                " LEFT JOIN Filter f ON f.listingId = l.id " +
                " LEFT JOIN Address a ON l.addressId = a.id " +
                " LEFT JOIN [dbo].[User] u ON l.userId = u.id WHERE userId = (?) ";
        List<ListingOrm> list= new ArrayList<ListingOrm>();
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            try {
                conn.setAutoCommit(false);
                PreparedStatement statement = conn.prepareStatement(sqlQuery);
                statement.setLong(1, userId);


                AddressDaoImpl addressDao = AddressDaoImpl.getInstance();
                UserDaoImpl userDao = UserDaoImpl.getInstance();
                FilterDaoImpl filterDao = FilterDaoImpl.getInstance();
                PhotoDaoImpl photoDao = PhotoDaoImpl.getInstance();

//                System.out.println(sqlQuery);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {//тут ничего
                    ListingOrm listingORM = new ListingOrm();

                    Address address = new Address();
                    addressDao.buildAddress(address, rs);
                    listingORM.setAddress(address);

                    User user = new User();
                    userDao.buildUser(user, rs);
                    listingORM.setUser(user);


                    listingORM.setPropertyTypeName(PropertyType.valueOf(rs.getString("propertyTypeName")));

                    Filter filter = new Filter();
                    filterDao.buildFilter(filter, rs);
                    listingORM.setFilter(filter);

                    listingORM.setId(rs.getLong("id"));
                    listingORM.setName(rs.getString("name"));
                    listingORM.setDescription(rs.getString("description"));

                    List<Photo> photos = photoDao.selectAllByListingId(listingORM.getId());
                    listingORM.setPhotos(photos);

                    list.add(listingORM);
                }

                conn.commit();
            } catch (SQLException e) {
                throw new DaoException(e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return list;
    };
    public List<ListingOrm> getListingsWithOffset(long listingCount, long offset) throws DaoException {
        String sqlQuery = "SELECT * FROM " +
                " Listing l " +
                " LEFT JOIN Filter f ON f.listingId = l.id " +
                " LEFT JOIN Address a ON l.addressId = a.id " +
                " LEFT JOIN [dbo].[User] u ON l.userId = u.id " +
                " ORDER BY l.id DESC OFFSET (?) ROWS " +
                " FETCH NEXT (?) ROWS ONLY ";
        List<Object> params = new ArrayList<>();
        params.add(offset);
        params.add(listingCount);

        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            try {
                PreparedStatement statement = conn.prepareStatement(sqlQuery);


                conn.setAutoCommit(false);
                for (int i = 0; i < params.size(); i++) {
                    statement.setObject(i + 1, params.get(i));
                    // System.out.println(params.get(i));
                }

                List<ListingOrm> list = new ArrayList<>();

                AddressDaoImpl addressDao = AddressDaoImpl.getInstance();
                UserDaoImpl userDao = UserDaoImpl.getInstance();
                FilterDaoImpl filterDao = FilterDaoImpl.getInstance();
                PhotoDaoImpl photoDao = PhotoDaoImpl.getInstance();

                ResultSet rs = statement.executeQuery();
                while (rs.next()) {//тут ничего
                    ListingOrm listingORM = new ListingOrm();

                    Address address = new Address();
                    addressDao.buildAddress(address, rs);
                    listingORM.setAddress(address);

                    User user = new User();
                    userDao.buildUser(user, rs);
                    listingORM.setUser(user);


                    listingORM.setPropertyTypeName(PropertyType.valueOf(rs.getString("propertyTypeName")));

                    Filter filter = new Filter();
                    filterDao.buildFilter(filter, rs);
                    listingORM.setFilter(filter);

                    listingORM.setId(rs.getLong("id"));
                    listingORM.setName(rs.getString("name"));
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
                    logger.error("rollback failed");
                    throw new DaoException(ex);
                }
                throw new DaoException(e);
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    logger.error("set auto commit failed");
                    throw new DaoException(e);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    public Long insertIfValid(ListingOrmReq listingOrmReq) throws DaoException, ValidationException, IOException {
        try (var conn = connPool.getConnection()) {
            return insertIfValid(listingOrmReq, conn);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public Long insertIfValid(ListingOrmReq listingOrmReq, Connection conn) throws DaoException, ValidationException, IOException {
        try {
            conn.setAutoCommit(false);

            var addressReq = listingOrmReq.getAddress();
            Long addressId = addressService.insertIfValid(addressReq, conn);

            var userReq = listingOrmReq.getUser();
            Long userId = userService.getUser(userReq.getEmail(), userReq.getPassword(), conn).get().getId();

            var listingReq = ListingReq.builder()
                    .description(listingOrmReq.getDescription())
                    .propertyTypeName(listingOrmReq.getPropertyTypeName())
                    .name(listingOrmReq.getName())
                    .build();
            Long listingId = listingService.insertIfValid(listingReq, addressId, userId, conn);

            var filterReq = listingOrmReq.getFilter();
            filterService.insertIfValid(filterReq, listingId, conn);

            var photosReq = listingOrmReq.getPhotos();
            for (var photoReq : photosReq) {
                photoService.insertIfValid(photoReq, listingId, conn);
            }

            conn.commit();

            return listingId;
        } catch (Exception e) {
            logger.error("insert if valid listingORM service error\n");
            try {
                conn.rollback();
            } catch (SQLException ex) {
                logger.error("insert if valid listing service error(connection rollback failed\n)");
                throw new DaoException(ex);
            }

            if (e instanceof ValidationException) {
                throw (ValidationException) e;
            } else if (e instanceof IOException) {
                throw (IOException) e;
            } else {
                throw new DaoException(e);
            }

        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }

    public List<ListingOrmResp> getListingsRespWithOffset(int listingCount, int offset) throws DaoException {
        return getListingsWithOffset(listingCount, offset)
                .stream()
                .map(listingOrmMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<ListingOrmResp> getListingById(long id) throws DaoException {
        try (var conn = connPool.getConnection()) {
            return getListingById(id, conn);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }


    public Optional<ListingOrmResp> getListingById(long id, Connection conn) throws DaoException {
        try {
            conn.setAutoCommit(false);

            Listing listing = listingDao.select(id, conn).get();
            Filter filter = filterDao.selectByListingId(id, conn);
            Address address = addressDao.select(listing.getAddressId(), conn).get();
            User user = userDao.select(listing.getUserId(), conn);
            List<Photo> photos = photoDao.selectAllByListingId(listing.getId(), conn);
            ListingOrm listingOrm = ListingOrm.builder()
                    .name(listing.getName())
                    .propertyTypeName(listing.getPropertyTypeName())
                    .id(listing.getId())
                    .description(listing.getDescription())
                    .filter(filter)
                    .address(address)
                    .user(user)
                    .photos(photos)
                    .build();

            var listingOrmResp = listingOrmMapper.fromEntity(listingOrm);

            conn.commit();
            return Optional.ofNullable(listingOrmResp);
        } catch (Exception e) {
            logger.error(e);
            try {

                conn.rollback();
            } catch (SQLException ex) {
                logger.error(e);
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
}
