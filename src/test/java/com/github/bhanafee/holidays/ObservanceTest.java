package com.github.bhanafee.holidays;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

import static org.testng.Assert.*;

@Test
public class ObservanceTest {
    final String NAME = "name";
    final LocalDate DATE = LocalDate.now();
    Function<Optional<LocalDate>, Optional<Observance>> named;

    @BeforeClass
    public void setup() {
        named = Observance.named(NAME);
    }

    public void testNamedEmpty() {
        assertEquals(named.apply(Optional.empty()), Optional.empty(), "Empty input should produce empty output");
    }

    public void testNamed() {
        final Optional<Observance> result = named.apply(Optional.of(DATE));
        assertTrue(result.isPresent(), "Non-empty input should produce non-empty result");
        assertEquals(result.get().getName(), NAME, "Name should pass through");
        assertEquals(result.get().getDate(), DATE, "Date should pass through");
    }

    public void testNamedNullName() {
        Function<Optional<LocalDate>, Optional<Observance>> test = Observance.named(null);
        final Optional<Observance> result = test.apply(Optional.of(DATE));

        assertTrue(result.isPresent(),"Null name should produce results");
        assertTrue(result.get().getName().isEmpty(),
                "Null name should produce empty string in output");
    }

}
