package by.fpmibsu.bielrent.model.dtomapper.todto;

import by.fpmibsu.bielrent.model.dto.ListingOrmDto;
import by.fpmibsu.bielrent.model.dtomapper.Mapper;
import by.fpmibsu.bielrent.model.entity.ListingOrm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListingOrmMapperToDto implements Mapper<ListingOrmDto, ListingOrm> {
    private static final AddressMapperToDto addressMapper = AddressMapperToDto.getInstance();
    private static final FilterMapperToDto filterMapper = FilterMapperToDto.getInstance();
    private static final UserMapperToDto userMapper = UserMapperToDto.getInstance();
    private static final PhotoMapperToDto photoMapper = PhotoMapperToDto.getInstance();
    private static final ListingOrmMapperToDto INSTANCE = new ListingOrmMapperToDto();
    public static ListingOrmMapperToDto getInstance() {
        return INSTANCE;
    }

    @Override
    public ListingOrmDto mapFrom(ListingOrm obj) {
        return ListingOrmDto.builder()
                .id(obj.getId())
                .name(obj.getName())
                .description(obj.getDescription())
                .address(addressMapper.mapFrom(obj.getAddress()))
                .filter(filterMapper.mapFrom(obj.getFilter()))
                .user(userMapper.mapFrom(obj.getUser()))
                .propertyTypeName(obj.getPropertyTypeName().toString())
                .photos(obj.getPhotos().stream().map(photoMapper::mapFrom).collect(Collectors.toList()))
                .build();
    }
}