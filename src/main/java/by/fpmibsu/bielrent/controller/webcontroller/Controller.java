package by.fpmibsu.bielrent.controller.webcontroller;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    default void processGet(HttpServletRequest req, HttpServletResponse resp,
                            ITemplateEngine templateEngine, WebContext webContext)
            throws Exception{
        throw new UnsupportedOperationException("method GET is not supported");
    }

    default void processPost(HttpServletRequest req, HttpServletResponse resp,
                             ITemplateEngine templateEngine, WebContext webContext)
            throws Exception {
            throw new UnsupportedOperationException("method POST is not supported");
    }
}
