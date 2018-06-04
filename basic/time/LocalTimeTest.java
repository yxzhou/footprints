package fgafa.basic.time;

import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 *  LocaleTime represents a distinct time, e.g. 10:15:31. (without time-zone)
 *  It's immutable
 */
public class LocalTimeTest {
    @Test
    public void testLocalTime() {

        //Current Time
        LocalTime time = LocalTime.now();
        System.out.println("Current Time=" + time); //Current Time=15:51:45.240

        //Creating LocalTime by providing input arguments
        LocalTime specificTime = LocalTime.of(12, 20, 25, 40);
        System.out.println("Specific Time of Day=" + specificTime); //Specific Time of Day=12:20:25.000000040


        //Try creating time by providing invalid inputs
        //LocalTime invalidTime = LocalTime.of(25,20);
        //Exception in thread "main" java.time.DateTimeException:
        //Invalid value for HourOfDay (valid values 0 - 23): 25

        //Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
        LocalTime timeKolkata = LocalTime.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Time in IST=" + timeKolkata); //Current Time in IST=04:21:45.276

        //java.time.zone.ZoneRulesException: Unknown time-zone ID: IST
        //LocalTime todayIST = LocalTime.now(ZoneId.of("IST"));

        //Getting date from the base date i.e 01/01/1970
        LocalTime specificSecondTime = LocalTime.ofSecondOfDay(10000);
        System.out.println("10000th second time= " + specificSecondTime); //10000th second time= 02:46:40

        //
        DateTimeFormatter germanFormatter = DateTimeFormatter
                .ofLocalizedTime(FormatStyle.SHORT)
                .withLocale(Locale.GERMAN);
        LocalTime leetTime = LocalTime.parse("13:48", germanFormatter);

        //time difference between time-zone
        LocalTime timeOfBerlin = LocalTime.now(ZoneId.of("Europe/Berlin"));
        LocalTime timeOfBrazilEast = LocalTime.now(ZoneId.of("Brazil/East"));
        assertEquals(true, timeOfBerlin.isBefore(timeOfBrazilEast));

        System.out.println("Hours diff: " + ChronoUnit.HOURS.between(timeOfBerlin, timeOfBrazilEast));
        System.out.println("Minutes diff: " + ChronoUnit.MINUTES.between(timeOfBerlin, timeOfBrazilEast));

    }

}
