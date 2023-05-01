package by.fpmibsu.bielrent.mapper.toentity;

import by.fpmibsu.bielrent.dto.InsertUserDto;
import by.fpmibsu.bielrent.entity.Role;
import by.fpmibsu.bielrent.entity.User;
import by.fpmibsu.bielrent.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InsertUserMapperToEntity implements Mapper<User, InsertUserDto> {
    private static final InsertUserMapperToEntity INSTANCE = new InsertUserMapperToEntity();
    public static InsertUserMapperToEntity getInstance() {
        return INSTANCE;
    }

    @Override
    public User mapFrom(InsertUserDto obj) {
        return User.builder()
                .email(obj.getEmail())
                .name(obj.getName())
                .role(Role.CLIENT)
                .rating(null)
                .password(obj.getPassword())
                .build();
    }
}
