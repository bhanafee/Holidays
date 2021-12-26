package com.maybitssquid.holidays;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.util.Optional;

public class EveryYear implements Yearly {
    private final MonthDay date;
    private final String name;

    public EveryYear(final String name, final MonthDay date) {
        this.date = date;
        this.name = name;
    }

    public EveryYear(final String name, final Month month, final int dayOfMonth) {
        this(name, MonthDay.of(month, dayOfMonth));
    }

    @Override
    public LocalDate compute(final int year) {
        return date.atYear(year);
    }

    @Override
    public Optional<Event> apply(final int year) {
        try {
            return Optional.of(new Event(compute(year), name));
        } catch (DateTimeException e) {
            return Optional.empty();
        }
    }
}
