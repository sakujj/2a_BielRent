package by.fpmibsu.bielrent.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public abstract class Filter implements Entity {
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
    private LocalDate rentalPeriodStart;
    private LocalDate rentalPeriodEnd;
    private boolean hasWashingMachine;
    private boolean hasFurniture;
    private boolean hasWifi;
    private boolean hasElevator;

}