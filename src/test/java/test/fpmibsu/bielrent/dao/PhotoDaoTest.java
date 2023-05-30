package test.fpmibsu.bielrent.dao;

import static org.assertj.core.api.Assertions.*;

import by.fpmibsu.bielrent.model.dao.PhotoDaoImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.entity.Photo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import test.fpmibsu.bielrent.connectionpool.TestConnectionPoolImpl;


import java.sql.SQLException;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class PhotoDaoTest {
    static TestConnectionPoolImpl testConnPool = TestConnectionPoolImpl.getInstance();
    static PhotoDaoImpl photoDao = PhotoDaoImpl.getInstance();


    @Nested
    @Tag("select")
    class Select {
        @ParameterizedTest
        @MethodSource("test.fpmibsu.bielrent.dao.PhotoDaoTest#getSelectArgs")
        @DisplayName("selects a photo")
        void selectPhoto(Photo expected) throws DaoException {
            try (var conn = testConnPool.getConnection()) {
                var actual = photoDao.select(expected.getId(), conn);
                assertThat(actual.get()).isEqualTo(expected);
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }

    static Stream<Arguments> getSelectArgs() {
        return Stream.of(
                Arguments.of(Photo.builder()
                        .id(1L)
                        .listingId(1L)
                        .path("path11")
                        .build()),
                Arguments.of(Photo.builder()
                        .id(3L)
                        .listingId(2L)
                        .path("path21")
                        .build())
        );
    }
}
