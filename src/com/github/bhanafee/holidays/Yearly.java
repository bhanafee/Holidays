package com.github.bhanafee.holidays;

import java.time.*;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;
import java.util.function.Function;

/** Yearly on the nth weekday of a fixed month. */
@SuppressWarnings("java:S4276") // Do not replace with IntFunction because we need andThen() method
public class Yearly implements Function<Integer, Optional<LocalDate>> {
    private final Month month;
    private final TemporalAdjuster adjuster;
    private final int earliest;

    /**
     * Generate an event that occurs yearly on the nth weekday of a month
     *
     * @param month    the month of each year
     * @param ordinal  which occurrence of day within the month
     * @param day      the day of the week
     * @param earliest the earliest year this anniversary can occur. This may be the year the event that is being
     *                 recognized occurred, or the year that it became a recognized observance.
     */
    public Yearly(final Month month, final int ordinal, final DayOfWeek day, final int earliest) {
        if (month == null) throw new AssertionError("Month cannot be null");
        if (day == null) throw new AssertionError("Day of week cannot be null");
        if (earliest < Year.MIN_VALUE || earliest > Year.MAX_VALUE) throw new AssertionError("Earliest year out of range");
        this.month = month;
        this.adjuster = TemporalAdjusters.dayOfWeekInMonth(ordinal, day);
        this.earliest = earliest;
    }

    /**
     * Generate an event that occurs yearly on the nth weekday of a month
     *
     * @param month    the month of each year
     * @param ordinal  which occurrence of day within the month
     * @param day      the day of the week
     */
    public Yearly(final Month month, final int ordinal, final DayOfWeek day) {
        this(month, ordinal, day, Year.MIN_VALUE);
    }

    /**
     * Generate an observance date given the year.
     *
     * @param year the year of the observance
     * @return the observance date for the year, if it occurs
     */
    @Override
    public Optional<LocalDate> apply(final Integer year) {
        if (year == null || year < earliest) {
            return Optional.empty();
        } else try {
            return Optional.of(LocalDate.of(year, month, 1).with(adjuster));
        } catch (final DateTimeException e) {
            return Optional.empty();
        }
    }
}
