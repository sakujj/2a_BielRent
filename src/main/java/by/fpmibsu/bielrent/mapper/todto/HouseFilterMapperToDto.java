package by.fpmibsu.bielrent.mapper.todto;

import by.fpmibsu.bielrent.dto.FilterDto;
import by.fpmibsu.bielrent.dto.FlatFilterDto;
import by.fpmibsu.bielrent.dto.HouseFilterDto;
import by.fpmibsu.bielrent.entity.HouseFilter;
import by.fpmibsu.bielrent.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HouseFilterMapperToDto implements Mapper<HouseFilterDto, HouseFilter> {
    private static final HouseFilterMapperToDto INSTANCE = new HouseFilterMapperToDto();
    public static HouseFilterMapperToDto getInstance() {
        return INSTANCE;
    }

    @Override
    public HouseFilterDto mapFrom(HouseFilter obj) {
        FilterDto fDto = FilterMapperToDto.getInstance().mapFrom(obj);
        return HouseFilterDto.builder()
                .landArea(obj.getLandArea())
                .hasOtherBuildings(obj.getHasOtherBuildings())
                .filterDto(fDto)
                .build();

    }
}
