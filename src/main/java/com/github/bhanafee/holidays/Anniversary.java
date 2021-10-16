package com.github.bhanafee.holidays;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Optional;
import java.util.function.Function;

/** Yearly on fixed day of a fixed month. */
@SuppressWarnings("java:S4276") // Do not replace with IntFunction because we need andThen() method
public class Anniversary implements Function<Integer, Optional<LocalDate>> {
    private final Month month;
    private final int dayOfMonth;
    private final int earliest;

    /**
     * Generate anniversary dates.
     *
     * @param month      the month of each year
     * @param dayOfMonth the day of the month
     * @param earliest   the earliest year this anniversary can occur. This may be the year the event that is being
     *                   recognized occurred, or the year that it became a recognized observance.
     */
    public Anniversary(final Month month, final int dayOfMonth, final int earliest) {
        if (month == null) throw new AssertionError("Month cannot be null");
        if (dayOfMonth < 1 || dayOfMonth > 31) throw new AssertionError("Day of month out of range");
        if (earliest < Year.MIN_VALUE || earliest > Year.MAX_VALUE) throw new AssertionError("Earliest year out of range");
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.earliest = earliest;
    }

    /**
     * Generate anniversary dates.
     *
     * @param month      the month of each year
     * @param dayOfMonth the day of the month
     */
    public Anniversary(final Month month, final int dayOfMonth) {
        this(month, dayOfMonth, Year.MIN_VALUE);
    }

    /**
     * Generate an anniversary date given the year.
     *
     * @param year the year of the anniversary
     * @return the anniversary date for the year, if it occurs
     */
    @Override
    public Optional<LocalDate> apply(final Integer year) {
        if (year == null || year < this.earliest) {
            return Optional.empty();
        } else try {
            return Optional.of(LocalDate.of(year, month, dayOfMonth));
        } catch (final DateTimeException e) {
            return Optional.empty();
        }
    }
}
