package test.fpmibsu.bielrient.dao;

import by.fpmibsu.bielrent.model.dao.AddressDaoImpl;
import by.fpmibsu.bielrent.model.entity.Address;
import by.fpmibsu.bielrent.model.entity.Region;
import lombok.SneakyThrows;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class AddressDaoImplTest {

    @DataProvider(name = "positiveSelectInput")
    public Object[][] createPositiveInput() {
        return new Object[][] {
                {1L, Address.builder()
                        .city("Minsk")
                        .street("vul. Lobanka")
                        .id(1L)
                        .districtAdministrative("Frunzenski rajon")
                        .districtMicro("Suharevo")
                        .regionName(Region.MINSK_CITY)
                        .houseNumber(79)
                        .build()},
                {2L, Address.builder()
                        .city("Grodno")
                        .street("vul. XXX")
                        .id(2L)
                        .districtAdministrative("X rajon")
                        .districtMicro("XX mikrorajon")
                        .regionName(Region.GRODNO_REGION)
                        .houseNumber(13)
                        .build()}
        };
    }

    @DataProvider(name = "negativeSelectInput")
    public Object[][] createNegativeInput() {
        return new Object[][] {
                {2L, Address.builder()
                        .city("Minsk")
                        .street("vul. Lobanka")
                        .id(1L)
                        .districtAdministrative("Frunzenski rajon")
                        .districtMicro("Suharevo")
                        .regionName(Region.MINSK_CITY)
                        .houseNumber(79)
                        .build()},
                {1L, Address.builder()
                        .city("Grodno")
                        .street("vul. XXX")
                        .id(2L)
                        .districtAdministrative("X rajon")
                        .districtMicro("XX mikrorajon")
                        .regionName(Region.GRODNO_REGION)
                        .houseNumber(13)
                        .build()}
        };
    }
    @SneakyThrows
    @Test(description = "select negative test",
    dataProvider = "negativeSelectInput")
    public void selectNegativeTest(long id, Address expected) {
        Address actual = AddressDaoImpl.getInstance().select(id).get();
        assertNotEquals(actual, expected);
    }

    @SneakyThrows
    @Test(description = "select positive test",
            dataProvider = "positiveSelectInput")
    public void selectPositiveTest(long id, Address expected) {
        Address actual = AddressDaoImpl.getInstance().select(id).get();
        assertEquals(actual, expected);
    }
}
