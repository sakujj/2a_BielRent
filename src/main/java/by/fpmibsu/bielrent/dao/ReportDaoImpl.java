package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.Report;
import by.fpmibsu.bielrent.entity.Role;
import by.fpmibsu.bielrent.entity.User;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDaoImpl implements ReportDao {
    private final String SQL_INSERT_REPORT = "INSERT INTO dbo.[Report](userId, description) VALUES(?, ?)";
    private final String SQL_SELECT_ALL_REPORTS = "SELECT * FROM dbo.[Report]";
    private final String SQL_SELECT_REPORT_BY_ID = "SELECT * FROM dbo.[Report] WHERE id = ?";
    private final String SQL_UPDATE_REPORT = "UPDATE dbo.[Report] SET userId = ?, description = ? WHERE id = ?";
    private final String SQL_DELETE_REPORT_BY_ID = "DELETE FROM dbo.[Report] WHERE id = ?";

    private Connection conn;

    public ReportDaoImpl(Connection c) {
        conn = c;
    }

    @Override
    public long insert(Report record) throws DaoException {
        long i = -1;
        try (PreparedStatement preparedStatement
                     = conn.prepareStatement(SQL_INSERT_REPORT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, record.getUser().getId());
            preparedStatement.setString(2, record.getDescription());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                i = rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return i;
    }

    @Override
    public List<Report> selectAll() throws DaoException {

        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL_REPORTS)) {
            List<Report> reports = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long userId = resultSet.getLong("userId");
                String descr = resultSet.getString("description");
                Report report = new Report();
                report.setId(id);
                UserDao userDao = new UserDaoImpl(conn);
                report.setUser(userDao.select(userId));
                report.setDescription(descr);

                reports.add(report);
            }
            return reports;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public Report select(long id) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_REPORT_BY_ID)) {
            statement.setLong(1, id);
            Report report = new Report();
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserDao userDao = new UserDaoImpl(conn);
                report.setUser(userDao.select(resultSet.getLong("userId")));
                report.setDescription(resultSet.getString("description"));
                report.setId(resultSet.getLong("id"));
            }
            return report;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Report record) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_REPORT)) {
            statement.setLong(1, record.getUser().getId());
            statement.setString(2, record.getDescription());
            statement.setLong(3, record.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Report record) throws DaoException {
        return delete(record.getId());
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_DELETE_REPORT_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
