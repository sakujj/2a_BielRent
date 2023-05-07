package by.fpmibsu.bielrent.mapper.todto;

import by.fpmibsu.bielrent.dto.UserDto;
import by.fpmibsu.bielrent.entity.User;
import by.fpmibsu.bielrent.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapperToDto implements Mapper<Optional<UserDto>, Optional<User>> {
    private static final UserMapperToDto INSTANCE = new UserMapperToDto();
    public static UserMapperToDto getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<UserDto> mapFrom(Optional<User> obj) {
        if(obj.isEmpty()){
            return Optional.empty();
        }
        return Optional.ofNullable(UserDto.builder()
                .id(obj.get().getId())
                .email(obj.get().getEmail())
                .role(obj.get().getRole().toString())
                .rating(obj.get().getRating().toString())
                .build());
    }
}
