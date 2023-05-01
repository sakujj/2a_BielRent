package by.fpmibsu.bielrent.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class HouseFilter extends Filter{
    private double landArea;
    private boolean hasOtherBuildings;
}
