package by.fpmibsu.bielrent.mapper.toentity;

import by.fpmibsu.bielrent.dto.PhotoDto;
import by.fpmibsu.bielrent.entity.Photo;
import by.fpmibsu.bielrent.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhotoMapperToEntity implements Mapper<Photo, PhotoDto> {
    private static final String IMAGE_FOLDER = "photos\\";

    private static final PhotoMapperToEntity INSTANCE = new PhotoMapperToEntity();
    public static PhotoMapperToEntity getInstance() {
        return INSTANCE;
    }

    @Override
    public Photo mapFrom(PhotoDto obj) {
        Photo photo = Photo.builder()
                .id(obj.getId())
                .listingId(obj.getListingId())
                .path(IMAGE_FOLDER + obj.getId() + obj.getPhoto().getSubmittedFileName())
                .build();
        return photo;
    }
}
