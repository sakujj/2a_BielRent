package by.fpmibsu.bielrent.model.connectionpool;

import by.fpmibsu.bielrent.model.dao.exception.DaoException;

import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection() throws DaoException;
    void close();
}
