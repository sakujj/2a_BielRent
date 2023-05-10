package by.fpmibsu.bielrent.controller.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class AddressDto {
    //private final Long id;
    private final String regionName;
    private final String city;
    private final String districtAdministrative;
    private final String districtMicro;
    private final String street;
    private final String houseNumber;
}
