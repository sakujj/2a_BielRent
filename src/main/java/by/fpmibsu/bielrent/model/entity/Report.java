package by.fpmibsu.bielrent.model.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class Report implements Entity{
    private Long id;
    private Long userId;

    private String description;
}
