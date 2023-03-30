import by.fpmibsu.bielrent.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.dao.DaoException;
import by.fpmibsu.bielrent.dao.ReportDao;
import by.fpmibsu.bielrent.dao.ReportDaoImpl;
import by.fpmibsu.bielrent.dbservice.DbService;
import by.fpmibsu.bielrent.dbservice.DbServiceImpl;
import by.fpmibsu.bielrent.entity.Address;
import by.fpmibsu.bielrent.entity.Report;
import by.fpmibsu.bielrent.entity.User;
import by.fpmibsu.bielrent.entity.HouseFilter;
import java.util.List;

public class Main {
    public static void main(String[] args) throws DaoException {
        DbService dbService = DbServiceImpl.getInstance();
        ConnectionPoolImpl cp = ConnectionPoolImpl.getInstance();
        ReportDao rd = new ReportDaoImpl(cp.getConnection());
        Report report = new Report();
        Report report1 = rd.select(2);
        System.out.println(report1.getUser());
    }
}
