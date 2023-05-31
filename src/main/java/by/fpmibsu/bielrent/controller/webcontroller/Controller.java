package by.fpmibsu.bielrent.controller.webcontroller;

import by.fpmibsu.bielrent.controller.TemplateParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Controller {
    default void processGet(HttpServletRequest req, HttpServletResponse resp, TemplateParser parser)
            throws IOException, ServletException {
        throw new UnsupportedOperationException("method GET is not supported");
    }

    default void processPost(HttpServletRequest req, HttpServletResponse resp,TemplateParser parser)
            throws IOException, ServletException {
        throw new UnsupportedOperationException("method POST is not supported");
    }
}
