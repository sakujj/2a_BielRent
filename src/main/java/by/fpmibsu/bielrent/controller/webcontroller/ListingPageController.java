package by.fpmibsu.bielrent.controller.webcontroller;

import by.fpmibsu.bielrent.constants.HtmlPages;
import by.fpmibsu.bielrent.controller.ErrorHandler;
import by.fpmibsu.bielrent.controller.TemplateParser;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.resp.ListingOrmResp;
import by.fpmibsu.bielrent.model.service.ListingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListingPageController implements Controller {

    private static final ListingService listingService = ListingService.getInstance();
    private static final Logger logger = LogManager.getLogger(ListingPageController.class);

    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp, TemplateParser parser)
            throws IOException, ServletException {
        String listingId = req.getParameter("id");

        long id;
        try {
            id = Long.parseLong(listingId);
        } catch (NumberFormatException e) {
            logger.error(e);
            ErrorHandler.forwardToErrorPage(req, resp, ErrorHandler.NOT_FOUND);
            return;
        }

        if (id <= 0 || id > listingService.getListingCount()) {
            logger.error("Specified page does not exist" + " [ListingPageController]");
            ErrorHandler.forwardToErrorPage(req, resp, ErrorHandler.NOT_FOUND);
            return;
        }

        ListingOrmResp listingOrmResp;
        try {
            listingOrmResp = listingService.getListingById(id).get();
        } catch (DaoException e) {
            logger.error(e + " [ListingPageController]");
            ErrorHandler.forwardToErrorPage(req, resp, ErrorHandler.INTERNAL_ERROR);
            return;
        }

        var webContext = parser.getContext();
        webContext.setVariable("photos", listingOrmResp.getPhotos());
        webContext.setVariable("user", listingOrmResp.getUser());
        webContext.setVariable("address", listingOrmResp.getAddress());
        webContext.setVariable("filter", listingOrmResp.getFilter());
        webContext.setVariable("name", listingOrmResp.getName());
        webContext.setVariable("description", listingOrmResp.getDescription());
        webContext.setVariable("propertyTypeName", listingOrmResp.getPropertyTypeName());

        resp.setStatus(HttpsURLConnection.HTTP_OK);
        parser.parse(HtmlPages.LISTING_PAGE, resp.getWriter());
    }
}
