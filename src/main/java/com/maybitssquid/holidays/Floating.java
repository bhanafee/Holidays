package com.maybitssquid.holidays;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class Floating extends Anniversary {
    private final TemporalAdjuster adjuster;

    public Floating(final Event event, final String observedAs, final LocalDate first, final int ordinal, final DayOfWeek dayOfWeek) {
        super(event, observedAs, first);
        this.adjuster = TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek);
    }

    public Floating(final Event event, final LocalDate first, final int ordinal, final DayOfWeek dayOfWeek) {
        this(event, null, first, ordinal, dayOfWeek);
    }

    public Floating(final Event event, final int ordinal, final DayOfWeek dayOfWeek) {
        this(event, null, null, ordinal, dayOfWeek);
    }

    @Override
    public LocalDate compute(final int year) {
        return super.compute(year).with(adjuster);
    }
}
