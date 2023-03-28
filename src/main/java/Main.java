import by.fpmibsu.bielrent.dao.DaoException;
import by.fpmibsu.bielrent.dbservice.DbService;
import by.fpmibsu.bielrent.dbservice.DbServiceImpl;
import by.fpmibsu.bielrent.entity.Role;
import by.fpmibsu.bielrent.entity.User;
//import sun.jvm.hotspot.debugger.Address;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) throws DaoException {
        DbService dbService = DbServiceImpl.getInstance();

        User uz = new User();
        uz.setRole(Role.CLIENT);
        uz.setName("Пабл23о32");
        uz.setPassword("12");
        uz.setEmail("eew");
        uz.setRating(new BigDecimal("4.2"));
        System.out.println(uz.getRating());
        System.out.println(dbService.insertUser(uz));
        List<User> users = dbService.selectAllUsers();
        for (User u : users) {
            System.out.println(u.getId() + " " +u.getEmail() +" " +u.getPassword() +" " +u.getName()+" " + u.getRole() +" "+ u.getRating());
        }

    }
}
