package by.fpmibsu.bielrent.dto;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReportDto {
    private long id;
    private long userId;
    private String description;
}
