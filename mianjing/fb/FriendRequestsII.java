package mianjing.fb;

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * Description
 * Given an array Ages of length n, where the first i elements represent the age of the individual i. 
 * Find total number of friend requests sent by this n person. There are some requirements:
 *
 * if Age(B) <= (1/2)Age(A) + 7, A will not send a request to B.
 * if Age(B) > Age(A), A will not send a request to B.
 * if Age(B) < 100 and Age(A) > 100, A will not send a request to B.
 * If it does not satisfy 1,2,3, then A will send a request to B
 *
 * Ages.length <= 1000。
 * Everyone's age is greater than 0, no larger than 150。
 *
 * Example
 * Example1
 * Input: Ages = [10,39,50]
 * Output: 1
 * Explanation:
 * Only people of age 50 will send friend requests to people of age 39.
 *
 * Example2
 * Input: Ages = [101,79,102]
 * Output: 1
 * Explanation:
 * Only people of age 102 will send friend requests to people of age 101.
 *
 */

public class FriendRequestsII {

    /**
     * Define two person A and B, with ages a and b, if A will send a friend request to B, when:
     * if a > 100,   Math.max(99, a / 2 + 7) <  b <= a
     *  else a / 2 + 7 < b <= a
     */
    public int friendRequest(int[] ages) {
        if(ages == null || ages.length < 2){
            return 0;
        }

        final int N = 151;

        int[] counts = new int[N];
        for(int age : ages){
            counts[age]++;
        }

        int[] sums = new int[N];
        // for(int i = 1; i < n; i++ ){
        //     sums[i] = sums[i - 1] + counts[i];
        // }

        int result = 0;
        int l;
        for(int i = 1; i < N; i++){
            sums[i] = sums[i - 1] + counts[i];

            if(counts[i] == 0){
                continue;
            }

            l = i / 2 + 7;
            if(i > 100){
                l = Math.max(99, l);
            }
            if(l < i){
                result += counts[i] * ( sums[i] - sums[l] - 1 );
            }
        }

        return result;
    }

    @Test
    public void test(){
        Assert.assertEquals(1, friendRequest(new int[]{10, 39, 50}));
        Assert.assertEquals(1, friendRequest(new int[]{101, 79, 102}));
        Assert.assertEquals(2, friendRequest(new int[]{10, 10}));
        Assert.assertEquals(2, friendRequest(new int[]{16,17,18}));
        Assert.assertEquals(3, friendRequest(new int[]{20,30,100,110,120}));
    }

}
