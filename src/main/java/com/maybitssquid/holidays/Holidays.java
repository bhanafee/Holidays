package com.maybitssquid.holidays;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface Holidays {

    /**
     * Replace an event with an alternate if there is one, otherwise retain the original event.
     *
     * @param alternate generator for potential alternate event.
     * @return function to substitute alternate events.
     * @see java.util.stream.Stream#mapMulti
     */
    static BiConsumer<Event, Consumer<Event>> either(Function<Event, Optional<Event>> alternate) {
        return (e, c) -> c.accept(alternate.apply(e).orElse(e));
    }

    /**
     * Replace an event with zero or more other events based on the original.
     *
     * @param replacements functions to generate replacement events.
     * @return the replacement events.
     * @see java.util.stream.Stream#mapMulti
     */
    @SafeVarargs
    static BiConsumer<Event, Consumer<Event>> replace(Function<Event, Optional<Event>>... replacements) {
        return (e, c) -> {
            for (Function<Event, Optional<Event>> replacement : replacements) {
                replacement.apply(e).ifPresent(c);
            }
        };
    }

    /**
     * Supplement an event with zero or more other events based on the original.
     *
     * @param additions functions to generate additional events.
     * @return the original and replacement events.
     * @see java.util.stream.Stream#mapMulti
     */
    @SafeVarargs
    static BiConsumer<Event, Consumer<Event>> additions(Function<Event, Optional<Event>>... additions) {
        return (e, c) -> {
            c.accept(e);
            for (Function<Event, Optional<Event>> addition : additions) {
                addition.apply(e).ifPresent(c);
            }
        };
    }

    /**
     * Convert a stream of years into a stream of yearly events.
     *
     * @param events generators for the yearly events.
     * @return function to map a stream of years into a stream of yearly events.
     */
    @SafeVarargs
    static Function<IntStream, Stream<Event>> generator(IntFunction<Optional<Event>>... events) {
        final IntFunction<Stream<Event>> year =
                y -> {
                    final ArrayList<Event> generated = new ArrayList<>(events.length);
                    for (IntFunction<Optional<Event>> generator : events) {
                        generator.apply(y).ifPresent(generated::add);
                    }
                    generated.sort(Comparator.comparing(Event::date).thenComparing(Event::name));
                    return generated.stream();
                };
        return years -> years.mapToObj(year).flatMap(Function.identity());
    }

}
