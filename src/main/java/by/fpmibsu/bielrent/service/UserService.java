package by.fpmibsu.bielrent.service;

import by.fpmibsu.bielrent.dao.UserDao;
import by.fpmibsu.bielrent.dao.UserDaoImpl;
import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.dto.InsertUserDto;
import by.fpmibsu.bielrent.dto.validator.InsertUserValidator;
import by.fpmibsu.bielrent.dto.validator.ValidationResult;
import by.fpmibsu.bielrent.entity.User;
import by.fpmibsu.bielrent.mapper.toentity.InsertUserMapperToEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {
    private static final InsertUserValidator insertUserValidator = InsertUserValidator.getInstance();
    private static final InsertUserMapperToEntity insertUserMapperToEntity = InsertUserMapperToEntity.getInstance();
    private static final UserDao userDao = UserDaoImpl.getInstance();



    private static final UserService INSTANCE = new UserService();
    public static UserService getInstance() {
        return INSTANCE;
    }

    public long insert(InsertUserDto userDto) throws DaoException {
        long id = -1;

        ValidationResult vr = insertUserValidator.validate(userDto);
        if (!vr.isValid()) {
            return id;
        }
        User userEntity = insertUserMapperToEntity.mapFrom(userDto);
        userDao.insert(userEntity);

        return id;
    }
}
