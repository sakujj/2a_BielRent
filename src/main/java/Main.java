import by.fpmibsu.bielrent.dao.DaoException;
import by.fpmibsu.bielrent.dbservice.DbService;
import by.fpmibsu.bielrent.dbservice.DbServiceImpl;
import by.fpmibsu.bielrent.entity.User;

import java.util.List;

public class Main {
    public static void main(String[] args) throws DaoException {
        DbService dbService = DbServiceImpl.getInstance();
        List<User> users = dbService.selectAllUsers();
        for (User u : users) {
            System.out.println(u.getId() + " " +u.getEmail() +" " +u.getPassword() +" " +u.getName());
        }

        User u = dbService.selectUser(2);
        System.out.println(u.getEmail() +" " +u.getPassword() +" " +u.getName());

        u.setId(0);
        u.setName("Artem");
        u.setEmail("Artem@f3e.uk");
        u.setPassword("123");
        System.out.println(dbService.insertUser(u));

        u = dbService.selectUser(2);
        u.setName("GenadiyXXXXXX");
        u.setEmail("G@mail.uk");
        u.setPassword("12345");
        System.out.println(dbService.updateUser(u));

        dbService.deleteUser(3);

        u = dbService.selectUser("abiba@gmail.com");
        System.out.println(u.getEmail() +" " +u.getPassword() +" " +u.getName());
    }
}
