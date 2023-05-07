package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.entity.User;

import java.util.Optional;

public interface UserDao extends Dao<User> {
    Optional<User> selectByEmail(String email) throws DaoException;
    Optional<User> selectByEmailAndPassword(String email, String id) ;
}
