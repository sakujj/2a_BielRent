package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.Report;
import by.fpmibsu.bielrent.entity.Role;
import by.fpmibsu.bielrent.entity.User;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final String SQL_INSERT_USER = "INSERT INTO [dbo].[User](email, password, name, rating, role) VALUES(?, ?, ?, ?, ?)";
    private final String SQL_SELECT_ALL_USERS = "SELECT * FROM [dbo].[User]";
    private final String SQL_SELECT_USER_BY_ID = "SELECT * FROM [dbo].[User] WHERE id = ?";
    private final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM [dbo].[User] WHERE email = ?";
    private final String SQL_UPDATE_USER = "UPDATE [dbo].[User] " +
            "SET email = ?, password = ?, name = ?, rating = ?, role = ? WHERE id = ?";
    private final String SQL_DELETE_USER_BY_ID = "DELETE FROM [dbo].[User] WHERE id = ?";
    private final String SQL_SELECT_REPORTS_BY_USER_ID = "SELECT * FROM dbo.[Report] WHERE userId = ?";

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
        try (PreparedStatement statement
                     = conn.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            long id = -1;
            if (record.getEmail().length() > 50
                    || record.getPassword().length() > 50
                    || record.getName().length() > 50
                    || record.getRating().toPlainString().length() != 3
                    || record.getRole() == null) {
                return id;
            }
            if (selectByEmail(record.getEmail()) != null || select(record.getId()) != null) {
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

    @Override
    public List<User> selectAll() throws DaoException {
        try (Statement statement = conn.createStatement()) {
            List<User> users = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String name = resultSet.getString("name");
                BigDecimal rating = resultSet.getBigDecimal("rating");
                String stringRole = resultSet.getString("role");
                Role role = Role.valueOf(stringRole);
                User user = new User();
                user.setId(id);
                user.setEmail(email);
                user.setPassword(password);
                user.setName(name);
                user.setRating(rating);
                user.setRole(role);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    /**
     You should pass a User instance with User.id field already set.
     The method initializes User.reports based on User.id and links every report in User.reports
     to the User instance.
     */
    private void setReports(User user) throws DaoException {
        if (user.getId() <= 0) {
            throw new DaoException("User cant have id <= 0");
        }
        long id = user.getId();
        List<Report> reports = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_REPORTS_BY_USER_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Report report = new Report();
                report.setId(resultSet.getLong("id"));
                report.setDescription(resultSet.getString("description"));
                report.setUser(user);
                reports.add(report);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        user.setReports(reports);
    }

    @Override
    public User select(long id) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            User user = null;
            statement.setLong(1, id);
            ReportDao rd = new ReportDaoImpl(conn);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(id);
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setRating(resultSet.getBigDecimal("rating"));
                String stringRole = resultSet.getString("role");
                Role role = Role.valueOf(stringRole);
                user.setRole(role);
                this.setReports(user);
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(User record) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_USER)) {
            if (record.getEmail().length() > 50
                    || record.getPassword().length() > 50
                    || record.getName().codePointCount(0, record.getName().length()) > 50
                    || record.getRating().toPlainString().length() != 3
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

    @Override
    public boolean delete(User record) throws DaoException {
        return delete(record.getId());
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_DELETE_USER_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User selectByEmail(String email) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_USER_BY_EMAIL)) {
            User user = null;
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setEmail(email);
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setRating(resultSet.getBigDecimal("rating"));
                String stringRole = resultSet.getString("role");
                Role role = Role.valueOf(stringRole);
                user.setRole(role);
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
