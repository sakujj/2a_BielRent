package by.fpmibsu.bielrent.controller.filter;

import by.fpmibsu.bielrent.model.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.*;

@WebFilter("/*")
public class BasicFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setLocale(request.getLocale());
        chain.doFilter(request, response);

    }


}
