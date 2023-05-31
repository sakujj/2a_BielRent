package by.fpmibsu.bielrent.controller;

import by.fpmibsu.bielrent.constants.UriPatterns;
import lombok.experimental.UtilityClass;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ErrorHandler {
    String INTERNAL_ERROR_INFO =  "An internal server error";
    String NOT_FOUND_INFO = "Not found";
    String UNAUTHORIZED_INFO = "Unauthorized";
    String BAD_REQUEST_INFO = "Bad request";
    String FORBIDDEN_INFO = "Forbidden";

    public int BAD_REQUEST = 400;
    public int UNAUTHORIZED = 401;
    public int FORBIDDEN = 403;
    public int NOT_FOUND = 404;
    public int INTERNAL_ERROR = 500;

    Map<Integer, String> statusCodeInfo = new HashMap<>();

    static {
        statusCodeInfo.put(BAD_REQUEST, BAD_REQUEST_INFO);
        statusCodeInfo.put(UNAUTHORIZED, UNAUTHORIZED_INFO);
        statusCodeInfo.put(FORBIDDEN, FORBIDDEN_INFO);
        statusCodeInfo.put(NOT_FOUND, NOT_FOUND_INFO);
        statusCodeInfo.put(INTERNAL_ERROR, INTERNAL_ERROR_INFO);
    }

    public void forwardToErrorPage(HttpServletRequest req, HttpServletResponse resp, int statusCode) throws ServletException, IOException {
        resp.setStatus(statusCode);
        req.setAttribute("ERROR_INFO", statusCodeInfo.get(statusCode));
        req.getRequestDispatcher(UriPatterns.ROOT + UriPatterns.ERROR_PAGE).forward(req, resp);
    }
}
