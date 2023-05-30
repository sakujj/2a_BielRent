package by.fpmibsu.bielrent.model.dtomapper;

import by.fpmibsu.bielrent.model.dto.req.FilterReq;
import by.fpmibsu.bielrent.model.dto.resp.FilterResp;
import by.fpmibsu.bielrent.model.entity.Filter;
import by.fpmibsu.bielrent.utility.LocalDateFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterMapper {
    private static final FilterMapper INSTANCE = new FilterMapper();

    public static FilterMapper getInstance() {
        return INSTANCE;
    }

    public Filter toEntity(FilterReq obj, Long listingId) {
        Filter filter = new Filter();
        filter.setId(-1L);
        filter.setListingId(listingId);
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

    public FilterResp fromEntity(Filter obj) {
        return FilterResp.builder()
                .id(obj.getId())
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
