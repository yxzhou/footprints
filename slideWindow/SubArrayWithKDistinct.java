package fgafa.slideWindow;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an array A of positive integers, call a (contiguous, not necessarily distinct) subarray of A good if the number of different integers in that subarray is exactly K.
 *
 * (For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3.)
 *
 * Return the number of good subarrays of A.
 *
 *
 *
 * Example 1:
 *
 * Input: A = [1,2,1,2,3], K = 2
 * Output: 7
 * Explanation: Subarrays formed with exactly 2 different integers: [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2].
 * Example 2:
 *
 * Input: A = [1,2,1,3,4], K = 3
 * Output: 3
 * Explanation: Subarrays formed with exactly 3 different integers: [1,2,1,3], [2,1,3], [1,3,4].
 *
 *
 * Note:
 *
 * 1 <= A.length <= 20000
 * 1 <= A[i] <= A.length
 * 1 <= K <= A.length
 *
 *
 *
 */

public class SubArrayWithKDistinct {

    /**
     * case 1, [1, 2, 1, 2, 3]
     *    the 1st valid window (with K Different integers) is: [1, 2], the valid subArray (with K ---) is [1, 2], l == z == 0
     *
     *    the 2nd valid window is: [1, 2, 1],  the valid subArray is [1, 2, 1] and [2, 1],  l == 0 and z == 1
     *
     *    the 3rd valid window is: [1, 2, 1, 2], the valid subArray is [1, 2, 1, 2] and [2, 1, 2] and [1, 2] ,  l == 0 and z == 2
     *
     *    the 4rd valid window is: [1, 2, 1, 2, 3] -> [2, 3], the valid subArray is [2, 3],  l == 3 and z == 3
     *
     *
     * case 1, [1, 2, 1, 1, 3]
     *    the 1st valid window (with K Different integers) is: [1, 2], the valid subArray (with K ---) is [1, 2]
     *
     *    the 2nd valid window is: [1, 2, 1],  the valid subArray is [1, 2, 1] and [2, 1]
     *
     *    the 3rd valid window is: [1, 2, 1, 1], the valid subArray is [1, 2, 1, 1] and [2, 1, 1]
     *
     *    the 4rd valid window is: [1, 2, 1, 1, 3] -> [1, 1, 3], the valid subArray is [1, 1, 3] and [1, 3]
     *
     */

    public int subarraysWithKDistinct(int[] A, int K) {
        if(A == null || A.length < K){
            return 0;
        }

        int count = 0;
        Map<Integer, Integer> map = new HashMap<>(); // map<A[i], the right most index of A[i] in window>
        for(int l = 0, r = 0, z = 0, n = A.length; r < n; r++){
            map.put(A[r], r);

            while(map.size() > K){
                //if(l == map.get(A[l])){
                if(l == z) {
                    map.remove(A[l]);
                }

                l++;
            }

            z = Math.max(z, l);
            while( z < map.get(A[z]) ){
                z++;
            }

            if(map.size() == K){
                count += z - l + 1;
            }
        }

        return count;
    }
}
