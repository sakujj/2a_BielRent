package by.fpmibsu.bielrent.dto.validator;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends Exception{
    @Getter
    List<Error> errors = new ArrayList<>();

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}
