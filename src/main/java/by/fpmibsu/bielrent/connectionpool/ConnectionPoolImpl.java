package by.fpmibsu.bielrent.connectionpool;

import by.fpmibsu.bielrent.dao.DaoException;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPoolImpl implements ConnectionPool {
    private static final String PATH = "src\\main\\resources\\db.properties";
    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final int MAX_POOL_SIZE = 10;
    private static final int MIN_IDLE = 10;
    private static final int MAX_LIFETIME = 1_800_000;
    private static final int IDLE_TIMEOUT = 600_000;

    private static final ConnectionPoolImpl connectionPool = createPoolAndInitializeDataSource();
    private HikariDataSource dataSource = null;

    private ConnectionPoolImpl() {
    }

    public static ConnectionPoolImpl getInstance() {
        return connectionPool;
    }

    public Connection getConnection() throws DaoException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static ConnectionPoolImpl createPoolAndInitializeDataSource() {
        Properties props = new Properties();
        HikariDataSource dataSource = new HikariDataSource();
        try (FileReader fr = new FileReader(PATH)) {
            props.load(fr);
            dataSource.setJdbcUrl(props.getProperty(URL_KEY));
            dataSource.setUsername(props.getProperty(USER_KEY));
            dataSource.setPassword(props.getProperty(PASSWORD_KEY));
            dataSource.setMaximumPoolSize(MAX_POOL_SIZE);
            dataSource.setMinimumIdle(MIN_IDLE);
            dataSource.setMaxLifetime(MAX_LIFETIME);
            dataSource.setIdleTimeout(IDLE_TIMEOUT);
        }  catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

        ConnectionPoolImpl cp = new ConnectionPoolImpl();
        cp.dataSource = dataSource;
        return cp;
    }
}
