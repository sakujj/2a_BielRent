package by.fpmibsu.bielrent.dto.validator;

import by.fpmibsu.bielrent.dto.AddressDto;
import by.fpmibsu.bielrent.entity.Address;
import by.fpmibsu.bielrent.entity.PropertyType;
import by.fpmibsu.bielrent.entity.Region;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressValidator implements Validator<AddressDto> {
    private static final Error REGION_NAME_NOT_FOUND_ERROR = Error.of("invalid.region-name", "");
    public final Error CITY_LENGTH_ERROR = Error.of("invalid.city", "City length is too long");
    public final Error DISTRICT_ADMINISTRATIVE_LENGTH_ERROR = Error.of("invalid.district-administrative", "District administrative length is too long");
    public final Error DISTRICT_MICRO_LENGTH_ERROR = Error.of("invalid.district-micro", "District micro length is too long");
    public final Error STREET_LENGTH_ERROR = Error.of("invalid.street", "Street length is too long");
    public final Error HOUSE_NUMBER_ERROR = Error.of("invalid.house-number", "");

    private static final AddressValidator INSTANCE = new AddressValidator();

    public static AddressValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult validate(AddressDto obj) {
        ValidationResult validationResult = new ValidationResult();
        if (obj.getCity().codePoints().count() > 30) {
            validationResult.add(CITY_LENGTH_ERROR);
        }
        if (obj.getDistrictAdministrative() != null
                && obj.getDistrictAdministrative().codePoints().count() > 20) {
            validationResult.add(DISTRICT_ADMINISTRATIVE_LENGTH_ERROR);
        }
        if (obj.getDistrictMicro() != null
                && obj.getDistrictMicro().codePoints().count() > 20) {
            validationResult.add(DISTRICT_MICRO_LENGTH_ERROR);
        }
        if (obj.getStreet().codePoints().count() > 30) {
            validationResult.add(STREET_LENGTH_ERROR);
        }
        if (Integer.valueOf(obj.getHouseNumber()) < 0) {
            validationResult.add(HOUSE_NUMBER_ERROR);
        }
        try {
            Region r = Region.valueOf(obj.getRegionName());
        } catch (IllegalArgumentException e) {
            validationResult.add(REGION_NAME_NOT_FOUND_ERROR);
        }

        return validationResult;
    }
}
