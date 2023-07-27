package by.fpmibsu.bielrent.model.service;

import by.fpmibsu.bielrent.model.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.model.dto.resp.UserResp;
import by.fpmibsu.bielrent.model.dao.UserDaoImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.req.UserReq;
import by.fpmibsu.bielrent.model.dtovalidator.UserValidator;
import by.fpmibsu.bielrent.model.dtovalidator.ValidationException;
import by.fpmibsu.bielrent.model.dtovalidator.ValidationResult;
import by.fpmibsu.bielrent.model.entity.User;
import by.fpmibsu.bielrent.model.dtomapper.UserMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {
    private static final UserValidator insertUserValidator = UserValidator.getInstance();
    private static final UserMapper insertUserMapperToEntity = UserMapper.getInstance();
    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static final UserMapper userMapper = UserMapper.getInstance();
    private static final ConnectionPoolImpl connPool = ConnectionPoolImpl.getInstance();


    private static final UserService INSTANCE = new UserService();

    public static UserService getInstance() {
        return INSTANCE;
    }
    Logger logger = LogManager.getLogger(UserService.class);


    public Long insertIfValid(UserReq userReq) throws DaoException, ValidationException {
        try (var conn = connPool.getConnection()) {
            return insertIfValid(userReq, conn);
        } catch (SQLException e) {
            logger.error("insert if valid in user service failed\n");
            throw new DaoException(e);
        }
    }

    public Long insertIfValid(UserReq userReq, Connection conn) throws DaoException, ValidationException {
        ValidationResult vr = insertUserValidator.validate(userReq);
        if (!vr.isValid()) {
            throw new ValidationException(vr.getErrors());
        }
        User user = insertUserMapperToEntity.toEntity(userReq);
        Long id = userDao.insert(user, conn);

        return id;
    }


    public boolean isUserWithEmailInDB(String email) throws DaoException {
        return userDao.selectByEmail(email).isPresent();
    }

    public Optional<User> getUser(long id) throws DaoException {
        try (var conn = connPool.getConnection()) {
            return userDao.select(id);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<User> getUser(String email, String password) throws DaoException {
        try (var conn = connPool.getConnection()) {
            return userDao.selectByEmailAndPassword(email, password, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public Optional<User> getUser(String email, String password, Connection conn) throws DaoException {
        return userDao.selectByEmailAndPassword(email, password, conn);
    }

    @SneakyThrows
    public List<Optional<UserResp>> getAllUsers() {
        return userDao.selectAll()
                .stream()
                .map(userMapper::fromEntity)
                .map(Optional::of)
                .collect(Collectors.toList());
    }
}
