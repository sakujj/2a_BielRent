package by.fpmibsu.bielrent.dao;

import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.entity.Report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ReportDao extends Dao<Report> {
    List<Report> selectAllByUserId(long userId) throws DaoException;
}
