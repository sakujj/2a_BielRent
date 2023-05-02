package by.fpmibsu.bielrent.servlet;

import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.dto.InsertUserDto;
import by.fpmibsu.bielrent.dto.validator.InsertUserValidator;
import by.fpmibsu.bielrent.dto.validator.ValidationResult;
import by.fpmibsu.bielrent.service.UserService;
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

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    private JavaxServletWebApplication webApplication;
    private TemplateEngine templateEngine;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        webApplication = JavaxServletWebApplication.buildApplication(this.getServletContext());

        WebApplicationTemplateResolver templateResolver
                = new WebApplicationTemplateResolver(webApplication);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/");
        templateResolver.setSuffix("");
        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var webExchange = webApplication.buildExchange(req, resp);
        var webContext = new WebContext(webExchange, webExchange.getLocale());

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
                ValidationResult vr = userService.validateAndInsertIfValid(userDto);

                if (!vr.getErrors().isEmpty()) {
                    error = vr.getErrors().get(0).getMessage();
                }
            } else if (userService.doesUserWithEmailInDB(userDto.getEmail())) {
                error = InsertUserValidator.getInstance().EMAIL_ALREADY_EXISTS_ERROR.getMessage();
            } else {
                error = "Пароли не совпадают";
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }

        if (error != null) {
            webContext.setVariable("error", error);
            templateEngine.process("reg.html", webContext, resp.getWriter());
        } else {
            resp.sendRedirect("/");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doGet(req, resp);
    }

}
