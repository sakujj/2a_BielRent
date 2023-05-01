package by.fpmibsu.bielrent.mapper.todto;

import by.fpmibsu.bielrent.dto.UserDto;
import by.fpmibsu.bielrent.entity.User;
import by.fpmibsu.bielrent.mapper.Mapper;
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
                .role(obj.getRole().toString())
                .rating(obj.getRating().toString())
                .build();
    }
}
