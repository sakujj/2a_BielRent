package by.fpmibsu.bielrent.dto.validator;

import by.fpmibsu.bielrent.dto.UserDto;
import by.fpmibsu.bielrent.entity.PropertyType;
import by.fpmibsu.bielrent.entity.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserValidator implements Validator<UserDto>{

    public final Error EMAIL_LENGTH_ERROR= Error.of("invalid.email", "");
    public final Error PASSWORD_LENGTH_ERROR = Error.of("invalid.password", "");
    public final Error NAME_LENGTH_ERROR = Error.of("invalid.name", "");
    public final Error ROLE_NOT_FOUND_ERROR= Error.of("invalid.role","");
    public final Error RATING_FORMAT_ERROR=Error.of("invalid.rating", "");

    private static final UserValidator INSTANCE = new UserValidator();
    public static UserValidator getInstance() {
        return INSTANCE;
    }


    @Override
    public ValidationResult validate(UserDto obj) {
        ValidationResult vr = new ValidationResult();

        if (obj.getEmail().length() > 50) {
            vr.add(EMAIL_LENGTH_ERROR);
        }
        if (obj.getName().codePoints().count() > 50) {
            vr.add(NAME_LENGTH_ERROR);
        }
        if (obj.getPassword().codePoints().count() > 50) {
            vr.add(PASSWORD_LENGTH_ERROR);
        }

        try {
            Role r = Role.valueOf(obj.getRole());
        } catch (IllegalArgumentException e) {
            vr.add(ROLE_NOT_FOUND_ERROR);
        }

        Pattern pattern = Pattern.compile("[0-9].[0-9]");
        Matcher matcher = pattern.matcher(obj.getRating());
        if (!matcher.find()) {
            vr.add(RATING_FORMAT_ERROR);
        }

        return vr;
    }
}
