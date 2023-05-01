package by.fpmibsu.bielrent.mapper.toentity;

import by.fpmibsu.bielrent.dto.FlatFilterDto;
import by.fpmibsu.bielrent.entity.FlatFilter;
import by.fpmibsu.bielrent.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlatFilterMapperToEntity implements Mapper<FlatFilter, FlatFilterDto> {
    private static final FlatFilterMapperToEntity INSTANCE = new FlatFilterMapperToEntity();
    public static FlatFilterMapperToEntity getInstance() {
        return INSTANCE;
    }

    @Override
    public FlatFilter mapFrom(FlatFilterDto obj) {
        FlatFilter filter = new FlatFilter();
        FilterMapperToEntity.getInstance().mapDtoToFilter(obj.getFilterDto(), filter);

        filter.setFloorNumber(obj.getFloorNumber());
        return filter;
    }
}