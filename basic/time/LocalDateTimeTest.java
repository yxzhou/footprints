package basic.time;

import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoField;

import java.util.Date;

/**
 * LocalDateTime represents a date-time without time-zone, such as yyyy-MM-dd-HH-mm-ss.zz. It's a description of the date, as used for birthday.
 *
 * LocalDateTime is an immutable date-time object.
 *
 * It provides a factory method that takes LocalDate and LocalTime input arguments to create LocalDateTime instance.
 */
public class LocalDateTimeTest {

    @Test
    public void testLocalDateTime() {

        //Current Date
        LocalDateTime today = LocalDateTime.now();
        System.out.println("Current DateTime=" + today); //Current DateTime=2014-04-28T16:00:49.455

        //Current Date using LocalDate and LocalTime
        today = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        System.out.println("Current DateTime=" + today); //Current DateTime=2014-04-28T16:00:49.493

        //Creating LocalDateTime by providing input arguments
        LocalDateTime specificDate = LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30);
        System.out.println("Specific Date=" + specificDate); //Specific Date=2014-01-01T10:10:30


        //Try creating date by providing invalid inputs
        //LocalDateTime feb29_2014 = LocalDateTime.of(2014, Month.FEBRUARY, 28, 25,1,1);
        //Exception in thread "main" java.time.DateTimeException:
        //Invalid value for HourOfDay (valid values 0 - 23): 25
        //java.time.DateTimeException is a RuntimeException, so we don’t need to explicitly catch it.

        //Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
        LocalDateTime todayKolkata = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Date in IST=" + todayKolkata); //Current Date in IST=2014-04-29T04:30:49.493

        //java.time.zone.ZoneRulesException: Unknown time-zone ID: IST
        //LocalDateTime todayIST = LocalDateTime.now(ZoneId.of("IST"));

        //Getting date from the base date i.e 01/01/1970
        LocalDateTime dateFromBase = LocalDateTime.ofEpochSecond(10000, 0, ZoneOffset.UTC);
        System.out.println("10000th second time from 01/01/1970= " + dateFromBase); //10000th second time from 01/01/1970= 1970-01-01T02:46:40

    }

    @Test
    public void testDateTimeWithTimeZone(){
        LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);

        DayOfWeek dayOfWeek = sylvester.getDayOfWeek();
        System.out.println(dayOfWeek);      // WEDNESDAY

        Month month = sylvester.getMonth();
        System.out.println(month);          // DECEMBER

        long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
        System.out.println(minuteOfDay);    // 1439

        Instant instant = sylvester
                .atZone(ZoneId.systemDefault())
                .toInstant();

        Date legacyDate = Date.from(instant);
        System.out.println(legacyDate);     // Wed Dec 31 23:59:59 CET 2014
    }


}
