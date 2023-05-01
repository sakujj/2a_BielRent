package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.entity.Listing;

import java.sql.Connection;
import java.util.List;

public interface ListingDao extends Dao<Listing> {
    List<Listing> selectAllByAddressId(long addressId) throws DaoException;
    List<Listing> selectAllByUserId(long userId) throws DaoException;
}
