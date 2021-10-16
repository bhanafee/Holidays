package com.github.bhanafee.holidays;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static java.time.DayOfWeek.*;

/**
 * An observance shifted to a different date, such as to take a holiday on a Monday when the actual anniversary falls on a Sunday.
 */
public class Observed implements UnaryOperator<Optional<Observance>> {
    private final Predicate<Observance> shifts;
    private final TemporalAdjuster adjustment;
    private final UnaryOperator<String> annotation;

    /** Default annotation applied to the base name of the observance.
     * @see Observance#getName()
     */
    @SuppressWarnings("unused")  // Convenience for external API
    public static final String DEFAULT_ANNOTATION = "%s (Observed)";

    /**
     * No-op annotation
     * @see #DEFAULT_ANNOTATION
     */
    public static final UnaryOperator<String> NO_ANNOTATION = UnaryOperator.identity();

    /**
     * For holidays that are observed on Monday when they fall on a Sunday.
     */
    @SuppressWarnings("unused")  // Convenience for external API
    public static final Observed FOLLOWING_MONDAY = new Observed(TemporalAdjusters.next(MONDAY), SUNDAY);

    /**
     * For holidays that are observed on Friday when they fall on a Saturday.
     */
    @SuppressWarnings("unused")  // Convenience for external API
    public static final Observed PREVIOUS_FRIDAY = new Observed(TemporalAdjusters.previous(FRIDAY), SATURDAY);

    private static UnaryOperator<String> formatter(final String format) {
        return s -> String.format(format, s);
    }

    private static Predicate<Observance> fromDay(final DayOfWeek day) {
        return h -> day.equals(h.getDate().getDayOfWeek());
    }

    /**
     * Observe a holiday on an adjusted date
     *
     * @param adjustment function to adjust a canonical observance date to an observed date.
     * @param shifts     function to determine whether to shift the holiday. If this value is null, defaults to always
     *                   shifting the holiday. Never shifting the holiday would make the function a NOOP.
     * @param annotation function to change the name of the holiday to indicate that it has been shifted. If this value
     *                   is null, defaults to leaving the name unchanged.
     */
    public Observed(final TemporalAdjuster adjustment, final Predicate<Observance> shifts, final UnaryOperator<String> annotation) {
        if (adjustment == null) throw new AssertionError("Adjustment to observed date cannot be null");
        this.adjustment = adjustment;
        this.shifts = shifts == null ? h -> true : shifts;
        this.annotation = annotation == null ? NO_ANNOTATION : annotation;
    }

    /**
     * Observe a holiday on an adjusted date
     *
     * @param adjustment function to adjust a canonical observance date to an observed date.
     * @param shifts     function to determine whether to shift the holiday. If this value is null, defaults to always
     *                   shifting the holiday. Never shifting the holiday would make the function a NOOP.
     * @param annotation format string applied to the name of the holiday to indicate that it has been shifted.
     */
    public Observed(final TemporalAdjuster adjustment, final Predicate<Observance> shifts, final String annotation) {
        this(adjustment, shifts, formatter(annotation));
    }

    /**
     * Observe a holiday on an adjusted date
     *
     * @param adjustment function to adjust a canonical observance date to an observed date.
     * @param shifts     day of the week that triggers a shifted observance.
     * @param annotation format string applied to the name of the observance to indicate that it has been shifted.
     */
    public Observed(final TemporalAdjuster adjustment, final DayOfWeek shifts, final String annotation) {
        this(adjustment, fromDay(shifts), annotation);
    }

    /**
     * Observe a holiday on an adjusted date, with no annotation applied to the name of the holiday.
     *
     * @param adjustment function to adjust a canonical observance date to an observed date.
     * @param shifts     day of the week that triggers a shifted observance.
     */
    public Observed(final TemporalAdjuster adjustment, final DayOfWeek shifts) {
        this(adjustment, fromDay(shifts), NO_ANNOTATION);
    }

    /**
     * Generate an observance shifted from the canonical observance date.
     * @param observance the observance to shift
     * @return the shifted observance if the predicate is true, otherwise empty.
     */
    @Override
    public Optional<Observance> apply(final Optional<Observance> observance) {
        return observance.filter(shifts).map(original -> new Observance() {
            @Override
            public String getName() {
                return annotation.apply(original.getName());
            }

            @Override
            public LocalDate getDate() {
                return original.getDate().with(adjustment);
            }
        });
    }
}
