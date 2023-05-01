package by.fpmibsu.bielrent.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Report implements Entity{
    private long id;
    private long userId;

    private String description;
}
