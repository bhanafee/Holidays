package com.maybitssquid.holidays.us;

import com.maybitssquid.holidays.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Federal implements Events {
    /** Establishment date of four original Federal holidays. */
    public static final LocalDate ORIGINAL = LocalDate.of(1870, Month.JUNE, 28);

    public static final Yearly NEW_YEARS_DAY =
            new Anniversary(US.NEW_YEARS_DAY, ORIGINAL);

    // TODO Uniform Monday Holiday Act 1968
    public static final Yearly WASHINGTONS_BIRTHDAY =
            new Floating(US.GEORGE_WASHINGTON_BIRTHDAY, "Washington's Birthday",
                    LocalDate.of(1879, Month.FEBRUARY, 22), 3, DayOfWeek.MONDAY);

    // TODO Uniform Monday Holiday Act 1968
    public static final Yearly MEMORIAL_DAY =
            new Floating(US.MEMORIAL_DAY, -1, DayOfWeek.MONDAY );

    public static final Yearly JUNETEENTH =
            new Anniversary(US.JUNETEENTH, LocalDate.of(2022, Month.JUNE, 19));

    public static final Yearly INDEPENDENCE_DAY =
            new Anniversary(US.INDEPENDENCE_DAY, ORIGINAL);

    public static final Yearly LABOR_DAY =
            new Floating(US.LABOR_DAY, 1, DayOfWeek.MONDAY);

    public static final Yearly COLUMBUS_DAY =
            new Floating(US.COLUMBUS_DAY, LocalDate.of(1971, Month.OCTOBER, 11), 2, DayOfWeek.MONDAY);

    public static final Yearly VETERANS_DAY =
            new Anniversary(US.ARMISTICE_DAY, "Veteran's Day");

    // TODO reconcile with assorted Presidential proclamations
    //  of the date from 1870 until formally established as 4th Thursday in 1941
    public static final Yearly THANKSGIVING_DAY =
            new Floating(US.THANKSGIVING_DAY, ORIGINAL, 4, DayOfWeek.THURSDAY);

    public static final Yearly CHRISTMAS_DAY =
            new Anniversary(US.CHRISTMAS_DAY, ORIGINAL);

    private Function<IntStream, Stream<Event>> generator = Holidays.generator(
            NEW_YEARS_DAY,
            WASHINGTONS_BIRTHDAY,
            MEMORIAL_DAY,
            JUNETEENTH,
            INDEPENDENCE_DAY,
            LABOR_DAY,
            COLUMBUS_DAY,
            VETERANS_DAY,
            THANKSGIVING_DAY,
            CHRISTMAS_DAY);

    @Override
    public Stream<Event> apply(IntStream years) {
        return generator.apply(years);
    }

}
