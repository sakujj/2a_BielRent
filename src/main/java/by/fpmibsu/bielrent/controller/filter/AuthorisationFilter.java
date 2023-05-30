//package by.fpmibsu.bielrent.controller.filter;
//
//import by.fpmibsu.bielrent.constants.UriPatterns;
//import by.fpmibsu.bielrent.model.entity.User;
//import by.fpmibsu.bielrent.model.service.UserService;
//import lombok.SneakyThrows;
//
//import javax.net.ssl.HttpsURLConnection;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Set;
//
//
//public class AuthorisationFilter extends HttpFilter {
//    UserService userService = UserService.getInstance();
//    Set<String> urisRequiringAuthorisation = Set.of(
//            UriPatterns.LISTING_PAGE
//    );
//
//
//    @SneakyThrows
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        var httpReq = (HttpServletRequest) request;
//        var httpResp = (HttpServletResponse) response;
//
//        var user = (User) httpReq.getSession().getAttribute("user");
//
//        if (user == null || userService.getUser(user.getEmail(), user.getPassword()).isEmpty()) {
//            httpReq.setAttribute("isAuthorised", "false");
//            if (urisRequiringAuthorisation.contains(httpReq.getRequestURI())) {
//                httpResp.setStatus(HttpsURLConnection.HTTP_UNAUTHORIZED);
//                httpReq.getSession().invalidate();
//                httpResp.sendRedirect(UriPatterns.LOGIN);
//            }
//        } else {
//            request.setAttribute("isAuthorised", "true");
//        }
//
//        super.doFilter(request, response, chain);
//    }
//}
