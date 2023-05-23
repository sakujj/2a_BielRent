package by.fpmibsu.bielrent.controller.webcontroller;

import by.fpmibsu.bielrent.constants.HtmlPages;
import by.fpmibsu.bielrent.constants.UriPatterns;
import by.fpmibsu.bielrent.model.entity.ListingORM;
import by.fpmibsu.bielrent.model.service.ListingService;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListingPageController implements Controller{

    ListingService listingService = ListingService.getInstance();
    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp,
                           ITemplateEngine templateEngine, WebContext webContext) throws Exception {
        String listingId = req.getParameter("id");
        long id = -1;
        try {
            id = Long.parseLong(listingId);
        } catch (NumberFormatException e) {
        }

        if (id == -1) {
            resp.sendRedirect(UriPatterns.HOME);
            return;
        }

        ListingORM listingORM = listingService.getListingById(id).get();
        webContext.setVariable("photos", listingORM.getPhotos());
        webContext.setVariable("user", listingORM.getUser());
        webContext.setVariable("address", listingORM.getAddress());
        webContext.setVariable("filter", listingORM.getFilter());
        webContext.setVariable("name", listingORM.getName());
        webContext.setVariable("description", listingORM.getDescription());
        webContext.setVariable("propertyTypeName", listingORM.getPropertyTypeName());

        templateEngine.process(HtmlPages.LISTING_PAGE, webContext, resp.getWriter());
    }
}
