package com.maybitssquid.holidays;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * Event that occurs at most yearly.
 */
public interface Yearly extends IntFunction<Optional<Event>> {
    /**
     * Compute the date given the year. This method is expected to compute only the raw date, without considering
     * whether the resulting date falls outside valid ranges. For example, it may compute an anniversary date that
     * is before the original event. The {@link #apply(int)} method is expected to perform final checks before
     * emitting a valid {@link Event}.
     *
     * @param year the year to generate the event for
     * @return the date the event falls on for the given year.
     */
    LocalDate compute(int year);

    default Yearly with(final Function<Event, Optional<Event>> modifier) {
        return new Yearly() {

            /**
             * Delegates to the {@link #compute(int)} method of the enclosing instance.
             * @param year {@inheritDoc}
             * @return {@inheritDoc}
             */
            @Override
            public LocalDate compute(int year) {
                return Yearly.this.compute(year);
            }

            /**
             * Returns the modified event, or the original event if the modifier returns empty.
             * @param year {@inheritDoc}
             * @return {@inheritDoc}
             */
            @Override
            public Optional<Event> apply(int year) {
                final Optional<Event> original = Yearly.this.apply(year);
                if (original.isPresent()) {
                    final Optional<Event> modified = modifier.apply(original.get());
                    return modified.isPresent() ? modified : original;
                } else {
                    return Optional.empty();
                }
            }
        };
    }
}
