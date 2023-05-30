package by.fpmibsu.bielrent.model.dtomapper;

import by.fpmibsu.bielrent.model.dto.req.UserReq;
import by.fpmibsu.bielrent.model.dto.resp.UserResp;
import by.fpmibsu.bielrent.model.entity.Role;
import by.fpmibsu.bielrent.model.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    private static final UserMapper INSTANCE = new UserMapper();
    public static UserMapper getInstance() {
        return INSTANCE;
    }


    public User toEntity(UserReq obj) {
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

    public UserResp fromEntity(User obj) {
        return UserResp.builder()
                .id(obj.getId())
                .email(obj.getEmail())
                .name(obj.getName())
                .role(obj.getRole().toString())
                .rating(obj.getRating() == null ? null: obj.getRating().toString())
                .build();
    }
}
