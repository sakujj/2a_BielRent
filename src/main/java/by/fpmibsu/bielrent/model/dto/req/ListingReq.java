package by.fpmibsu.bielrent.model.dto.req;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ListingReq {
     String name;
     String propertyTypeName;
     String description;
}
