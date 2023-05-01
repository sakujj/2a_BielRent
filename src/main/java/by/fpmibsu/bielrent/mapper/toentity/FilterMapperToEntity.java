package by.fpmibsu.bielrent.mapper.toentity;

import by.fpmibsu.bielrent.dto.FilterDto;
import by.fpmibsu.bielrent.entity.Filter;
import by.fpmibsu.bielrent.mapper.Mapper;
import by.fpmibsu.bielrent.utility.LocalDateFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterMapperToEntity implements Mapper<FilterDto, Filter> {
    private static final FilterMapperToEntity INSTANCE = new FilterMapperToEntity();

    public static FilterMapperToEntity getInstance() {
        return INSTANCE;
    }

    public void mapDtoToFilter(FilterDto obj, Filter filter) {
        filter.setId(obj.getId());
        filter.setListingId(obj.getListingId());
        filter.setRoomCount(obj.getRoomCount());
        filter.setFloorCount(obj.getBedroomCount());
        filter.setBedroomCount(obj.getBedroomCount());
        filter.setBalconyCount(obj.getBalconyCount());
        filter.setSquareArea(obj.getSquareArea());
        filter.setPriceMonthly(obj.getPriceMonthly());
        filter.setBuildYear(obj.getBuildYear());
        filter.setHasBathroom(obj.getHasBathroom());
        filter.setHasWashingMachine(obj.getHasWashingMachine());
        filter.setHasFurniture(obj.getHasFurniture());
        filter.setHasWifi(obj.getHasWifi());
        filter.setHasElevator(obj.getHasElevator());
        filter.setRentalPeriodStart(LocalDateFormatter.format(obj.getRentalPeriodStart()));
        filter.setRentalPeriodEnd(LocalDateFormatter.format(obj.getRentalPeriodEnd()));
    }

    @Override
    public FilterDto mapFrom(Filter obj) {
        throw new UnsupportedOperationException();
    }
}
