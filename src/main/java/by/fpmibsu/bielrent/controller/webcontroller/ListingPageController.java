package by.fpmibsu.bielrent.controller.webcontroller;

import by.fpmibsu.bielrent.constants.HtmlPages;
import by.fpmibsu.bielrent.constants.UriPatterns;
import by.fpmibsu.bielrent.model.entity.ListingOrm;
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

        var listingOrmDto = listingService.getListingById(id).get();
        webContext.setVariable("photos", listingOrmDto.getPhotos());
        webContext.setVariable("user", listingOrmDto.getUser());
        webContext.setVariable("address", listingOrmDto.getAddress());
        webContext.setVariable("filter", listingOrmDto.getFilter());
        webContext.setVariable("name", listingOrmDto.getName());
        webContext.setVariable("description", listingOrmDto.getDescription());
        webContext.setVariable("propertyTypeName", listingOrmDto.getPropertyTypeName());

        templateEngine.process(HtmlPages.LISTING_PAGE, webContext, resp.getWriter());
    }
}
