package com.maybitssquid.holidays;

import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface Events extends Function<IntStream, Stream<Event>> {
}
