package by.fpmibsu.bielrent.model.dtovalidator;

import by.fpmibsu.bielrent.model.dto.req.PhotoReq;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhotoValidator implements Validator<PhotoReq>{

    private static final PhotoValidator INSTANCE = new PhotoValidator();
    public static PhotoValidator getInstance() {
        return INSTANCE;
    }


    @Override
    public ValidationResult validate(PhotoReq obj) {
        ValidationResult vr = new ValidationResult();
        return vr;
    }
}
