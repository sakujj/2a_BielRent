package by.fpmibsu.bielrent.dbservice;

import by.fpmibsu.bielrent.connectionpool.ConnectionPool;
import by.fpmibsu.bielrent.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.dao.*;
import by.fpmibsu.bielrent.entity.Address;
import by.fpmibsu.bielrent.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class DbServiceImpl implements DbService {
    private DbServiceImpl() {
    }

    private ConnectionPool connectionPool;
    private static volatile DbServiceImpl dbService = null;
    private static final Object mutex = new Object();

    public static DbService getInstance() {
        if (dbService == null) {
            synchronized (mutex) {
                if (dbService == null) {
                    dbService = new DbServiceImpl();
                    dbService.connectionPool = ConnectionPoolImpl.getInstance();
                }
            }
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
        User user = userDao.selectByEmail(email);
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
    public long insertAdress(Address address) throws DaoException {
        long id = -1;
        try(Connection conn = connectionPool.getConnection()) {
            AddressDao addressDao = new AddressDaoImpl(conn);
            id = addressDao.insert(address);
        }
        catch (SQLException e){
            throw  new DaoException();
        }
        return id;
    }

    @Override
    public Address selectAdress(long id) throws DaoException {
        Address address = null;
        try(Connection conn = connectionPool.getConnection()) {
            AddressDao addressDao = new AddressDaoImpl(conn);
            address = addressDao.select(id);
        }
        catch (SQLException e){
            throw  new DaoException();
        }
        return address;
    }

    @Override
    public List<Address> selectAllAdresses() throws DaoException {
        List<Address> addresses = null;
        try(Connection conn = connectionPool.getConnection()) {
            AddressDao addressDao = new AddressDaoImpl(conn);
            addresses = addressDao.selectAll();
        }
        catch (SQLException e){
            throw  new DaoException();
        }
        return addresses;
    }

    @Override
    public Address selectAdressByStreet(String street) throws DaoException {
        return null;
    }

    @Override
    public Address selectAdressByCity(String city) throws DaoException {
        return null;
    }

    @Override
    public Address selectAdressByArea(String area) throws DaoException {
        return null;
    }

    @Override
    public Address selectAdressByRegion(String region) throws DaoException {
        return null;
    }

    @Override
    public boolean deleteAdress(long id) throws DaoException {
        try(Connection connection = connectionPool.getConnection()){
            AddressDao addressDao = new AddressDaoImpl(connection);
            return addressDao.delete(id);
        }
        catch (SQLException e){
            throw new DaoException(e);
        }
    }

    @Override
    public boolean deleteAllAdresses() throws DaoException {
        try(Connection connection = connectionPool.getConnection()){
            AddressDao addressDao = new AddressDaoImpl(connection);
            return addressDao.deleteAllRecords();
        }
        catch (SQLException e){
            throw new DaoException(e);
        }
    }
}
