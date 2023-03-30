package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.Filter;
import by.fpmibsu.bielrent.entity.FlatFilter;

import java.sql.*;
import java.util.List;

import static java.sql.Types.NULL;

public class FlatFilterDaoImpl extends FilterDaoAbstract{
    private final String SQL_INSERT_FLAT_FILTER = "INSERT INTO FlatFilter(filterId,floorNumber)"
            + " VALUES(?, ?)";
    private final String SQL_SELECT_ALL_FLAT_FILTERS = "SELECT * FROM FlatFilter";
    private final String SQL_SELECT_FLAT_FILTER_BY_ID = "SELECT * FROM FlatFilter WHERE id = ?";
    private final String SQL_UPDATE_FLAT_FILTER = "UPDATE Filter SET filterId = ?, floorNumber= ?";


    private final String SQL_DELETE_FILTER_BY_ID = "DELETE FROM [dbo].[User] WHERE id = ?";

    private Connection conn;

    public FlatFilterDaoImpl(Connection c) {
        super(c);
        conn = c;
    }

    @Override
    public long insert(Filter record) throws DaoException {
        try (PreparedStatement statement
                     = conn.prepareStatement(SQL_INSERT_FLAT_FILTER, Statement.RETURN_GENERATED_KEYS)) {
            long id = -1;
            long filterSuperRecord = super.insert(record);
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
    public List<Filter> selectAll() throws DaoException{
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL_FLAT_FILTERS)) {
            List<Filter> flatFilters = null;
            FlatFilter flatFilter = null;
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flatFilter = (FlatFilter)super.select(resultSet.getLong("filterId"));
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
                flatFilter = (FlatFilter)super.select(resultSet.getLong("filterId"));
                flatFilter.setFloorNumber(resultSet.getInt("floorNumber"));
            }
            return flatFilter;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public boolean update(Filter record) throws DaoException{
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_FLAT_FILTER)) {
            long id = -1;
            FlatFilter flatFilter = (FlatFilter)record;

            boolean isSuperUpdated = super.update(record);
            statement.setInt(1,flatFilter.getFloorNumber());
            return statement.executeUpdate() > 0 || isSuperUpdated;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public boolean delete(Filter record) throws DaoException{
        return super.delete(record);
    }
    @Override
    public boolean delete(long id) throws DaoException{
        return super.delete(id);
    }
}
