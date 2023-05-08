package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.entity.Filter;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface FilterDao extends  Dao<Filter> {
    Filter selectByListingId(long listingId) throws DaoException;
}
