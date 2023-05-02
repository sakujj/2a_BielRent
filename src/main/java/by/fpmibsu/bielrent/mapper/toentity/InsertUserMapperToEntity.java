package by.fpmibsu.bielrent.mapper.toentity;

import by.fpmibsu.bielrent.dto.InsertUserDto;
import by.fpmibsu.bielrent.entity.Role;
import by.fpmibsu.bielrent.entity.User;
import by.fpmibsu.bielrent.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InsertUserMapperToEntity implements Mapper<User, InsertUserDto> {
    private static final InsertUserMapperToEntity INSTANCE = new InsertUserMapperToEntity();
    public static InsertUserMapperToEntity getInstance() {
        return INSTANCE;
    }

   // @SneakyThrows
    @Override
    public User mapFrom(InsertUserDto obj) {
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//        KeySpec spec = new PBEKeySpec(obj.getPassword().toCharArray(), salt, 65536, 128);
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//        byte[] hash = factory.generateSecret(spec).getEncoded();
//        System.out.println(hash.length);
//        System.out.println(hash);

        return User.builder()
                .email(obj.getEmail())
                .name(obj.getName())
                .role(Role.CLIENT)
                .rating(null)
                .password(obj.getPassword())
                .build();
    }
}
