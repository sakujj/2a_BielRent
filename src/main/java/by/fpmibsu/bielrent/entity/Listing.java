package by.fpmibsu.bielrent.entity;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Listing implements Entity {

    private Long id;
    private String name;
    private Long addressId;
    private Long filterId;
    private Long userId;
    //photo list

    private PropertyType propertyTypeName;
    private String description;
}
