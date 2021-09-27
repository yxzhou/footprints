package dp.arithmetic;

import java.util.HashMap;
import java.util.Map;

/**
 * Leetcode #1027
 *
 * Given an array A of integers, return the length of the longest arithmetic subsequence in A.
 *
 * Recall that a subsequence of A is a list A[i_1], A[i_2], ..., A[i_k] with 0 <= i_1 < i_2 < ... < i_k <= A.length - 1, and that a sequence B is arithmetic if B[i+1] - B[i] are all the same value (for 0 <= i < B.length - 1).
 *
 *
 *
 * Example 1:
 *
 * Input: [3,6,9,12]
 * Output: 4
 * Explanation:
 * The whole array is an arithmetic sequence with steps of length = 3.
 * Example 2:
 *
 * Input: [9,4,7,2,10]
 * Output: 3
 * Explanation:
 * The longest arithmetic subsequence is [4,7,10].
 * Example 3:
 *
 * Input: [20,1,15,3,10,5,8]
 * Output: 4
 * Explanation:
 * The longest arithmetic subsequence is [20,15,10,5].
 *
 *
 * Note:
 *
 * 2 <= A.length <= 2000
 * 0 <= A[i] <= 10000
 *
 */

public class LongestArithmeticSequence {

    public int longestArithSeqLength(int[] A) {
        if(A == null || A.length < 2){
            return 0;
        }

        int max = 1;
        int n = A.length;

        int diff;
        Map<Integer, Integer>[] counts = new HashMap[n]; // <diff, count>

        for(int r = 0; r < n; r++){

            counts[r] = new HashMap<>();

            for(int l = 0; l < r; l++){
                diff = A[r] - A[l];

                counts[r].put(diff, Math.max(counts[r].getOrDefault(diff, 0), counts[l].getOrDefault(diff, 0) + 1 ));

                max = Math.max(max, counts[r].get(diff));
            }
        }

        return max + 1;
    }

}
