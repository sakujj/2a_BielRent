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
    private long id;
    private int regionNumber;
    private String city;
    private String districtAdministrative = null;
    private String districtMicro = null;
    private String street;
    private int houseNumber;
}
