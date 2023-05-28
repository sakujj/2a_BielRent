package by.fpmibsu.bielrent.model.dto.validator;

import by.fpmibsu.bielrent.model.dto.InsertPhotoDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhotoValidator implements Validator<InsertPhotoDto>{

    private static final PhotoValidator INSTANCE = new PhotoValidator();
    public static PhotoValidator getInstance() {
        return INSTANCE;
    }


    @Override
    public ValidationResult validate(InsertPhotoDto obj) {
        ValidationResult vr = new ValidationResult();
        return vr;
    }
}
