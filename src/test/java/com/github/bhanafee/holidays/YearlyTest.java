package com.github.bhanafee.holidays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Optional;
import java.util.function.Function;

import static org.testng.Assert.*;

public class YearlyTest {

    /**
     * Constructor parameters that should violate assertions
     */
    @DataProvider(name = "bad-constructors")
    public Object[][] badConstructors() {
        return new Object[][]{
                {null, 1, DayOfWeek.MONDAY, 2000},
                {Month.MAY, -1, null, 2000},
                {Month.MAY, 1, DayOfWeek.MONDAY, Year.MIN_VALUE - 1},
                {Month.MAY, 1, DayOfWeek.MONDAY, Year.MAX_VALUE + 1}
        };
    }

    /**
     * Test constructor assertions
     */
    @Test(dataProvider = "bad-constructors", expectedExceptions = {AssertionError.class})
    public void testConstructorParameters(final Month month, final int ordinal, final DayOfWeek dayOfWeek, final int earliest) {
        new Yearly(month, ordinal, dayOfWeek, earliest);
    }

    /**
     * Year values that should generate empty anniversaries for February 29
     */
    @DataProvider(name="empties")
    public Object[][] empties() {
        return new Object[][] {
                {null},
                {Year.MIN_VALUE - 1},
                {Year.MAX_VALUE + 1}
        };
    }

    @Test(dataProvider = "empties")
    public void testApplyEmpty(final Integer year) {
        Function<Integer, Optional<LocalDate>> test = new Yearly(Month.FEBRUARY, 1, DayOfWeek.MONDAY);
        assertTrue(test.apply(year).isEmpty());
    }

    @DataProvider(name="tests")
    public Object[][] tests() {
        return new Object[][] {
                {Month.JANUARY, 3, DayOfWeek.MONDAY, 2020, 20},
                {Month.JANUARY, 3, DayOfWeek.MONDAY, 2021, 18},
                {Month.JANUARY, 3, DayOfWeek.MONDAY, 2022, 17},
                {Month.JANUARY, 3, DayOfWeek.MONDAY, 2023, 16},
                {Month.NOVEMBER, 4, DayOfWeek.THURSDAY, 2020, 26},
                {Month.NOVEMBER, 4, DayOfWeek.THURSDAY, 2021, 25},
                {Month.NOVEMBER, 4, DayOfWeek.THURSDAY, 2022, 24},
                {Month.NOVEMBER, 4, DayOfWeek.THURSDAY, 2023, 23},
                {Month.MAY, -1, DayOfWeek.MONDAY, 2020, 25},
                {Month.MAY, -1, DayOfWeek.MONDAY, 2021, 31},
                {Month.MAY, -1, DayOfWeek.MONDAY, 2022, 30},
                {Month.MAY, -1, DayOfWeek.MONDAY, 2023, 29}
        };
    }

    @Test(dataProvider = "tests")
    public void testApply(final Month month, final int ordinal, final DayOfWeek dayOfWeek, final int year, final int expected) {
        Function<Integer, Optional<LocalDate>> test = new Yearly(month, ordinal, dayOfWeek);
        assertEquals(test.apply(year), Optional.of(LocalDate.of(year, month, expected)),
                "Should generate observance day");
    }

    @Test
    public void testApplyEarliest() {
        Function<Integer, Optional<LocalDate>> test = new Yearly(Month.JANUARY, 1, DayOfWeek.MONDAY, 2000);
        assertEquals(test.apply(1999), Optional.empty(),
                "Should generate empty before earliest year");
        assertTrue(test.apply(2000).isPresent(),
                "Should generate date for earliest year");
        assertTrue(test.apply(2001).isPresent(),
                "Should generate date for year after earliest");
    }
}
