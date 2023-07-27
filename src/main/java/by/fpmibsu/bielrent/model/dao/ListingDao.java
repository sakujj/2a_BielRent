package by.fpmibsu.bielrent.model.dao;

import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.entity.Listing;

import java.util.List;

public interface ListingDao extends Dao<Listing> {
    List<Listing> selectAllByAddressId(long addressId) throws DaoException;
    List<Listing> selectAllByUserId(long userId) throws DaoException;
}
