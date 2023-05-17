package by.fpmibsu.bielrent.model.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class Address implements Entity{
    private Long id;
    private Region regionName;
    private String city;
    private String districtAdministrative = null;
    private String districtMicro = null;
    private String street;
    private Integer houseNumber;
}
