package basic.time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;

/**
 * most of the Date Time principle classes provide various utility methods such as plus/minus days, weeks, months etc.
 * There are some other utility methods for adjusting the date using TemporalAdjuster and to calculate the period between two dates.
 */
public class DateUtilitiesExample {

    public static void main(String[] args) {

        LocalDate today = LocalDate.now();

        //Get the Year, check if it's leap year
        System.out.println("Year " + today.getYear() + " is Leap Year? " + today.isLeapYear()); //Year 2014 is Leap Year? false

        //Compare two LocalDate for before and after
        System.out.println("Today is before 01/01/2015? " + today.isBefore(LocalDate.of(2015, 1, 1))); //Today is before 01/01/2015? true

        //Create LocalDateTime from LocalDate
        System.out.println("Current Time=" + today.atTime(LocalTime.now())); //Current Time=2014-04-28T16:23:53.154

        //plus and minus operations
        System.out.println("10 days after today will be " + today.plusDays(10));// 10 days after today will be 2014-05-08
        System.out.println("3 weeks after today will be " + today.plusWeeks(3));// 3 weeks after today will be 2014-05-19
        System.out.println("20 months after today will be " + today.plusMonths(20));// 20 months after today will be 2015-12-28

        System.out.println("10 days before today will be " + today.minusDays(10));// 10 days before today will be 2014-04-18
        System.out.println("3 weeks before today will be " + today.minusWeeks(3));// 3 weeks before today will be 2014-04-07
        System.out.println("20 months before today will be " + today.minusMonths(20));// 20 months before today will be 2012-08-28

        //Temporal adjusters for adjusting the dates
        System.out.println("First date of this month= " + today.with(TemporalAdjusters.firstDayOfMonth()));//First date of this month= 2014-04-01
        LocalDate lastDayOfYear = today.with(TemporalAdjusters.lastDayOfYear());//Last date of this year= 2014-12-31
        System.out.println("Last date of this year= " + lastDayOfYear);//Period Format= P8M3D

        Period period = today.until(lastDayOfYear);
        System.out.println("Period Format= " + period);
        System.out.println("Months remaining in the year= " + period.getMonths());//Months remaining in the year= 8
    }

}
