package by.fpmibsu.bielrent.model.service;

import by.fpmibsu.bielrent.model.dao.*;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.controller.dto.ListingDto;
import by.fpmibsu.bielrent.controller.dto.validator.ListingValidator;
import by.fpmibsu.bielrent.controller.dto.validator.ValidationException;
import by.fpmibsu.bielrent.controller.dto.validator.ValidationResult;
import by.fpmibsu.bielrent.model.entity.*;
import by.fpmibsu.bielrent.model.mapper.toentity.ListingMapperToEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListingService {

    private static final ListingService INSTANCE = new ListingService();
    public static ListingService getInstance() {
        return INSTANCE;
    }
    private static final ListingMapperToEntity listingMapperToEntity = ListingMapperToEntity.getInstance();
    private static final ListingDaoImpl listingDao  = ListingDaoImpl.getInstance();
    private static final FilterDaoImpl filterDao  = FilterDaoImpl.getInstance();
    private static final AddressDaoImpl addressDao  = AddressDaoImpl.getInstance();
    private static final UserDaoImpl userDao  = UserDaoImpl.getInstance();
    private static final PhotoDaoImpl photoDao  = PhotoDaoImpl.getInstance();
    private static final AtomicInteger listingCount = new AtomicInteger(listingDao.countListings());;



    private static final ListingValidator listingValidator = ListingValidator.getInstance();

    public Long validateAndInsertIfValid(ListingDto listingDto) throws DaoException, ValidationException {
        ValidationResult vr = listingValidator.validate(listingDto);
        if (!vr.isValid()) {
            throw new ValidationException(vr.getErrors());
        }
        Listing listingEntity = listingMapperToEntity.mapFrom(listingDto);
        Long id = Long.valueOf(listingDao.insert(listingEntity));
        listingCount.incrementAndGet();

        return id;
    }

    public List<ListingORM> getRowsByQueryWithOffset(ListingQuery query, int rowsNumber, int offset) throws DaoException {
        return listingDao.selectRowsByListingDataWithOffset(query, rowsNumber, offset);
    }

    @SneakyThrows
    public Optional<ListingORM> getListingById(long id) {
        Listing listing = listingDao.select(id).get();
        Filter filter = filterDao.select(listing.getFilterId()).get();
        Address address = addressDao.select(listing.getAddressId()).get();
        User user = userDao.select(listing.getUserId()).get();
        List<Photo> photos = photoDao.selectAllByListingId(listing.getId());
        ListingORM listingORM = ListingORM.builder()
                .name(listing.getName())
                .propertyTypeName(listing.getPropertyTypeName())
                .id(listing.getId())
                .description(listing.getDescription())
                .filter(filter)
                .address(address)
                .user(user)
                .photos(photos)
                .build();

        return Optional.ofNullable(listingORM);
    }

    public int getListingCount() {
        return listingCount.get();
    }
}
