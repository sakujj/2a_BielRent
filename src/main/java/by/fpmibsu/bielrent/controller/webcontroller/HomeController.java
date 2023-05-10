package by.fpmibsu.bielrent.controller.webcontroller;

import by.fpmibsu.bielrent.constants.HtmlPages;
import by.fpmibsu.bielrent.model.entity.ListingORM;
import by.fpmibsu.bielrent.model.entity.ListingQuery;
import by.fpmibsu.bielrent.model.service.ListingService;
import by.fpmibsu.bielrent.utility.PropertiesUtil;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HomeController implements Controller {
    ListingService listingService = ListingService.getInstance();

    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp,
                           ITemplateEngine templateEngine, WebContext webContext) throws Exception {

        List<ListingORM> list = listingService.getTopNByQuery(new ListingQuery(), 5, 21);
        webContext.setVariable("listings", list);
        webContext.setVariable("backslash", "\\");
        webContext.setVariable("imageBaseUrl", PropertiesUtil.get("image.base.url"));
        templateEngine.process(HtmlPages.INDEX, webContext, resp.getWriter());
    }

}
