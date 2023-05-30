package by.fpmibsu.bielrent.model.dto.req;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserReq {
     String email;
     String name;
     String password;
}
