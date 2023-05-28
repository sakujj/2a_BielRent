package by.fpmibsu.bielrent.model.dtomapper.todto;

import by.fpmibsu.bielrent.model.entity.Address;
import by.fpmibsu.bielrent.model.dtomapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressMapperToDto implements Mapper<by.fpmibsu.bielrent.model.dto.AddressDto, Address> {
    private static final AddressMapperToDto INSTANCE = new AddressMapperToDto();
    public static AddressMapperToDto getInstance() {
        return INSTANCE;
    }

    @Override
    public by.fpmibsu.bielrent.model.dto.AddressDto mapFrom(Address obj) {
        return by.fpmibsu.bielrent.model.dto.AddressDto.builder()
                //.id(obj.getId())
                .houseNumber(String.valueOf(obj.getHouseNumber()))
                .street(obj.getStreet())
                .city(obj.getCity())
                .regionName(obj.getRegionName().toString())
                .districtAdministrative(obj.getDistrictAdministrative())
                .districtMicro(obj.getDistrictMicro())
                .build();
    }
}
