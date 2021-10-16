package com.github.bhanafee.holidays;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.Optional;
import java.util.function.Function;

/**
 * Common holidays in the United States, not adjusted for Saturday/Sunday
 */
public class US {

    /**
     * New Year's Day, January 1.
     *
     * @return Observances of New Year's Day.
     */
    public Function<Integer, Optional<Observance>> newYearsDay() {
        return new Anniversary(Month.JANUARY, 1).
                andThen(Observance.named("New Year's Day"));
    }

    /**
     * Birthday of Martin Luther King, Jr., the third Monday in January.
     * Actual birthday January 15, 1929.
     *
     * @return Observances of the birthday of Martin Luther King,Jr.
     */
    public Function<Integer, Optional<Observance>> mlkBirthday() {
        return new Yearly(Month.JANUARY, 3, DayOfWeek.MONDAY, 1929).andThen(Observance.named("Birthday of Martin Luther King, Jr."));
    }

    /**
     * Washington’s Birthday, the third Monday in February.
     * Actual birthday February 22, 1732.
     *
     * @return Observances of Washington's birthday.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Function<Integer, Optional<Observance>> washingtonsBirthday() {
        return new Yearly(Month.FEBRUARY, 3, DayOfWeek.MONDAY, 1732).andThen(Observance.named("Washington's Birthday"));
    }

    /**
     * Memorial Day, the last Monday in May.
     * First observed in 1868.
     *
     * @return Observances of Memorial Day.
     */
    public Function<Integer, Optional<Observance>> memorialDay() {
        //FIXME observed on May 30 from 1868 to 1970, last Monday in May thereafter
        return new Yearly(Month.MAY, -1, DayOfWeek.MONDAY, 1868).andThen(Observance.named("Memorial Day"));
    }

    /**
     * Juneteenth National Independence Day, June 19.
     * First celebrated in 1865.
     *
     * @return Observances of Juneteenth National Independence Day.
     */
    public Function<Integer, Optional<Observance>> juneteenth() {
        return new Anniversary(Month.JUNE, 19, 1865).andThen(Observance.named("Juneteenth National Independence Day"));
    }

    /**
     * Independence Day, July 4.
     * Declaration of Independence dated July 4, 1776.
     *
     * @return Observances of Independence Day.
     */
    public Function<Integer, Optional<Observance>> independenceDay() {
        return new Anniversary(Month.JULY, 4, 1776).andThen(Observance.named("Independence Day"));
    }

    /**
     * Labor Day, the first Monday in September.
     * Proposed in 1882, officially observed in Oregon 1887, federal holiday in 1894.
     *
     * @return Observances of Labor Day.
     */
    public Function<Integer, Optional<Observance>> laborDay() {
        return new Yearly(Month.SEPTEMBER, 1, DayOfWeek.MONDAY, 1882).andThen(Observance.named("Labor Day"));
    }

    /**
     * Columbus Day, the second Monday in October.
     * Columbus arrived in the Americas October 12, 1492.
     *
     * @return Observances of Columbus Day.
     */
    public Function<Integer, Optional<Observance>> columbusDay() {
        return new Yearly(Month.OCTOBER, 2, DayOfWeek.MONDAY, 1492).andThen(Observance.named("Columbus Day"));
    }

    /**
     * Veterans Day, November 11.
     * Armistice 11/11/1911 at 11:00. Renamed Veteran's Day in 1954.
     *
     * @return Observances of Veteran's Day.
     */
    public Function<Integer, Optional<Observance>> veteransDay() {
        return new Anniversary(Month.NOVEMBER, 11, 1911).andThen(Observance.named("Veteran's Day"));
    }

    /**
     * Thanksgiving Day, the fourth Thursday in November.
     * First Thanksgiving in 1621.
     *
     * @return Observances of Veteran's Day
     */
    public Function<Integer, Optional<Observance>> thanksgivingDay() {
        return new Yearly(Month.NOVEMBER, 4, DayOfWeek.THURSDAY, 1621).andThen(Observance.named("Thanksgiving"));
    }

    /**
     * Christmas Day, December 25.
     * First observed on December 25, 366 under Roman Emperor Constantine.
     *
     * @return Observances of Christmas Day.
     */
    public Function<Integer, Optional<Observance>> christmasDay() {
        return new Anniversary(Month.DECEMBER, 25, 336).andThen(Observance.named("Christmas Day"));
    }

}
