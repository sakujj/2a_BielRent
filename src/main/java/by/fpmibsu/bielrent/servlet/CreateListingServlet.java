package by.fpmibsu.bielrent.servlet;

import by.fpmibsu.bielrent.dao.Dao;
import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.dto.*;
import by.fpmibsu.bielrent.dto.validator.ValidationException;
import by.fpmibsu.bielrent.service.AddressService;
import by.fpmibsu.bielrent.service.FilterService;
import by.fpmibsu.bielrent.service.ListingService;
import by.fpmibsu.bielrent.service.PhotoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@MultipartConfig()
@WebServlet("/createListing")
public class CreateListingServlet extends HttpServlet {
    AddressService addressService = AddressService.getInstance();
    ListingService listingService = ListingService.getInstance();
    FilterService filterService = FilterService.getInstance();
    PhotoService photoService = PhotoService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("creation-page.html").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //var
        // super.doPost(req,resp);
        var listingTitle = req.getParameter("listingTitle");
        var description = req.getParameter("description");
        var region = req.getParameter("region");
        var city = req.getParameter("city");
        var districtAdministrative = req.getParameter("districtAdministrative");
        var districtMicro = req.getParameter("districtMicro");
        var street = req.getParameter("street");
        var houseNumber  =req.getParameter("houseNumber");

        var isFlat = req.getParameter("isFlat");//null here
        //var isHouse = req.getParameter("isHouse");//or here
        var flatOrHouse = (isFlat==null ? "FLAT" : "HOUSE");
        var hasElevator = req.getParameter("hasElevator");//НЕ НАДЕЖНО
        var hasWiFi = req.getParameter("hasWiFi");
        var hasFurniture = req.getParameter("hasFurniture");
        var hasBathroom = req.getParameter("hasBathroom");
        var hasWashingMachine = req.getParameter("hasWashingMachine");

        var roomCount = req.getParameter("roomCount");
        var bedroomCount = req.getParameter("bedroomCount");
        var floorCount = req.getParameter("floorCount");
        var balconyCount = req.getParameter("balconyCount");
        var buildYear = req.getParameter("buildYear");
        var squareArea = req.getParameter("squareArea");
        var priceMonthly = req.getParameter("priceMonthly");
        var rentalPeriodStart = req.getParameter("rentalPeriodStart");
        var rentalPeriodEnd = req.getParameter("rentalPeriodEnd");
        var image = req.getParameter("image");
        AddressDto addressDto = AddressDto.builder()
                .city(city)
                .regionName(region)
                .districtAdministrative(districtAdministrative)
                .regionName(region)
                .districtMicro(districtMicro)
                .street(street)
                .houseNumber(houseNumber).build();
        Long addressId = -1l;
        try {
            addressId = addressService.validateAndInsertIfValid(addressDto);
        }
        catch (ValidationException validationException){

        }
        catch (DaoException daoException){

        }

        ListingDto listingDto = ListingDto.builder()
                .name(listingTitle)
                .addressId(addressId)
                .description(description)
                .userId(((UserDto)req.getSession().getAttribute("user")).getId())
                .propertyTypeName(flatOrHouse).build();
        Long listingId = -1l;
        try {
            listingId = listingService.validateAndInsertIfValid(listingDto);
        }
        catch (ValidationException validationException){

        }
        catch (DaoException daoException){

        }
        FilterDto filterDto = FilterDto.builder().
                hasBathroom(hasBathroom)
                .hasElevator(hasElevator)
                .hasFurniture(hasFurniture)
                .hasWashingMachine(hasWashingMachine)
                .hasWifi(hasWiFi)
                .balconyCount(balconyCount)
                .bedroomCount(bedroomCount)
                .floorCount(floorCount)
                .roomCount(roomCount)
                .buildYear(buildYear)
                .squareArea(squareArea)
                .priceMonthly(priceMonthly)
                .rentalPeriodStart(rentalPeriodStart)
                .rentalPeriodEnd(rentalPeriodEnd)
                .listingId(listingId).build();
        try {
            filterService.validateAndInsertIfValid(filterDto);
        }
        catch (ValidationException validationException){

        }
        catch (DaoException daoException){

        }
        var part = req.getPart("image");
        PhotoDto photoDto = PhotoDto.builder()
                .photo(part)
                .listingId(listingId).build();
        try {
            photoService.insert(photoDto);
        } catch (DaoException e) {

        }


    }
}
