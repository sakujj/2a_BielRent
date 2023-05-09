package by.fpmibsu.bielrent.servlet;

import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.dto.PhotoDto;
import by.fpmibsu.bielrent.service.ImageService;
import by.fpmibsu.bielrent.service.PhotoService;
import by.fpmibsu.bielrent.utility.PropertiesUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
//TODO(зачем он???)


@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 20, maxFileSize = 1024 * 1024 * 20)
@WebServlet("/inda")
public class UploadServlet extends HttpServlet {
    PhotoService photoService = PhotoService.getInstance();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PhotoDto photoDto = PhotoDto.builder()
                //.id(1)
                .listingId(1)
                .photo(req.getPart("photo"))
                .build();
        try {
            photoService.insert(photoDto);
        } catch (DaoException e) {
            System.out.println(2);
        }
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/");
    }
}
