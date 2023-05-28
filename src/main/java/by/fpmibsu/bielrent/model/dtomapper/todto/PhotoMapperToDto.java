package by.fpmibsu.bielrent.model.dtomapper.todto;

import by.fpmibsu.bielrent.model.dto.InsertPhotoDto;
import by.fpmibsu.bielrent.model.dto.PhotoDto;
import by.fpmibsu.bielrent.model.entity.Photo;
import by.fpmibsu.bielrent.model.dtomapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhotoMapperToDto implements Mapper<PhotoDto, Photo> {
    private static final PhotoMapperToDto INSTANCE = new PhotoMapperToDto();
    public static PhotoMapperToDto getInstance() {
        return INSTANCE;
    }

    @Override
    public PhotoDto mapFrom(Photo obj) {
        return PhotoDto.builder()
                .id(obj.getId())
                .listingId(obj.getId())
                .path(obj.getPath())
                .build();
    }
}
