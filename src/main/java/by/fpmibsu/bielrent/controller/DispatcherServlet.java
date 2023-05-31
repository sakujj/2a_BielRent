package by.fpmibsu.bielrent.controller;

import by.fpmibsu.bielrent.controller.webcontroller.CreateListingController;
import by.fpmibsu.bielrent.controller.webcontroller.ListingPageController;
import by.fpmibsu.bielrent.model.connectionpool.ConnectionPoolImpl;
import by.fpmibsu.bielrent.controller.webcontroller.Controller;
import by.fpmibsu.bielrent.constants.UriPatterns;
import lombok.SneakyThrows;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig()
@WebServlet(urlPatterns = {"*.html", "/images/*"})
public class DispatcherServlet extends HttpServlet {
    private JavaxServletWebApplication webApplication;
    private TemplateEngine templateEngine;
    private WebApplicationTemplateResolver templateResolver;

    @Override
    public void init() {
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


    @SneakyThrows
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        var httpReq = (HttpServletRequest) req;
        var httpResp = (HttpServletResponse) res;
        String uri = httpReq.getRequestURI();
        Controller controller = ControllerMappings.resolveControllerByPath(uri);
        if (controller == null) {
            ErrorHandler.forwardToErrorPage(httpReq, httpResp, ErrorHandler.NOT_FOUND);
            return;
        }
        var webExchange = this.webApplication.buildExchange(httpReq, httpResp);
        var webContext = new WebContext(webExchange, webExchange.getLocale());

        putUriPatternsInContext(webContext);
        webContext.setVariable("isAuthorised", req.getAttribute("isAuthorised"));

        TemplateParser parser = new TemplateParser(webContext, this.templateEngine);
        switch (httpReq.getMethod()) {
            case "GET" -> controller.processGet(httpReq, httpResp, parser);
            case "POST" -> controller.processPost(httpReq, httpResp, parser);
        }

    }


    void putUriPatternsInContext(WebContext context) {
        context.setVariable("HOME_URI", UriPatterns.HOME);
        context.setVariable("LOGIN_URI", UriPatterns.LOGIN);
        context.setVariable("LISTING_URI", UriPatterns.LISTING_PAGE);
        context.setVariable("REGISTRATION_URI", UriPatterns.REGISTRATION);
        context.setVariable("CREATE_LISTING_URI", UriPatterns.CREATE_LISTING);
        context.setVariable("IMAGES_URI", UriPatterns.IMAGES);
        context.setVariable("PROFILE_URI", UriPatterns.PROFILE_PAGE);
    }

    @Override
    public void destroy() {
        ConnectionPoolImpl.getInstance().close();
    }
}
