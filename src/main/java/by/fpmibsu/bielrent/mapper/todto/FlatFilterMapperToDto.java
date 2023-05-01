package by.fpmibsu.bielrent.mapper.todto;

import by.fpmibsu.bielrent.dto.FilterDto;
import by.fpmibsu.bielrent.dto.FlatFilterDto;
import by.fpmibsu.bielrent.entity.FlatFilter;
import by.fpmibsu.bielrent.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlatFilterMapperToDto implements Mapper<FlatFilterDto, FlatFilter> {
    private static final FlatFilterMapperToDto INSTANCE = new FlatFilterMapperToDto();
    public static FlatFilterMapperToDto getInstance() {
        return INSTANCE;
    }

    @Override
    public FlatFilterDto mapFrom(FlatFilter obj) {
        FilterDto fDto = FilterMapperToDto.getInstance().mapFrom(obj);
        FlatFilterDto ffDto = FlatFilterDto.builder()
                .floorNumber(obj.getFloorNumber())
                .filterDto(fDto)
                .build();
        return ffDto;
    }
}
