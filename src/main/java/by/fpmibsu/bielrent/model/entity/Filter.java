package by.fpmibsu.bielrent.model.entity;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Filter implements Entity {
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