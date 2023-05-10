package by.fpmibsu.bielrent.controller.dto;

import lombok.Builder;
import lombok.Value;

import javax.servlet.http.Part;


@Value
@Builder
public class PhotoDto {
    //private long id;
    private long listingId;
    private Part photo;
}
