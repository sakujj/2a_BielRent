package by.fpmibsu.bielrent.controller.webcontroller;

import by.fpmibsu.bielrent.controller.templateparser.TemplateParser;
import by.fpmibsu.bielrent.model.service.ImageService;
import by.fpmibsu.bielrent.constants.UriPatterns;
import lombok.SneakyThrows;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class ImagesController implements Controller {
    private final ImageService imageService = ImageService.getInstance();

    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp, TemplateParser parser)  {
        var r = req.getRequestURI().substring(4);

        var imagePath = r.replace(UriPatterns.IMAGES, "");
        imageService.getImage(imagePath).ifPresentOrElse(image -> {
            resp.setContentType("application/octet-stream");
            resp.setStatus(HttpURLConnection.HTTP_OK);
            writeImage(image,resp);
        }, ()->resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND));
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
