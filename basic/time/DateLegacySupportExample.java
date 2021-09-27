package basic.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Legacy Date/Time classes are used in almost all the applications, so having backward compatibility is a must.
 * That’s why there are several utility methods through which we can convert Legacy classes to new classes and vice versa.
 */
public class DateLegacySupportExample {

    public static void main(String[] args) {

        //Date to Instant
        Instant timestamp = Instant.now();
        
        //Now we can convert Instant to LocalDateTime or other similar classes
        LocalDateTime date = LocalDateTime.ofInstant(timestamp, ZoneId.of(ZoneId.SHORT_IDS.get("PST")));
        System.out.println("Date = " + date);//Date = 2014-04-28T16:28:54.340

        //SplitDurationByMonth to Instant
        Instant time = Calendar.getInstance().toInstant();
        System.out.println(time);//2014-04-28T23:28:54.395Z
        //TimeZone to ZoneId
        ZoneId defaultZone = TimeZone.getDefault().toZoneId();
        System.out.println(defaultZone);//America/Los_Angeles

        //ZonedDateTime from specific SplitDurationByMonth
        ZonedDateTime gregorianCalendarDateTime = new GregorianCalendar().toZonedDateTime();
        System.out.println(gregorianCalendarDateTime);//2014-04-28T16:28:54.404-07:00[America/Los_Angeles]

        //Date API to Legacy classes
        LocalDate dt = LocalDate.from(Instant.now());
        System.out.println(dt);//Mon Apr 28 16:28:54 PDT 2014

        TimeZone tz = TimeZone.getTimeZone(defaultZone);
        System.out.println(tz);//sun.util.calendar.ZoneInfo[id="America/Los_Angeles",offset=-28800000,dstSavings=3600000,useDaylight=true,transitions=185,lastRule=java.util.SimpleTimeZone[id=America/Los_Angeles,offset=-28800000,dstSavings=3600000,useDaylight=true,startYear=0,startMode=3,startMonth=2,startDay=8,startDayOfWeek=1,startTime=7200000,startTimeMode=0,endMode=3,endMonth=10,endDay=1,endDayOfWeek=1,endTime=7200000,endTimeMode=0]]

        GregorianCalendar gc = GregorianCalendar.from(gregorianCalendarDateTime);
        System.out.println(gc);//java.util.GregorianCalendar[time=1398727734404,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="America/Los_Angeles",offset=-28800000,dstSavings=3600000,useDaylight=true,transitions=185,lastRule=java.util.SimpleTimeZone[id=America/Los_Angeles,offset=-28800000,dstSavings=3600000,useDaylight=true,startYear=0,startMode=3,startMonth=2,startDay=8,startDayOfWeek=1,startTime=7200000,startTimeMode=0,endMode=3,endMonth=10,endDay=1,endDayOfWeek=1,endTime=7200000,endTimeMode=0]],firstDayOfWeek=2,minimalDaysInFirstWeek=4,ERA=1,YEAR=2014,MONTH=3,WEEK_OF_YEAR=18,WEEK_OF_MONTH=5,DAY_OF_MONTH=28,DAY_OF_YEAR=118,DAY_OF_WEEK=2,DAY_OF_WEEK_IN_MONTH=4,AM_PM=1,HOUR=4,HOUR_OF_DAY=16,MINUTE=28,SECOND=54,MILLISECOND=404,ZONE_OFFSET=-28800000,DST_OFFSET=3600000]

    }

}
