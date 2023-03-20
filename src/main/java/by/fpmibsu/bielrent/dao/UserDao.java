package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.User;

public interface UserDao extends Dao<User> {
    User select(String email) throws DaoException;
}
