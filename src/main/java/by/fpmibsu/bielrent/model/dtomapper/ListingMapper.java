package by.fpmibsu.bielrent.model.dtomapper;

import by.fpmibsu.bielrent.model.dto.req.ListingReq;
import by.fpmibsu.bielrent.model.dto.resp.ListingResp;
import by.fpmibsu.bielrent.model.entity.Listing;
import by.fpmibsu.bielrent.model.entity.PropertyType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListingMapper {
    private static final ListingMapper INSTANCE = new ListingMapper();
    public static ListingMapper getInstance() {
        return INSTANCE;
    }

    public Listing toEntity(ListingReq obj, Long addressId, Long userId) {
        return Listing.builder()
                .id(-1L)
                .name(obj.getName())
                .addressId(addressId)
                .userId(userId)
                .propertyTypeName(PropertyType.valueOf(obj.getPropertyTypeName()))
                .description(obj.getDescription())
                .build();
    }

    public ListingResp fromEntity(Listing obj) {
        return ListingResp.builder()
                .id(obj.getId())
                .name(obj.getName())
                .addressId(obj.getAddressId())
                .userId(obj.getUserId())
                .propertyTypeName(obj.getPropertyTypeName().toString())
                .description(obj.getDescription())
                .build();
    }
}