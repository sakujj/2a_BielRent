package by.fpmibsu.bielrent.model.dtovalidator;

public interface Validator<T> {
    ValidationResult validate(T obj);
}