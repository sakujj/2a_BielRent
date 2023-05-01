package by.fpmibsu.bielrent.servlet;

import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.dto.InsertUserDto;
import by.fpmibsu.bielrent.entity.Role;
import by.fpmibsu.bielrent.entity.User;
import by.fpmibsu.bielrent.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pass1 = req.getParameter("password1");
        String pass2 = req.getParameter("password2");
        if (!pass1.equals(pass2)) {
            System.out.println("errrPass");
        }

        InsertUserDto userDto = InsertUserDto.builder()
                .name(req.getParameter("name"))
                .email(req.getParameter("email"))
                .password(pass1)
                .build();
        try {
            userService.insert(userDto);
        } catch (DaoException e) {
            System.out.println("errrDB");
        }

        resp.sendRedirect("/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

}
