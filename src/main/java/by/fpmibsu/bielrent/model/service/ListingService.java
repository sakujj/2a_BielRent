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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private Logger logger = LogManager.getLogger(AddressService.class);

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

    public int getListingCount() {
        return listingCount.get();
    }

}
