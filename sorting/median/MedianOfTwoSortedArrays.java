package sorting.median;

/**
 * 
 * There are two sorted arrays A and B of size m and n respectively. Find the median of the two sorted arrays.

	Have you met this question in a real interview? Yes
	Example
	Given A=[1,2,3,4,5,6] and B=[2,3,4,5], the median is 3.5.
	
	Given A=[1,2,3] and B=[4,5], the median is 3.
	
	Challenge
	The overall run time complexity should be O(log (m+n)).
 *
 */

public class MedianOfTwoSortedArrays {

    public double findMedianSortedArrays(int A[], int B[]) {
        if (A == null ){
        	if(B == null ){
        		return 0;
        	}
            return medianSortedArray(B); //B[(B.length - 1)/2];
        }else if (B == null ){
        	return medianSortedArray(A); //A[(A.length - 1)/2];
        }
            
        int len = A.length + B.length;
        if (isOdd(len)) {
            return findKth(A, 0, B, 0, len / 2 + 1);
        }
        
        return ( findKth(A, 0, B, 0, len / 2) + findKth(A, 0, B, 0, len / 2 + 1) ) / 2.0;
    }

	/**
	 * @param nums: A list of sorted integers.
	 * @return: An integer denotes the middle number of the array.
	 */
	public double medianSortedArray(int[] A) {
		if (A == null || A.length == 0)
			throw new IllegalArgumentException("");

		int n = A.length;
		int mid = (n >> 1);
		if (isOdd(n)){
			return A[mid];
		}else{
			return (A[mid] + A[mid - 1]) >> 1;
		}
	}
	
	private boolean isOdd(int x) {
		return (x & 1) == 1;
	}
    
    // find kth number of two sorted array
    public static int findKth(int[] A, int A_start,
                              int[] B, int B_start,
                              int k){		
		if (A_start >= A.length) {
			return B[B_start + k - 1];
		}
		if (B_start >= B.length) {
			return A[A_start + k - 1];
		}

		if (k == 1) {
			return Math.min(A[A_start], B[B_start]);
		}
		
		int A_key = A_start + k / 2 - 1 < A.length
		            ? A[A_start + k / 2 - 1]
		            : Integer.MAX_VALUE;
		int B_key = B_start + k / 2 - 1 < B.length
		            ? B[B_start + k / 2 - 1]
		            : Integer.MAX_VALUE; 
		
		if (A_key < B_key) {
			return findKth(A, A_start + k / 2, B, B_start, k - k / 2);
		} else {
			return findKth(A, A_start, B, B_start + k / 2, k - k / 2);
		}
	}
	
}
