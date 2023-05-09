package by.fpmibsu.bielrent.service;

import by.fpmibsu.bielrent.dao.FilterDao;
import by.fpmibsu.bielrent.dao.FilterDaoImpl;
import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.dto.FilterDto;
import by.fpmibsu.bielrent.dto.InsertUserDto;
import by.fpmibsu.bielrent.dto.validator.FilterValidator;
import by.fpmibsu.bielrent.dto.validator.ValidationException;
import by.fpmibsu.bielrent.dto.validator.ValidationResult;
import by.fpmibsu.bielrent.entity.Filter;
import by.fpmibsu.bielrent.entity.User;
import by.fpmibsu.bielrent.mapper.toentity.FilterMapperToEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterService {
    private static final FilterService INSTANCE = new FilterService();
    public static FilterService getInstance() {
        return INSTANCE;
    }
    private static final FilterDao filterDao = FilterDaoImpl.getInstance();
    private static final FilterMapperToEntity filterMapperToEntity = FilterMapperToEntity.getInstance();
    private static final FilterValidator filterValidator = FilterValidator.getInstance();
    public Long validateAndInsertIfValid(FilterDto filterDto) throws DaoException, ValidationException {
        ValidationResult vr = filterValidator.validate(filterDto);
        if (!vr.isValid()) {
            throw new ValidationException(vr.getErrors());
        }
        Filter filterEntity = filterMapperToEntity.mapFrom(filterDto);
        Long id = Long.valueOf(filterDao.insert(filterEntity));

        return id;
    }

}
