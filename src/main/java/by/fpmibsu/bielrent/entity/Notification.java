package by.fpmibsu.bielrent.entity;

public class Notification implements Entity{
    private long id;
    private Filter compositeFilter;
    private PropertyType propertyType;

    public Notification() {}

    public Filter getCompositeFilter() {
        return compositeFilter;
    }

    public void setCompositeFilter(Filter compositeFilter) {
        this.compositeFilter = compositeFilter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }
}
