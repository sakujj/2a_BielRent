package by.fpmibsu.bielrent.model.dao;

import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.entity.User;

import java.util.Optional;

public interface UserDao extends Dao<User> {
    Optional<User> selectByEmail(String email) throws DaoException;
    Optional<User> selectByEmailAndPassword(String email, String id) ;
}
