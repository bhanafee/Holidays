package com.maybitssquid.holidays.us;

import com.maybitssquid.holidays.Event;

import java.time.LocalDate;
import java.time.Month;

/**
 * Selection of events observed in the United States.
 */
public class US {

    /**
     * US follows from British Empire, which adopted Gregorian calendar September 2, 1752.
     */
    public static final Event NEW_YEARS_DAY =
            new Event(LocalDate.of(1753, Month.JANUARY, 1), "New Year's Day");

    /**
     * Birthday of Martin Luther King, Jr., January 15, 1929.
     */
    public static final Event MARTIN_LUTHER_KING_BIRTHDAY =
            new Event(LocalDate.of(1929, Month.JANUARY, 15), "Birthday of Martin Luther King, Jr.");

    /**
     * Proleptic birthday of George Washington, February 22, 1732. Originally recorded as February 11, 1732 (Old Style).
     */
    public static final Event GEORGE_WASHINGTON_BIRTHDAY =
            new Event(LocalDate.of(1732, Month.FEBRUARY, 22), "Birthday of George Washington");

    /**
     * Memorial Day observed May 30 from 1868 to 1970, then last Monday in May.
     */
    public static final Event MEMORIAL_DAY =
            new Event(LocalDate.of(1868, Month.MAY,30), "Memorial Day");

    /**
     * Juneteenth National Independence Day, first celebrated June 19, 1865.
     */
    public static final Event JUNETEENTH =
            new Event(LocalDate.of(1865, Month.JUNE, 19), "Juneteenth National Independence Day");

    /**
     * Declaration of Independence dated July 4, 1776.
     */
    public static final Event INDEPENDENCE_DAY =
            new Event(LocalDate.of(1776, Month.JULY, 4), "Independence Day");

    public static final Event LABOR_DAY =
            new Event(LocalDate.of(1894, Month.SEPTEMBER, 3), "Labor Day");
    /**
     * Proleptic arrival of Columbus in the Americas. Originally recorded as October 12, 1492 on the Julian calendar.
     */
    public static final Event COLUMBUS_DAY =
            new Event(LocalDate.of(1492, Month.OCTOBER, 21), "Columbus Day");

    /**
     * Armistice Day, 11/11/1911 at 11:00. Renamed Veteran's Day in 1954.
     */
    public static final Event ARMISTICE_DAY =
            new Event(LocalDate.of(1911, Month.NOVEMBER, 11), "Armistice Day");

    /**
     * Thanksgiving Day, the fourth Thursday in November. First Thanksgiving was 3 day feast with unknown date
     * in late November 1621. November 22, 1621 is Julian 4th Thursday.
     */
    public static final Event THANKSGIVING_DAY =
            new Event(LocalDate.of(1621, Month.NOVEMBER, 22), "Thanksgiving");

    /**
     * First observed as December 25 in 366 (Julian Calendar) under Roman Emperor Constantine.
     */
    public static final Event CHRISTMAS_DAY =
            new Event(LocalDate.of(336, Month.DECEMBER, 25), "Christmas Day");

}
