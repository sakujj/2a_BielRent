package by.fpmibsu.bielrent.model.dtomapper;

public interface Mapper<T, F> {
    T mapFrom(F obj);

}
