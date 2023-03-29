package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.entity.Report;
import by.fpmibsu.bielrent.entity.User;

public interface ReportDao extends Dao<Report> {

    Report selectByUser(User user) throws DaoException;
}
