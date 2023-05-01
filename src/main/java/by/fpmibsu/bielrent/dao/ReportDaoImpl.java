package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.entity.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDaoImpl implements ReportDao {
    private final String SQL_INSERT_REPORT = "INSERT INTO dbo.[Report](userId, description) VALUES(?, ?)";
    private final String SQL_SELECT_ALL_REPORTS = "SELECT * FROM dbo.[Report]";
    private final String SQL_SELECT_REPORT_BY_ID = "SELECT * FROM dbo.[Report] WHERE id = ?";
    private final String SQL_UPDATE_REPORT = "UPDATE dbo.[Report] SET userId = ?, description = ? WHERE id = ?";
    private final String SQL_DELETE_REPORT_BY_ID = "DELETE FROM dbo.[Report] WHERE id = ?";
    private final String SQL_SELECT_REPORTS_BY_USER_ID
            = "SELECT * " +
            "FROM dbo.[Report] " +
            "WHERE userId = ?";


    private static final ReportDaoImpl INSTANCE = new ReportDaoImpl();

    public static ReportDaoImpl getInstance() {
        return INSTANCE;
    }

    public ReportDaoImpl() {

    }

    public long insert(Report record, Connection conn) throws DaoException {
        try (PreparedStatement preparedStatement
                     = conn.prepareStatement(SQL_INSERT_REPORT, Statement.RETURN_GENERATED_KEYS)) {
            long id = -1;

            preparedStatement.setLong(1, record.getUserId());
            preparedStatement.setString(2, record.getDescription());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                id = rs.getLong(1);
            }

            return id;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Report select(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_REPORT_BY_ID)) {
            conn.setAutoCommit(false);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            Report report = null;
            if (resultSet.next()) {
                report = new Report();
                buildReport(report, resultSet);
            }

            conn.commit();
            return report;
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

    public List<Report> selectAll(Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL_REPORTS)) {
            conn.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery();

            List<Report> reports = new ArrayList<>();
            while (resultSet.next()) {
                Report report = new Report();
                buildReport(report, resultSet);
                reports.add(report);
            }

            conn.commit();
            return reports;
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

    public List<Report> selectAllByUserId(long userId, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_REPORTS_BY_USER_ID)) {
            statement.setLong(1, userId);

            List<Report> reports = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Report report = new Report();
                buildReport(report, resultSet);
                reports.add(report);
            }

            return reports;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(Report record, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_REPORT)) {
            statement.setLong(1, record.getUserId());
            statement.setString(2, record.getDescription());
            statement.setLong(3, record.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_DELETE_REPORT_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Report> selectAllByUserId(long userId) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectAllByUserId(userId, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public long insert(Report record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return insert(record, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Report> selectAll() throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectAll(conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Report select(long id) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return select(id, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Report record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return update(record, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Report record) throws DaoException {
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

    private void buildReport(Report report, ResultSet resultSet) throws DaoException {
        try {
            report.setId(resultSet.getLong("id"));
            report.setDescription(resultSet.getString("description"));
            report.setUserId(resultSet.getLong("userId"));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
