package fgafa.basic.time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

/**
 *  LocaleDate represents a distinct date, e.g. 2017-07-10. (without time-zone)
 *  It's immutable, as used for birthday and holiday, often viewed as year-month-day,  day-of-year, day-of-week and week-of-year.
 */
public class LocalDateTest {

    @Test
    public void testLocalDate() {

        //Current Date
        LocalDate today = LocalDate.now();
        System.out.println("Current Date=" + today); //Current Date=2014-04-28

        //Creating LocalDate by providing input arguments
        LocalDate firstDay_2014 = LocalDate.of(2014, Month.JANUARY, 1);
        System.out.println("Specific Date=" + firstDay_2014); //Specific Date=2014-01-01


        //Try creating date by providing invalid inputs
        //LocalDate feb29_2014 = LocalDate.of(2014, Month.FEBRUARY, 29);
        //Exception in thread "main" java.time.DateTimeException:
        //Invalid date 'February 29' as '2014' is not a leap year

        //Current date in "Asia/Kolkata", you can get it from ZoneId javadoc, or the ISO-8601 calendar system
        LocalDate todayKolkata = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Date in IST=" + todayKolkata); //Current Date in IST=2014-04-29

        //java.time.zone.ZoneRulesException: Unknown time-zone ID: IST
        //LocalDate todayIST = LocalDate.now(ZoneId.of("IST"));

        //Getting date from the base date i.e 01/01/1970
        LocalDate dateFromBase = LocalDate.ofEpochDay(365);
        System.out.println("365th day from base date= " + dateFromBase); //365th day from base date= 1971-01-01

        LocalDate hundredDay2014 = LocalDate.ofYearDay(2014, 100);
        System.out.println("100th day of 2014=" + hundredDay2014); //100th day of 2014=2014-04-10
    }

}
