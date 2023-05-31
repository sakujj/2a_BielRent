package by.fpmibsu.bielrent.controller.webcontroller;

import by.fpmibsu.bielrent.constants.HtmlPages;
import by.fpmibsu.bielrent.controller.ErrorHandler;
import by.fpmibsu.bielrent.controller.TemplateParser;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.req.UserReq;
import by.fpmibsu.bielrent.model.dtovalidator.UserValidator;
import by.fpmibsu.bielrent.model.dtovalidator.ValidationException;
import by.fpmibsu.bielrent.model.service.UserService;
import by.fpmibsu.bielrent.constants.UriPatterns;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationController implements Controller {
    private final UserService userService = UserService.getInstance();
    Logger logger = LogManager.getLogger(RegistrationController.class);

    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp, TemplateParser parser) throws IOException {
        resp.setStatus(HttpsURLConnection.HTTP_OK);
        parser.parse(HtmlPages.REGISTRATION_PAGE, resp.getWriter());
    }

    @Override
    public void processPost(HttpServletRequest req, HttpServletResponse resp, TemplateParser parser) throws IOException, ServletException {
        String error = null;
        String pass1 = req.getParameter("password1");
        String pass2 = req.getParameter("password2");

        UserReq userDto = UserReq.builder()
                .name(req.getParameter("name"))
                .email(req.getParameter("email"))
                .password(pass1)
                .build();

        try {
            if (pass1.equals(pass2)) {
                userService.insertIfValid(userDto);
            } else if (userService.isUserWithEmailInDB(userDto.getEmail())) {
                error = UserValidator.getInstance().EMAIL_ALREADY_EXISTS_ERROR.getMessage();
            } else {
                error = "Пароли не совпадают";
            }
        } catch (DaoException e) {
            logger.error("dao error in registration controller");
            ErrorHandler.forwardToErrorPage(req, resp, ErrorHandler.INTERNAL_ERROR);
            return;
        } catch (ValidationException e) {
            logger.error("invalid data in registration controller");
            error = e.getErrors().get(0).getMessage();
        }

        if (error != null) {
            var webContext = parser.getContext();
            webContext.setVariable("error", error);

            resp.setStatus(HttpsURLConnection.HTTP_BAD_REQUEST);
            parser.parse(HtmlPages.REGISTRATION_PAGE, resp.getWriter());
        } else {
            logger.info("NEW USER REGISTERED: \n" + req.getParameter("email\n")
                    + req.getParameter("name"));
            resp.setStatus(HttpsURLConnection.HTTP_ACCEPTED);
            resp.sendRedirect(UriPatterns.HOME);
        }
    }
}
