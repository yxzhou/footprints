package dp.backpack;

import java.util.ArrayList;
import java.util.Arrays;

import util.Misc;

/**
 * 
 * Given n distinct positive integers, integer k (k <= n) and a number target.

	Find k numbers where sum is target. Calculate how many solutions there are?

	Example
	Given [1,2,3,4], k = 2, target = 5.
	
	There are 2 solutions: [1,4] and [2,3].
	
	Return 2.
 *
 */

public class KSubSum {

    /**
     * @param A: an integer array.
     * @param k: a positive integer (k <= length(A))
     * @param target: a integer
     * @return an integer
     */
	/* Time O(n^k) */
    public int kSum(int A[], int k, int target) {
        //check
    	if(null == A || k < 1 || k > A.length){
    		return 0;
    	}
    	
    	Arrays.sort(A);
    	
    	return kSum(A, A.length - 1, k, target);
    }
    
    private int kSum(int[] A, int end, int k, int target){
    	if( k < 0 || target < 0 || k > end + 1 ){
    		return 0;
    	}
    	if( k == 0 && target == 0 ){
    		return 1;
    	}
    	
    	int result = 0;
    	
    	for(int i = end; i >= 0; i--){
    		if(target >= A[i]){
    			result += kSum(A, i - 1, k - 1, target - A[i]);
    		}
    	}
    	
    	return result;
    }
    /**
     * @param A: an integer array.
     * @param k: a positive integer (k <= length(A))
     * @param target: a integer
     * @return a list of lists of integer 
     */ 
    /* Time O(n^k) */
    public ArrayList<ArrayList<Integer>> kSumII(int A[], int k, int target) {
        // write your code here
        ArrayList<ArrayList<Integer>> result = new  ArrayList<ArrayList<Integer>>();
        if (A == null || A.length == 0) {
            return result;
        }
        helper(A, k, target, result, 0, new ArrayList<Integer>());
        return result;
    }
    
    private void helper(int A[], int k, int target, ArrayList<ArrayList<Integer>> result, int curIndex, ArrayList<Integer> curList) {
        if (curList.size() == k) {
            if (target == 0) {
                ArrayList<Integer> temp = new ArrayList<Integer>(curList);//deep-copy
                result.add(temp);
            }
            return;
        }
        for (int i = curIndex; i < A.length; i++) {
            curList.add(A[i]);
            helper(A, k, target - A[i], result, i + 1, curList);
            curList.remove(curList.size() - 1);
        }
    }
    
    
    /**
     * @param A: an integer array.
     * @param k: a positive integer (k <= length(A))
     * @param target: a integer
     * @return an integer
     */
    /* Time O(k*target*n) */
    public int  kSum_dp(int A[], int k, int target) {
        // write your code here
        if (A == null || A.length == 0) {
            return 0;
        }
        int[][][] f = new int[A.length + 1][k + 1][target + 1];//default all are 0
        
        //only the slots with k == 0 && t == 0 will be initialized as 1. All others need to be 0
        //when A[i] == t so we could derive f[i][k][t] from f[i - 1][k - 1][0]
        for (int i = 0; i < A.length + 1; i++) {
            f[i][0][0] = 1; 
        }
        
        for (int i = 1; i < A.length + 1; i++) { //the first i elements in A[]
            for (int j = 1; j <= k && j <= i; j++) { //pick n items from first i elements
                for (int t = 1; t <= target; t++) { //sum from 1 to target
                    //f[i][n][t] = 0;
                    //case 1: the # of solutions with A[i - 1]
                    if (t >= A[i - 1]) {
                        //pick n - 1 items from first i - 1 elements with the sum = t - A[i - 1] plus this item A[i-1]
                        f[i][j][t] += f[i - 1][j - 1][t - A[i - 1]];
                    }
                    //case 2: the # of solution w/o A[i - 1]
                    f[i][j][t] += f[i - 1][j][t]; //directly inherit from i - 1
                }
            }
        }
        return f[A.length][k][target];
    }
    

    public int  kSum_n2_wrong(int A[], int k, int target) {
        // write your code here
        if (target < 0) {
            return 0;
        }
        
        //int len = A.length;
        
        // D[i][j]: k = i, target j, the solution.
        int[][] f = new int[k + 1][target + 1];
        
        // only one solution for the empty set.
        f[0][0] = 1;
        for (int i = 1; i < A.length + 1; i++) { //the first i elements in A[]
            for (int j = 1; j <= k && j <= i; j++) { //pick n items from first i elements
                for (int t = 1; t <= target; t++) { //sum from 1 to target
                	

//		for (int i = 1; i <= len; i++) {
//			for (int j = 1; j <= k; j++) {
//				for (int t = target; t > 0; t--) {
					if (t >= A[i - 1]) {
						f[j][t] += f[j - 1][t - A[i - 1]];
					}
				}
			}
		}
        
        return f[k][target];
    }
	public static void main(String[] args) {
		

		int[][] inputs = {
				{1,2,3,4},
				{1,4,7,10,12,15,16,18,21,23,24,25,26,29},
				{1,4,6,8,10,13,15,17,18,21,23,26,27,28,29,30,32,35,36}
		};
		
		int[] ks = {
				2,
				5,
				9
		};

		int[] targets = {
				5,
				113,
				133
		};
		
		int[] output = {
				2,
				9,
				231
		};
		
		KSubSum sv = new KSubSum();
		
		for(int i = 0; i < output.length; i++){
			System.out.println(String.format("Input: %s, k:%d, target:%d ", Misc.array2String(inputs[i]), ks[i], targets[i]));
			
			System.out.println(String.format("Output: %d, kSum:%d, kSum_dp:%d, kSum_dp2: %d", output[i], sv.kSum(inputs[i], ks[i], targets[i]), sv.kSum_dp(inputs[i], ks[i], targets[i]), sv.kSum_n2_wrong(inputs[i], ks[i], targets[i])));
		}
	}

}
