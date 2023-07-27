package by.fpmibsu.bielrent.model.dto.resp;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ListingResp {
     Long id;
     Long addressId;
     Long userId;

     String name;
     String propertyTypeName;
     String description;
}
