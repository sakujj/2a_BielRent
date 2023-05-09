package by.fpmibsu.bielrent.mapper.toentity;

import by.fpmibsu.bielrent.dto.FilterDto;
import by.fpmibsu.bielrent.entity.Filter;
import by.fpmibsu.bielrent.mapper.Mapper;
import by.fpmibsu.bielrent.utility.LocalDateFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterMapperToEntity implements Mapper<Filter, FilterDto> {
    private static final FilterMapperToEntity INSTANCE = new FilterMapperToEntity();

    public static FilterMapperToEntity getInstance() {
        return INSTANCE;
    }


    @Override
    public Filter mapFrom(FilterDto obj) {
        Filter filter = new Filter();
        //filter.setId(Long.valueOf(obj.getId()));
        filter.setListingId(obj.getListingId());
        filter.setRoomCount(Integer.valueOf(obj.getRoomCount()));
        filter.setFloorCount(Integer.valueOf(obj.getBedroomCount()));
        filter.setBedroomCount(Integer.valueOf(obj.getBedroomCount()));
        filter.setBalconyCount(Integer.valueOf(obj.getBalconyCount()));
        filter.setSquareArea(Double.valueOf(obj.getSquareArea()));
        filter.setPriceMonthly(Long.valueOf(obj.getPriceMonthly()));
        filter.setBuildYear(Integer.valueOf(obj.getBuildYear()));
        filter.setHasBathroom(Boolean.valueOf(obj.getHasBathroom()));
        filter.setHasWashingMachine(Boolean.valueOf(obj.getHasWashingMachine()));
        filter.setHasFurniture(Boolean.valueOf(obj.getHasFurniture()));
        filter.setHasWifi(Boolean.valueOf(obj.getHasWifi()));
        filter.setHasElevator(Boolean.valueOf(obj.getHasElevator()));
        filter.setRentalPeriodStart(LocalDateFormatter.format(obj.getRentalPeriodStart()));
        filter.setRentalPeriodEnd(LocalDateFormatter.format(obj.getRentalPeriodEnd()));
        return filter;
    }
}
