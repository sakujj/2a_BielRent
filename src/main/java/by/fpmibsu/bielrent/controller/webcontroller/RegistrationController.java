package by.fpmibsu.bielrent.controller.webcontroller;

import by.fpmibsu.bielrent.constants.HtmlPages;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.InsertUserDto;
import by.fpmibsu.bielrent.model.dto.validator.InsertUserValidator;
import by.fpmibsu.bielrent.model.dto.validator.ValidationException;
import by.fpmibsu.bielrent.model.service.UserService;
import by.fpmibsu.bielrent.constants.UriPatterns;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationController implements Controller {
    private final UserService userService = UserService.getInstance();

    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp, ITemplateEngine templateEngine, WebContext webContext) throws Exception {
        templateEngine.process(HtmlPages.REGISTRATION, webContext, resp.getWriter());
    }

    @Override
    public void processPost(HttpServletRequest req, HttpServletResponse resp, ITemplateEngine templateEngine, WebContext webContext) throws Exception {
        String error = null;
        String pass1 = req.getParameter("password1");
        String pass2 = req.getParameter("password2");

        InsertUserDto userDto = InsertUserDto.builder()
                .name(req.getParameter("name"))
                .email(req.getParameter("email"))
                .password(pass1)
                .build();

        try {
            if (pass1.equals(pass2)) {
                userService.insertIfValid(userDto);
            } else if (userService.isUserWithEmailInDB(userDto.getEmail())) {
                error = InsertUserValidator.getInstance().EMAIL_ALREADY_EXISTS_ERROR.getMessage();
            } else {
                error = "Пароли не совпадают";
            }
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            error = e.getErrors().get(0).getMessage();
        }

        if (error != null) {
            webContext.setVariable("error", error);
            templateEngine.process(HtmlPages.REGISTRATION, webContext, resp.getWriter());
        } else {
           resp.sendRedirect(UriPatterns.HOME);
        }
    }
}
