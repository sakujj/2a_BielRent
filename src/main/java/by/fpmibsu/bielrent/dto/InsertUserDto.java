package by.fpmibsu.bielrent.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InsertUserDto {
    private String email;
    private String name;
    private String role;
    private String rating;
    private String password;
}
