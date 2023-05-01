package by.fpmibsu.bielrent.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FlatFilterDto {
    private FilterDto filterDto;
    private int floorNumber;
}
