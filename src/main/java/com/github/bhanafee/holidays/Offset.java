package com.github.bhanafee.holidays;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class Offset implements UnaryOperator<Optional<Observance>> {
    private final Predicate<Observance> shifts;
    private final TemporalAdjuster adjustment;
    private final UnaryOperator<String> annotation;

    /**
     * No-op annotation
     */
    public static final UnaryOperator<String> NO_ANNOTATION = UnaryOperator.identity();

    /**
     * Default String format for annotating an observance
     *
     * @see #OBSERVED
     */
    public static final String OBSERVED_FORMAT = "%s (Observed)";

    /**
     * Annotation applied to the base name of a shifted observance to mark it as an observance.
     *
     * @see Observance#getName()
     */
    public static final UnaryOperator<String> OBSERVED = s -> String.format(OBSERVED_FORMAT, s);

    /**
     * Predicate to indicate the shift always should be applied.
     */
    public static final Predicate<Observance> ALWAYS = o -> true;

    /**
     * Offset to generate Monday observances of Sunday holidays.
     * @see #OBSERVED
     */
    public static final Offset SATURDAY_TO_FRIDAY = new Offset(11L, DayOfWeek.SATURDAY, OBSERVED);

    /**
     * Offset to generate Friday observances of Saturday holidays.
     * @see #OBSERVED
     */
    public static final Offset SUNDAY_TO_MONDAY = new Offset(1L, DayOfWeek.SUNDAY, OBSERVED);

    /**
     * Observe on an adjusted date
     *
     * @param adjustment function to adjust a canonical observance date to an observed date.
     * @param annotation function to change the name of the observance to the name of the shifted observance. If this value
     *                   is null, defaults to leaving the name unchanged.
     * @param shifts     function to determine whether to shift the holiday. If this value is null, defaults to always
     *                   shifting the holiday. Never shifting the holiday would make the function a NOOP.
     */
    public Offset(final TemporalAdjuster adjustment, final UnaryOperator<String> annotation, final Predicate<Observance> shifts) {
        if (adjustment == null) throw new AssertionError("Adjustment to observed date cannot be null");
        this.adjustment = adjustment;
        this.shifts = shifts == null ? ALWAYS : shifts;
        this.annotation = annotation == null ? NO_ANNOTATION : annotation;
    }

    /**
     * A named observance that is always a fixed number of days before or after another
     *
     * @param days the signed number to shift the observance.
     * @param name the name of the offset observance.
     */
    public Offset(final long days, final String name) {
        this(TemporalAdjusters.ofDateAdjuster(d -> d.plusDays(days)), s -> name, ALWAYS);
    }

    /**
     * A named observance that is shifted a fixed number of days if it falls on a given day of the week.
     * @param offset the signed number of days to shift the observance.
     * @param day the day of the week that triggers the shift.
     * @param annotation annotation to apply to the shifted observance
     */
    public Offset(final long offset, final DayOfWeek day, final UnaryOperator<String> annotation) {
        this(TemporalAdjusters.ofDateAdjuster(d -> d.plusDays(offset)),
                annotation,
                o -> day.equals(o.getDate().getDayOfWeek()));
    }

    /**
     * Generate an observance on a date that is shifted from some other observance
     *
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
