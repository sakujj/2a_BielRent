package by.fpmibsu.bielrent.model.dtomapper;

import by.fpmibsu.bielrent.model.dto.resp.ListingOrmResp;
import by.fpmibsu.bielrent.model.entity.ListingOrm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListingOrmMapper {
    private static final AddressMapper addressMapper = AddressMapper.getInstance();
    private static final FilterMapper filterMapper = FilterMapper.getInstance();
    private static final UserMapper userMapper = UserMapper.getInstance();
    private static final PhotoMapper photoMapper = PhotoMapper.getInstance();
    private static final ListingOrmMapper INSTANCE = new ListingOrmMapper();
    public static ListingOrmMapper getInstance() {
        return INSTANCE;
    }

    public ListingOrmResp fromEntity(ListingOrm obj) {
        return ListingOrmResp.builder()
                .id(obj.getId())
                .name(obj.getName())
                .description(obj.getDescription())
                .address(addressMapper.fromEntity(obj.getAddress()))
                .filter(filterMapper.fromEntity(obj.getFilter()))
                .user(userMapper.fromEntity(obj.getUser()))
                .propertyTypeName(obj.getPropertyTypeName().toString())
                .photos(obj.getPhotos()
                        .stream()
                        .map(photoMapper::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}