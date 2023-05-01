package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.entity.FlatFilter;

public interface FlatFilterDao extends Dao<FlatFilter> {
    FlatFilter selectByListingId(long listingId) throws DaoException;
}
