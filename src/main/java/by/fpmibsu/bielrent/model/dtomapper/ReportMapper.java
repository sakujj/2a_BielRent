package by.fpmibsu.bielrent.model.dtomapper;

import by.fpmibsu.bielrent.model.dto.req.ReportReq;
import by.fpmibsu.bielrent.model.dto.resp.ReportResp;
import by.fpmibsu.bielrent.model.entity.Report;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportMapper {
    private static final ReportMapper INSTANCE = new ReportMapper();
    public static ReportMapper getInstance() {
        return INSTANCE;
    }

    public Report toEntity(ReportReq obj, Long userId) {
        return Report.builder()
                .id(-1L)
                .description(obj.getDescription())
                .userId(userId)
                .build();
    }

    public ReportResp fromEntity(Report obj) {
        return ReportResp.builder()
                .id(obj.getId())
                .description(obj.getDescription())
                .userId(obj.getUserId())
                .build();
    }
}