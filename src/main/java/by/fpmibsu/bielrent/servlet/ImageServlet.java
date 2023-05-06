package by.fpmibsu.bielrent.servlet;

import by.fpmibsu.bielrent.service.ImageService;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {
    ImageService imageService = ImageService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var r = req.getRequestURI();
        var imagePath = r.replace("/images", "");

            imageService.get(imagePath).ifPresentOrElse(image -> {
                resp.setContentType("application/octet-stream");
                writeImage(image,resp);
                }, ()->resp.setStatus(404));
                ;

    }
    @SneakyThrows
    private void writeImage(InputStream image, HttpServletResponse resp) {
        int currentByte;
        try(image;var outputStream = resp.getOutputStream()){
            while((currentByte = image.read()) != -1){
                outputStream.write(currentByte);
            }
        }
    }

}
