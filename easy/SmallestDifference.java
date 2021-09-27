package easy;

import java.util.Arrays;

public class SmallestDifference {
	/**
	 * From lintcode
	 * Given two array of integers(the first array is array A, the second array
	 * is array B), now we are going to find a element in array A which is A[i],
	 * and another element in array B which is B[j], so that the difference
	 * between A[i] and B[j] (|A[i] - B[j]|) is as small as possible, return
	 * their smallest difference.
	 * 
	 * Example
	 * For example, given array A = [3,6,7,4], B = [2,8,9,3], return 0
	 */
    /**
     * @param A, B: Two integer arrays.
     * @return: Their smallest difference.
     */
    public int smallestDifference(int[] A, int[] B) {
        // check
        if(null == A || null == B || 0 == A.length || 0 == B.length){
            return -1;
        }
        
        Arrays.sort(A);
        Arrays.sort(B);
        
        int minDiff = Integer.MAX_VALUE;
        for(int i = 0, j = 0; i < A.length && j < B.length; ){
            if(A[i] == B[j]){
                return 0;
            }else if(A[i] < B[j]){
                minDiff = Math.min(minDiff, B[j] - A[i]);
                i++;
            }else{ //A[i] > B[j]
                minDiff = Math.min(minDiff,  A[i] - B[j]);
                j++;
            }
        }
        
        return minDiff;
    }
    
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
