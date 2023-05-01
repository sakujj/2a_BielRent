package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.entity.Photo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface PhotoDao extends Dao<Photo>{
    List<Photo> selectAllByListingId(long listingId) throws DaoException;
}
