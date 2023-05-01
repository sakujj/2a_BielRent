package by.fpmibsu.bielrent.mapper.todto;

import by.fpmibsu.bielrent.dto.FilterDto;
import by.fpmibsu.bielrent.entity.Filter;
import by.fpmibsu.bielrent.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterMapperToDto implements Mapper<FilterDto, Filter> {
    private static final FilterMapperToDto INSTANCE = new FilterMapperToDto();
    public static FilterMapperToDto getInstance() {
        return INSTANCE;
    }
    
    @Override
    public FilterDto mapFrom(Filter obj) {
        return FilterDto.builder()
                .id(obj.getId())
                .listingId(obj.getListingId())
                .roomCount(obj.getRoomCount())
                .floorCount(obj.getBedroomCount())
                .bedroomCount(obj.getBedroomCount())
                .balconyCount(obj.getBalconyCount())
                .squareArea(obj.getSquareArea())
                .priceMonthly(obj.getPriceMonthly())
                .buildYear(obj.getBuildYear())
                .hasBathroom(obj.getHasBathroom())
                .hasWashingMachine(obj.getHasWashingMachine())
                .hasFurniture(obj.getHasFurniture())
                .hasWifi(obj.getHasWifi())
                .hasElevator(obj.getHasElevator())
                .rentalPeriodStart(obj.getRentalPeriodStart().toString())
                .rentalPeriodEnd(obj.getRentalPeriodEnd().toString())
                .build();
    }
}
