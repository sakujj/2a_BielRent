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
                        .build()),
                Arguments.of(Report.builder()
                        .id(4L)
                        .userId(4L)
                        .description("Сообщаю о ...")
                        .build()),
                Arguments.of(Report.builder()
                        .id(5L)
                        .userId(4L)
                        .description("Вот так вот")
                        .build()),
                Arguments.of(Report.builder()
                        .id(6L)
                        .userId(4L)
                        .description("Вот как бывает")
                        .build()),
                Arguments.of(Report.builder()
                        .id(7L)
                        .userId(5L)
                        .description("Все плачевно")
                        .build())
        );
    }
}
