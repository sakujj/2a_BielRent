package by.fpmibsu.bielrent.model.dao;

import by.fpmibsu.bielrent.model.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.entity.Photo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PhotoDaoImpl implements PhotoDao {
    private final String SQL_INSERT_PHOTO
            = "INSERT INTO dbo.[Photo](path, listingId) VALUES(?, ?)";
    private final String SQL_SELECT_ALL_PHOTOS
            = "SELECT * FROM dbo.[Photo]";
    private final String SQL_SELECT_PHOTO_BY_ID
            = "SELECT * FROM dbo.[Photo] WHERE id = ?";
    private final String SQL_UPDATE_PHOTO
            = "UPDATE dbo.[Photo] SET path= ?,listingId = ? WHERE id = ?";
    private final String SQL_DELETE_PHOTO_BY_ID = "DELETE FROM dbo.[Photo] WHERE id = ?";

    private final String SQL_SELECT_PHOTOS_BY_LISTING_ID
            = "SELECT * " +
            "FROM PHOTO " +
            "WHERE listingId = ?";

    private static final PhotoDaoImpl INSTANCE = new PhotoDaoImpl();
    Logger logger = LogManager.getLogger(FilterDaoImpl.class);

    public static PhotoDaoImpl getInstance() {
        return INSTANCE;
    }

    private PhotoDaoImpl() {

    }

    public long insert(Photo record, Connection conn) throws DaoException {
        try (PreparedStatement statement
                     = conn.prepareStatement(SQL_INSERT_PHOTO, Statement.RETURN_GENERATED_KEYS)) {
            long id = -1;

            statement.setString(1, record.getPath());
            statement.setLong(2, record.getListingId());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }

            return id;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public Optional<Photo> select(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_PHOTO_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            Photo photo = null;
            if (resultSet.next()) {
                photo = new Photo();
                buildPhoto(photo, resultSet);
            }

            return Optional.ofNullable(photo);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public List<Photo> selectAll(Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL_PHOTOS)) {
            ResultSet resultSet = statement.executeQuery();

            List<Photo> photos = new ArrayList<>();
            while (resultSet.next()) {
                Photo photo = new Photo();
                buildPhoto(photo, resultSet);
                photos.add(photo);
            }

            return photos;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public List<Photo> selectAllByListingId(long listingId, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_PHOTOS_BY_LISTING_ID)) {
            statement.setLong(1, listingId);
            ResultSet resultSet = statement.executeQuery();

            List<Photo> photos = new ArrayList<>();
            while (resultSet.next()) {
                Photo photo = new Photo();
                buildPhoto(photo, resultSet);
                photos.add(photo);
            }

            return photos;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public boolean update(Photo record, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_PHOTO)) {
            if (record.getPath() == null || record.getPath().length() > 255) {
                return false;
            }

            statement.setString(1, record.getPath());
            statement.setLong(2, record.getListingId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public boolean delete(long id, Connection conn) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_DELETE_PHOTO_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Photo> selectAllByListingId(long listingId) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectAllByListingId(listingId, conn);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public long insert(Photo record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return insert(record, conn);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Photo> selectAll() throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return selectAll(conn);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Photo> select(long id) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return select(id, conn);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Photo record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return update(record, conn);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Photo record) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return delete(record.getId(), conn);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try (Connection conn = ConnectionPoolImpl.getInstance().getConnection()) {
            return delete(id, conn);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    private void buildPhoto(Photo photo, ResultSet resultSet) throws DaoException {
        try {
            photo.setId(resultSet.getLong("id"));
            photo.setPath(resultSet.getString("path"));
            photo.setListingId(resultSet.getLong("listingId"));
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
}
