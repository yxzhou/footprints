package basic.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * format date into different formats and then parse a String to get the Date Time objects.
 */
public class DateTimeFormatterExample {

    public static void main(String[] args) {

        //Format examples
        LocalDate date = LocalDate.now();
        //default format
        System.out.println("Default format of LocalDate=" + date);//Default format of LocalDate=2014-04-28
        //specific format
        System.out.println(date.format(DateTimeFormatter.ofPattern("d::MMM::uuuu")));//28::Apr::2014
        System.out.println(date.format(DateTimeFormatter.BASIC_ISO_DATE));//20140428


        LocalDateTime dateTime = LocalDateTime.now();
        //default format
        System.out.println("Default format of LocalDateTime=" + dateTime);//Default format of LocalDateTime=2014-04-28T16:25:49.341
        //specific format
        System.out.println(dateTime.format(DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss")));//28::Apr::2014 16::25::49
        System.out.println(dateTime.format(DateTimeFormatter.BASIC_ISO_DATE));//20140428

        Instant timestamp = Instant.now();
        //default format
        System.out.println("Default format of Instant=" + timestamp);//Default format of Instant=2014-04-28T23:25:49.342Z

        //Parse examples
        LocalDateTime dt = LocalDateTime.parse("27::Apr::2014 21::39::48",
                DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss"));
        System.out.println("Default format after parsing = " + dt);//Default format after parsing = 2014-04-27T21:39:48
    }

}
