package fgafa.basic.time;

import org.junit.Test;

import java.time.ZoneId;

/**
 * Timezones are represented by a ZoneId. They can easily be accessed via static factory methods.
 * Timezones define the offsets which are important to convert between instants and local dates and times.
 *
 *
 */
public class TimezoneTest {

    @Test
    public void testTimezone(){
        System.out.println(ZoneId.getAvailableZoneIds());
       // prints all available timezone ids

        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        System.out.println(zone1.getRules());  // ZoneRules[currentStandardOffset=+01:00]
        System.out.println(zone2.getRules());  // ZoneRules[currentStandardOffset=-03:00]
    }

}
