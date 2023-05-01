package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.entity.FlatFilter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlatFilterDaoImpl implements FlatFilterDao {
    private static final String SQL_SELECT_FLAT_FILTER_BY_ID
            = "SELECT * FROM dbo.[Filter] f JOIN dbo.[FlatFilter] ff ON f.id = ff.filterId  WHERE id = ?";
    private static final String SQL_SELECT_FLAT_FILTER_BY_LISTING_ID
            = "SELECT * FROM dbo.[Filter] f JOIN dbo.[FlatFilter] ff ON f.id = ff.filterId  WHERE f.listingId = ?";
    private final String SQL_INSERT_FLAT_FILTER
            = "INSERT INTO dbo.[FlatFilter](filterId, floorNumber) VALUES(?, ?)";
    private final String SQL_SELECT_ALL_FLAT_FILTERS
            = "SELECT * FROM dbo.[Filter] f JOIN dbo.[FlatFilter] ff ON f.id = ff.filterId";
    private final String SQL_UPDATE_FLAT_FILTER
            = "UPDATE FlatFilter SET floorNumber= ? WHERE filterId = ?";

    private static final FlatFilterDaoImpl INSTANCE = new FlatFilterDaoImpl();

    private FlatFilterDaoImpl() {
    }

    public static FlatFilterDaoImpl getInstance() {
        return INSTANCE;
    }

    public long insert(FlatFilter record, Connection conn) throws DaoException {
        try (PreparedStatement statement
                     = conn.prepareStatement(SQL_INSERT_FLAT_FILTER)) {
            conn.setAutoCommit(false);
            FilterDaoImpl filterDaoImpl = FilterDaoImpl.getInstance();
            long id = filterDaoImpl.insert(record);
            if (id == -1) {
                return id;
            }

            statement.setLong(1, id);
            statement.setInt(2, record.getFloorNumber());
            statement.executeUpdate();
            conn.commit();
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

    public FlatFilter select(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_FLAT_FILTER_BY_ID)) {
            conn.setAutoCommit(false);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            FlatFilter flatFilter = null;
            if (resultSet.next()) {
                flatFilter = new FlatFilter();
                buildFlatFilter(flatFilter, resultSet);

            }

            conn.commit();
            return flatFilter;
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

    public List<FlatFilter> selectAll(Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL_FLAT_FILTERS)) {
            conn.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery();

            List<FlatFilter> flatFilters = new ArrayList<>();
            while (resultSet.next()) {
                FlatFilter flatFilter = new FlatFilter();
                buildFlatFilter(flatFilter, resultSet);

                flatFilters.add(flatFilter);
            }

            conn.commit();
            return flatFilters;
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

    public FlatFilter selectByListingId(long listingId, Connection conn) throws DaoException {
        try (PreparedStatement statement
                     = conn.prepareStatement(SQL_SELECT_FLAT_FILTER_BY_LISTING_ID)) {
            statement.setLong(1, listingId);
            ResultSet rs = statement.executeQuery();

            FlatFilter flatFilter = null;
            if (rs.next()) {
                flatFilter = new FlatFilter();
                buildFlatFilter(flatFilter, rs);
            }

            return flatFilter;
        }  catch (SQLException e) {
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

    public boolean update(FlatFilter record, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_FLAT_FILTER)) {
            conn.setAutoCommit(false);
            FilterDaoImpl filterDaoImpl = FilterDaoImpl.getInstance();
            boolean isSuperUpdated = filterDaoImpl.update(record);
            if (!isSuperUpdated)
                return false;

            statement.setInt(1, record.getFloorNumber());
            statement.setLong(2, record.getId());
            boolean isUpdated = statement.executeUpdate() > 0;
            if (!isUpdated) {
                return false;
            }

            conn.commit();
            return true;
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

    public boolean delete(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_FLAT_FILTER_BY_ID)) {
            conn.setAutoCommit(false);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            long filterId = rs.getLong("filterId");
            FilterDaoImpl filterDaoImpl = FilterDaoImpl.getInstance();
            boolean isDeleted = filterDaoImpl.delete(filterId);
            if (!isDeleted) {
                return false;
            }

            conn.commit();
            return true;
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
    public FlatFilter selectByListingId(long listingId) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectByListingId(listingId, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public long insert(FlatFilter record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return insert(record, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<FlatFilter> selectAll() throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectAll(conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public FlatFilter select(long id) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return select(id, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(FlatFilter record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return update(record, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(FlatFilter record) throws DaoException {
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

    private void buildFlatFilter(FlatFilter flatFilter, ResultSet resultSet) throws DaoException, SQLException {
        FilterDaoImpl.buildFilter(flatFilter, resultSet);
        flatFilter.setFloorNumber(resultSet.getInt("floorNumber"));
    }

}
