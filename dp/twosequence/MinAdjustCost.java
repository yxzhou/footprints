package fgafa.dp.twosequence;

import java.util.ArrayList;

/**
 * 
 * Given an integer array, adjust each integers so that the difference of every
 * adjacent integers are not greater than a given number target.
 * 
 * If the array before adjustment is A, the array after adjustment is B, you
 * should minimize the sum of |A[i]-B[i]|
 * 
 * Example Given [1,4,2,3] and target = 1, one of the solutions is [2,3,2,3],
 * the adjustment cost is 2 and it's minimal.
 * 
 * Return 2.
 * 
 * Note You can assume each number in the array is a positive integer and not
 * greater than 100.
 *
 */

public class MinAdjustCost {

	
    /**
     * @param A: An integer array.
     * @param target: An integer.
     */

	public int MinAdjustmentCost(ArrayList<Integer> A, int target) {
		// write your code here
		int n = A.size();
		int max = 0;
		int min = 100;
		for (int i = 0; i < n; i++) {
			max = Math.max(max, A.get(i));
			min = Math.min(min, A.get(i));
		}
		
		//define d[i][v] as the min cost when change A[i] to v
		int[][] d = new int[n][max + 1]; //default all are 0
		for (int j = min; j <= max; j++) {
			d[0][j] = Math.abs(A.get(0) - j);
		}
		
		int curMin = 0;
		for (int i = 1; i < n; i++) {
			curMin = Integer.MAX_VALUE;
			for (int j = min; j <= max; j++) {
				d[i][j] = Integer.MAX_VALUE;
				for (int k = Math.max(min, j - target); k <= Math.min(max, j + target); k++) {
					d[i][j] = Math.min(d[i][j], d[i - 1][k] + Math.abs(A.get(i) - j));
					curMin = Math.min(curMin, d[i][j]);
				}
			}
		}
		
		return curMin;
	}

    
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
