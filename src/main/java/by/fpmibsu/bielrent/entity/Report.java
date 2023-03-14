package by.fpmibsu.bielrent.entity;

public class Report {
    private long id;
    private Client client;
    private String details;
    private Client accusedClient;

    public Report() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Client getAccusedClient() {
        return accusedClient;
    }

    public void setAccusedClient(Client accusedClient) {
        this.accusedClient = accusedClient;
    }
}