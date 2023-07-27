package by.fpmibsu.bielrent.model.dtovalidator;

import lombok.Value;

@Value(staticConstructor = "of")
public class Error {
    private String code;
    private String message;
}
