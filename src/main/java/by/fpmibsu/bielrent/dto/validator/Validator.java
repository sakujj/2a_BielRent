package by.fpmibsu.bielrent.dto.validator;

public interface Validator<T> {
    ValidationResult validate(T obj);
}
