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

    @Nested
    @Tag("insert_delete")
    class InsertAndDelete {
        @ParameterizedTest
        @MethodSource("test.fpmibsu.bielrent.dao.UserDaoTest#getInsertArgs")
        @DisplayName("selects and delete a user")
        void insertAndDeleteUser(User expected) throws DaoException {
            try (var conn = testConnPool.getConnection()) {
                long actualID = userDao.insert(expected, conn);
                var actual = userDao.select(actualID, conn);
                expected.setId(actualID);
                assertThat(actual).isEqualTo(expected);
                userDao.delete(actualID, conn);
                actual = userDao.select(actualID, conn);
                assertThat(actual).isEqualTo(null);
            }
            catch (SQLException e) {
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

    static Stream<Arguments> getInsertArgs() {
        return Stream.of(
                Arguments.of(User.builder()
                        .email("linux@gmail.com")
                        .password("123")
                        .name("Linux")
                        .rating(BigDecimal.valueOf(3.3))
                        .role(Role.CLIENT)
                        .build()),
                Arguments.of(User.builder()
                        .email("organ@gmail.com")
                        .password("1w3")
                        .name("Organ")
                        .rating(BigDecimal.valueOf(2.1))
                        .role(Role.ADMIN)
                        .build())
        );
    }
}
