package by.fpmibsu.bielrent.model.dto.req;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FilterReq {
     String roomCount;
     String floorCount;
     String bedroomCount;
     String balconyCount;
     String squareArea;
     String priceMonthly;
     String buildYear;

     String rentalPeriodStart;
     String rentalPeriodEnd;

     String hasWashingMachine;
     String hasFurniture;
     String hasWifi;
     String hasElevator;
     String hasBathroom;
}
