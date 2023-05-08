package by.fpmibsu.bielrent.servlet;

import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.dto.UserDto;
import by.fpmibsu.bielrent.service.UserService;
import by.fpmibsu.bielrent.utility.PropertiesUtil;
import lombok.SneakyThrows;
import org.apache.catalina.manager.JspHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private JavaxServletWebApplication webApplication;
    private TemplateEngine templateEngine;
    private WebApplicationTemplateResolver templateResolver;
    UserService userService = UserService.getInstance();
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        webApplication = JavaxServletWebApplication.buildApplication(this.getServletContext());

        templateResolver
                = new WebApplicationTemplateResolver(webApplication);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/");
        templateResolver.setSuffix("");
        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/enter.html").forward(req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var email = req.getParameter("email");
        var password = req.getParameter("password");
        req.getSession().setAttribute("email", email);
        userService.login(email,password).ifPresentOrElse(
                user->onLoginSuccess(req,resp,user),()->onLoginFailed(req,resp)
        );
    }
    @SneakyThrows
    private void onLoginSuccess(HttpServletRequest req, HttpServletResponse resp, UserDto us){

        resp.sendRedirect("/");
    }
    @SneakyThrows
    private void onLoginFailed(HttpServletRequest req, HttpServletResponse resp){
        var webExchange = webApplication.buildExchange(req, resp);
        var webContext = new WebContext(webExchange, webExchange.getLocale());
        String error = "Invalid password or email, please retry";
        webContext.setVariable("error", error);
        templateEngine.process("enter.html", webContext, resp.getWriter());
        //resp.sendRedirect("/login");


    }
}
