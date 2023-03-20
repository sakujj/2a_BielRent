package by.fpmibsu.bielrent.dbservice;

import by.fpmibsu.bielrent.dao.DaoException;
import by.fpmibsu.bielrent.entity.User;

import java.util.List;

public interface DbService {
    long insertUser(User user) throws DaoException;
    boolean deleteUser(long id) throws DaoException;
    boolean deleteUser(User user) throws DaoException;
    User selectUser(long id) throws DaoException;
    User selectUser(String email) throws DaoException;
    List<User> selectAllUsers() throws DaoException;
    boolean updateUser(User user) throws DaoException;
}
