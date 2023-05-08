package by.fpmibsu.bielrent.dto;

import by.fpmibsu.bielrent.entity.Region;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class AddressDto {
    private final long id;
    private final String regionName;
    private final String city;
    private final String districtAdministrative;
    private final String districtMicro;
    private final String street;
    private final int houseNumber;
}
