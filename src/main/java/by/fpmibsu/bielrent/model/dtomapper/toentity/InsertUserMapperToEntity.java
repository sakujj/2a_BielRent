package by.fpmibsu.bielrent.model.dtomapper.toentity;

import by.fpmibsu.bielrent.model.dto.InsertUserDto;
import by.fpmibsu.bielrent.model.entity.Role;
import by.fpmibsu.bielrent.model.entity.User;
import by.fpmibsu.bielrent.model.dtomapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
