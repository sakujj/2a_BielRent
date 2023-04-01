import by.fpmibsu.bielrent.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.dao.*;
import by.fpmibsu.bielrent.dbservice.DbService;
import by.fpmibsu.bielrent.dbservice.DbServiceImpl;
import by.fpmibsu.bielrent.entity.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class Main {
    public static void main(String[] args) throws DaoException {
        DbService dbService = DbServiceImpl.getInstance();
        ConnectionPoolImpl cp = ConnectionPoolImpl.getInstance();
        FlatFilterDao fd = new FlatFilterDaoImpl(cp.getConnection());

        FlatFilter ff = new FlatFilter(8, 333, 3, 10, 5, 6, 7, 1999,
                Date.valueOf("2000-12-19"), Date.valueOf("2500-03-21"),
                true, true, true, true, true,
                null,
                155);

       System.out.println(fd.delete(6));
    }
}
