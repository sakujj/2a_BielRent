package by.fpmibsu.bielrent.controller.webcontroller;

import by.fpmibsu.bielrent.constants.HtmlPages;
import by.fpmibsu.bielrent.controller.ErrorHandler;
import by.fpmibsu.bielrent.controller.TemplateParser;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.resp.ListingOrmResp;
import by.fpmibsu.bielrent.model.dto.resp.UserResp;
import by.fpmibsu.bielrent.model.dtomapper.ListingMapper;
import by.fpmibsu.bielrent.model.dtomapper.ListingOrmMapper;
import by.fpmibsu.bielrent.model.entity.ListingQuery;
import by.fpmibsu.bielrent.model.entity.User;
import by.fpmibsu.bielrent.model.service.ListingOrmService;
import by.fpmibsu.bielrent.model.service.ListingService;
import by.fpmibsu.bielrent.model.service.UserService;
import by.fpmibsu.bielrent.utility.PropertiesUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ProfileController implements Controller {
    private static final Logger logger = LogManager.getLogger(ProfileController.class);
    private static final ListingService listingService = ListingService.getInstance();
    private static final ListingOrmService listingOrmService = ListingOrmService.getInstance();
    private final ListingOrmMapper listingOrmMapper = ListingOrmMapper.getInstance();

    private static final Integer LISTINGS_PER_PAGE = 2;


    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp, TemplateParser parser) throws IOException, ServletException {

        String listingId = req.getParameter("id");
        long id;
        try {
            id = Long.parseLong(listingId);
        } catch (NumberFormatException e) {
            logger.error(e);
            ErrorHandler.forwardToErrorPage(req, resp, ErrorHandler.NOT_FOUND);
            return;
        }

//        Optional<User> userResp;
//        try {
//            userResp = UserService.getInstance().getUser(id);
//        } catch (DaoException e) {
//            logger.error(e);
//            ErrorHandler.forwardToErrorPage(req, resp, ErrorHandler.INTERNAL_ERROR);
//            return;
//        }

        var currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null  || currentUser.getId() != id) {
            ErrorHandler.forwardToErrorPage(req, resp, ErrorHandler.UNAUTHORIZED);
            return;
        }

//        if (userResp.isEmpty()) {
//            ErrorHandler.forwardToErrorPage(req, resp, ErrorHandler.NOT_FOUND);
//            return;
//        }



        List<ListingOrmResp> listings;
        try {
            listings = listingOrmService.getListingsByUserId(id).stream()
                    .map(listingOrmMapper::fromEntity).collect(Collectors.toList());
        } catch (
                DaoException e) {
            ErrorHandler.forwardToErrorPage(req, resp, ErrorHandler.INTERNAL_ERROR);
            return;
        }


        var webContext = parser.getContext();
        webContext.setVariable("imageBaseUrl", PropertiesUtil.get("image.base.url"));
        webContext.setVariable("listings", listings);
        if (webContext.getVariable("isAuthorised") == "true") {
            webContext.setVariable("user", currentUser);
        }

        resp.setStatus(HttpsURLConnection.HTTP_OK);
        parser.parse(HtmlPages.PROFILE_PAGE, resp.getWriter());
    }

}


