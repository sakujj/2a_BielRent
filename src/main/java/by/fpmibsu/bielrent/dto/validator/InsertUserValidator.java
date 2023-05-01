package by.fpmibsu.bielrent.dto.validator;

import by.fpmibsu.bielrent.dao.UserDao;
import by.fpmibsu.bielrent.dao.UserDaoImpl;
import by.fpmibsu.bielrent.dao.exception.DaoException;
import by.fpmibsu.bielrent.dto.InsertUserDto;
import by.fpmibsu.bielrent.entity.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InsertUserValidator implements Validator<InsertUserDto>{

    public final Error EMAIL_LENGTH_ERROR= Error.of("invalid.email", "");
    public final Error EMAIL_ALREADY_EXISTS_ERROR= Error.of("invalid.email", "");
    public final Error PASSWORD_LENGTH_ERROR = Error.of("invalid.password", "");
    public final Error NAME_LENGTH_ERROR = Error.of("invalid.name", "");

    private static final UserDao userDao = UserDaoImpl.getInstance();

    private static final InsertUserValidator INSTANCE = new InsertUserValidator();
    public static InsertUserValidator getInstance() {
        return INSTANCE;
    }


    @Override
    public ValidationResult validate(InsertUserDto obj) {
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
            if (userDao.selectByEmail(obj.getEmail()) != null) {
                vr.add(EMAIL_ALREADY_EXISTS_ERROR);
            }
        } catch (DaoException e) {
            vr.add(Error.of("dao error", ""));
        }

        return vr;
    }
}
