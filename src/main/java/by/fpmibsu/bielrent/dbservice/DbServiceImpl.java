package by.fpmibsu.bielrent.dbservice;

import by.fpmibsu.bielrent.connectionpool.ConnectionPool;
import by.fpmibsu.bielrent.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.dao.DaoException;
import by.fpmibsu.bielrent.dao.UserDao;
import by.fpmibsu.bielrent.dao.UserDaoImpl;
import by.fpmibsu.bielrent.dbservice.DbService;
import by.fpmibsu.bielrent.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DbServiceImpl implements DbService {
    private DbServiceImpl() {
    }

    private ConnectionPool connectionPool;
    private static DbServiceImpl dbService = null;

    public static DbService getInstance() {
        if (dbService == null) {
            dbService = new DbServiceImpl();
            dbService.connectionPool = ConnectionPoolImpl.getInstance();
        }
        return dbService;
    }



    @Override
    public long insertUser(User user) throws DaoException {
        Connection conn = connectionPool.getConnection();
        UserDao userDao = new UserDaoImpl(conn);
        long id = userDao.insert(user);
        try {
            conn.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return id;
    }

    @Override
    public boolean deleteUser(long id) throws DaoException {
        Connection conn = connectionPool.getConnection();
        UserDao userDao = new UserDaoImpl(conn);
        boolean isDeleted = userDao.delete(id);
        try {
            conn.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return isDeleted;
    }

    @Override
    public boolean deleteUser(User user) throws DaoException {
        Connection conn = connectionPool.getConnection();
        UserDao userDao = new UserDaoImpl(conn);
        boolean isDeleted = userDao.delete(user);
        try {
            conn.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return isDeleted;
    }

    @Override
    public User selectUser(long id) throws DaoException {
        Connection conn = connectionPool.getConnection();
        UserDao userDao = new UserDaoImpl(conn);
        User user = userDao.select(id);
        try {
            conn.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }

    @Override
    public User selectUser(String email) throws DaoException {
        Connection conn = connectionPool.getConnection();
        UserDao userDao = new UserDaoImpl(conn);
        User user = userDao.select(email);
        try {
            conn.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }

    @Override
    public List<User> selectAllUsers() throws DaoException {
        Connection conn = connectionPool.getConnection();
        UserDao userDao = new UserDaoImpl(conn);
        List<User> users = userDao.selectAll();
        try {
            conn.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return users;
    }

    @Override
    public boolean updateUser(User user) throws DaoException {
        Connection conn = connectionPool.getConnection();
        UserDao userDao = new UserDaoImpl(conn);
        boolean isUpdated = userDao.update(user);
        try {
            conn.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return isUpdated;
    }
}
