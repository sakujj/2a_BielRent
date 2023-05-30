package by.fpmibsu.bielrent.model.dto.req;

import lombok.Builder;
import lombok.Value;

import javax.servlet.http.Part;


@Value
@Builder
public class PhotoReq {
     Part photo;
}
