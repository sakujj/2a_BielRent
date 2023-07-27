package by.fpmibsu.bielrent.model.dto.resp;

import by.fpmibsu.bielrent.model.dto.req.AddressReq;
import by.fpmibsu.bielrent.model.dto.req.FilterReq;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ListingOrmResp {
     Long id;
     AddressResp address;
     FilterResp filter;
     UserResp user;
     List<PhotoResp> photos;

     String name;
     String propertyTypeName;
     String description;
}