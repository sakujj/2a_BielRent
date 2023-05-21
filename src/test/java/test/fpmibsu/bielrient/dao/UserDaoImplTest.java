package test.fpmibsu.bielrient.dao;

import by.fpmibsu.bielrent.model.dao.AddressDaoImpl;
import by.fpmibsu.bielrent.model.dao.UserDaoImpl;
import by.fpmibsu.bielrent.model.entity.Address;
import by.fpmibsu.bielrent.model.entity.Region;
import by.fpmibsu.bielrent.model.entity.Role;
import by.fpmibsu.bielrent.model.entity.User;
import lombok.SneakyThrows;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class UserDaoImplTest {
    @DataProvider(name = "positiveSelectInput")
    public Object[][] createPositiveInput() {
        return new Object[][] {
                {3L,Optional.ofNullable(User.builder().id(3L).email("pasha@lox").password("123456789").name("valera").role(Role.valueOf("CLIENT")).build())}
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
    public void selectByIdNegativeTest(long id,  Optional<User> expected) {
        Optional<User> actual = UserDaoImpl.getInstance().select(id);
        assertEquals(actual, expected);
    }

    @SneakyThrows
    @Test(description = "select positive test",
            dataProvider = "positiveSelectInput")
    public void selectByIdPositiveTest(long id,  Optional<User> expected) {
        Optional<User> actual = UserDaoImpl.getInstance().select(id);
        assertEquals(actual, expected);
    }
}
