package test.fpmibsu.bielrient.dao;

import by.fpmibsu.bielrent.model.dao.FilterDaoImpl;
import by.fpmibsu.bielrent.model.dao.ListingDaoImpl;
import by.fpmibsu.bielrent.model.entity.Filter;
import by.fpmibsu.bielrent.model.entity.Listing;
import by.fpmibsu.bielrent.model.entity.PropertyType;
import lombok.SneakyThrows;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.testng.Assert.assertEquals;

public class FilterDaoImplTest {
    @DataProvider(name = "positiveSelectInput")
    public Object[][] createPositiveInput() {
        return new Object[][] {
                {1L, Optional.ofNullable(Filter.builder()
                        .id(1L)
                        .roomCount(2)
                        .floorCount(2)
                        .bedroomCount(2)
                        .squareArea(55D)
                        .balconyCount(2)
                        .buildYear(2002)
                        .rentalPeriodStart(LocalDate.of(2023,5,25))
                        .rentalPeriodEnd(LocalDate.of(2023,6,25))
                        .priceMonthly(20L)
                        .hasBathroom(Boolean.TRUE)
                        .hasWifi(Boolean.TRUE)
                        .hasWashingMachine(Boolean.TRUE)
                        .hasFurniture(Boolean.TRUE)
                        .hasElevator(Boolean.TRUE)
                        .listingId(3L).build())
                }
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
    public void selectByIdNegativeTest(long id,  Optional<Filter> expected) {
        Optional<Filter> actual = FilterDaoImpl.getInstance().select(id);
        assertEquals(actual, expected);
    }

    @SneakyThrows
    @Test(description = "select positive test",
            dataProvider = "positiveSelectInput")
    public void selectByIdPositiveTest(long id,  Optional<Filter> expected) {
        Optional<Filter> actual = FilterDaoImpl.getInstance().select(id);
        assertEquals(actual, expected);
    }
}
