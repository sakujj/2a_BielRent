package by.fpmibsu.bielrent.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class UserDto {
    private final long id;
    private final String email;
    private final String name;
    private final String role;
    private final String rating;
}
