package fgafa.dailyCoding;

import fgafa.datastructure.LFU.LFUCacheImpl;
import org.junit.Assert;
import org.junit.Test;

public class PerfectNumberTest {

    @Test public void test() throws Throwable{
        PerfectNumber sv = new PerfectNumber();

        try {
            sv.perfectNumber(-1);
            Assert.fail( "Failed to throw exception!" );
        } catch (IllegalArgumentException e) {
            Assert.assertNotNull(e);
        }

        Assert.assertEquals( "19", sv.perfectNumber(1));
        Assert.assertEquals( "28", sv.perfectNumber(2));
        Assert.assertEquals( "19", sv.perfectNumber(19));
        Assert.assertEquals( "127", sv.perfectNumber(12));
        Assert.assertEquals( "1289", sv.perfectNumber(128));
        Assert.assertEquals( "1234567895", sv.perfectNumber(123456789));
        Assert.assertEquals( "12345678914", sv.perfectNumber(1234567891));

        System.out.println(sv.perfectNumber(1234567891));
    }
}
