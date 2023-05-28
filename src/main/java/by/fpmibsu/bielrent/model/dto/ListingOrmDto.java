package by.fpmibsu.bielrent.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ListingOrmDto {
    private Long id;
    private String name;
    private AddressDto address;
    private FilterDto filter;
    private UserDto user;
    private List<PhotoDto> photos;
    private String propertyTypeName;
    private String description;
}
