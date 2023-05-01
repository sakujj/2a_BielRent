package by.fpmibsu.bielrent.dto;

import by.fpmibsu.bielrent.entity.PropertyType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ListingDto {
    private long id;
    private long addressId;
    private long filterId;
    private long userId;

    private String propertyType;
    private String description;
}
