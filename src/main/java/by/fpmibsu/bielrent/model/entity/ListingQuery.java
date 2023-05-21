package by.fpmibsu.bielrent.model.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class ListingQuery {
    Address address;
    Filter filter;
    PropertyType propertyTypeName;
    Long priceFrom;
    Long priceTo;
}
