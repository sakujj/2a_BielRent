package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.Address;

import java.util.ArrayList;
import java.util.List;

public interface AddressDao extends Dao<Address>{
    public ArrayList<Address> selectByStreet(String street) throws DaoException;
    public ArrayList<Address> selectByCity(String street) throws DaoException;
    public ArrayList<Address> selectByRegion(String region) throws DaoException;
    public ArrayList<Address> selectByArea(String area) throws DaoException;

    public boolean deleteAllRecords() throws DaoException;
}
