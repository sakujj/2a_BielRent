package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.entity.Role;
import by.fpmibsu.bielrent.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    private final String SQL_SELECT_REPORTS_BY_USER_ID
            = "SELECT * " +
            "FROM dbo.[Report] " +
            "WHERE userId = ?";

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
            if (record.getEmail() == null || record.getEmail().length() > 50
                    || record.getPassword() == null || record.getPassword().length() > 50
                    || record.getName() == null || record.getName().codePoints().count() > 50
                    || record.getRating() == null || record.getRating().toPlainString().length() != 3
                    || record.getRole() == null) {
                return id;
            }

            statement.setString(1, record.getEmail());
            statement.setString(2, record.getPassword());
            statement.setString(3, record.getName());
            statement.setBigDecimal(4, record.getRating());
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
                initUser(conn, resultSet, user);
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
                initUser(conn, resultSet, user);
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

    private void initUser(Connection conn, ResultSet resultSet, User user) throws DaoException {
        buildUserPartly(user, resultSet);
        user.setListings(ListingDaoImpl.getInstance().selectWORefsByUserId(user.getId(), conn));
        user.setReports(ReportDaoImpl.getInstance().selectWORefsByUserId(user.getId(), conn));
    }

    protected User selectWORefs(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                user = new User();
                buildUserPartly(user, resultSet);
            }

            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public User selectByEmail(String email, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_USER_BY_EMAIL)) {
            conn.setAutoCommit(false);
            statement.setString(0, email);
            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                user = new User();
                initUser(conn, resultSet, user);
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

    public boolean update(User record, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_USER)) {
            if (record.getEmail() == null || record.getEmail().length() > 50
                    || record.getPassword() == null || record.getPassword().length() > 50
                    || record.getName() == null || record.getName().codePoints().count() > 50
                    || record.getRating() == null || record.getRating().toPlainString().length() != 3
                    || record.getRole() == null) {
                return false;
            }

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
    public User select(long id) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return select(id, conn);
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
    public User selectByEmail(String email) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectByEmail(email, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void buildUserPartly(User user, ResultSet resultSet) throws DaoException {
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
