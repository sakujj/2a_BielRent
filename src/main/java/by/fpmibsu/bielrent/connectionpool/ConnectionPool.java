package by.fpmibsu.bielrent.connectionpool;

import by.fpmibsu.bielrent.dao.exception.DaoException;

import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection() throws DaoException;
    void close();
}
