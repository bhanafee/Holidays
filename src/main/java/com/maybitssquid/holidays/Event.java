package com.maybitssquid.holidays;

import java.time.LocalDate;
import java.util.Comparator;

public record Event(LocalDate date, String name) implements Comparable<Event> {

    private static final Comparator<Event> comparator = Comparator.comparing(Event::date).thenComparing(Event::name);

    public static Comparator<Event> getComparator() {
        return comparator;
    };

    /**
     * Compares based on event date, falling back to the event name.
     * @param o {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public int compareTo(Event o) {
        return getComparator().compare(this, o);
    }
}
