package by.fpmibsu.bielrent.model.dto.resp;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class AddressResp {
    Long id;

    String regionName;
    String city;
    String districtAdministrative;
    String districtMicro;
    String street;
    String houseNumber;
}
