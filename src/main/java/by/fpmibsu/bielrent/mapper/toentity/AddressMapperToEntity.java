package by.fpmibsu.bielrent.mapper.toentity;

import by.fpmibsu.bielrent.dto.AddressDto;
import by.fpmibsu.bielrent.entity.Address;
import by.fpmibsu.bielrent.entity.Region;
import by.fpmibsu.bielrent.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressMapperToEntity implements Mapper<Address, AddressDto> {
    private static final AddressMapperToEntity INSTANCE = new AddressMapperToEntity();
    public static AddressMapperToEntity getInstance() {
        return INSTANCE;
    }

    @Override
    public Address mapFrom(AddressDto obj) {
        return Address.builder()
                .id(obj.getId())
                .houseNumber(obj.getHouseNumber())
                .street(obj.getStreet())
                .city(obj.getCity())
                .regionName(Region.valueOf(obj.getRegionName()))
                .districtAdministrative(obj.getDistrictAdministrative())
                .districtMicro(obj.getDistrictMicro())
                .build();
    }
}
