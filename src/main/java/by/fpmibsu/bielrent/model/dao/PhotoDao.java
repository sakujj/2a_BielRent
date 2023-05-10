package by.fpmibsu.bielrent.model.dao;

import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.entity.Photo;

import java.util.List;

public interface PhotoDao extends Dao<Photo>{
    List<Photo> selectAllByListingId(long listingId) throws DaoException;
}
