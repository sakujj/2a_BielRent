package test.fpmibsu.bielrent.dao;

import static org.assertj.core.api.Assertions.*;

import by.fpmibsu.bielrent.model.dao.ReportDaoImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.entity.Report;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import test.fpmibsu.bielrent.connectionpool.TestConnectionPoolImpl;

import java.sql.SQLException;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ReportDaoTest {

    static TestConnectionPoolImpl testConnPool = TestConnectionPoolImpl.getInstance();
    static ReportDaoImpl reportDao = ReportDaoImpl.getInstance();

    @Nested
    @Tag("select")
    class Select {
        @ParameterizedTest
        @MethodSource("test.fpmibsu.bielrent.dao.ReportDaoTest#getSelectArgs")
        @DisplayName("select a report")
        void selectReport(Report expected) throws DaoException {
            try (var conn = testConnPool.getConnection()) {
                var actual = reportDao.select(expected.getId(), conn);
                assertThat(actual.get()).isEqualTo(expected);
            }
            catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }

    @Nested
    @Tag("insert_delete")
    class InsertAndDelete {
        @ParameterizedTest
        @MethodSource("test.fpmibsu.bielrent.dao.ReportDaoTest#getInsertArgs")
        @DisplayName("selects and delete a report")
        void insertAndDeleteReport(Report expected) throws DaoException {
            try (var conn = testConnPool.getConnection()) {
                long actualID = reportDao.insert(expected, conn);
                var actual = reportDao.select(actualID, conn);
                expected.setId(actualID);
                assertThat(actual.get()).isEqualTo(expected);
                reportDao.delete(actualID, conn);
                actual = reportDao.select(actualID, conn);
                assertThat(actual.orElse(null)).isEqualTo(null);
            }
            catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }

    static Stream<Arguments> getSelectArgs() {
        return Stream.of(
                Arguments.of(Report.builder()
                        .id(1L)
                        .userId(1L)
                        .description("Меня обманули...")
                        .build()),
                Arguments.of(Report.builder()
                        .id(2L)
                        .userId(1L)
                        .description("Все плёхо")
                        .build()),
                Arguments.of(Report.builder()
                        .id(3L)
                        .userId(2L)
                        .description("Имеется жалоба")
                        .build())
        );
    }

    static Stream<Arguments> getInsertArgs() {
        return Stream.of(
                Arguments.of(Report.builder()
                        .userId(1L)
                        .description("report 1")
                        .build()),
                Arguments.of(Report.builder()
                        .id(2L)
                        .userId(3L)
                        .description("report 2")
                        .build())
        );
    }
}
