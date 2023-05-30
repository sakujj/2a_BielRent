package by.fpmibsu.bielrent.model.dao;

import by.fpmibsu.bielrent.model.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.entity.Address;
import by.fpmibsu.bielrent.model.entity.Region;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import org.apache.log4j.*;
import static java.sql.Types.NULL;

public class AddressDaoImpl implements AddressDao {
    private final String SQL_INSERT_ADDRESS
            = "INSERT INTO [dbo].[Address](" +
            "regionName, " +
            "city, " +
            "districtAdministrative, " +
            "districtMicro, " +
            "street, " +
            "houseNumber)" +
            " VALUES(?, ?, ?, ?, ?,?)";
    private final String SQL_SELECT_ALL_ADDRESSES
            = "SELECT * " +
            "FROM [dbo].[Address]";
    private final String SQL_SELECT_ADDRESS_BY_ID
            = "SELECT * " +
            "FROM [dbo].[Address] " +
            "WHERE id = ?";
    private final String SQL_DELETE_ADDRESS_BY_ID
            = "DELETE " +
            "FROM [dbo].[Address] " +
            "WHERE id = ?";

    private final String SQL_UPDATE_ADDRESS_BY_ID
            = "UPDATE " +
            "[dbo].[Address] " +
            "SET " +
            "regionName = ?, " +
            "city = ?, " +
            "districtAdministrative = ?, " +
            "districtMicro = ?, " +
            "street = ?, " +
            "houseNumber = ? " +
            "WHERE id = ?";

    private static final AddressDaoImpl INSTANCE = new AddressDaoImpl();
    private Logger logger = Logger.getLogger(AddressDaoImpl.class.getName());

    private AddressDaoImpl() {
    }

    public static AddressDaoImpl getInstance() {
        return INSTANCE;
    }

    public long insert(Address record, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_INSERT_ADDRESS, Statement.RETURN_GENERATED_KEYS)) {
            long id = -1;

            statement.setString(1, record.getRegionName().toString());
            statement.setString(2, record.getCity());
            if (record.getDistrictAdministrative() != null) {
                statement.setString(3, record.getDistrictAdministrative());
            } else {
                statement.setNull(3, NULL);
            }
            if (record.getDistrictMicro() != null) {
                statement.setString(4, record.getDistrictMicro());
            } else {
                statement.setNull(4, NULL);
            }
            statement.setString(5, record.getStreet());
            statement.setInt(6, record.getHouseNumber());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                logger.info("Address was added");
                id = rs.getLong(1);
            }
            return id;
        } catch (SQLException e) {
            logger.error("Address wasn't added\n");
            throw new DaoException(e);
        }
    }

    public Optional<Address> select(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ADDRESS_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            Address address = null;
            if (resultSet.next()) {
                address = new Address();
                buildAddress(address, resultSet);
            }

            return Optional.ofNullable(address);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Address> selectAll(Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL_ADDRESSES)) {
            ResultSet resultSet = statement.executeQuery();

            List<Address> addresses = new ArrayList<>();
            while (resultSet.next()) {
                Address a = new Address();
                buildAddress(a, resultSet);
                addresses.add(a);
            }

            return addresses;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(Address record, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_ADDRESS_BY_ID)) {
            statement.setString(1, record.getRegionName().toString());
            statement.setString(2, record.getCity());
            if (record.getDistrictAdministrative() == null) {
                statement.setNull(3, NULL);
            } else {
                statement.setString(3, record.getDistrictAdministrative());
            }

            if (record.getDistrictMicro() == null) {
                statement.setNull(4, NULL);
            } else {
                statement.setString(4, record.getDistrictMicro());
            }

            statement.setString(5, record.getStreet());
            statement.setInt(6, record.getHouseNumber());
            statement.setLong(7, record.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public boolean delete(Address record, Connection conn) throws DaoException {
        return delete(record.getId());
    }

    public boolean delete(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_DELETE_ADDRESS_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public long insert(Address record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return insert(record, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Address> selectAll() throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectAll(conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Address> select(long id) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return select(id, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Address record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return update(record, conn);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Address record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return delete(record, conn);
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

    void buildAddress(Address address, ResultSet resultSet) throws DaoException {
        try {
            address.setId(resultSet.getLong("id"));
            address.setCity(resultSet.getString("city"));
            address.setDistrictAdministrative(resultSet.getString("districtAdministrative"));
            address.setDistrictMicro(resultSet.getString("districtMicro"));
            address.setHouseNumber(resultSet.getInt("houseNumber"));
            address.setRegionName(Region.valueOf(resultSet.getString("regionName")));
            address.setStreet(resultSet.getString("street"));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
