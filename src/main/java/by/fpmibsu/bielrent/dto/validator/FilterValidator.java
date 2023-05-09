package by.fpmibsu.bielrent.dto.validator;

import by.fpmibsu.bielrent.dto.FilterDto;
import by.fpmibsu.bielrent.utility.LocalDateFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterValidator implements Validator<FilterDto>{
    public final Error ROOM_COUNT_ERROR = Error.of("invalid.room-count", "");
    public final Error FLOOR_COUNT_ERROR = Error.of("invalid.floor-count", "");
    public final Error BEDROOM_COUNT_ERROR = Error.of("invalid.bedroom-count", "");
    public final Error BUILD_YEAR_ERROR = Error.of("invalid.build-year", "");
    public final Error PRICE_MONTHLY_ERROR = Error.of("invalid.price-monthly", "");
    public final Error SQUARE_AREA_ERROR = Error.of("invalid.square-area", "");
    public final Error BALCONY_COUNT_ERROR = Error.of("invalid.balcony-count", "");
    public final Error RENTAL_PERIOD_ERROR = Error.of("invalid.rental-period", "");



    private static final FilterValidator INSTANCE = new FilterValidator();
    public static FilterValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult validate(FilterDto obj) {
        ValidationResult validationResult = new ValidationResult();

        if (!LocalDateFormatter.isValid(obj.getRentalPeriodStart())
                || !LocalDateFormatter.isValid(obj.getRentalPeriodEnd())) {
            validationResult.add(RENTAL_PERIOD_ERROR);
        } else {
            LocalDate rentalPeriodStart = LocalDateFormatter.format(obj.getRentalPeriodStart());
            LocalDate rentalPeriodEnd = LocalDateFormatter.format(obj.getRentalPeriodEnd());
            if (rentalPeriodEnd.isBefore(rentalPeriodStart)) {
                validationResult.add(RENTAL_PERIOD_ERROR);
            }
        }
        if (Integer.valueOf(obj.getRoomCount()) <= 0) {
            validationResult.add(ROOM_COUNT_ERROR);
        }
        if (Integer.valueOf(obj.getFloorCount()) <= 0) {
            validationResult.add(FLOOR_COUNT_ERROR);
        }
        if (Integer.valueOf(obj.getBedroomCount()) < 0) {
            validationResult.add(BEDROOM_COUNT_ERROR);
        }
        if (Integer.valueOf(obj.getBuildYear()) < 1700 || Integer.valueOf(obj.getBuildYear()) > LocalDate.now().getYear()) {
            validationResult.add(BUILD_YEAR_ERROR);
        }
        if (Integer.valueOf(obj.getPriceMonthly()) <= 0) {
            validationResult.add(PRICE_MONTHLY_ERROR);
        }
        if (Double.valueOf(obj.getSquareArea()) <= 0) {
            validationResult.add(SQUARE_AREA_ERROR);
        }
        if (Integer.valueOf(obj.getBalconyCount()) < 0) {
            validationResult.add(BALCONY_COUNT_ERROR);
        }

        return validationResult;
    }
}
