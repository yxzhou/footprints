package fgafa.greedy;

import fgafa.util.Misc;

/*
 * 
 * Given an array of non-negative integers, you are initially positioned at the first index of the array.
 * Each element in the array represents your maximum jump length at that position.
 * Your goal is to reach the last index in the minimum number of jumps.
 * 
 * For example:
 * Given array A = [2,3,1,1,4]

 * The minimum number of jumps to reach the last index is 2. (Jump 1 step from index 0 to 1, then 3 steps to the last index.)
 * 
 */
public class JumpGameII {

    
	/**
	 *  compare to jump_x,  it didn't check the case that it can't jump to the end.  
	 */
	public int jump_2(int A[], int n) {

		int i, currLimit, nextLimit, count;
		for (i = currLimit = nextLimit = count = 0; i < n; ++i) {
			if (i <= currLimit) {
				nextLimit = Math.max(nextLimit, A[i] + i);
			} else {
				++count;
				currLimit = nextLimit;
				--i;
			}
		}
		
		return count;
	}

	/*
	 * Time O(n), Space O(1)
	 */
	public int jump_X(int A[]) {
		// check
		if (A == null || A.length == 0)
			return -1;
		if (1 == A.length)
			return 0;

		int i, currLimit, nextLimit, count;
		for (i = currLimit = nextLimit = count = 0; i < A.length;) {
			for (; i <= currLimit; i++){
				nextLimit = Math.max(nextLimit, A[i] + i);
			}

			if (nextLimit <= currLimit){ // for case that can not jump to the tail
				return -1;
			}

			currLimit = nextLimit;
			count++;

			if (currLimit >= A.length - 1){
				return count;
			}

		}

		return -1;
	}

    public int jump_n(int[] A) {
        //check
        if(null == A || 0 == A.length){
            return -1;
        }
        
        int size = A.length;
        if(1 == size){
        	return 0;
        }
        
        for(int i = 0, count = 0, currLimit = 0, nextLimit = 0; i < size;  ){
            for( ; i <= currLimit; i++){
            	nextLimit = Math.max(nextLimit, i + A[i]);
            }

            if(currLimit >= nextLimit){
            	return -1;
            }

            count++;
            
            if( nextLimit >= size - 1){
            	return count;
            }
            
            currLimit = nextLimit;
        }
        
        return -1; //error
    }


	public static void main(String[] args) {
		JumpGameII service = new JumpGameII();
		int[][] arr = { { 1 }, { 1, 2 }, { 1, 2, 3 }, { 2, 3, 1, 1, 4 },
				{ 2, 3, 1, 2, 0, 1 }, { 3, 2, 1, 0, 4 }, { 0, 1 } };

		for (int i = 0; i < arr.length; i++) {
			System.out.println("\n -" + i
					+ "-The non-negative integers array is: "
					+ Misc.array2String(arr[i]));

			System.out.println("The minimum number of jumps is "
					+ service.jump_X(arr[i]));
			System.out.println("The minimum number of jumps is "
					+ service.jump_n(arr[i]));
		}
	}

}
