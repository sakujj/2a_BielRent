package test.fpmibsu.bielrent.dao;

import static org.assertj.core.api.Assertions.*;

import by.fpmibsu.bielrent.model.dao.AddressDaoImpl;
import by.fpmibsu.bielrent.model.dao.exception.DaoException;
import by.fpmibsu.bielrent.model.entity.Address;
import by.fpmibsu.bielrent.model.entity.Region;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import test.fpmibsu.bielrent.connectionpool.TestConnectionPoolImpl;

import java.sql.SQLException;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AddressDaoTest {

    static TestConnectionPoolImpl testConnPool = TestConnectionPoolImpl.getInstance();
    static AddressDaoImpl addressDao = AddressDaoImpl.getInstance();

    @Nested
    @Tag("select")
    class Select {
        @ParameterizedTest
        @MethodSource("test.fpmibsu.bielrent.dao.AddressDaoTest#getSelectArgs")
        @DisplayName("select an address")
        void selectAddress(Address expected) throws DaoException {
            try (var conn = testConnPool.getConnection()) {
                var actual = addressDao.select(expected.getId(), conn);
                assertThat(actual.get()).isEqualTo(expected);
            }
            catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }

    static Stream<Arguments> getSelectArgs() {
        return Stream.of(
                Arguments.of(Address.builder()
                        .id(1L)
                        .regionName(Region.MINSK_CITY)
                        .city("Minsk")
                        .districtAdministrative("Frunzenski rajon")
                        .districtMicro("Suharevo")
                        .street("vul. Lobanka")
                        .houseNumber(79)
                        .build()),
                Arguments.of(Address.builder()
                        .id(2L)
                        .regionName(Region.GRODNO_REGION)
                        .city("Grodno")
                        .districtAdministrative("X rajon")
                        .districtMicro("XX mikrorajon")
                        .street("vul. XXX")
                        .houseNumber(13)
                        .build()),
                Arguments.of(Address.builder()
                        .id(3L)
                        .regionName(Region.MINSK_REGION)
                        .city("Zaslavl")
                        .districtAdministrative(null)
                        .districtMicro(null)
                        .street("vul. Y")
                        .houseNumber(1448)
                        .build())
        );
    }
}
