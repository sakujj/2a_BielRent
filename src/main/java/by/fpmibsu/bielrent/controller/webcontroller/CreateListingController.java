package by.fpmibsu.bielrent.controller.webcontroller;

import by.fpmibsu.bielrent.constants.HtmlPages;
import by.fpmibsu.bielrent.controller.ErrorHandler;
import by.fpmibsu.bielrent.controller.TemplateParser;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.req.*;
import by.fpmibsu.bielrent.model.dtovalidator.ValidationException;
import by.fpmibsu.bielrent.model.entity.User;
import by.fpmibsu.bielrent.model.service.ListingOrmService;
import by.fpmibsu.bielrent.model.service.ListingService;
import by.fpmibsu.bielrent.constants.UriPatterns;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateListingController implements Controller {
    private static final Logger logger = LogManager.getLogger(CreateListingController.class);
    private static final ListingService listingService = ListingService.getInstance();
    private static final ListingOrmService listingOrmService = ListingOrmService.getInstance();

    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp, TemplateParser parser) throws IOException {
        resp.setStatus(HttpsURLConnection.HTTP_OK);
        var user = (User)req.getSession().getAttribute("user");
        parser.getContext().setVariable("user", user);
        parser.parse(HtmlPages.CREATE_LISTING_PAGE, resp.getWriter());
    }

    @Override
    public void processPost(HttpServletRequest req, HttpServletResponse resp, TemplateParser parser) throws IOException, ServletException {

        AddressReq addressReq = AddressReq.builder()
                .city(req.getParameter("city"))
                .regionName(req.getParameter("region"))
                .districtAdministrative(req.getParameter("districtAdministrative"))
                .districtMicro(req.getParameter("districtMicro"))
                .street(req.getParameter("street"))
                .houseNumber(req.getParameter("houseNumber"))
                .build();

        ;
        FilterReq filterReq = FilterReq.builder()
                .hasBathroom(String.valueOf(req.getParameter("hasBathroom") != null))
                .hasElevator(String.valueOf(req.getParameter("hasElevator") != null))
                .hasFurniture(String.valueOf(req.getParameter("hasFurniture") != null))
                .hasWashingMachine(String.valueOf(req.getParameter("hasWashingMachine") != null))
                .hasWifi(String.valueOf(req.getParameter("hasWiFi") != null))
                .balconyCount(req.getParameter("balconyCount"))
                .bedroomCount(req.getParameter("bedroomCount"))
                .floorCount(req.getParameter("floorCount"))
                .roomCount(req.getParameter("roomCount"))
                .buildYear(req.getParameter("buildYear"))
                .squareArea(req.getParameter("squareArea"))
                .priceMonthly(req.getParameter("priceMonthly"))
                .rentalPeriodStart(req.getParameter("rentalPeriodStart"))
                .rentalPeriodEnd(req.getParameter("rentalPeriodEnd"))
                .build();

        PhotoReq photoReq;
        photoReq = PhotoReq.builder()
                .photo(req.getPart("image"))
                .build();


        User user = (User) req.getSession().getAttribute("user");
        UserReq userReq = UserReq.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        ListingOrmReq listingOrmReq = ListingOrmReq.builder()
                .name(req.getParameter("listingTitle"))
                .description(req.getParameter("description"))
                .propertyTypeName(req.getParameter("propertyType"))
                .address(addressReq)
                .filter(filterReq)
                .user(userReq)
                .photos(List.of(photoReq))
                .build();


        try {
            listingOrmService.insertIfValid(listingOrmReq);
        } catch (ValidationException ve) {
            logger.error(ve.getErrors().get(0).getMessage());
            resp.setStatus(HttpsURLConnection.HTTP_BAD_REQUEST);
            parser.parse(HtmlPages.CREATE_LISTING_PAGE, resp.getWriter());

            return;
        } catch (DaoException e) {
            logger.error(e);
            ErrorHandler.forwardToErrorPage(req, resp, ErrorHandler.INTERNAL_ERROR);
            return;
        }

        logger.info("New listing successfully created");
        resp.sendRedirect(UriPatterns.HOME);
    }
}
