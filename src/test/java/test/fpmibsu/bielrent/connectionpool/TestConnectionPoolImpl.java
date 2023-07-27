package test.fpmibsu.bielrent.connectionpool;

import by.fpmibsu.bielrent.model.connectionpool.ConnectionPool;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import test.fpmibsu.bielrent.utility.TestPropertiesUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;


public class TestConnectionPoolImpl implements ConnectionPool {
    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DRIVER_KEY = "db.driver";
    private static final int MAX_POOL_SIZE = 10;
    private static final int MIN_IDLE = 10;
    private static final int MAX_LIFETIME = 1_800_000;
    private static final int IDLE_TIMEOUT = 600_000;

    private static final TestConnectionPoolImpl INSTANCE = new TestConnectionPoolImpl();
    private static final HikariDataSource dataSource = getDataSource();

    private TestConnectionPoolImpl() {
    }

    public static TestConnectionPoolImpl getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() throws DaoException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    @SneakyThrows
    private static HikariDataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        Class.forName(TestPropertiesUtil.get(DRIVER_KEY));
        dataSource.setJdbcUrl(TestPropertiesUtil.get(URL_KEY));
        dataSource.setUsername(TestPropertiesUtil.get(USER_KEY));
        dataSource.setPassword(TestPropertiesUtil.get(PASSWORD_KEY));
        dataSource.setMaximumPoolSize(MAX_POOL_SIZE);
        dataSource.setMinimumIdle(MIN_IDLE);
        dataSource.setMaxLifetime(MAX_LIFETIME);
        dataSource.setIdleTimeout(IDLE_TIMEOUT);

        return dataSource;
    }
}
