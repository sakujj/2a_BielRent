package by.fpmibsu.bielrent.connectionpool;

import java.sql.Connection;

public interface ConnectionPool {
    public Connection getConnection();
}
