package by.fpmibsu.bielrent.model.dao;

import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends Entity> {
    long insert(T record) throws DaoException;
    List<T> selectAll() throws DaoException;
    Optional<T> select(long id) throws DaoException;
    boolean update(T record) throws DaoException;
    boolean delete(T record) throws DaoException;
    boolean delete(long id) throws DaoException;
}
