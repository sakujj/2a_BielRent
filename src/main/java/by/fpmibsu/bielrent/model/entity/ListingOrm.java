package by.fpmibsu.bielrent.model.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ListingOrm implements Entity {
        private Long id;
        private String name;
        private Address address;
        private Filter filter;
        private User user;
        private List<Photo> photos;
        private PropertyType propertyTypeName;
        private String description;
}
