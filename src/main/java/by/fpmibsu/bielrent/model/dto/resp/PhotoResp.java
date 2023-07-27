package by.fpmibsu.bielrent.model.dto.resp;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PhotoResp {
    long id;
    long listingId;

    String path;
}
