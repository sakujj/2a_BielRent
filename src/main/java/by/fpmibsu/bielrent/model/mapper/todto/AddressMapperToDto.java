package by.fpmibsu.bielrent.model.mapper.todto;

import by.fpmibsu.bielrent.controller.dto.AddressDto;
import by.fpmibsu.bielrent.model.entity.Address;
import by.fpmibsu.bielrent.model.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressMapperToDto implements Mapper<AddressDto, Address> {
    private static final AddressMapperToDto INSTANCE = new AddressMapperToDto();
    public static AddressMapperToDto getInstance() {
        return INSTANCE;
    }

    @Override
    public AddressDto mapFrom(Address obj) {
        return AddressDto.builder()
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
