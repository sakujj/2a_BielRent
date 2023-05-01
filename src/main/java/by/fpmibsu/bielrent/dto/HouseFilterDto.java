package by.fpmibsu.bielrent.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HouseFilterDto {
    private FilterDto filterDto;
    private double landArea;
    private boolean hasOtherBuildings;
}
