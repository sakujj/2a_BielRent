package by.fpmibsu.bielrent.mapper.toentity;

import by.fpmibsu.bielrent.dto.HouseFilterDto;
import by.fpmibsu.bielrent.entity.HouseFilter;
import by.fpmibsu.bielrent.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HouseFilterMapperToEntity implements Mapper<HouseFilter, HouseFilterDto> {
    private static final HouseFilterMapperToEntity INSTANCE = new HouseFilterMapperToEntity();
    public static HouseFilterMapperToEntity getInstance() {
        return INSTANCE;
    }

    @Override
    public HouseFilter mapFrom(HouseFilterDto obj) {
        HouseFilter filter = new HouseFilter();
        FilterMapperToEntity.getInstance().mapDtoToFilter(obj.getFilterDto(), filter);

        filter.setLandArea(obj.getLandArea());
        filter.setHasOtherBuildings(obj.getHasOtherBuildings());
        return filter;
    }
}