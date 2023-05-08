package by.fpmibsu.bielrent.mapper.toentity;

import by.fpmibsu.bielrent.dto.ListingDto;
import by.fpmibsu.bielrent.entity.Listing;
import by.fpmibsu.bielrent.entity.PropertyType;
import by.fpmibsu.bielrent.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListingMapperToEntity implements Mapper<Listing, ListingDto> {
    private static final ListingMapperToEntity INSTANCE = new ListingMapperToEntity();
    public static ListingMapperToEntity getInstance() {
        return INSTANCE;
    }

    @Override
    public Listing mapFrom(ListingDto obj) {
        return Listing.builder()
                .id(obj.getId())
                .name(obj.getName())
                .addressId(obj.getAddressId())
                .filterId(obj.getFilterId())
                .userId(obj.getUserId())
                .propertyTypeName(PropertyType.valueOf(obj.getPropertyTypeName()))
                .description(obj.getDescription())
                .build();
    }
}