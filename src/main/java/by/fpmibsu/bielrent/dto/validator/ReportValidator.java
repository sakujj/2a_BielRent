package by.fpmibsu.bielrent.dto.validator;

import by.fpmibsu.bielrent.dto.ReportDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportValidator implements Validator<ReportDto>{
    public final Error DESCRIPTION_LENGTH_ERROR = Error.of("invalid.reportDescription", "");

    private static final ReportValidator INSTANCE = new ReportValidator();
    public static ReportValidator getInstance() {
        return INSTANCE;
    }


    @Override
    public ValidationResult validate(ReportDto obj) {
        ValidationResult vr = new ValidationResult();

        if (obj.getDescription().codePoints().count() > 1000) {
            vr.add(DESCRIPTION_LENGTH_ERROR);
        }

        return vr;
    }
}
