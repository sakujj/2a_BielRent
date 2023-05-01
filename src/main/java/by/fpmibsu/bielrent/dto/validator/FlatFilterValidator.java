package by.fpmibsu.bielrent.dto.validator;

import by.fpmibsu.bielrent.dto.FlatFilterDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlatFilterValidator implements Validator<FlatFilterDto> {

    private static final FlatFilterValidator INSTANCE = new FlatFilterValidator();
    public static FlatFilterValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult validate(FlatFilterDto obj) {
        ValidationResult validationResult = FilterValidator.getInstance().validate(obj.getFilterDto());

        return validationResult;
    }
}
