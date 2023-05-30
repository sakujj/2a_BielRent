package by.fpmibsu.bielrent.model.dtomapper;

import by.fpmibsu.bielrent.model.dto.req.PhotoReq;
import by.fpmibsu.bielrent.model.dto.resp.PhotoResp;
import by.fpmibsu.bielrent.model.entity.Photo;
import by.fpmibsu.bielrent.model.service.ImageService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhotoMapper {
    private static final String IMAGE_FOLDER = "photos\\";

    private static final PhotoMapper INSTANCE = new PhotoMapper();
    public static PhotoMapper getInstance() {
        return INSTANCE;
    }

    public Photo toEntity(PhotoReq obj, Long listingId) {
        String fileName = IMAGE_FOLDER + listingId
                + "-"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSSSSS"))
                + ImageService.getFileExt(obj.getPhoto().getSubmittedFileName());

        Photo photo = Photo.builder()
                .id(-1L)
                .listingId(listingId)
                .path(fileName)
                .build();
        return photo;
    }

    public PhotoResp fromEntity(Photo obj) {
        return PhotoResp.builder()
                .id(obj.getId())
                .listingId(obj.getId())
                .path(obj.getPath())
                .build();
    }
}
