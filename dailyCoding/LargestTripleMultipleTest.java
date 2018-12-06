package fgafa.dailyCoding;

import org.junit.Assert;
import org.junit.Test;

public class LargestTripleMultipleTest {

    @Test public void test(){
        LargestTripleMultiple sv = new LargestTripleMultiple();

        Assert.assertEquals(-4*-3*-2, sv.largestTripleMultiple(new int[]{-5, -4, -3, -2}));

        Assert.assertEquals(2*3*4, sv.largestTripleMultiple(new int[]{-5, 2, 3, 4}));

        Assert.assertEquals(Math.max(-5*-4*4, 2*3*4), sv.largestTripleMultiple(new int[]{-5, -4, 2, 3, 4}));
        Assert.assertEquals(Math.max(-5*-4*4, 5*6*4), sv.largestTripleMultiple(new int[]{-5, -4, 5, 6, 4}));

        Assert.assertEquals(-5*-4*2, sv.largestTripleMultiple(new int[]{-5, -4, -3, -2, 2}));
        Assert.assertEquals(Math.max(-5*-4*4, 2*3*4), sv.largestTripleMultiple(new int[]{-5, -4, -3, -2, 2, 3, 4}));

        Assert.assertEquals(5*3*4, sv.largestTripleMultiple(new int[]{5, 2, 3, 4}));
    }
}
