package by.fpmibsu.bielrent.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PhotoDto {
    long id;
    long listingId;
    String path;
}
