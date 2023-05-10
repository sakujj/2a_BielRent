package by.fpmibsu.bielrent.model.mapper.toentity;

import by.fpmibsu.bielrent.controller.dto.ReportDto;
import by.fpmibsu.bielrent.model.entity.Report;
import by.fpmibsu.bielrent.model.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportMapperToEntity implements Mapper<Report, ReportDto> {
    private static final ReportMapperToEntity INSTANCE = new ReportMapperToEntity();
    public static ReportMapperToEntity getInstance() {
        return INSTANCE;
    }

    @Override
    public Report mapFrom(ReportDto obj) {
        return Report.builder()
                .id(obj.getId())
                .description(obj.getDescription())
                .userId(obj.getUserId())
                .build();
    }
}