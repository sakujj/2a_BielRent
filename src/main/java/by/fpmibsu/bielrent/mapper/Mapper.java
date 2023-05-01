package by.fpmibsu.bielrent.mapper;

public interface Mapper<T, F> {
    T mapFrom(F obj);

}
