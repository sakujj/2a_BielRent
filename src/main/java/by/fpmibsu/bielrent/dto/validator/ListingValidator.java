package by.fpmibsu.bielrent.dto.validator;

import by.fpmibsu.bielrent.dto.ListingDto;
import by.fpmibsu.bielrent.entity.PropertyType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListingValidator implements Validator<ListingDto> {
    public final Error PROPERTY_TYPE_NOT_FOUND_ERROR = Error.of("invalid.property-type", "");
    public final Error DESCRIPTION_LENGTH_ERROR = Error.of("invalid.description", "");

    private static final ListingValidator INSTANCE = new ListingValidator();
    public static ListingValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult validate(ListingDto obj) {
        ValidationResult vr = new ValidationResult();

        if (obj.getDescription().codePoints().count() > 1000) {
            vr.add(DESCRIPTION_LENGTH_ERROR);
        }

        try {
            PropertyType pt = PropertyType.valueOf(obj.getPropertyType());
        } catch (IllegalArgumentException e) {
            vr.add(PROPERTY_TYPE_NOT_FOUND_ERROR);
        }

        return vr;
    }

}