package by.fpmibsu.bielrent.model.entity;

import by.fpmibsu.bielrent.model.dto.req.AddressReq;
import by.fpmibsu.bielrent.model.dto.req.FilterReq;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class ListingQuery {
    AddressReq address;
    FilterReq filter;
    PropertyType propertyTypeName;
    Long priceFrom;
    Long priceTo;
}
