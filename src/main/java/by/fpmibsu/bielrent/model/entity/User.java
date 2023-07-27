package by.fpmibsu.bielrent.model.entity;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
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
