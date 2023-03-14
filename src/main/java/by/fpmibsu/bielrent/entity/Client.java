package by.fpmibsu.bielrent.entity;

public class Client extends User{
    private double rating;

    public Client() {}

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
