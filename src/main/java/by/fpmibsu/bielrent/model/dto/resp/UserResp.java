package by.fpmibsu.bielrent.model.dto.resp;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public  class UserResp {
      long id;
      String email;
      String name;
      String role;
      String rating;
}
