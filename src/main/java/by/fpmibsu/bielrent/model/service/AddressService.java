package by.fpmibsu.bielrent.model.service;

import by.fpmibsu.bielrent.model.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.model.dao.AddressDaoImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.req.AddressReq;
import by.fpmibsu.bielrent.model.dtovalidator.AddressValidator;
import by.fpmibsu.bielrent.model.dtovalidator.ValidationException;
import by.fpmibsu.bielrent.model.dtovalidator.ValidationResult;
import by.fpmibsu.bielrent.model.entity.Address;
import by.fpmibsu.bielrent.model.dtomapper.AddressMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressService {
    private static final AddressService INSTANCE = new AddressService();
    public static AddressService getInstance() {
        return INSTANCE;
    }
    private static final AddressMapper addressMapper = AddressMapper.getInstance();
    private static final AddressDaoImpl addressDao = AddressDaoImpl.getInstance();
    private static final AddressValidator addressValidator = AddressValidator.getInstance();
    private static final ConnectionPoolImpl connPool = ConnectionPoolImpl.getInstance();
    private Logger logger = Logger.getLogger(AddressService.class);

    public Long insertIfValid (AddressReq addressReq) throws DaoException, ValidationException {
        try (var conn = connPool.getConnection()) {
            return insertIfValid(addressReq, conn);
        } catch (SQLException e) {
            logger.error("address service ins if valid error");
            throw new DaoException(e);
        }
    }

    public Long insertIfValid(AddressReq addressReq, Connection conn) throws DaoException, ValidationException {
        ValidationResult vr = addressValidator.validate(addressReq);
        if (!vr.isValid()) {
            throw new ValidationException(vr.getErrors());
        }
        Address address = addressMapper.toEntity(addressReq);

        return addressDao.insert(address, conn);
    }

}
