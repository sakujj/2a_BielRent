package by.fpmibsu.bielrent.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Report implements Entity{
    private Long id;
    private Long userId;

    private String description;
}
