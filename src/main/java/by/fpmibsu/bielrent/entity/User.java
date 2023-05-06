package by.fpmibsu.bielrent.entity;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User implements Entity {
    private long id;
    // report list
    // listing list

    private String email;
    private String password;
    private String name;
    private Role role;
    private BigDecimal rating;

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private long id;
        private String email;
        private String password;
        private String name;
        private Role role;
        private BigDecimal rating;

        UserBuilder() {
        }

        public UserBuilder id(long id) {
            this.id = id;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public UserBuilder rating(BigDecimal rating) {
            this.rating = rating;
            return this;
        }

        public User build() {
            return new User(this.id, this.email, this.password, this.name, this.role, this.rating);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", email=" + this.email + ", password=" + this.password + ", name=" + this.name + ", role=" + this.role + ", rating=" + this.rating + ")";
        }
    }
}
