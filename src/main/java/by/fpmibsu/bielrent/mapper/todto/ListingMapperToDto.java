package by.fpmibsu.bielrent.mapper.todto;

import by.fpmibsu.bielrent.dto.ListingDto;
import by.fpmibsu.bielrent.entity.Listing;
import by.fpmibsu.bielrent.entity.PropertyType;
import by.fpmibsu.bielrent.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListingMapperToDto implements Mapper<ListingDto, Listing> {
    private static final ListingMapperToDto INSTANCE = new ListingMapperToDto();
    public static ListingMapperToDto getInstance() {
        return INSTANCE;
    }

    @Override
    public ListingDto mapFrom(Listing obj) {
        return ListingDto.builder()
                .id(obj.getId())
                .name(obj.getName())
                .addressId(obj.getAddressId())
                .filterId(obj.getFilterId())
                .userId(obj.getUserId())
                .propertyTypeName(obj.getPropertyTypeName().toString())
                .description(obj.getDescription())
                .build();
    }
}
