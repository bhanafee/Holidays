package com.github.bhanafee.holidays;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

/** A named date. */
public interface Observance {

    /**
     * Function to convert a date into a named observance. Useful for mapping streams from LocalDate to Observance.
     * @param name the name of the observance
     * @return function to convert the date into a named observance
     */
    static Function<Optional<LocalDate>, Optional<Observance>> named(final String name) {
        final String n = name == null ? "" : name;
        return od -> od.map(date -> new Observance() {
            @Override
            public String getName() {
                return n;
            }
            @Override
            public LocalDate getDate() {
                return date;
            }
        });
    }

    /** The name of the observance. */
    String getName();
    /** The date of the observance. */
    LocalDate getDate();
}
