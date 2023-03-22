import by.fpmibsu.bielrent.dao.DaoException;
import by.fpmibsu.bielrent.dbservice.DbService;
import by.fpmibsu.bielrent.dbservice.DbServiceImpl;
import by.fpmibsu.bielrent.entity.Adress;
import by.fpmibsu.bielrent.entity.Listing;
import by.fpmibsu.bielrent.entity.Photo;
import by.fpmibsu.bielrent.entity.User;
//import sun.jvm.hotspot.debugger.Address;

import java.util.List;

public class Main {
    public static void main(String[] args) throws DaoException {
        DbService dbService = DbServiceImpl.getInstance();

        List<User> users = dbService.selectAllUsers();
        for (User u : users) {
            System.out.println(u.getId() + " " +u.getEmail() +" " +u.getPassword() +" " +u.getName());
        }

        User u = dbService.selectUser(7);
        System.out.println(u.getEmail() +" " +u.getPassword() +" " +u.getName());

        u.setId(0);
        u.setName("Artem");
        u.setEmail("Artem@f3e.uk");
        u.setPassword("123");
        System.out.println(dbService.insertUser(u));

        u = dbService.selectUser(7);
        u.setName("GenadiyXXXXXX");
        u.setEmail("G@mail.uk");
        u.setPassword("12345");
        System.out.println(dbService.updateUser(u));

        dbService.deleteUser(9);

        u = dbService.selectUser("G@mail.uk");
        System.out.println(u.getEmail() +" " +u.getPassword() +" " +u.getName());
        System.out.println("///////////////AAADDDRRREEESSS/////////");
        Adress ad1 = new Adress();
        ad1.setRegion("Gomelskaya");
        ad1.setCity("Mozyr");
        ad1.setArea("Bazar");
        ad1.setStreet("Kalinouskaga");
        ad1.setHouse(5);
        ad1.setFlat(12);
        Adress ad2 = new Adress();
        ad2.setRegion("Gomelskaya");
        ad2.setCity("Mozyr");
        ad2.setArea("Bazar");
        ad2.setStreet("Myasnikova");
        ad2.setHouse(5);
        dbService.deleteAllAdresses();
        dbService.insertAdress(ad1);
        dbService.insertAdress(ad2);
        for(Adress a :dbService.selectAllAdresses()){
            System.out.println(a.getId() +" " +a.getRegion() +" "+ a.getCity() + " " + a.getArea() + " " + a.getStreet() + " "
                    + a.getHouse() + " "+  a.getFlat());
        }

    }
}
