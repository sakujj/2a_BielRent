package by.fpmibsu.bielrent.dto.validator;

import by.fpmibsu.bielrent.dto.PhotoDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhotoValidator implements Validator<PhotoDto>{

    private static final PhotoValidator INSTANCE = new PhotoValidator();
    public static PhotoValidator getInstance() {
        return INSTANCE;
    }


    @Override
    public ValidationResult validate(PhotoDto obj) {
        ValidationResult vr = new ValidationResult();
        return vr;
    }
}
