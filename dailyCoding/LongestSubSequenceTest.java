package fgafa.dailyCoding;

import junit.framework.Assert;
import org.junit.Test;

public class LongestSubSequenceTest {

    @Test public void test(){
        LongestSubSequence sv = new LongestSubSequence();

        int[] input = {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15};
        Assert.assertEquals(6, sv.longestSubSequence(input));
    }

}
