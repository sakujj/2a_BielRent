package by.fpmibsu.bielrent.model.service;

import by.fpmibsu.bielrent.model.dao.AddressDao;
import by.fpmibsu.bielrent.model.dao.AddressDaoImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.controller.dto.AddressDto;
import by.fpmibsu.bielrent.controller.dto.validator.AddressValidator;
import by.fpmibsu.bielrent.controller.dto.validator.ValidationException;
import by.fpmibsu.bielrent.controller.dto.validator.ValidationResult;
import by.fpmibsu.bielrent.model.entity.Address;
import by.fpmibsu.bielrent.model.mapper.toentity.AddressMapperToEntity;
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
