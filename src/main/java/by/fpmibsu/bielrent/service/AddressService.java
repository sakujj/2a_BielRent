package by.fpmibsu.bielrent.service;

import by.fpmibsu.bielrent.dao.AddressDao;
import by.fpmibsu.bielrent.dao.AddressDaoImpl;
import by.fpmibsu.bielrent.dao.ListingDao;
import by.fpmibsu.bielrent.dao.ListingDaoImpl;
import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.dto.AddressDto;
import by.fpmibsu.bielrent.dto.ListingDto;
import by.fpmibsu.bielrent.dto.validator.AddressValidator;
import by.fpmibsu.bielrent.dto.validator.ListingValidator;
import by.fpmibsu.bielrent.dto.validator.ValidationException;
import by.fpmibsu.bielrent.dto.validator.ValidationResult;
import by.fpmibsu.bielrent.entity.Address;
import by.fpmibsu.bielrent.entity.Listing;
import by.fpmibsu.bielrent.mapper.toentity.AddressMapperToEntity;
import by.fpmibsu.bielrent.mapper.toentity.ListingMapperToEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressService {
    private static final AddressService INSTANCE = new AddressService();
    public static AddressService getInstance() {
        return INSTANCE;
    }
    private static final AddressMapperToEntity addressMapperToEntity = AddressMapperToEntity.getInstance();
    private static final AddressDao addressDao = AddressDaoImpl.getInstance();
    private static final AddressValidator addressValidator = AddressValidator.getInstance();
    public Long validateAndInsertIfValid(AddressDto addressDto) throws DaoException, ValidationException {
        ValidationResult vr = addressValidator.validate(addressDto);
        if (!vr.isValid()) {
            throw new ValidationException(vr.getErrors());
        }
        Address addressEntity = addressMapperToEntity.mapFrom(addressDto);
        Long id = Long.valueOf(addressDao.insert(addressEntity));

        return id;
    }

}
