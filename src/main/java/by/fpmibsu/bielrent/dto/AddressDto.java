package by.fpmibsu.bielrent.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AddressDto {
    private long id;
    private int regionNumber;
    private String city;
    private String districtAdministrative = null;
    private String districtMicro = null;
    private String street;
    private int houseNumber;
}
