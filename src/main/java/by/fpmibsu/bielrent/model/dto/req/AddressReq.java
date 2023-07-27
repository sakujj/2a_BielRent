package by.fpmibsu.bielrent.model.dto.req;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public  class AddressReq {
      String regionName;
      String city;
      String districtAdministrative;
      String districtMicro;
      String street;
      String houseNumber;
}
