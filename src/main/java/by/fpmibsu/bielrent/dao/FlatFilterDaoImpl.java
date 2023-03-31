package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.Filter;
import by.fpmibsu.bielrent.entity.FlatFilter;

import java.sql.*;
import java.util.List;

import static java.sql.Types.NULL;

public class FlatFilterDaoImpl implements FlatFilterDao{
    private final String SQL_INSERT_FLAT_FILTER = "INSERT INTO FlatFilter(filterId,floorNumber)"
            + " VALUES(?, ?)";
    private final String SQL_SELECT_ALL_FLAT_FILTERS = "SELECT * FROM FlatFilter";
    private final String SQL_SELECT_FLAT_FILTER_BY_ID = "SELECT * FROM FlatFilter WHERE id = ?";
    private final String SQL_UPDATE_FLAT_FILTER = "UPDATE FlatFilter SET filterId = ?, floorNumber= ?";


    private final String SQL_DELETE_FLAT_FILTER_BY_ID = "DELETE FROM FlatFilter WHERE id = ?";

    private Connection conn;

    public FlatFilterDaoImpl(Connection c) {

        conn = c;
    }

    @Override
    public long insert(FlatFilter record) throws DaoException {
        try (PreparedStatement statement
                     = conn.prepareStatement(SQL_INSERT_FLAT_FILTER, Statement.RETURN_GENERATED_KEYS)) {
            long id = -1;
            FilterDaoAbstract filterDaoAbstract = new FilterDaoAbstract(conn);
            long filterSuperRecord = filterDaoAbstract.insert(record);
            statement.setLong(1,filterSuperRecord);
            FlatFilter newRecord = (FlatFilter)record;
            statement.setInt(2,newRecord.getFloorNumber());
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
    public List<FlatFilter> selectAll() throws DaoException{
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL_FLAT_FILTERS)) {
            List<FlatFilter> flatFilters = null;
            FlatFilter flatFilter = null;
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                FilterDaoAbstract filterDaoAbstract = new FilterDaoAbstract(conn);
                flatFilter = (FlatFilter)filterDaoAbstract.select(resultSet.getLong("filterId"));
                flatFilter.setFloorNumber(resultSet.getInt("floorNumber"));
                flatFilters.add(flatFilter);
            }
            return flatFilters;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public FlatFilter select(long id) throws DaoException{
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_FLAT_FILTER_BY_ID)) {
            FlatFilter flatFilter = null;
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                FilterDaoAbstract filterDaoAbstract = new FilterDaoAbstract(conn);
                flatFilter = (FlatFilter)filterDaoAbstract.select(resultSet.getLong("filterId"));
                flatFilter.setFloorNumber(resultSet.getInt("floorNumber"));
            }
            return flatFilter;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public boolean update(FlatFilter record) throws DaoException{
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_FLAT_FILTER)) {
            long id = -1;
            FlatFilter flatFilter = (FlatFilter)record;
            FilterDaoAbstract filterDaoAbstract = new FilterDaoAbstract(conn);
            boolean isSuperUpdated = filterDaoAbstract.update(record);
            statement.setInt(1,flatFilter.getFloorNumber());
            return statement.executeUpdate() > 0 || isSuperUpdated;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public boolean delete(FlatFilter record) throws DaoException{
        return delete(record.getId());
    }
    @Override
    public boolean delete(long id) throws DaoException{
        try {
           FilterDaoAbstract filterDaoAbstract = new FilterDaoAbstract(conn);
            return filterDaoAbstract.delete(id);
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }
}
