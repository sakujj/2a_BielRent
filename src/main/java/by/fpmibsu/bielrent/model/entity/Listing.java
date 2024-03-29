package by.fpmibsu.bielrent.model.entity;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class Listing implements Entity {
    private Long id;
    private String name;
    private Long addressId;
    private Long userId;

    private PropertyType propertyTypeName;
    private String description;
}
