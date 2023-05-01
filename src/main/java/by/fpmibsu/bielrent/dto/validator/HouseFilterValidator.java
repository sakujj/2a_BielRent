package by.fpmibsu.bielrent.dto.validator;

import by.fpmibsu.bielrent.dto.HouseFilterDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HouseFilterValidator implements Validator<HouseFilterDto> {
    public static final Error LAND_AREA_ERROR = Error.of("invalid.land-area", "");

    private static final HouseFilterValidator INSTANCE = new HouseFilterValidator();
    public static HouseFilterValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult validate(HouseFilterDto obj) {
        ValidationResult validationResult = FilterValidator.getInstance().validate(obj.getFilterDto());

        if (obj.getLandArea() < 0) {
            validationResult.add(LAND_AREA_ERROR);
        }

        return validationResult;
    }
}