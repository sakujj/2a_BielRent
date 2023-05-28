package by.fpmibsu.bielrent.model.service;

import by.fpmibsu.bielrent.model.dto.UserDto;
import by.fpmibsu.bielrent.model.dao.UserDao;
import by.fpmibsu.bielrent.model.dao.UserDaoImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.InsertUserDto;
import by.fpmibsu.bielrent.model.dto.validator.InsertUserValidator;
import by.fpmibsu.bielrent.model.dto.validator.ValidationException;
import by.fpmibsu.bielrent.model.dto.validator.ValidationResult;
import by.fpmibsu.bielrent.model.entity.User;
import by.fpmibsu.bielrent.model.dtomapper.todto.UserMapperToDto;
import by.fpmibsu.bielrent.model.dtomapper.toentity.InsertUserMapperToEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {
    private static final InsertUserValidator insertUserValidator = InsertUserValidator.getInstance();
    private static final InsertUserMapperToEntity insertUserMapperToEntity = InsertUserMapperToEntity.getInstance();
    private static final UserDao userDao = UserDaoImpl.getInstance();
    private static final UserMapperToDto userMapperToDto = UserMapperToDto.getInstance();


    private static final UserService INSTANCE = new UserService();

    public static UserService getInstance() {
        return INSTANCE;
    }

    /**
     * Validates InsertUserDto and inserts in database if it is valid.
     *
     * @param insertUserDto
     * @return inserted entity id
     * @throws ValidationException if @param is not valid.
     * @throws DaoException        if database management system can not perform insertion.
     */
    public Long insertIfValid(InsertUserDto insertUserDto) throws DaoException, ValidationException {
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
     *
     * @param email
     * @return true if email is reserved by any user.
     * @throws DaoException if database management system can not perform selection.
     */
    public boolean isUserWithEmailInDB(String email) throws DaoException {
        return !userDao.selectByEmail(email).isEmpty();
    }

    public Optional<User> login(String email, String password) {
        var sel = userDao.selectByEmailAndPassword(email, password);
        if (sel.isEmpty()) {
            return Optional.ofNullable(null);
        }
        return userDao.selectByEmailAndPassword(email, password);
    }

    @SneakyThrows
    public List<Optional<UserDto>> getAllUsers() {
        return userDao.selectAll()
                .stream()
                .map(userMapperToDto::mapFrom)
                .map(Optional::of)
                .collect(Collectors.toList());
    }
}
