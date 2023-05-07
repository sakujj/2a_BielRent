package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.entity.Role;
import by.fpmibsu.bielrent.entity.User;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private final String SQL_INSERT_USER
            = "INSERT INTO " +
            "[dbo].[User](" +
            "email, " +
            "password, " +
            "name, " +
            "rating, " +
            "role) " +
            "VALUES(?, ?, ?, ?, ?)";
    private final String SQL_SELECT_ALL_USERS
            = "SELECT * " +
            "FROM [dbo].[User]";
    private final String SQL_SELECT_USER_BY_ID
            = "SELECT * " +
            "FROM [dbo].[User] " +
            "WHERE id = ?";
    private final String SQL_SELECT_USER_BY_EMAIL
            = "SELECT * " +
            "FROM [dbo].[User] " +
            "WHERE email = ?";
    private final String SQL_UPDATE_USER
            = "UPDATE [dbo].[User] " +
            "SET " +
            "email = ?, " +
            "password = ?, " +
            "name = ?, " +
            "rating = ?, " +
            "role = ? " +
            "WHERE id = ?";
    private final String SQL_DELETE_USER_BY_ID
            = "DELETE " +
            "FROM [dbo].[User] " +
            "WHERE id = ?";
    private final String SQL_SELECT_USER_BY_EMAIL_AND_PASSWORD
            = "SELECT *" +
            "FROM [dbo].[User] " +
            "WHERE email = ? AND password = ?";

    private static final UserDaoImpl INSTANCE = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }

    /**
     * Tries to insert user and return corresponding ID.
     * Returns -1 in case of failure.
     */
    public long insert(User record, Connection conn) throws DaoException {
        try (PreparedStatement statement
                     = conn.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            long id = -1;

            statement.setString(1, record.getEmail());
            statement.setString(2, record.getPassword());
            statement.setString(3, record.getName());
            if (record.getRating() != null)
                statement.setBigDecimal(4, record.getRating());
            else
                statement.setNull(4, Types.NULL);
            statement.setString(5, record.getRole().toString());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }
            return id;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public User select(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            conn.setAutoCommit(false);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                user = new User();
                buildUser(user, resultSet);
            }

            conn.commit();
            return user;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
            throw new DaoException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }

    public List<User> selectAll(Connection conn) throws DaoException {
        try (Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);

            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                buildUser(user, resultSet);
                users.add(user);
            }

            conn.commit();
            return users;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
            throw new DaoException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }

    }

    public User selectByEmail(String email, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_USER_BY_EMAIL)) {
            conn.setAutoCommit(false);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                user = new User();
                buildUser(user, resultSet);
            }

            conn.commit();
            return user;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
            throw new DaoException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }
    public Optional<User> selectByEmailAndPassword(String email,String password, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_USER_BY_EMAIL_AND_PASSWORD)) {
            conn.setAutoCommit(false);
            statement.setString(1, email);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                user = new User();
                buildUser(user, resultSet);
            }

            conn.commit();
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
            throw new DaoException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }

    public boolean update(User record, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_USER)) {
            statement.setString(1, record.getEmail());
            statement.setString(2, record.getPassword());
            statement.setString(3, record.getName());
            statement.setBigDecimal(4, record.getRating());
            statement.setString(5, record.getRole().toString());
            statement.setLong(6, record.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_DELETE_USER_BY_ID)) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public long insert(User record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return insert(record, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> selectAll() throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectAll(conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> select(long id) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return Optional.ofNullable(select(id, conn));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(User record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return update(record, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(User record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return delete(record.getId(), conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return delete(id, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> selectByEmail(String email) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return Optional.ofNullable(selectByEmail(email, conn));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @SneakyThrows
    @Override
    public Optional<User> selectByEmailAndPassword(String email, String password) {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectByEmailAndPassword(email, password, conn);
        }
    }

    void buildUser(User user, ResultSet resultSet) throws DaoException {
        try {
            user.setId(resultSet.getLong("id"));
            user.setEmail(resultSet.getString("email"));
            user.setName(resultSet.getString("name"));
            user.setRating(resultSet.getBigDecimal("rating"));
            user.setPassword(resultSet.getString("password"));
            user.setRole(Role.valueOf(resultSet.getString("role")));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
