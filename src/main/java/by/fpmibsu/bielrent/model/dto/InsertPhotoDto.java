package by.fpmibsu.bielrent.model.dto;

import lombok.Builder;
import lombok.Value;

import javax.servlet.http.Part;


@Value
@Builder
public class InsertPhotoDto {
    //private long id;
    private long listingId;
    private Part photo;
}
