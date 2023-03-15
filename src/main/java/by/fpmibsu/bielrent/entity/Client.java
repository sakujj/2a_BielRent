package by.fpmibsu.bielrent.entity;

import java.util.List;

public class Client extends User{
    private double rating;
    private List<Notification> notifications;
    private List<Report> reports;
    private List<Listing> listings;
    public Client() {}

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
