package fgafa.dailyCoding;

import java.util.Random;

/**
 * Assume you have access to a function toss_biased() which returns 0 or 1 with a probability that's not 50-50 (but also not 0-100 or 100-0). You do not know the bias of the coin.
 *
 * Write a function to simulate an unbiased coin toss.
 *
 * Tags: Square
 *
 * Solution:
 *   suppose the function toss_biased(), return 1 with a probability 60%, return 0 with a probability 40%
 *   if toss twice, the result and probability is
 *     (1, 1)  0.6 * 0.6 = 0.36
 *     (0, 0)  0.4 * 0.4 = 0.16
 *     (1, 0)  0.4 * 0.6 = 0.24
 *     (0, 1)  0.6 * 0.4 = 0.24
 *
 *   (1, 0) and (0, 1) both appears with equal probability. The idea is to return consider only the above two cases, return 0 in one case, return 1 in other case.
 *   For other cases [(0, 0) and (1, 1)], recur until you end up in any of the above two cases..
 */

public class TossCoin {

    public boolean toss_unbiased(){
        boolean r1 = toss_biased();
        boolean r2 = toss_biased();

        if(r1 == r2){
            return toss_unbiased();
        }else{
            return r2;
        }
    }


    //provided
    Random rd = new Random();
    public boolean toss_biased(){
        return rd.nextInt(10) < 6 ? true : false;
    }

}
