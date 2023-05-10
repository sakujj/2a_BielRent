package by.fpmibsu.bielrent.controller.dto.validator;

public interface Validator<T> {
    ValidationResult validate(T obj);
}