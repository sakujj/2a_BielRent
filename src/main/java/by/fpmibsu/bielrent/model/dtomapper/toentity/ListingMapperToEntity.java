package by.fpmibsu.bielrent.model.dtomapper.toentity;

import by.fpmibsu.bielrent.model.dto.ListingDto;
import by.fpmibsu.bielrent.model.entity.Listing;
import by.fpmibsu.bielrent.model.entity.PropertyType;
import by.fpmibsu.bielrent.model.dtomapper.Mapper;
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
                //.id(obj.getId())
                .name(obj.getName())
                .addressId(obj.getAddressId())
                //.filterId(obj.getFilterId())
                .userId(obj.getUserId())
                .propertyTypeName(PropertyType.valueOf(obj.getPropertyTypeName()))
                .description(obj.getDescription())
                .build();
    }
}