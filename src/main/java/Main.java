import by.fpmibsu.bielrent.dao.*;
import by.fpmibsu.bielrent.entity.*;

import java.util.List;

public class Main {
    public static void main(String[] args) throws DaoException {
        System.out.println("-------------------------------------------");
        System.out.println("Address");
        System.out.println("-------------------------------------------");
        List<Address> addresses =AddressDaoImpl.getInstance().selectAll();
        Address address =AddressDaoImpl.getInstance().select(1);
        AddressDaoImpl.getInstance().update(address);
        System.out.println(addresses);
        System.out.println("-------------------------------------------");
        System.out.println(address);

//        System.out.println("-------------------------------------------");
//        System.out.println("User");
//        System.out.println("-------------------------------------------");
//        List<User> users =UserDaoImpl.getInstance().selectAll();
//        User user =UserDaoImpl.getInstance().select(1);
//        System.out.println(users);
//        System.out.println("\n-------------------------------------------\n");
//        System.out.println(user);

//        System.out.println("-------------------------------------------");
//        System.out.println("Listing");
//        System.out.println("-------------------------------------------");
//        List<Listing> listings =ListingDaoImpl.getInstance().selectAll();
//        Listing listing =ListingDaoImpl.getInstance().select(1);
//        System.out.println(listings);
//        System.out.println("\n-------------------------------------------\n");
//        System.out.println(listing);

//        System.out.println("-------------------------------------------");
//        System.out.println("HouseFilter");
//        System.out.println("-------------------------------------------");
//        List<HouseFilter> houseFilters =HouseFilterDaoImpl.getInstance().selectAll();
//        HouseFilter houseFilter =HouseFilterDaoImpl.getInstance().select(1);
//        System.out.println(houseFilters);
//        System.out.println("\n-------------------------------------------\n");
//        System.out.println(houseFilter);

//        System.out.println("-------------------------------------------");
//        System.out.println("FlatFilter");
//        System.out.println("-------------------------------------------");
//        List<FlatFilter> flatFilters =FlatFilterDaoImpl.getInstance().selectAll();
//        FlatFilter flatFilter =FlatFilterDaoImpl.getInstance().select(5);
//        System.out.println(flatFilters);
//        System.out.println("\n-------------------------------------------\n");
//        System.out.println(flatFilter);

//        System.out.println("-------------------------------------------");
//        System.out.println("Report");
//        System.out.println("-------------------------------------------");
//        List<Report> reports =ReportDaoImpl.getInstance().selectAll();
//        Report report =ReportDaoImpl.getInstance().select(1);
//        System.out.println(reports);
//        System.out.println("\n-------------------------------------------\n");
//        System.out.println(report);


//        System.out.println("-------------------------------------------");
//        System.out.println("Photo");
//        System.out.println("-------------------------------------------");
//        List<Photo> photos =PhotoDaoImpl.getInstance().selectAll();
//        Photo photo =PhotoDaoImpl.getInstance().select(3);
//        System.out.println(photos);
//        System.out.println("\n-------------------------------------------\n");
//        System.out.println(photo);

    }
}
