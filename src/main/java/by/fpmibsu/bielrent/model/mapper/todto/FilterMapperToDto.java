package by.fpmibsu.bielrent.model.mapper.todto;

import by.fpmibsu.bielrent.controller.dto.FilterDto;
import by.fpmibsu.bielrent.model.entity.Filter;
import by.fpmibsu.bielrent.model.mapper.Mapper;
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
                //.id(obj.getId())
                .listingId(obj.getListingId())
                .roomCount(String.valueOf(obj.getRoomCount()))
                .floorCount(String.valueOf(obj.getBedroomCount()))
                .bedroomCount(String.valueOf(obj.getBedroomCount()))
                .balconyCount(String.valueOf(obj.getBalconyCount()))
                .squareArea(String.valueOf(obj.getSquareArea()))
                .priceMonthly(String.valueOf(obj.getPriceMonthly()))
                .buildYear(String.valueOf(obj.getBuildYear()))
                .hasBathroom(String.valueOf(obj.getHasBathroom()))
                .hasWashingMachine(String.valueOf(obj.getHasWashingMachine()))
                .hasFurniture(String.valueOf(obj.getHasFurniture()))
                .hasWifi(String.valueOf(obj.getHasWifi()))
                .hasElevator(String.valueOf(obj.getHasElevator()))
                .rentalPeriodStart(obj.getRentalPeriodStart().toString())
                .rentalPeriodEnd(obj.getRentalPeriodEnd().toString())
                .build();
    }
}
