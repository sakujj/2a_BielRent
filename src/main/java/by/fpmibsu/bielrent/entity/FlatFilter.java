package by.fpmibsu.bielrent.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
public class FlatFilter extends Filter{
    private Integer floorNumber;
}
