package by.fpmibsu.bielrent.controller.webcontroller;

import by.fpmibsu.bielrent.model.service.ImageService;
import by.fpmibsu.bielrent.constants.UriPatterns;
import lombok.SneakyThrows;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

public class ImagesController implements Controller {
    private final ImageService imageService = ImageService.getInstance();

    @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp, ITemplateEngine templateEngine, WebContext webContext) throws Exception {
        var r = req.getRequestURI();

        var imagePath = r.replace(UriPatterns.IMAGES, "");
        imageService.get(imagePath).ifPresentOrElse(image -> {
            resp.setContentType("application/octet-stream");
            writeImage(image,resp);
        }, ()->resp.setStatus(404));
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
