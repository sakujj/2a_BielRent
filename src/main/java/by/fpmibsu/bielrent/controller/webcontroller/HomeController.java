package by.fpmibsu.bielrent.controller.webcontroller;

import by.fpmibsu.bielrent.constants.HtmlPages;
import by.fpmibsu.bielrent.controller.ErrorHandler;
import by.fpmibsu.bielrent.controller.TemplateParser;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.resp.ListingOrmResp;
import by.fpmibsu.bielrent.model.entity.ListingQuery;
import by.fpmibsu.bielrent.model.service.ListingOrmService;
import by.fpmibsu.bielrent.model.service.ListingService;
import by.fpmibsu.bielrent.utility.PropertiesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class HomeController implements Controller {
    private static final Logger logger = LogManager.getLogger(HomeController.class);
    private static final ListingService listingService = ListingService.getInstance();
    private static final ListingOrmService listingOrmService = ListingOrmService.getInstance();


    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp, TemplateParser parser)
            throws IOException, ServletException {
        Map<Integer, Integer> pageMap = getPageNumbers(req);
        List<ListingOrmResp> list;
        try {
            list = listingOrmService.queryListingsResp(new ListingQuery(), 6, 5 * (pageMap.get(0) - 1));
        } catch (DaoException e) {
            logger.error(e);
            ErrorHandler.forwardToErrorPage(req, resp, ErrorHandler.INTERNAL_ERROR);
            return;
        }

        var subList = list.subList(0, list.size() >= 6 ? 5 : list.size());

        Set<Integer> disabledNumbers = getDisabledPaginationNumbers(pageMap, list);

        var webContext = parser.getContext();
        webContext.setVariable("disabledPaginationNumbers", disabledNumbers);
        webContext.setVariable("pageMap", pageMap);
        webContext.setVariable("listings", subList);
        webContext.setVariable("imageBaseUrl", PropertiesUtil.get("image.base.url"));

        resp.setStatus(HttpsURLConnection.HTTP_OK);
        parser.parse(HtmlPages.INDEX_PAGE, resp.getWriter());
    }

    /**
     * @param req
     * @return pageMap, where KEYS are:
     * CURRENT_PAGE_NUMBER == 0, PAGINATION_NUMBER_i == i
     */
    Map<Integer, Integer> getPageNumbers(HttpServletRequest req) {
        Map<Integer, Integer> pageNumbers = new HashMap<>();

        String pageNumberString = req.getParameter("page");

        pageNumbers.put(0, 1);
        if (pageNumberString != null) {
            try {
                pageNumbers.put(0, Integer.parseInt(pageNumberString));
                if (pageNumbers.get(0) < 1 || (pageNumbers.get(0) - 1) * 5 > listingService.getListingCount()) {
                    pageNumbers.put(0, 1);
                }
            } catch (NumberFormatException e) {
                logger.error("number format wrong in home controller\n");
            }
        }

        if (pageNumbers.get(0) == 1) {
            pageNumbers.put(1, 1);
            pageNumbers.put(2, 2);
            pageNumbers.put(3, 3);
        } else {
            pageNumbers.put(1, pageNumbers.get(0) - 1);
            pageNumbers.put(2, pageNumbers.get(0));
            pageNumbers.put(3, pageNumbers.get(0) + 1);
        }
        return pageNumbers;
    }


    /**
     * @param pageMap
     * @param listings
     * @return disabled pagination numbers set,
     * NEXT_PAGE == 0, PREV_PAGE == -1, PAGE_i == i.
     */
    Set<Integer> getDisabledPaginationNumbers(Map<Integer, Integer> pageMap,
                                              List<ListingOrmResp> listings) {
        HashSet<Integer> disabledNumbers = new HashSet<>();
        //Next == 0
        //Prev == -1
        if (pageMap.get(0).equals(pageMap.get(1))) {
            disabledNumbers.add(-1);
            if (listingService.getListingCount() <= 10) {
                disabledNumbers.add(3);
            }
            if (listingService.getListingCount() <= 5) {
                disabledNumbers.add(2);
                disabledNumbers.add(0);
            }
        }

        if (listings.size() < 6) {
            disabledNumbers.add(3);
            disabledNumbers.add(0);
        }

        return disabledNumbers;
    }
}
