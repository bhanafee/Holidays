package com.maybitssquid.holidays;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Optional;

public class Anniversary implements Yearly {
    private final Event event;
    private final String observedAs;
    private final LocalDate first;

    /**
     * Utility constructor that has sensible defaults for null parameters.
     * @param event event this is the anniversary of (mandatory).
     * @param observedAs format for anniversary events, defaulting to the name of the original event. This parameter may
     *               include $s as a placeholder for the name of the original event.
     * @param first first date the anniversary is observed, defaulting to the date of the original even.
     */
    public Anniversary(final Event event, final String observedAs, final LocalDate first) {
        if (event == null) throw new AssertionError("Missing original event for anniversary");
        this.event = event;
        this.observedAs = observedAs == null ? event.name() : String.format(observedAs, event.name());
        this.first = first == null ? event.date() : first;
    }

    public Anniversary(final Event event, final LocalDate first) {
        this(event, null, first);
    }

    public Anniversary(final Event event, final String observedAs) {
        this(event, observedAs, null);
    }

    public Anniversary(final Event event) {
        this(event, null, null);
    }

    public Event getEvent() {
        return this.event;
    }

    public LocalDate compute(final int year) {
        return event.date().withYear(year);
    }

    @Override
    public Optional<Event> apply(final int year) {
        try {
            LocalDate observed = compute(year);
            if (observed.isBefore(first)) {
                return Optional.empty();
            } else {
                return Optional.of(new Event(observed, observedAs));
            }
        } catch (final DateTimeException e) {
            return Optional.empty();
        }
    }
}
