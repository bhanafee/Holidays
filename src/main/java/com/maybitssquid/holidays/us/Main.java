package com.maybitssquid.holidays.us;

import java.util.stream.IntStream;
import java.util.List;

public class Main {

    public static void main(String... a) {
        final IntStream years = IntStream.of(2000, 2010, 2021, 2022, 2023);

        final Federal federal = new Federal();
        federal.apply(years).forEach(System.out::println);
        System.out.println("More years");
        federal.apply(IntStream.of(1850, 1869, 1870, 1871, 1900, 1950, 2000, 2050)).forEach(System.out::println);
    }
}
