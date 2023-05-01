package by.fpmibsu.bielrent.service;

import by.fpmibsu.bielrent.utility.PropertiesUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageService {
    private static final ImageService INSTANCE = new ImageService();

    private final String BASE_PATH = PropertiesUtil.get("image.base.url");

    public void upload(String imagePath, InputStream imageContent) {
        Path imageFullPath = Path.of(BASE_PATH, imagePath);
        try (imageContent) {
            Files.createDirectories(imageFullPath.getParent());
            Files.write(imageFullPath, imageContent.readAllBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFileExt(String fileName) {
        int index = fileName.lastIndexOf(".");
        String fileExt = "";
        if (index != fileName.length() - 1 || index != -1) {
            fileExt = fileName.substring(index);
        }

        return fileExt;
    }
    public static ImageService getInstance() {
        return INSTANCE;
    }
}
