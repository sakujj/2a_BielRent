package by.fpmibsu.bielrent.model.dtovalidator;

import by.fpmibsu.bielrent.model.dto.req.ReportReq;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportValidator implements Validator<ReportReq>{
    public final Error DESCRIPTION_LENGTH_ERROR = Error.of("invalid.reportDescription", "");

    private static final ReportValidator INSTANCE = new ReportValidator();
    public static ReportValidator getInstance() {
        return INSTANCE;
    }


    @Override
    public ValidationResult validate(ReportReq obj) {
        ValidationResult vr = new ValidationResult();

        if (obj.getDescription().codePoints().count() > 1000) {
            vr.add(DESCRIPTION_LENGTH_ERROR);
        }

        return vr;
    }
}
