package by.fpmibsu.bielrent.model.dao;

import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.entity.Filter;

public interface FilterDao extends  Dao<Filter> {
    Filter selectByListingId(long listingId) throws DaoException;
}
