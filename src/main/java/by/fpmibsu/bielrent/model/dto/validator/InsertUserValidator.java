package by.fpmibsu.bielrent.model.dto.validator;

import by.fpmibsu.bielrent.model.dao.UserDao;
import by.fpmibsu.bielrent.model.dao.UserDaoImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.dto.InsertUserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InsertUserValidator implements Validator<InsertUserDto>{

    private static final Error PASSWORD_MIN_LENGTH_ERROR = Error.of("invalid.password", "Введите пароль длиной не менее 8 символов");
    public final Error EMAIL_LENGTH_ERROR= Error.of("invalid.email", "Введите email длиной менее 50 символов");
    public final Error EMAIL_ALREADY_EXISTS_ERROR= Error.of("invalid.email", "Email уже существует");
    public final Error PASSWORD_LENGTH_ERROR = Error.of("invalid.password", "Введите пароль длиной менее 50 символов");
    public final Error NAME_LENGTH_ERROR = Error.of("invalid.name", "Введите имя длиной менее 50 символов");

    private static final UserDao userDao = UserDaoImpl.getInstance();

    private static final InsertUserValidator INSTANCE = new InsertUserValidator();
    public static InsertUserValidator getInstance() {
        return INSTANCE;
    }


    @Override
    public ValidationResult validate(InsertUserDto obj) {
        ValidationResult vr = new ValidationResult();

        try {
            System.out.println(userDao.selectByEmail(obj.getEmail()).isPresent());
            if (userDao.selectByEmail(obj.getEmail()).isPresent()) {
                System.out.println("3q4");
                vr.add(EMAIL_ALREADY_EXISTS_ERROR);
            }
        } catch (DaoException e) {
            e.printStackTrace();
            vr.add(Error.of("dao error", "Ошибка в дао"));
        }

        if (obj.getPassword().length() < 8) {
            vr.add(PASSWORD_MIN_LENGTH_ERROR);
        }
        if (obj.getEmail().length() > 50) {
            vr.add(EMAIL_LENGTH_ERROR);
        }
        if (obj.getName().codePoints().count() > 50) {
            vr.add(NAME_LENGTH_ERROR);
        }
        if (obj.getPassword().codePoints().count() > 50) {
            vr.add(PASSWORD_LENGTH_ERROR);
        }

        return vr;
    }
}
