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

    @Nested
    @Tag("insert_delete")
    class InsertAndDelete {
        @ParameterizedTest
        @MethodSource("test.fpmibsu.bielrent.dao.PhotoDaoTest#getInsertArgs")
        @DisplayName("selects and delete a photo")
        void insertAndDeletePhoto(Photo expected) throws DaoException {
            try (var conn = testConnPool.getConnection()) {
                long actualID = photoDao.insert(expected, conn);
                var actual = photoDao.select(actualID, conn);
                expected.setId(actualID);
                assertThat(actual.get()).isEqualTo(expected);
                photoDao.delete(actualID, conn);
                actual = photoDao.select(actualID, conn);
                assertThat(actual.orElse(null)).isEqualTo(null);
            }
            catch (SQLException e) {
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

    static Stream<Arguments> getInsertArgs() {
        return Stream.of(
                Arguments.of(Photo.builder()
                        .listingId(2L)
                        .path("path13")
                        .build()),
                Arguments.of(Photo.builder()
                        .listingId(3L)
                        .path("path01")
                        .build())
        );
    }
}
