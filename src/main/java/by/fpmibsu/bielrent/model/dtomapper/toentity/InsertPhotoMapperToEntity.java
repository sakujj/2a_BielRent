package by.fpmibsu.bielrent.model.dtomapper.toentity;

import by.fpmibsu.bielrent.model.dto.InsertPhotoDto;
import by.fpmibsu.bielrent.model.entity.Photo;
import by.fpmibsu.bielrent.model.dtomapper.Mapper;
import by.fpmibsu.bielrent.model.service.ImageService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InsertPhotoMapperToEntity implements Mapper<Photo, InsertPhotoDto> {
    private static final String IMAGE_FOLDER = "photos\\";

    private static final InsertPhotoMapperToEntity INSTANCE = new InsertPhotoMapperToEntity();
    public static InsertPhotoMapperToEntity getInstance() {
        return INSTANCE;
    }

    @Override
    public Photo mapFrom(InsertPhotoDto obj) {

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
