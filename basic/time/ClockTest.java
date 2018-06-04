package fgafa.basic.time;

import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

/**
 * Clock provides access to the current instance, date and time using a time-zone.
 * Clock can be used instead of System.currentTimeMillis() and TimeZone.getDefault()
 *
 */
public class ClockTest {
    @Test
    public void testClock(){
        Clock clock = Clock.systemDefaultZone();
        long timestamp = clock.millis();
        System.out.println("timestamp: " + timestamp);  //timestamp: 1499797686325

        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);
    }

}


/*
 * Best practice for applications is to pass a Clock into any method that requires the current instant.
 * A dependency injection framework is one way to achieve this:
 *
 * This approach allows an alternate clock, such as fixed or offset to be used during testing.
 */
class MyBean {
    private Clock clock;  // dependency inject


    public void process(LocalDate eventDate) {
        if (eventDate.isBefore(LocalDate.now(clock))) {
        //todo
        }
    }
}


