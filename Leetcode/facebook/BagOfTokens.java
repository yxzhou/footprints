package fgafa.Leetcode.facebook;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * You have an initial power P, an initial score of 0 points, and a bag of tokens.

 Each token can be used at most once, has a value token[i], and has potentially two ways to use it.

 If we have at least token[i] power, we may play the token face up, losing token[i] power, and gaining 1 point.
 If we have at least 1 point, we may play the token face down, gaining token[i] power, and losing 1 point.
 Return the largest number of points we can have after playing any number of tokens.



 Example 1:
 Input: tokens = [100], P = 50
 Output: 0

 Example 2:
 Input: tokens = [100,200], P = 150
 Output: 1
 Explain:  150

 Example 3:
 Input: tokens = [100,200,300,400], P = 200
 Output: 2


 Note:

 tokens.length <= 1000
 0 <= tokens[i] < 10000
 0 <= P < 10000
 *
 */

public class BagOfTokens {

    public int bagOfTokensScore(int[] tokens, int P) {
        if(null == tokens || 0 == tokens.length){
            return 0;
        }

        Arrays.sort(tokens);

        int len = tokens.length;
        int point = 0;
        for(int left = 0, right = len - 1; left <= right && P >= tokens[left]; ){
            if( P >= tokens[left]){
                P -= tokens[left];
                point++;
                left++;
            }

            if(left < right && P < tokens[left]){
                P += tokens[right];
                point--;
                right--;
            }
        }

        return point;
    }


    @Test public void test(){
        Assert.assertEquals(0, bagOfTokensScore(new int[]{}, 10));

        Assert.assertEquals(0, bagOfTokensScore(new int[]{100}, 50));
        Assert.assertEquals(1, bagOfTokensScore(new int[]{100,200}, 150));
        Assert.assertEquals(2, bagOfTokensScore(new int[]{300, 200,100,400}, 200));

    }
}
