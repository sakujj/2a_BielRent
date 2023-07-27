package by.fpmibsu.bielrent.controller.webcontroller;

import by.fpmibsu.bielrent.constants.HtmlPages;
import by.fpmibsu.bielrent.controller.TemplateParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorPageController implements Controller{

    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp, TemplateParser parser) throws IOException {
        int statusCode = resp.getStatus();

        var webContext = parser.getContext();
        webContext.setVariable("statusCode", statusCode);
        webContext.setVariable("errorInfo", req.getAttribute("ERROR_INFO"));

       parser.parse(HtmlPages.ERROR_PAGE, resp.getWriter());
    }
}
