package basic;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TimestampTest {

    @Test
    public void toMinutesTest(){
        long currTime = System.currentTimeMillis();

        long currentMinute = TimeUnit.MILLISECONDS.toMinutes(currTime);
//        System.out.println(" " + currTime);
//        System.out.println(" " + TimeUnit.MINUTES.toMillis(currentMinute));

        System.out.println(currTime + "\n" + currentMinute + "\n" + (currTime / 60000) + "\n" + TimeUnit.MINUTES.toMillis(currentMinute) + "\n" + (currTime / 60000) * 60000 +  "\n" + (currTime - currTime % 60000) );


        System.out.println(" " + TimeUnit.SECONDS.toMillis(1));
        System.out.println(" " + TimeUnit.SECONDS.toMillis(60));
        System.out.println(" " + TimeUnit.HOURS.toMillis(1));
        System.out.println(" " + TimeUnit.DAYS.toMillis(1));




    }

}
