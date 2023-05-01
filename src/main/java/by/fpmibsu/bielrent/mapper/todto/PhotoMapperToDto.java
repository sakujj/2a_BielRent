package by.fpmibsu.bielrent.mapper.todto;

import by.fpmibsu.bielrent.dto.PhotoDto;
import by.fpmibsu.bielrent.entity.Photo;
import by.fpmibsu.bielrent.mapper.Mapper;
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
        PhotoDto photoDto = PhotoDto.builder()
                .id(obj.getId())
                .listingId(obj.getId())
                .build();
        //TODO TODO
        return photoDto;
    }
}
