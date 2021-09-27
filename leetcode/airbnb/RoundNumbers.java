package leetcode.airbnb;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * When you book on airbnb the total price is:

 Total price = base price + service fee + cleaning fee + …

 input : array of decimals ~ X
 output : array of int ~ Y

 But they need to satisfy the condition:
 sum(Y) = round(sum(x))
 minmize (|y1-x1| + |y2-x2| + ... + |yn-xn|)

 Example1:
 input = 30.3, 2.4, 3.5
 output = 30 2 4

 Example2:
 input = 30.9, 2.4, 3.9
 output = 31 2 4

 *
 */

public class RoundNumbers {

    public int[] roundNumber(double[] prices){
        if(null == prices || 0 == prices.length){
            return new int[0];
        }

        int length = prices.length;
        int[] result = new int[length];

        //double total = 0;
        //int totalFloor = 0;
        Pair<Double, Integer>[] diff2Index = new Pair[length];
        double diff;
        double totoalDiff = 0;
        for(int i = 0; i < length; i++){
            result[i] = (int)Math.floor(prices[i]);
            //totalFloor += result[i];

            diff = prices[i] - result[i];
            diff2Index[i] = Pair.of(diff, i);
            totoalDiff += diff;
        }

        Arrays.sort(diff2Index, (p1, p2) -> Double.compare(p2.getKey(), p1.getKey()));

        for(int i = 0, limit = (int)Math.round(totoalDiff); i < limit; i++) {
            result[diff2Index[i].getValue()]++;
        }

        return result;
    }

    @Test
    public void test(){
        Assert.assertArrayEquals(new int[]{30, 2, 4}, roundNumber(new double[]{30.3, 2.4, 3.5}));

        Assert.assertArrayEquals(new int[]{31, 2, 4}, roundNumber(new double[]{30.9, 2.4, 3.9}));
    }

}
