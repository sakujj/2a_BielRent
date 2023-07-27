package by.fpmibsu.bielrent.model.dtovalidator;

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
