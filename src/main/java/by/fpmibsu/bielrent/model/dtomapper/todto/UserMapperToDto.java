package by.fpmibsu.bielrent.model.dtomapper.todto;

import by.fpmibsu.bielrent.model.dto.UserDto;
import by.fpmibsu.bielrent.model.entity.User;
import by.fpmibsu.bielrent.model.dtomapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapperToDto implements Mapper<UserDto, User> {
    private static final UserMapperToDto INSTANCE = new UserMapperToDto();
    public static UserMapperToDto getInstance() {
        return INSTANCE;
    }

    @Override
    public UserDto mapFrom(User obj) {

        return UserDto.builder()
                .id(obj.getId())
                .email(obj.getEmail())
                .name(obj.getName())
                .role(obj.getRole().toString())
                .rating(obj.getRating() == null ? null: obj.getRating().toString())
                .build();
    }
}
