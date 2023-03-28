package by.fpmibsu.bielrent.connectionpool;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.microsoft.sqlserver.jdbc.SQLServerXADataSource;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class ConnectionPoolImpl implements ConnectionPool {
    private static final String PATH = "src\\main\\resources\\db.properties";
    private static final String URL_KEY = "db.url";
    private static final String ENCRYPT_KEY = "db.encrypt";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";

    private SQLServerDataSource dataSource = null;

    private static ConnectionPoolImpl connectionPool = null;

    private ConnectionPoolImpl() {
    }

    public static ConnectionPoolImpl getInstance() {
        if (connectionPool == null) {
            connectionPool = createConnectionPoolImpl();
        }
        return connectionPool;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLServerException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static ConnectionPoolImpl createConnectionPoolImpl() {
        Properties props = new Properties();
        SQLServerXADataSource dataSource = new SQLServerXADataSource();
        try (FileReader fr = new FileReader(PATH)) {
            props.load(fr);
            dataSource.setURL(props.getProperty(URL_KEY));
            dataSource.setEncrypt(props.getProperty(ENCRYPT_KEY));
            dataSource.setTrustServerCertificate(true);
            dataSource.setUser(props.getProperty(USER_KEY));
            dataSource.setPassword(props.getProperty(PASSWORD_KEY));
        }  catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

        ConnectionPoolImpl cp = new ConnectionPoolImpl();
        cp.dataSource = dataSource;
        return cp;
    }
}
