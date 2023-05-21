package test.fpmibsu.bielrient.dao;

import by.fpmibsu.bielrent.model.dao.ListingDaoImpl;
import by.fpmibsu.bielrent.model.dao.PhotoDaoImpl;
import by.fpmibsu.bielrent.model.dao.UserDaoImpl;
import by.fpmibsu.bielrent.model.entity.Photo;
import by.fpmibsu.bielrent.model.entity.Role;
import by.fpmibsu.bielrent.model.entity.User;
import lombok.SneakyThrows;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;

public class PhotoDaoImplTest {
    @DataProvider(name = "positiveSelectInput")
    public Object[][] createPositiveInput() {
        return new Object[][] {
                {1L, Optional.ofNullable(Photo.builder()
                        .id(1L)
                        .listingId(3L)
                        .path("hello")
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
    public void selectByIdNegativeTest(long id,  Optional<Photo> expected) {
        Optional<Photo> actual = PhotoDaoImpl.getInstance().select(id);
        assertEquals(actual, expected);
    }

    @SneakyThrows
    @Test(description = "select positive test",
            dataProvider = "positiveSelectInput")
    public void selectByIdPositiveTest(long id,  Optional<Photo> expected) {
        Optional<Photo> actual = PhotoDaoImpl.getInstance().select(id);
        assertEquals(actual, expected);
    }
}
