package by.fpmibsu.bielrent.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InsertUserDto {
    private String email;
    private String name;
    private String password;
}
