package by.fpmibsu.bielrent.model.dtomapper.toentity;

import by.fpmibsu.bielrent.model.entity.Address;
import by.fpmibsu.bielrent.model.entity.Region;
import by.fpmibsu.bielrent.model.dtomapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressMapperToEntity implements Mapper<Address, by.fpmibsu.bielrent.model.dto.AddressDto> {
    private static final AddressMapperToEntity INSTANCE = new AddressMapperToEntity();
    public static AddressMapperToEntity getInstance() {
        return INSTANCE;
    }

    @Override
    public Address mapFrom(by.fpmibsu.bielrent.model.dto.AddressDto obj) {
        return Address.builder()
                //.id(obj.getId())
                .houseNumber(Integer.valueOf(obj.getHouseNumber()))
                .street(obj.getStreet())
                .city(obj.getCity())
                .regionName(Region.valueOf(obj.getRegionName()))
                .districtAdministrative(obj.getDistrictAdministrative())
                .districtMicro(obj.getDistrictMicro())
                .build();
    }
    private String transferEnumToDao(Region r){
        switch (r){
            case MINSK_REGION:return "Minsk region";
            case BREST_REGION:return "Brest region";
            case MINSK_CITY:return "Minsk city";
            case MOGILEV_REGION:return "Mogilev region";
            case  VITEBSK_REGION:return "Vitebsk region";
            case GOMEL_REGION:return "Gomel region";
            case GRODNO_REGION:return "Grodno region";

        }
        return null;

    }
}
