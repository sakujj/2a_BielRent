package by.fpmibsu.bielrent.model.mapper.toentity;

import by.fpmibsu.bielrent.controller.dto.PhotoDto;
import by.fpmibsu.bielrent.model.entity.Photo;
import by.fpmibsu.bielrent.model.mapper.Mapper;
import by.fpmibsu.bielrent.model.service.ImageService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhotoMapperToEntity implements Mapper<Photo, PhotoDto> {
    private static final String IMAGE_FOLDER = "photos\\";

    private static final PhotoMapperToEntity INSTANCE = new PhotoMapperToEntity();
    public static PhotoMapperToEntity getInstance() {
        return INSTANCE;
    }

    @Override
    public Photo mapFrom(PhotoDto obj) {

        String fileName = IMAGE_FOLDER + obj.getListingId()
                + "-"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSSSSS"))
                + ImageService.getFileExt(obj.getPhoto().getSubmittedFileName());

        Photo photo = Photo.builder()
                //.id(obj.getId())
                .listingId(obj.getListingId())
                .path(fileName)
                .build();
        return photo;
    }
}
