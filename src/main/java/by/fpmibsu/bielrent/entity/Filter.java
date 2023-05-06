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
    private Long id;
    private Long listingId;

    private Integer roomCount;
    private Integer floorCount;
    private Integer bedroomCount;
    private Integer balconyCount;
    private Double squareArea;
    private Long priceMonthly;
    private Integer buildYear;
    private Boolean hasBathroom;
    private LocalDate rentalPeriodStart;
    private LocalDate rentalPeriodEnd;
    private Boolean hasWashingMachine;
    private Boolean hasFurniture;
    private Boolean hasWifi;
    private Boolean hasElevator;

}