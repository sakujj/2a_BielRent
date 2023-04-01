package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.FlatFilter;
import by.fpmibsu.bielrent.entity.Photo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhotoDaoImpl implements PhotoDao{
    private final String SQL_INSERT_PHOTO
            = "INSERT INTO Photo(path, listingId) VALUES(?, ?)";
    private final String SQL_SELECT_ALL_PHOTOS
            = "SELECT * FROM Photo";
    private final String SQL_SELECT_PHOTO_BY_ID
            = "SELECT * FROM Photo WHERE id = ?";
    private final String SQL_UPDATE_PHOTO
            = "UPDATE Photo SET path= ?,listingId = ? WHERE filterId = ?";


    private final String SQL_DELETE_PHOTO_BY_ID = "DELETE FROM Photo WHERE id = ?";

    private Connection conn;

    public PhotoDaoImpl(Connection c) {
        conn = c;
    }
    @Override
    public long insert(Photo record) throws DaoException {
        try (PreparedStatement statement
                     = conn.prepareStatement(SQL_INSERT_PHOTO)) {
            long id = -1;
            statement.setString(1,record.getPath());
            statement.setLong(2,record.getListing().getId());
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
    public List<Photo> selectAll() throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL_PHOTOS)) {
            conn.setAutoCommit(false);
            List<Photo> photos = new ArrayList<Photo>();
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Photo photo = new Photo();
                photo.setPath(resultSet.getString("path"));
                ListingDao listingDao = new ListingDaoImpl(conn);
                photo.setListing(listingDao.select(resultSet.getLong("listingId")));
                photos.add(photo);
            }
            return photos;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public Photo select(long id) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_PHOTO_BY_ID)) {
            conn.setAutoCommit(false);
            statement.setLong(1, id);
            Photo photo = null;
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                photo = new Photo();
                photo.setPath(resultSet.getString("path"));
                ListingDao listingDao= new ListingDaoImpl(conn);
                photo.setListing(listingDao.select(resultSet.getLong("listingId")));
            }
            return photo;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Photo record) throws DaoException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_PHOTO)) {
            conn.setAutoCommit(false);

            statement.setString(1,record.getPath());
            statement.setLong(2,record.getListing().getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {

            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Photo record) throws DaoException {
        return delete(record.getId());
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try(PreparedStatement statement = conn.prepareStatement(SQL_DELETE_PHOTO_BY_ID)){
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
