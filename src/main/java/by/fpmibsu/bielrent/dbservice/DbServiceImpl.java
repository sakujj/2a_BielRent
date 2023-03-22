package by.fpmibsu.bielrent.dbservice;

import by.fpmibsu.bielrent.connectionpool.ConnectionPool;
import by.fpmibsu.bielrent.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.dao.*;
import by.fpmibsu.bielrent.dbservice.DbService;
import by.fpmibsu.bielrent.entity.Adress;
import by.fpmibsu.bielrent.entity.Photo;
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
//////////////////////////////////////////////////////////////////////////////////
    @Override
    public long insertAdress(Adress adress) throws DaoException {
        long id = -1;
        try(Connection conn = connectionPool.getConnection()) {
            AdressDao adressDao = new AdressDaoImpl(conn);
            id = adressDao.insert(adress);
        }
        catch (SQLException e){
            throw  new DaoException();
        }
        return id;
    }

    @Override
    public Adress selectAdress(long id) throws DaoException {
        Adress adress = null;
        try(Connection conn = connectionPool.getConnection()) {
            AdressDao adressDao = new AdressDaoImpl(conn);
            adress = adressDao.select(id);
        }
        catch (SQLException e){
            throw  new DaoException();
        }
        return adress;
    }

    @Override
    public List<Adress> selectAllAdresses() throws DaoException {
        List<Adress> adress = null;
        try(Connection conn = connectionPool.getConnection()) {
            AdressDao adressDao = new AdressDaoImpl(conn);
            adress = adressDao.selectAll();
        }
        catch (SQLException e){
            throw  new DaoException();
        }
        return adress;
    }

    @Override
    public Adress selectAdressByStreet(String street) throws DaoException {
        return null;
    }

    @Override
    public Adress selectAdressByCity(String city) throws DaoException {
        return null;
    }

    @Override
    public Adress selectAdressByArea(String area) throws DaoException {
        return null;
    }

    @Override
    public Adress selectAdressByRegion(String region) throws DaoException {
        return null;
    }

    @Override
    public boolean deleteAdress(long id) throws DaoException {
        try(Connection connection = connectionPool.getConnection()){
            AdressDao adressDao = new AdressDaoImpl(connection);
            return adressDao.delete(id);
        }
        catch (SQLException e){
            throw new DaoException(e);
        }
    }

    @Override
    public boolean deleteAllAdresses() throws DaoException {
        try(Connection connection = connectionPool.getConnection()){
            AdressDao adressDao = new AdressDaoImpl(connection);
            return adressDao.deleteAllRecords();
        }
        catch (SQLException e){
            throw new DaoException(e);
        }
    }
}
