package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.entity.FlatFilter;
import by.fpmibsu.bielrent.entity.HouseFilter;

public interface HouseFilterDao extends Dao<HouseFilter> {
    HouseFilter selectByListingId(long listingId) throws DaoException;
}
