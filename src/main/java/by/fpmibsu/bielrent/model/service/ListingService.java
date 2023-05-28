package by.fpmibsu.bielrent.model.service;

import by.fpmibsu.bielrent.model.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.model.dao.*;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.ListingDto;
import by.fpmibsu.bielrent.model.dto.ListingOrmDto;
import by.fpmibsu.bielrent.model.dto.validator.ListingValidator;
import by.fpmibsu.bielrent.model.dto.validator.ValidationException;
import by.fpmibsu.bielrent.model.dto.validator.ValidationResult;
import by.fpmibsu.bielrent.model.dtomapper.todto.ListingOrmMapperToDto;
import by.fpmibsu.bielrent.model.entity.*;
import by.fpmibsu.bielrent.model.dtomapper.toentity.ListingMapperToEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListingService {

    private static final ListingService INSTANCE = new ListingService();
    private static final ConnectionPoolImpl connPool = ConnectionPoolImpl.getInstance();

    public static ListingService getInstance() {
        return INSTANCE;
    }

    private static final ListingMapperToEntity listingMapperToEntity = ListingMapperToEntity.getInstance();
    private static final ListingOrmMapperToDto listingOrmMapperToDto = ListingOrmMapperToDto.getInstance();
    private static final ListingDaoImpl listingDao = ListingDaoImpl.getInstance();
    private static final FilterDaoImpl filterDao = FilterDaoImpl.getInstance();
    private static final AddressDaoImpl addressDao = AddressDaoImpl.getInstance();
    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static final PhotoDaoImpl photoDao = PhotoDaoImpl.getInstance();
    private static final AtomicInteger listingCount = new AtomicInteger(listingDao.countListings());
    ;


    private static final ListingValidator listingValidator = ListingValidator.getInstance();

    public Long insertIfValid(ListingDto listingDto) throws DaoException, ValidationException {
        ValidationResult vr = listingValidator.validate(listingDto);
        if (!vr.isValid()) {
            throw new ValidationException(vr.getErrors());
        }
        Listing listingEntity = listingMapperToEntity.mapFrom(listingDto);
        Long id = Long.valueOf(listingDao.insert(listingEntity));
        listingCount.incrementAndGet();

        return id;
    }

    public List<ListingOrmDto> queryListings(ListingQuery query, int listingCount, int offset) throws DaoException {
        return listingDao.queryListings(query, listingCount, offset)
                .stream()
                .map(listingOrmMapperToDto::mapFrom)
                .collect(Collectors.toList());
    }

    public Optional<ListingOrmDto> getListingById(long id) throws DaoException {
        try (var conn = connPool.getConnection()) {
            return getListingById(id, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Optional<ListingOrmDto> getListingById(long id, Connection conn) throws DaoException {
        try {
            conn.setAutoCommit(false);

            Listing listing = listingDao.select(id, conn).get();
            Filter filter = filterDao.select(listing.getFilterId(), conn).get();
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

            var listingOrmDto = listingOrmMapperToDto.mapFrom(listingOrm);

            conn.commit();
            return Optional.ofNullable(listingOrmDto);
        } catch (Exception e) {
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

    public int getListingCount() {
        return listingCount.get();
    }
}
