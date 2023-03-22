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

    long insertAdress(Adress adress) throws DaoException;
    Adress selectAdress(long id) throws DaoException;
    List<Adress> selectAllAdresses() throws DaoException;
    Adress selectAdressByStreet(String street) throws DaoException;
    Adress selectAdressByCity(String city) throws DaoException;
    Adress selectAdressByArea(String area) throws DaoException;
    Adress selectAdressByRegion(String region) throws DaoException;

    boolean deleteAdress(long id)throws DaoException;
    boolean deleteAllAdresses()throws DaoException;
}
