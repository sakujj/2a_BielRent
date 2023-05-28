package by.fpmibsu.bielrent.model.dto.validator;

public interface Validator<T> {
    ValidationResult validate(T obj);
}