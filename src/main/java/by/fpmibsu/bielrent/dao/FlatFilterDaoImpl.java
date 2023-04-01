package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.Filter;
import by.fpmibsu.bielrent.entity.FlatFilter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class FlatFilterDaoImpl implements FlatFilterDao {
    private final String SQL_INSERT_FLAT_FILTER
            = "INSERT INTO dbo.[FlatFilter](filterId, floorNumber) VALUES(?, ?)";
    private final String SQL_SELECT_ALL_FLAT_FILTERS
            = "SELECT * FROM dbo.[Filter] f JOIN dbo.[FlatFilter] ff ON f.id = ff.filterId";
    private final String SQL_SELECT_FLAT_FILTER_BY_ID
            = "SELECT * FROM dbo.[Filter] f JOIN dbo.[FlatFilter] ff ON f.id = ff.filterId  WHERE id = ?";
    private final String SQL_UPDATE_FLAT_FILTER
            = "UPDATE FlatFilter SET floorNumber= ? WHERE filterId = ?";


    private final String SQL_DELETE_FLAT_FILTER_BY_ID = "DELETE FROM FlatFilter WHERE filterId = ?";

    private Connection conn;

    public FlatFilterDaoImpl(Connection c) {
        conn = c;
    }

    @Override
    public long insert(FlatFilter record) throws DaoException {
        try (PreparedStatement statement
                     = conn.prepareStatement(SQL_INSERT_FLAT_FILTER)) {
            long id = -1;
            conn.setAutoCommit(false);
            FilterDaoAbstract filterDaoAbstract = new FilterDaoAbstract(conn);
            id = filterDaoAbstract.insert(record);
            statement.setLong(1, id);
            statement.setInt(2, record.getFloorNumber());
            statement.executeUpdate();
            return id;
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

    @Override
    public List<FlatFilter> selectAll() throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL_FLAT_FILTERS)) {
            List<FlatFilter> flatFilters = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                FlatFilter flatFilter = new FlatFilter();
                FilterDao.initializeFilterFromResultSet(flatFilter, resultSet);
                flatFilter.setFloorNumber(resultSet.getInt("floorNumber"));
                flatFilters.add(flatFilter);
            }
            return flatFilters;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public FlatFilter select(long id) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_FLAT_FILTER_BY_ID)) {
            statement.setLong(1, id);
            FlatFilter flatFilter = null;
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                flatFilter = new FlatFilter();
                FilterDao.initializeFilterFromResultSet(flatFilter, resultSet);
                flatFilter.setFloorNumber(resultSet.getInt("floorNumber"));
            }
            return flatFilter;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(FlatFilter record) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_FLAT_FILTER)) {
            conn.setAutoCommit(false);
            FilterDaoAbstract filterDaoAbstract = new FilterDaoAbstract(conn);
            boolean isSuperUpdated = filterDaoAbstract.update(record);
            if (!isSuperUpdated)
                return false;
            statement.setInt(1, record.getFloorNumber());
            statement.setLong(2, record.getId());
            return statement.executeUpdate() > 0;
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

    @Override
    public boolean delete(FlatFilter record) throws DaoException {
        return delete(record.getId());
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_DELETE_FLAT_FILTER_BY_ID)) {
            conn.setAutoCommit(false);
            FilterDaoAbstract filterDaoAbstract = new FilterDaoAbstract(conn);
            statement.setLong(1, id);
            if (statement.executeUpdate() == 0) {
                return false;
            }

            return filterDaoAbstract.delete(id);
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
}
