package by.fpmibsu.bielrent.mapper.todto;

import by.fpmibsu.bielrent.dto.ReportDto;
import by.fpmibsu.bielrent.entity.Report;
import by.fpmibsu.bielrent.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportMapperToDto implements Mapper<ReportDto, Report> {
    private static final ReportMapperToDto INSTANCE = new ReportMapperToDto();
    public static ReportMapperToDto getInstance() {
        return INSTANCE;
    }

    @Override
    public ReportDto mapFrom(Report obj) {
        return ReportDto.builder()
                .id(obj.getId())
                .description(obj.getDescription())
                .userId(obj.getUserId())
                .build();
    }
}
