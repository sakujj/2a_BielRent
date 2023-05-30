package by.fpmibsu.bielrent.model.service;

import by.fpmibsu.bielrent.model.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.model.dao.*;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.req.ListingReq;
import by.fpmibsu.bielrent.model.dto.req.ListingOrmReq;
import by.fpmibsu.bielrent.model.dto.resp.ListingOrmResp;
import by.fpmibsu.bielrent.model.dtovalidator.ListingValidator;
import by.fpmibsu.bielrent.model.dtovalidator.ValidationException;
import by.fpmibsu.bielrent.model.dtovalidator.ValidationResult;
import by.fpmibsu.bielrent.model.dtomapper.ListingOrmMapper;
import by.fpmibsu.bielrent.model.entity.*;
import by.fpmibsu.bielrent.model.dtomapper.ListingMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListingService {

    private static final ListingService INSTANCE = new ListingService();

    public static ListingService getInstance() {
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


    private static final ListingValidator listingValidator = ListingValidator.getInstance();
    private Logger logger = Logger.getLogger(AddressService.class);

    public Long insertIfValid(ListingReq listingReq, Long addressId, Long userId) throws DaoException, ValidationException {
        try (var conn = connPool.getConnection()) {
            return insertIfValid(listingReq, addressId, userId, conn);
        } catch (SQLException e) {
            logger.error("insert if valid listing service error\n");
            throw new DaoException(e);
        }
    }

    public Long insertIfValid(ListingReq listingReq, Long addressId, Long userId, Connection conn) throws DaoException, ValidationException {
        ValidationResult vr = listingValidator.validate(listingReq);
        if (!vr.isValid()) {
            throw new ValidationException(vr.getErrors());
        }
        Listing listing = listingMapper.toEntity(listingReq, addressId, userId);
        Long id = listingDao.insert(listing, conn);

        listingCount.incrementAndGet();
        return id;

    }

    public Long insertIfValid(ListingOrmReq listingOrmReq) throws DaoException, ValidationException, IOException {
        try (var conn = connPool.getConnection()) {
            return insertIfValid(listingOrmReq, conn);
        } catch (SQLException e) {
            logger.error("insert if valid listingORM service error\n");
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
            Long listingId = this.insertIfValid(listingReq, addressId, userId, conn);

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

    public List<ListingOrmResp> queryListings(ListingQuery query, int listingCount, int offset) throws DaoException {
        return listingDao.queryListings(query, listingCount, offset)
                .stream()
                .map(listingOrmMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<ListingOrmResp> getListingById(long id) throws DaoException {
        try (var conn = connPool.getConnection()) {
            return getListingById(id, conn);
        } catch (SQLException e) {
            logger.error("get listing by id service error\n");
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
            logger.error("get listing by id service error\n");
            try {

                conn.rollback();
            } catch (SQLException ex) {
                logger.error("connection rollback failed\n");
                throw new DaoException(ex);
            }
            throw new DaoException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("set autocommit failed\n");
                throw new DaoException(e);
            }
        }

    }

    public int getListingCount() {
        return listingCount.get();
    }

}
