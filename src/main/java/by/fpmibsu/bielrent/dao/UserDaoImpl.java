package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final String SQL_INSERT_USER = "INSERT INTO [dbo].[User](email, password, name) VALUES(?, ?, ?)";
    private final String SQL_SELECT_ALL_USERS = "SELECT * FROM [dbo].[User]";
    private final String SQL_SELECT_USER_BY_ID = "SELECT * FROM [dbo].[User] WHERE id = ?";
    private final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM [dbo].[User] WHERE email = ?";
    private final String SQL_UPDATE_USER = "UPDATE [dbo].[User] " +
            "SET email = ?, password = ?, name = ? WHERE id = ?";
    private final String SQL_DELETE_USER_BY_ID = "DELETE FROM [dbo].[User] WHERE id = ?";

    private Connection conn;

    public UserDaoImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * Tries to insert user and return corresponding ID.
     * Returns -1 in case of failure.
     */
    @Override
    public long insert(User record) throws DaoException {
        try {
            long id = -1;
            if (select(record.getEmail()) != null || select(record.getId()) != null) {
                return id;
            }
            PreparedStatement statement = conn.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, record.getEmail());
            statement.setString(2, record.getPassword());
            statement.setString(3, record.getName());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                statement.close();
            }

            return id;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> selectAll() throws DaoException {
        try {
            List<User> users = new ArrayList<>();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String name = resultSet.getString("name");
                User user = new User();
                user.setId(id);
                user.setEmail(email);
                user.setPassword(password);
                user.setName(name);
                users.add(user);
            }
            statement.close();

            return users;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public User select(long id) throws DaoException {
        try {
            User user = null;
            PreparedStatement statement = conn.prepareStatement(SQL_SELECT_USER_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(id);
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
            }
            statement.close();

            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(User record) throws DaoException {
        try {
            PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_USER);
            statement.setString(1, record.getEmail());
            statement.setString(2, record.getPassword());
            statement.setString(3, record.getName());
            statement.setLong(4, record.getId());
            boolean isUpdated = statement.executeUpdate() > 0;
            statement.close();

            return isUpdated;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(User record) throws DaoException {
        return delete(record.getId());
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try {
            PreparedStatement statement = conn.prepareStatement(SQL_DELETE_USER_BY_ID);
            statement.setLong(1, id);
            boolean isDeleted = statement.executeUpdate() > 0;
            statement.close();

            return isDeleted;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User select(String email) throws DaoException {
        try {
            User user = null;
            PreparedStatement statement = conn.prepareStatement(SQL_SELECT_USER_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setEmail(email);
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
            }
            statement.close();

            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }
}
