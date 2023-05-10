package by.fpmibsu.bielrent.controller.dto.validator;

import lombok.Value;

@Value(staticConstructor = "of")
public class Error {
    private String code;
    private String message;
}
