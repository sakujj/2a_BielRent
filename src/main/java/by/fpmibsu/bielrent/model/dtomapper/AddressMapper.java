package by.fpmibsu.bielrent.model.dtomapper;

import by.fpmibsu.bielrent.model.dto.req.AddressReq;
import by.fpmibsu.bielrent.model.dto.resp.AddressResp;
import by.fpmibsu.bielrent.model.entity.Address;
import by.fpmibsu.bielrent.model.entity.Region;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressMapper {
    private static final AddressMapper INSTANCE = new AddressMapper();
    public static AddressMapper getInstance() {
        return INSTANCE;
    }

    public Address toEntity(AddressReq obj) {
        return Address.builder()
                .id(-1L)
                .houseNumber(Integer.valueOf(obj.getHouseNumber()))
                .street(obj.getStreet())
                .city(obj.getCity())
                .regionName(Region.valueOf(obj.getRegionName()))
                .districtAdministrative(obj.getDistrictAdministrative())
                .districtMicro(obj.getDistrictMicro())
                .build();
    }

    public AddressResp fromEntity(Address obj) {
        return AddressResp.builder()
                .id(obj.getId())
                .houseNumber(String.valueOf(obj.getHouseNumber()))
                .street(obj.getStreet())
                .city(obj.getCity())
                .regionName(obj.getRegionName().toString())
                .districtAdministrative(obj.getDistrictAdministrative())
                .districtMicro(obj.getDistrictMicro())
                .build();
    }
}
