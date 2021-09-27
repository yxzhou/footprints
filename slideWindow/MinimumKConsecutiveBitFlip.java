package slideWindow;

/**
 * Leetcode #995
 *
 * In an array A containing only 0s and 1s, a K-bit flip consists of choosing a (contiguous) subarray of length K and simultaneously changing every 0 in the subarray to 1, and every 1 in the subarray to 0.
 *
 * Return the minimum number of K-bit flips required so that there is no 0 in the array.  If it is not possible, return -1.
 *
 *
 *
 * Example 1:
 *
 * Input: A = [0,1,0], K = 1
 * Output: 2
 * Explanation: Flip A[0], then flip A[2].
 * Example 2:
 *
 * Input: A = [1,1,0], K = 2
 * Output: -1
 * Explanation: No matter how we flip subarrays of size 2, we can't make the array become [1,1,1].
 * Example 3:
 *
 * Input: A = [0,0,0,1,0,1,1,0], K = 3
 * Output: 3
 * Explanation:
 * Flip A[0],A[1],A[2]: A becomes [1,1,1,1,0,1,1,0]
 * Flip A[4],A[5],A[6]: A becomes [1,1,1,1,1,0,0,0]
 * Flip A[5],A[6],A[7]: A becomes [1,1,1,1,1,1,1,1]
 *
 *
 * Note:
 *
 * 1 <= A.length <= 30000
 * 1 <= K <= A.length
 *
 */

public class MinimumKConsecutiveBitFlip {


    /**
     *  Thought:
     *  Input: A = [0,0,0,1,0,1,1,0], K = 3
     *
     *  A =   [0 , 0 , 0 , 1 , 0 , 1 , 1 , 0]
     *  flip    T1             T2  T3
     *  flag   0   1   1   0   0   1   0   1
     *                 T11             T21 T31
     *
     *
     *  Time O(n)  Space O(n)
     */
    public int minKBitFlips(int[] A, int K) {
        if(A == null){
            return 0;
        }

        int n = A.length;

        int count = 0;
        boolean[] flip = new boolean[n];
        int flag = 0;

        int l = 0;
        for(int r = 0; l < n; l++){
            if(A[l] == flag){
                r = l + K - 1;

                if(r >= n){
                    return -1;
                }

                flip[r] = true;

                count++;
                flag ^= 1;
            }

            if(flip[l]){
                flag ^= 1;
            }
        }

        return l == n ? count : -1;
    }


    /**
     *  same idea,  without extra O(n) space, change the future A[r] value
     *
     *  Time O(n)  Space O(1)
     *
     */
    public int minKBitFlips_x(int[] A, int K) {
        if(A == null){
            return 0;
        }

        int n = A.length;

        int count = 0;
        int flag = 0;

        int l = 0;
        for(int r = 0; l < n; l++){
            if(A[l] == flag || A[l] == flag + 2){
                r = l + K - 1;

                if(r >= n){
                    return -1;
                }

                if(r > l){
                    A[r] += 2;
                    flag ^= 1;
                }

                count++;
            }

            if(A[l] > 1){
                A[l] -= 2;
                flag ^= 1;
            }
        }

        return l == n ? count : -1;
    }

}
