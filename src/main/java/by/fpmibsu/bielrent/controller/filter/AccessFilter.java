package by.fpmibsu.bielrent.controller.filter;

import by.fpmibsu.bielrent.model.entity.User;
import by.fpmibsu.bielrent.model.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/create-listing.html"})
public class AccessFilter extends HttpFilter {
    UserService userService = UserService.getInstance();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var user = (User) ((HttpServletRequest) request).getSession().getAttribute("user");

        if (user == null || !userService.login(user.getEmail(), user.getPassword()).isPresent()) {
            ((HttpServletRequest) request).getSession().invalidate();
            ((HttpServletResponse) response).sendRedirect("/login.html");
        }
        super.doFilter(request, response, chain);
    }
}
