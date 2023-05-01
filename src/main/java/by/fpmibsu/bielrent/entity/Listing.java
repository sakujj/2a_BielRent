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

    private long id;
    private long addressId;
    private long filterId;
    private long userId;
    //photo list

    private PropertyType propertyType;
    private String description;
}
