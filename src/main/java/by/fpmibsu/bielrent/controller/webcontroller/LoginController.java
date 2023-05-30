package by.fpmibsu.bielrent.controller.webcontroller;

import by.fpmibsu.bielrent.constants.HtmlPages;
import by.fpmibsu.bielrent.controller.errorhandler.ErrorHandler;
import by.fpmibsu.bielrent.controller.templateparser.TemplateParser;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.entity.User;
import by.fpmibsu.bielrent.model.service.UserService;
import by.fpmibsu.bielrent.constants.UriPatterns;
import lombok.SneakyThrows;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController implements Controller {
    UserService userService = UserService.getInstance();

    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp, TemplateParser parser)
            throws IOException, ServletException {
        System.err.println(req.getRequestURI());
        System.err.println(req.getParameter("logout") + " #$#$#$");
        if ("true".equals(req.getParameter("logout"))) {
            String isAuthorised = (String) req.getAttribute("isAuthorised");
            if ("false".equals(isAuthorised)) {
                ErrorHandler.forwardToErrorPage(req, resp, ErrorHandler.UNAUTHORIZED);
            } else if ("true".equals(isAuthorised)) {
                req.getSession().invalidate();
                resp.setStatus(HttpsURLConnection.HTTP_OK);
                resp.sendRedirect(UriPatterns.HOME);
            } else {
                ErrorHandler.forwardToErrorPage(req, resp, ErrorHandler.NOT_FOUND);
            }
        } else if (null == req.getParameter("logout")) {
            String isAuthorised = (String) req.getAttribute("isAuthorised");
            if ("true".equals(isAuthorised)) {
                resp.sendRedirect(UriPatterns.HOME);
            } else {
                resp.setStatus(HttpsURLConnection.HTTP_OK);
                parser.parse(HtmlPages.LOGIN_PAGE, resp.getWriter());
            }
        } else {
            ErrorHandler.forwardToErrorPage(req, resp, ErrorHandler.NOT_FOUND);
        }
    }

    @Override
    public void processPost(HttpServletRequest req, HttpServletResponse resp, TemplateParser parser) throws IOException, ServletException {
        var email = req.getParameter("email");
        var password = req.getParameter("password");
        try {
            userService.getUser(email, password).ifPresentOrElse(
                    user -> onLoginSuccess(req, resp, user),
                    () -> onLoginFailed(req, resp, parser)
            );
        } catch (DaoException e) {
            ErrorHandler.forwardToErrorPage(req, resp, ErrorHandler.INTERNAL_ERROR);
        }
    }

    @SneakyThrows
    private void onLoginSuccess(HttpServletRequest req, HttpServletResponse resp, User user) {
        req.getSession().setAttribute("user", user);
        resp.setStatus(HttpsURLConnection.HTTP_OK);
        resp.sendRedirect(UriPatterns.HOME);
    }


    @SneakyThrows
    private void onLoginFailed(HttpServletRequest req, HttpServletResponse resp,
                               TemplateParser parser) {
        String error = "Invalid password or email, please retry";
        parser.getContext().setVariable("error", error);

        resp.setStatus(HttpsURLConnection.HTTP_BAD_REQUEST);
        parser.parse(HtmlPages.LOGIN_PAGE, resp.getWriter());
    }
}
