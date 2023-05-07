package by.fpmibsu.bielrent.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ListingQuery {
    Address address;
    Filter filter;
    PropertyType propertyType;
    Long priceFrom;
    Long priceTo;
}