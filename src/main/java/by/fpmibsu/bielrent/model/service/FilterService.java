package by.fpmibsu.bielrent.model.service;

import by.fpmibsu.bielrent.model.connectionpool.ConnectionPool;
import by.fpmibsu.bielrent.model.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.model.dao.FilterDaoImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.req.FilterReq;
import by.fpmibsu.bielrent.model.dtovalidator.FilterValidator;
import by.fpmibsu.bielrent.model.dtovalidator.ValidationException;
import by.fpmibsu.bielrent.model.dtovalidator.ValidationResult;
import by.fpmibsu.bielrent.model.entity.Filter;
import by.fpmibsu.bielrent.model.dtomapper.FilterMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterService {
    private static final FilterService INSTANCE = new FilterService();
    public static FilterService getInstance() {
        return INSTANCE;
    }
    private static final FilterDaoImpl filterDao = FilterDaoImpl.getInstance();
    private static final FilterMapper filterMapper = FilterMapper.getInstance();
    private static final FilterValidator filterValidator = FilterValidator.getInstance();
    private static final ConnectionPool connPool = ConnectionPoolImpl.getInstance();
    private Logger logger = LogManager.getLogger(FilterService.class);

    public Long insertIfValid(FilterReq filterReq, Long listingId) throws DaoException, ValidationException {
        try (var conn = connPool.getConnection()) {
            return insertIfValid(filterReq, listingId, conn);
        } catch (SQLException e) {
            logger.error("insert if valid filter error");
            throw new DaoException(e);
        }
    }

    public Long insertIfValid(FilterReq filterReq, Long listingId, Connection conn) throws DaoException, ValidationException {
        ValidationResult vr = filterValidator.validate(filterReq);
        if (!vr.isValid()) {
            throw new ValidationException(vr.getErrors());
        }

        Filter filter = filterMapper.toEntity(filterReq, listingId);
        Long id = filterDao.insert(filter, conn);

        return id;
    }
}
