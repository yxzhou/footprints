package fgafa.array.subSum;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Given an array of integers A, find the number of triples of indices (i, j, k) such that:

 0 <= i < A.length
 0 <= j < A.length
 0 <= k < A.length
 A[i] & A[j] & A[k] == 0, where & represents the bitwise-AND operator.


 Example 1:

 Input: [2,1,3]
 Output: 12
 Explanation: We could choose the following i, j, k triples:
 (i=0, j=0, k=1) : 2 & 2 & 1
 (i=0, j=1, k=0) : 2 & 1 & 2
 (i=0, j=1, k=1) : 2 & 1 & 1
 (i=0, j=1, k=2) : 2 & 1 & 3
 (i=0, j=2, k=1) : 2 & 3 & 1
 (i=1, j=0, k=0) : 1 & 2 & 2
 (i=1, j=0, k=1) : 1 & 2 & 1
 (i=1, j=0, k=2) : 1 & 2 & 3
 (i=1, j=1, k=0) : 1 & 1 & 2
 (i=1, j=2, k=0) : 1 & 3 & 2
 (i=2, j=0, k=1) : 3 & 2 & 1
 (i=2, j=1, k=0) : 3 & 1 & 2


 Note:

 1 <= A.length <= 1000
 0 <= A[i] < 2^16
 *
 */


public class TripleBitwiseAND {

    public int countTriplets(int[] A) {
        int result = 0;

        Arrays.sort(A);

        int len = A.length;
        //final int MIRROR = (1 << 16) - 1;

        //Map<Integer, Integer> pool = new HashMap<>(len);

        for( int i = 0; i < len; i++) {
            if (A[i] == 0) {
                int x = len - i - 1;
                result += 1 + x * 6 + x * (x - 1) * 3;
                continue;
            }

            for(int j = i + 1; j < len; j++){
                int r2 = (A[i] & A[j]);
                if( r2 == 0 ){
                    result += (len - j) * 6;
                    continue;
                }

                for(int k = j + 1; k < len; k++){
                    if((r2 & A[k]) == 0){
                        result += 6;
                    }
                }
            }
        }

        return result;
    }

    @Test
    public void test(){
        Assert.assertEquals(12, countTriplets(new int[]{2, 1, 3}));
        Assert.assertEquals(27, countTriplets(new int[]{0, 0, 0}));

        Assert.assertEquals(30, countTriplets(new int[]{2, 4, 7, 3}));
    }

}
