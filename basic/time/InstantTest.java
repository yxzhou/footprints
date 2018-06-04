package fgafa.basic.time;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

/**
 * Instant class is used to work with machine readable time format,
 * it stores date time in unix timestamp.
 */
public class InstantTest {

    @Test
    public void testInstant() {
        //Current instant
        Instant instant = Instant.now();
        System.out.println("Current Time = " + instant); //Current Time = 2014-04-28T23:20:08.489Z

        //Instant from instant
        Instant specificTime = Instant.ofEpochMilli(instant.toEpochMilli());
        System.out.println("Specific Time = " + specificTime); //Specific Time = 2014-04-28T23:20:08.489Z

        //Duration example
        Duration thirtyDay = Duration.ofDays(30);
        System.out.println(thirtyDay); //PT720H
    }

}
