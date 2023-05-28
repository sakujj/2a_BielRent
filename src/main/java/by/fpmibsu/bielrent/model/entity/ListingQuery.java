package by.fpmibsu.bielrent.model.entity;

import by.fpmibsu.bielrent.model.dto.AddressDto;
import by.fpmibsu.bielrent.model.dto.FilterDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class ListingQuery {
    AddressDto address;
    FilterDto filter;
    PropertyType propertyTypeName;
    Long priceFrom;
    Long priceTo;
}
