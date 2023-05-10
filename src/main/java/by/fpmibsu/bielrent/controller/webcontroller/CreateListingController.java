package by.fpmibsu.bielrent.controller.webcontroller;

import by.fpmibsu.bielrent.constants.HtmlPages;
import by.fpmibsu.bielrent.controller.dto.*;
import by.fpmibsu.bielrent.controller.dto.validator.ValidationException;
import by.fpmibsu.bielrent.model.entity.User;
import by.fpmibsu.bielrent.model.service.AddressService;
import by.fpmibsu.bielrent.model.service.FilterService;
import by.fpmibsu.bielrent.model.service.ListingService;
import by.fpmibsu.bielrent.model.service.PhotoService;
import by.fpmibsu.bielrent.constants.UriPatterns;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateListingController implements Controller {
    AddressService addressService = AddressService.getInstance();
    ListingService listingService = ListingService.getInstance();
    FilterService filterService = FilterService.getInstance();
    PhotoService photoService = PhotoService.getInstance();

    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp, ITemplateEngine templateEngine, WebContext webContext) throws Exception {
        templateEngine.process(HtmlPages.CREATE_LISTING, webContext, resp.getWriter());
    }

    @Override
    public void processPost(HttpServletRequest req, HttpServletResponse resp, ITemplateEngine templateEngine, WebContext webContext) throws Exception {
        var listingTitle = req.getParameter("listingTitle");
        var description = req.getParameter("description");
        var region = req.getParameter("region");
        var city = req.getParameter("city");
        var districtAdministrative = req.getParameter("districtAdministrative");
        var districtMicro = req.getParameter("districtMicro");
        var street = req.getParameter("street");
        var houseNumber = req.getParameter("houseNumber");

        var propertyType = req.getParameter("propertyType");
        var hasElevator = String.valueOf(req.getParameter("hasElevator") != null);//НЕ НАДЕЖНО
        var hasWiFi = String.valueOf(req.getParameter("hasWiFi") != null);
        var hasFurniture = String.valueOf(req.getParameter("hasFurniture") != null);
        var hasBathroom = String.valueOf(req.getParameter("hasBathroom") != null);
        var hasWashingMachine = String.valueOf(req.getParameter("hasWashingMachine") != null);

        var roomCount = req.getParameter("roomCount");
        var bedroomCount = req.getParameter("bedroomCount");
        var floorCount = req.getParameter("floorCount");
        var balconyCount = req.getParameter("balconyCount");
        var buildYear = req.getParameter("buildYear");
        var squareArea = req.getParameter("squareArea");
        var priceMonthly = req.getParameter("priceMonthly");
        var rentalPeriodStart = req.getParameter("rentalPeriodStart");
        var rentalPeriodEnd = req.getParameter("rentalPeriodEnd");

        AddressDto addressDto = AddressDto.builder()
                .city(city)
                .regionName(region)
                .districtAdministrative(districtAdministrative)
                .regionName(region)
                .districtMicro(districtMicro)
                .street(street)
                .houseNumber(houseNumber).build();
        Long addressId = -1l;

        addressId = addressService.validateAndInsertIfValid(addressDto);

        ListingDto listingDto = ListingDto.builder()
                .name(listingTitle)
                .addressId(addressId)
                .description(description)
                .userId(((User) req.getSession().getAttribute("user")).getId())
                .propertyTypeName(propertyType).build();
        Long listingId = -1l;

        listingId = listingService.validateAndInsertIfValid(listingDto);


        FilterDto filterDto = FilterDto.builder()
                .hasBathroom(hasBathroom)
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
        } catch (ValidationException e) {
            System.out.println(e.getErrors().get(0).getCode());
        }

        var part = req.getPart("image");
        PhotoDto photoDto = PhotoDto.builder()
                .photo(part)
                .listingId(listingId).build();

        photoService.insert(photoDto);

        resp.sendRedirect(UriPatterns.HOME);
    }
}
