package by.fpmibsu.bielrent.mapper.todto;

import by.fpmibsu.bielrent.dto.AddressDto;
import by.fpmibsu.bielrent.entity.Address;
import by.fpmibsu.bielrent.mapper.Mapper;
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
                .id(obj.getId())
                .houseNumber(obj.getHouseNumber())
                .street(obj.getStreet())
                .city(obj.getCity())
                .regionNumber(obj.getRegionNumber())
                .districtAdministrative(obj.getDistrictAdministrative())
                .districtMicro(obj.getDistrictMicro())
                .build();
    }
}
