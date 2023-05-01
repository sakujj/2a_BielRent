package by.fpmibsu.bielrent.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
public class HouseFilter extends Filter{
    private double landArea;
    private boolean hasOtherBuildings;
}
