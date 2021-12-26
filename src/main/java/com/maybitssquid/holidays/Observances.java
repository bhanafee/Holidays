package com.maybitssquid.holidays;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Generate events based on other events, such as:
 * Observances that fall on the nth weekday of the month
 * Observances that are shifted to Monday if they naturally would fall on a Sunday
 * Observances that have a fixed relationship, such as Boxing Day always being the day after Christmas
 */
public class Observances {
    private Observances() {
    }

    public static String OBSERVED_ANNOTATION_FORMAT = "$s (Observed)";

    /**
     * Applies {@link #OBSERVED_ANNOTATION_FORMAT} to the event name to generate the observance event name.
     */
    public static final UnaryOperator<String> OBSERVED_ANNOTATION =
            name -> String.format(OBSERVED_ANNOTATION_FORMAT, name);

    private static final Function<Event, Optional<Event>> offset(final UnaryOperator<String> name, final long offset) {
        final TemporalAdjuster adjustment = TemporalAdjusters.ofDateAdjuster((d -> d.plusDays(offset)));
        final UnaryOperator<String> n = name == null ? UnaryOperator.identity() : name;
        return e -> Optional.of(new Event(e.date().with(adjustment), n.apply(e.name())));
    }

    /**
     * Generates an event that is a fixed number of days before or after another event. For example,
     * New Year's Eve is always the day before New Year's Day.
     *
     * @param name   the name of the related event.
     * @param offset the number of days before or after the base event.
     * @return the related event.
     */
    public static Function<Event, Optional<Event>> related(final String name, final long offset) {
        return offset(s -> name, offset);
    }

    /**
     * Generates an event on Monday for an event that lands on a Sunday.
     *
     * @param annotation annotation to apply to the event name.
     * @see #OBSERVED_ANNOTATION
     */
    public static Function<Event, Optional<Event>> sundayToMonday(final UnaryOperator<String> annotation) {
        final Function<Event, Optional<Event>> offset = offset(annotation, 1L);
        return e -> DayOfWeek.SUNDAY.equals(e.date().getDayOfWeek()) ? offset.apply(e) : Optional.empty();
    }

    /**
     * Generates an event on Friday for an event that lands on a Saturday.
     *
     * @param annotation annotation to apply to the event name.
     * @see #OBSERVED_ANNOTATION
     */
    public static Function<Event, Optional<Event>> saturdayToFriday(final UnaryOperator<String> annotation) {
        final Function<Event, Optional<Event>> offset = offset(annotation, -1L);
        return e -> DayOfWeek.SATURDAY.equals(e.date().getDayOfWeek()) ? offset.apply(e) : Optional.empty();
    }

    /**
     * Generates an event on a weekday for an event that lands on a weekend.
     * @param annotation annotation to apply to the event name.
     * @see #OBSERVED_ANNOTATION
     */
    public static Function<Event, Optional<Event>> weekendToWeekday(final UnaryOperator<String> annotation) {
        final Function<Event, Optional<Event>> toMonday = offset(annotation, 1L);
        final Function<Event, Optional<Event>> toFriday = offset(annotation, -1L);
        return e -> switch (e.date().getDayOfWeek()) {
            case SATURDAY -> toFriday.apply(e);
            case SUNDAY -> toMonday.apply(e);
            default -> Optional.empty();
        };
    }

    /**
     * Identify events that are before the {@code first}.
     *
     * @param first the earliest event date that will be allowed.
     * @return whether the event date is before the first.
     */
    public static Predicate<Event> since(final LocalDate first) {
        return e -> !e.date().isBefore(first);
    }

    public static Function<Event, Optional<Event>> observedSince(final LocalDate first) {
        Predicate<Event> observe = since(first);
        return e -> observe.test(e) ? Optional.of(e) : Optional.empty();
    }

    public static Function<Event, Optional<Event>> nthWeekday(final UnaryOperator<String> name, final int ordinal, final DayOfWeek dayOfWeek) {
        final TemporalAdjuster adjustment = TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek);
        return e -> Optional.of(new Event(e.date().with(adjustment), name.apply(e.name())));
    }

    public static Function<Event, Optional<Event>> nthWeekday(final int ordinal, final DayOfWeek dayOfWeek) {
        return nthWeekday(UnaryOperator.identity(), ordinal, dayOfWeek);
    }

    public static Function<Event, Optional<Event>> nthWeekday(final UnaryOperator<String> name, final LocalDate first, final int ordinal, final DayOfWeek dayOfWeek) {
        Predicate<Event> observe = since(first);
        Function<Event, Optional<Event>> generate = nthWeekday(name, ordinal, dayOfWeek);
        return e -> observe.test(e) ? generate.apply(e) : Optional.empty();
    }

    public static Function<Event, Optional<Event>> nthWeekday(final LocalDate first, final int ordinal, final DayOfWeek dayOfWeek) {
        return nthWeekday(UnaryOperator.identity(), first, ordinal, dayOfWeek);
    }
}
