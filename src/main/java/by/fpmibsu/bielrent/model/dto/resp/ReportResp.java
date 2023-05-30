package by.fpmibsu.bielrent.model.dto.resp;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReportResp {
     long id;
     long userId;
     String description;
}