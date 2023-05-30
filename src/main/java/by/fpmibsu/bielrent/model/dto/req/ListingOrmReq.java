package by.fpmibsu.bielrent.model.dto.req;

import by.fpmibsu.bielrent.model.dto.resp.PhotoResp;
import by.fpmibsu.bielrent.model.dto.resp.UserResp;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ListingOrmReq {
     AddressReq address;
     FilterReq filter;
     List<PhotoReq> photos;
     UserReq user;

     String name;
     String propertyTypeName;
     String description;
}
