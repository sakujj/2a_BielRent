package by.fpmibsu.bielrent.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ListingORM implements Entity {
        private Long id;
        private Address address;
        private Filter filter;
        private User user;
        private List<Photo> photos;
        private PropertyType propertyType;
        private String description;
}
