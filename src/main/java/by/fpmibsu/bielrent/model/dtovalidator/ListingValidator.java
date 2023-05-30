package by.fpmibsu.bielrent.model.dtovalidator;

import by.fpmibsu.bielrent.model.dto.req.ListingReq;
import by.fpmibsu.bielrent.model.entity.PropertyType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListingValidator implements Validator<ListingReq> {
    private static final Error NAME_LENGTH_ERROR = Error.of("invalid.name", "");;
    public final Error PROPERTY_TYPE_NOT_FOUND_ERROR = Error.of("invalid.property-type-name", "");
    public final Error DESCRIPTION_LENGTH_ERROR = Error.of("invalid.description", "");

    private static final ListingValidator INSTANCE = new ListingValidator();
    public static ListingValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult validate(ListingReq obj) {
        ValidationResult vr = new ValidationResult();

        if (obj.getName().codePoints().count() > 300) {
            vr.add(NAME_LENGTH_ERROR);
        }
        if (obj.getDescription().codePoints().count() > 1000) {
            vr.add(DESCRIPTION_LENGTH_ERROR);
        }
        try {
            PropertyType pt = PropertyType.valueOf(obj.getPropertyTypeName());
        } catch (IllegalArgumentException e) {
            vr.add(PROPERTY_TYPE_NOT_FOUND_ERROR);
        }

        return vr;
    }

}
