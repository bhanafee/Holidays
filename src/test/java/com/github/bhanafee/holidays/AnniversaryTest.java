package com.github.bhanafee.holidays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Optional;
import java.util.function.Function;

import static org.testng.Assert.*;

public class AnniversaryTest {

    /**
     * Constructor parameters that should violate assertions
     */
    @DataProvider(name = "bad-constructors")
    public Object[][] badConstructors() {
        return new Object[][]{
                {null, 1, 2000},
                {Month.MAY, -1, 2000},
                {Month.MAY, 0, 2000},
                {Month.MAY, 32, 2000},
                {Month.MAY, 1, Year.MIN_VALUE - 1},
                {Month.MAY, 1, Year.MAX_VALUE + 1}
        };
    }

    /**
     * Test constructor assertions
     */
    @Test(dataProvider = "bad-constructors", expectedExceptions = {AssertionError.class})
    public void testConstructorParameters(final Month month, final int dayOfMonth, final int earliest) {
        new Anniversary(month, dayOfMonth, earliest);
    }

    /**
     * Year values that should generate empty anniversaries for February 29
     */
    @DataProvider(name="empties")
    public Object[][] empties() {
        return new Object[][] {
                {null},
                {Year.MIN_VALUE - 1},
                {Year.MAX_VALUE + 1},
                {2001}
        };
    }

    @Test(dataProvider = "empties")
    public void testApplyEmpty(final Integer year) {
        Function<Integer, Optional<LocalDate>> test = new Anniversary(Month.FEBRUARY, 29);
        assertTrue(test.apply(year).isEmpty());
    }

    @Test
    public void testApply() {
        Function<Integer, Optional<LocalDate>> test = new Anniversary(Month.FEBRUARY, 29);
        assertEquals(test.apply(2000), Optional.of(LocalDate.of(2000, Month.FEBRUARY, 29)),
                "Should generate simple anniversary");
    }

    @Test
    public void testApplyEarliest() {
        Function<Integer, Optional<LocalDate>> test = new Anniversary(Month.JANUARY, 31, 2000);
        assertEquals(test.apply(1999), Optional.empty(),
                "Should generate empty before earliest year");
        assertTrue(test.apply(2000).isPresent(),
                "Should generate date for earliest year");
        assertTrue(test.apply(2001).isPresent(),
                "Should generate date for year after earliest");
    }
}
