package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.Adress;

import java.util.ArrayList;
import java.util.List;

public interface AdressDao extends Dao<Adress>{
    public long insert(Adress record) throws DaoException;
    public boolean update(Adress record) throws DaoException;
    public List<Adress> selectAll() throws DaoException;
    public Adress select(long id) throws DaoException;

    public ArrayList<Adress> selectByStreet(String street) throws DaoException;
    public ArrayList<Adress> selectByCity(String street) throws DaoException;
    public ArrayList<Adress> selectByRegion(String region) throws DaoException;
    public ArrayList<Adress> selectByArea(String area) throws DaoException;

    public boolean deleteAllRecords() throws DaoException;
    public boolean delete(long id) throws DaoException;

}
