package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.Filter;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface FilterDao extends  Dao<Filter> {
    static void initializeFilterFromResultSet(Filter filter, ResultSet resultSet) throws DaoException {
        try {
            filter.setId(resultSet.getLong("id"));
            filter.setBedroomCount(resultSet.getInt("bedroomCount"));
            filter.setRoomCount(resultSet.getInt("roomCount"));
            filter.setBalconyCount(resultSet.getInt("balconyCount"));
            filter.setBuildYear(resultSet.getInt("buildYear"));
            filter.setFloorCount(resultSet.getInt("floorCount"));
            filter.setRentalPeriodStart(resultSet.getDate("rentalPeriodStart"));
            filter.setRentalPeriodEnd(resultSet.getDate("rentalPeriodEnd"));
            filter.setPriceMonthly(resultSet.getLong("priceMonthly"));
            filter.setSquareArea(resultSet.getDouble("squareArea"));
            filter.setHasBathroom(resultSet.getBoolean("hasBathroom"));
            filter.setHasFurniture(resultSet.getBoolean("hasFurniture"));
            filter.setHasWifi(resultSet.getBoolean("hasWifi"));
            filter.setHasWashingMachine(resultSet.getBoolean("hasWashingMachine"));
            filter.setHasElevator(resultSet.getBoolean("hasElevator"));
            long listingId = resultSet.getLong("listingId");
            if (listingId == 0) {
                filter.setListing(null);
            } else {
                //###################
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
