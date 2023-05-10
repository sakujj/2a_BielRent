package by.fpmibsu.bielrent.model.mapper;

public interface Mapper<T, F> {
    T mapFrom(F obj);

}
