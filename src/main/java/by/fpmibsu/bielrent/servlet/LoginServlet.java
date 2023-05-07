package by.fpmibsu.bielrent.servlet;

import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.dto.UserDto;
import by.fpmibsu.bielrent.service.UserService;
import by.fpmibsu.bielrent.utility.PropertiesUtil;
import lombok.SneakyThrows;
import org.apache.catalina.manager.JspHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    UserService userService = UserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/enter.html").forward(req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var email = req.getParameter("email");
        var password = req.getParameter("password");
        userService.login(email,password).ifPresentOrElse(
                user->onLoginSuccess(req,resp,user),()->onLoginFailed(req,resp)
        );
    }
    @SneakyThrows
    private void onLoginSuccess(HttpServletRequest req, HttpServletResponse resp, UserDto us){
        req.getSession().setAttribute("user",us);
        resp.sendRedirect("/");
    }
    @SneakyThrows
    private void onLoginFailed(HttpServletRequest req, HttpServletResponse resp){

        resp.sendRedirect("/login?email=" + req.getParameter("email"));

    }
}
