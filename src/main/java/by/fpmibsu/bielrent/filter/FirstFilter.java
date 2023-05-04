package by.fpmibsu.bielrent.filter;

import by.fpmibsu.bielrent.connectionpool.ConnectionPoolImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import java.io.IOException;

@WebFilter("/*")
public class FirstFilter extends HttpFilter {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setLocale(request.getLocale());
        super.doFilter(request, response, chain);
    }

    @Override
    public void destroy() {
        ConnectionPoolImpl.getInstance().close();
    }
}
