package by.fpmibsu.bielrent.service;

import by.fpmibsu.bielrent.dao.ListingDao;
import by.fpmibsu.bielrent.dao.ListingDaoImpl;
import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.dto.FilterDto;
import by.fpmibsu.bielrent.dto.ListingDto;
import by.fpmibsu.bielrent.dto.validator.ListingValidator;
import by.fpmibsu.bielrent.dto.validator.ValidationException;
import by.fpmibsu.bielrent.dto.validator.ValidationResult;
import by.fpmibsu.bielrent.entity.Filter;
import by.fpmibsu.bielrent.entity.Listing;
import by.fpmibsu.bielrent.mapper.toentity.ListingMapperToEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListingService {
    private static final ListingService INSTANCE = new ListingService();
    public static ListingService getInstance() {
        return INSTANCE;
    }
    private static final ListingMapperToEntity listingMapperToEntity = ListingMapperToEntity.getInstance();
    private static final ListingDao listingDao = ListingDaoImpl.getInstance();
    private static final ListingValidator listingValidator = ListingValidator.getInstance();
    public Long validateAndInsertIfValid(ListingDto listingDto) throws DaoException, ValidationException {
        ValidationResult vr = listingValidator.validate(listingDto);
        if (!vr.isValid()) {
            throw new ValidationException(vr.getErrors());
        }
        Listing listingEntity = listingMapperToEntity.mapFrom(listingDto);
        Long id = Long.valueOf(listingDao.insert(listingEntity));

        return id;
    }
}
