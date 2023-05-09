package by.fpmibsu.bielrent.filter;

import by.fpmibsu.bielrent.dto.UserDto;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/createListing")
public class CreateListingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //super.doFilter(request, response, chain);
        var user = (UserDto)((HttpServletRequest)servletRequest).getSession().getAttribute("user");
        if(user != null){
            filterChain.doFilter(servletRequest,servletResponse);
        }
        else{
            ((HttpServletResponse)servletResponse).sendRedirect("/login");
        }
    }
}
