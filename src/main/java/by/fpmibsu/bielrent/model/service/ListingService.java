package by.fpmibsu.bielrent.model.service;

import by.fpmibsu.bielrent.model.dao.ListingDaoImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.controller.dto.ListingDto;
import by.fpmibsu.bielrent.controller.dto.validator.ListingValidator;
import by.fpmibsu.bielrent.controller.dto.validator.ValidationException;
import by.fpmibsu.bielrent.controller.dto.validator.ValidationResult;
import by.fpmibsu.bielrent.model.entity.Listing;
import by.fpmibsu.bielrent.model.entity.ListingORM;
import by.fpmibsu.bielrent.model.entity.ListingQuery;
import by.fpmibsu.bielrent.model.mapper.toentity.ListingMapperToEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListingService {
    private static final ListingService INSTANCE = new ListingService();
    public static ListingService getInstance() {
        return INSTANCE;
    }
    private static final ListingMapperToEntity listingMapperToEntity = ListingMapperToEntity.getInstance();
    private static final ListingDaoImpl listingDao = ListingDaoImpl.getInstance();
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

    public List<ListingORM> getTopNByQuery(ListingQuery query, int topN, int idToStartFrom) throws DaoException {
        return listingDao.selectTopNByListingData(query, topN, idToStartFrom);
    }
}
