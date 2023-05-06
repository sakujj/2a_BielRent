package by.fpmibsu.bielrent.service;

import by.fpmibsu.bielrent.dao.UserDao;
import by.fpmibsu.bielrent.dao.UserDaoImpl;
import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.dto.InsertUserDto;
import by.fpmibsu.bielrent.dto.validator.InsertUserValidator;
import by.fpmibsu.bielrent.dto.validator.ValidationException;
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

    /**
     * Validates InsertUserDto and inserts in database if it is valid.
     * @param insertUserDto
     * @return inserted entity id
     * @throws ValidationException if @param is not valid.
     * @throws DaoException if database management system can not perform insertion.
     */
    public Long validateAndInsertIfValid(InsertUserDto insertUserDto) throws DaoException, ValidationException {
        ValidationResult vr = insertUserValidator.validate(insertUserDto);
        if (!vr.isValid()) {
            throw new ValidationException(vr.getErrors());
        }
        User userEntity = insertUserMapperToEntity.mapFrom(insertUserDto);
        Long id = Long.valueOf(userDao.insert(userEntity));

        return id;
    }

    /**
     * Checks if user by specified email is already in database.
     * @param email
     * @return true if email is reserved by any user.
     * @throws DaoException if database management system can not perform selection.
     */
    public boolean isUserWithEmailInDB(String email) throws DaoException {
        return userDao.selectByEmail(email) != null;
    }
}
