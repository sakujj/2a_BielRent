package by.fpmibsu.bielrent.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ListingDto {
    private String name;
    //private Long id;
    private Long addressId;
    //private Long filterId;
    private Long userId;

    private String propertyTypeName;
    private String description;
}
