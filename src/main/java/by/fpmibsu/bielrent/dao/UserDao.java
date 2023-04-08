package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.Role;
import by.fpmibsu.bielrent.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDao extends Dao<User> {
    User selectByEmail(String email) throws DaoException;
}
