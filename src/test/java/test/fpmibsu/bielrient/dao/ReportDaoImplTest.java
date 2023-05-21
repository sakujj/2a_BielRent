package test.fpmibsu.bielrient.dao;

import by.fpmibsu.bielrent.model.dao.PhotoDaoImpl;
import by.fpmibsu.bielrent.model.dao.ReportDaoImpl;
import by.fpmibsu.bielrent.model.entity.Photo;
import by.fpmibsu.bielrent.model.entity.Report;
import lombok.SneakyThrows;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;

public class ReportDaoImplTest {
    @DataProvider(name = "positiveSelectInput")
    public Object[][] createPositiveInput() {
        return new Object[][] {
                {2L, Optional.ofNullable(Report.builder()
                        .id(2L)
                        .userId(3L)
                        .description("fool")
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
    public void selectByIdNegativeTest(long id,  Optional<Report> expected) {
        Optional<Report> actual = ReportDaoImpl.getInstance().select(id);
        assertEquals(actual, expected);
    }

    @SneakyThrows
    @Test(description = "select positive test",
            dataProvider = "positiveSelectInput")
    public void selectByIdPositiveTest(long id,  Optional<Report> expected) {
        Optional<Report> actual = ReportDaoImpl.getInstance().select(id);
        assertEquals(actual, expected);
    }
}
