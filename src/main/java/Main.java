import by.fpmibsu.bielrent.dao.DaoException;
import by.fpmibsu.bielrent.dbservice.DbService;
import by.fpmibsu.bielrent.dbservice.DbServiceImpl;
import by.fpmibsu.bielrent.entity.Address;
import by.fpmibsu.bielrent.entity.User;
import by.fpmibsu.bielrent.entity.HouseFilter;
import java.util.List;

public class Main {
    public static void main(String[] args) throws DaoException {
        DbService dbService = DbServiceImpl.getInstance();
    }
}
