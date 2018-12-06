package fgafa.dailyCoding;

import org.junit.Assert;
import org.junit.Test;


public class BishopsTest {

    @Test public void test(){
        Bishops bishops = new Bishops();

        int[][] postions = { {0, 0}, {1, 2}, {2, 2}, {4, 0}};

        Assert.assertEquals(2, bishops.bishops(5, postions));

    }


}
