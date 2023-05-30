package by.fpmibsu.bielrent.controller.filter;

import by.fpmibsu.bielrent.constants.UriPatterns;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        var httpReq = (HttpServletRequest) req;
        var httpResp = (HttpServletResponse) res;

        String uri = httpReq.getRequestURI();

        if (uri.startsWith("/css")
        || uri.startsWith("/js")
        || uri.startsWith("/webfonts")
        || UriPatterns.GET_USERS_REST_API.equals(uri)) {
            chain.doFilter(httpReq, httpResp);
        } else {
            httpReq.getRequestDispatcher(UriPatterns.ROOT + uri).forward(httpReq, httpResp);
        }
    }
}
