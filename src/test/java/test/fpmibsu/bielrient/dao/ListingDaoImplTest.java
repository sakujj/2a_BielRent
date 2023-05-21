package test.fpmibsu.bielrient.dao;

import by.fpmibsu.bielrent.model.dao.AddressDaoImpl;
import by.fpmibsu.bielrent.model.dao.ListingDaoImpl;
import by.fpmibsu.bielrent.model.dao.UserDaoImpl;
import by.fpmibsu.bielrent.model.entity.*;
import lombok.SneakyThrows;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Optional;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class ListingDaoImplTest {
    @DataProvider(name = "positiveSelectInput")
    public Object[][] createPositiveInput() {
        return new Object[][] {
                {3L,Optional.ofNullable(Listing.builder().id(3L)
                        .name("Flat suharevo")
                        .propertyTypeName(PropertyType.FLAT)
                        .userId(3L)
                        .addressId(1L)
                        .description("hello")
                        .filterId(1L)
                        .build())}
        };
    }

    @DataProvider(name = "negativeSelectInput")
    public Object[][] createNegativeInput() {
        return new Object[][]{
                {4L,Optional.empty()}
        };
    }
    @SneakyThrows
    @Test(description = "select negative test",
            dataProvider = "negativeSelectInput")
    public void selectByIdNegativeTest(long id,  Optional<Listing> expected) {
        Optional<Listing> actual = ListingDaoImpl.getInstance().select(id);
        assertEquals(actual, expected);
    }

    @SneakyThrows
    @Test(description = "select positive test",
            dataProvider = "positiveSelectInput")
    public void selectByIdPositiveTest(long id,  Optional<Listing> expected) {
        Optional<Listing> actual = ListingDaoImpl.getInstance().select(id);
        assertEquals(actual, expected);
    }
}
