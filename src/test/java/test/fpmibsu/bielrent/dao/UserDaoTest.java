package test.fpmibsu.bielrent.dao;

import static org.assertj.core.api.Assertions.*;

import by.fpmibsu.bielrent.model.dao.UserDaoImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.entity.Role;
import by.fpmibsu.bielrent.model.entity.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import test.fpmibsu.bielrent.connectionpool.TestConnectionPoolImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class UserDaoTest {
    static TestConnectionPoolImpl testConnPool = TestConnectionPoolImpl.getInstance();
    static UserDaoImpl userDao = UserDaoImpl.getInstance();


    @Nested
    @Tag("select")
    class Select {
        @ParameterizedTest
        @MethodSource("test.fpmibsu.bielrent.dao.UserDaoTest#getSelectArgs")
        @DisplayName("selects a user")
        void selectUser(User expected) throws DaoException {
            try (var conn = testConnPool.getConnection()) {
                var actual = userDao.select(expected.getId(), conn);
                assertThat(actual).isEqualTo(expected);
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }

    static Stream<Arguments> getSelectArgs() {
        return Stream.of(
                Arguments.of(User.builder()
                        .id(1L)
                        .email("pierce@gmail.com")
                        .password("123")
                        .name("Pierce")
                        .rating(BigDecimal.valueOf(4.3))
                        .role(Role.CLIENT)
                        .build()),
                Arguments.of(User.builder()
                        .id(2L)
                        .email("morgan@gmail.com")
                        .password("1q3")
                        .name("Morgan")
                        .rating(BigDecimal.valueOf(3.1))
                        .role(Role.CLIENT)
                        .build()),
                Arguments.of(User.builder()
                        .id(5L)
                        .email("fos@mail.ru")
                        .password("mai123l12")
                        .name("Димитрий")
                        .rating(BigDecimal.valueOf(4.6))
                        .role(Role.ADMIN)
                        .build())
        );
    }

}
