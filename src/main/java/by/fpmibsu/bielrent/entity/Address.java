package by.fpmibsu.bielrent.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Address implements Entity{
    private Long id;
    private Region regionName;
    private String city;
    private String districtAdministrative = null;
    private String districtMicro = null;
    private String street;
    private Integer houseNumber;
}
