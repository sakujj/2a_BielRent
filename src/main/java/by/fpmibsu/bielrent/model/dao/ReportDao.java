package by.fpmibsu.bielrent.model.dao;

import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.entity.Report;

import java.util.List;

public interface ReportDao extends Dao<Report> {
    List<Report> selectAllByUserId(long userId) throws DaoException;
}
