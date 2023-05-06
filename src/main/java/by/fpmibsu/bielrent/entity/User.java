package by.fpmibsu.bielrent.entity;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User implements Entity {
    private Long id;
    // report list
    // listing list

    private String email;
    private String password;
    private String name;
    private Role role;
    private BigDecimal rating;
}
