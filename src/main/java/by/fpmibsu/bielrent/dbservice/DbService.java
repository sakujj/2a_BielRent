package by.fpmibsu.bielrent.dbservice;

import by.fpmibsu.bielrent.dao.DaoException;
import by.fpmibsu.bielrent.entity.*;
//import by.fpmibsu.bielrent.entity.User;

import java.util.List;

public interface DbService {

    //user functions//////////////////////
    long insertUser(User user) throws DaoException;

    boolean deleteUser(long id) throws DaoException;

    boolean deleteUser(User user) throws DaoException;
    User selectUser(long id) throws DaoException;

    User selectUser(String email) throws DaoException;
    List<User> selectAllUsers() throws DaoException;
    boolean updateUser(User user) throws DaoException;


    //adress functions//////////////////////////////

    long insertAdress(Address address) throws DaoException;
    Address selectAdress(long id) throws DaoException;
    List<Address> selectAllAdresses() throws DaoException;
    Address selectAdressByStreet(String street) throws DaoException;
    Address selectAdressByCity(String city) throws DaoException;
    Address selectAdressByArea(String area) throws DaoException;
    Address selectAdressByRegion(String region) throws DaoException;

    boolean deleteAdress(long id)throws DaoException;
    boolean deleteAllAdresses()throws DaoException;
}
