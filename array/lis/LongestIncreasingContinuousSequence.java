package array.lis;

/**
 * Give you an integer array. find the longest increasing continuous subsequence in this array. 
 * 
 * An increasing continuous subsequence here : 
 *    Can be from right to left or from left to right.
 *    Indices of the integers in the subsequence should be continuous.
 *
 * Example
 * For [5, 4, 2, 1, 3], the LICS is [5, 4, 2, 1], return 4.
 * For [5, 1, 2, 3, 4], the LICS is [1, 2, 3, 4], return 4.
 *
 * Note O(n) time and O(1) extra space.
 * 
 */

public class LongestIncreasingContinuousSequence {
	
    public int longestIncreasingContinuousSubsequence(int[] A) {
        if (null == A || 0 == A.length) {
            return 0;
        }

        int max = 0;
        int count = 1;
        for (int i = 1, end = A.length; i < end; i++) {
            if (A[i] > A[i - 1]) {
                count++;
            } else {
                max = Math.max(max, count);
                count = 1;
            }
        }
        max = Math.max(max, count);

        count = 1;
        for (int i = A.length - 2; i >= 0; i--) {
            if (A[i] > A[i + 1]) {
                count++;
            } else {
                max = Math.max(max, count);
                count = 1;
            }
        }
        max = Math.max(max, count);

        return max;
    }
	
   
    public int longestIncreasingContinuousSubsequence_2(int[] A) {
        if(A == null || A.length == 0){
            return 0;
        }

        return Math.max(helper(A, true), helper(A, false));
    }

    private int helper(int[] A, boolean asc){
        int flag = asc? 1 : -1;
        int max = 0;
        int count = 1;
        for(int i = 1; i < A.length; i++){
            if( (A[i] - A[i - 1]) * flag > 0 ){
                count++;
            }else{
                max = Math.max(max, count);
                count = 1;
            }
        }

        return Math.max(max, count);
    }    
    
}
