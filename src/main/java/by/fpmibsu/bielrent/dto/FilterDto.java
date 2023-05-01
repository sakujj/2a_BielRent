package by.fpmibsu.bielrent.dto;

import lombok.Builder;
import lombok.Value;

import java.sql.Date;
import java.time.LocalDateTime;

@Value
@Builder
public class FilterDto {
    private long id;
    private long listingId;

    private int roomCount;
    private int floorCount;
    private int bedroomCount;
    private int balconyCount;
    private double squareArea;
    private long priceMonthly;
    private int buildYear;
    private boolean hasBathroom;
    private String rentalPeriodStart;
    private String rentalPeriodEnd;
    private boolean hasWashingMachine;
    private boolean hasFurniture;
    private boolean hasWifi;
    private boolean hasElevator;
}
