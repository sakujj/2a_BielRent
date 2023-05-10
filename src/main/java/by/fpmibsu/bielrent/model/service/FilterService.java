package by.fpmibsu.bielrent.model.service;

import by.fpmibsu.bielrent.model.dao.FilterDao;
import by.fpmibsu.bielrent.model.dao.FilterDaoImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.controller.dto.FilterDto;
import by.fpmibsu.bielrent.controller.dto.validator.FilterValidator;
import by.fpmibsu.bielrent.controller.dto.validator.ValidationException;
import by.fpmibsu.bielrent.controller.dto.validator.ValidationResult;
import by.fpmibsu.bielrent.model.entity.Filter;
import by.fpmibsu.bielrent.model.mapper.toentity.FilterMapperToEntity;
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
