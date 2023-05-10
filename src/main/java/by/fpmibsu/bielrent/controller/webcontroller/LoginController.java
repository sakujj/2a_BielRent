package by.fpmibsu.bielrent.controller.webcontroller;

import by.fpmibsu.bielrent.constants.HtmlPages;
import by.fpmibsu.bielrent.model.entity.User;
import by.fpmibsu.bielrent.model.service.UserService;
import by.fpmibsu.bielrent.constants.UriPatterns;
import lombok.SneakyThrows;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController implements Controller {
    UserService userService = UserService.getInstance();

    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp, ITemplateEngine templateEngine, WebContext webContext) throws Exception {
        templateEngine.process(HtmlPages.LOGIN, webContext, resp.getWriter());
    }

    @Override
    public void processPost(HttpServletRequest req, HttpServletResponse resp, ITemplateEngine templateEngine,  WebContext webContext) throws Exception {
        var email = req.getParameter("email");
        var password = req.getParameter("password");
        userService.login(email,password).ifPresentOrElse(
                user->onLoginSuccess(req,resp,user),
                ()->onLoginFailed(req,resp, templateEngine, webContext)
        );
    }

    @SneakyThrows
    private void onLoginSuccess(HttpServletRequest req, HttpServletResponse resp, User user){
        req.getSession().setAttribute("user",user);
        resp.sendRedirect(UriPatterns.HOME);
    }


    @SneakyThrows
    private void onLoginFailed(HttpServletRequest req, HttpServletResponse resp,
                               ITemplateEngine templateEngine, WebContext webContext){
        String error = "Invalid password or email, please retry";
        webContext.setVariable("error", error);
        templateEngine.process(HtmlPages.LOGIN, webContext, resp.getWriter());
    }
}
