package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.entity.HouseFilter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HouseFilterDaoImpl implements HouseFilterDao {
    private final String SQL_INSERT_HOUSE_FILTER
            = "INSERT INTO dbo.[HouseFilter](filterId, landArea, hasOtherBuildings) VALUES(?, ?, ?)";
    private final String SQL_SELECT_ALL_HOUSE_FILTERS
            = "SELECT * FROM dbo.[Filter] f JOIN dbo.[HouseFilter] hf ON f.id = hf.filterId";
    private final String SQL_SELECT_HOUSE_FILTER_BY_ID
            = "SELECT * FROM dbo.[Filter] f JOIN dbo.[HouseFilter] hf ON f.id = hf.filterId  WHERE id = ?";
    private final String SQL_SELECT_HOUSE_FILTER_BY_LISTING_ID
            = "SELECT * FROM dbo.[Filter] f JOIN dbo.[HouseFilter] hf ON f.id = hf.filterId  WHERE f.listingId = ?";
    private final String SQL_UPDATE_HOUSE_FILTER
            = "UPDATE HouseFilter SET landArea= ?, hasOtherBuildings= ? WHERE filterId = ?";

    private static final HouseFilterDaoImpl INSTANCE = new HouseFilterDaoImpl();

    private HouseFilterDaoImpl() {
    }

    public static HouseFilterDaoImpl getInstance() {
        return INSTANCE;
    }

    public long insert(HouseFilter record, Connection conn) throws DaoException {
        try (PreparedStatement statement
                     = conn.prepareStatement(SQL_INSERT_HOUSE_FILTER)) {
            conn.setAutoCommit(false);

            FilterDaoImpl filterDaoImpl = FilterDaoImpl.getInstance();
            long id = filterDaoImpl.insert(record);
            if (id == -1) {
                return id;
            }

            statement.setLong(1, id);
            statement.setDouble(2, record.getLandArea());
            statement.setBoolean(3, record.getHasOtherBuildings());
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

    public HouseFilter select(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_HOUSE_FILTER_BY_ID)) {
            conn.setAutoCommit(false);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            HouseFilter houseFilter = null;
            if (resultSet.next()) {
                houseFilter = new HouseFilter();
                buildHouseFilter(houseFilter, resultSet);
            }

            conn.commit();
            return houseFilter;
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

    public HouseFilter selectByListingId(long listingId, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_HOUSE_FILTER_BY_LISTING_ID)) {
            conn.setAutoCommit(false);
            statement.setLong(1, listingId);
            ResultSet resultSet = statement.executeQuery();

            HouseFilter houseFilter = null;
            if (resultSet.next()) {
                houseFilter = new HouseFilter();
                buildHouseFilter(houseFilter, resultSet);
            }

            conn.commit();
            return houseFilter;
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

    public List<HouseFilter> selectAll(Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL_HOUSE_FILTERS)) {
            conn.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery();

            List<HouseFilter> houseFilters = new ArrayList<>();
            while (resultSet.next()) {
                HouseFilter houseFilter = new HouseFilter();
                buildHouseFilter(houseFilter, resultSet);
                houseFilters.add(houseFilter);
            }

            conn.commit();
            return houseFilters;
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

    public boolean update(HouseFilter record, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_HOUSE_FILTER)) {
            conn.setAutoCommit(false);
            FilterDaoImpl filterDaoImpl = FilterDaoImpl.getInstance();
            boolean isSuperUpdated = filterDaoImpl.update(record);
            if (!isSuperUpdated)
                return false;

            statement.setDouble(1, record.getLandArea());
            statement.setBoolean(2, record.getHasOtherBuildings());
            statement.setLong(3, record.getId());
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
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_HOUSE_FILTER_BY_ID)) {
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
    public HouseFilter selectByListingId(long listingId) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectByListingId(listingId, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public long insert(HouseFilter record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return insert(record, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<HouseFilter> selectAll() throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectAll(conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public HouseFilter select(long id) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return select(id, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(HouseFilter record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return update(record, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(HouseFilter record) throws DaoException {
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

    private void buildHouseFilter(HouseFilter houseFilter, ResultSet resultSet) throws DaoException, SQLException {
        FilterDaoImpl.buildFilter(houseFilter, resultSet);
        houseFilter.setLandArea(resultSet.getDouble("landArea"));
        houseFilter.setHasOtherBuildings(resultSet.getBoolean("hasOtherBuildings"));
    }
}
