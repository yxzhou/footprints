package sorting.median;

/**
 * _https://www.lintcode.com/problem/65
 * 
 * There are two sorted arrays A and B of size m and n respectively. Find the median of the two sorted arrays.
 * 
 * Example
 * Given A=[1,2,3,4,5,6] and B=[2,3,4,5], the median is 3.5.
 * 
 * Given A=[1,2,3] and B=[4,5], the median is 3.
 * 
 * Challenge The overall run time complexity should be O(log (m+n)).
 *
 */

public class MedianOfTwoSortedArrays {

    /*    
     *
     * @param A: An integer array
     * @param B: An integer array
     * @return: a double whose format is *.5 or *.0
     */
    public double findMedianSortedArrays(int[] A, int[] B) {
        assert !(A == null && B == null);
        
        if(A == null){
            return findMedianSortedArray(B);
        }else if(B == null){
            return findMedianSortedArray(A);
        }

        int n = A.length + B.length;

        if( (n & 1) == 1 ){
            return findKth(A, 0, B, 0, n/2 + 1);
        }else{
            return (findKth(A, 0, B, 0, n/2) + findKth(A, 0, B, 0, n/2 + 1)) / 2.0;
        }
    }

    private double findMedianSortedArray(int[] arr){
        int n = arr.length;
        int mid = n / 2;
        if((n & 1) == 1){
            return (double)arr[mid];
        }else{
            return ((double)arr[mid - 1] + arr[mid]) / 2.0;
        }
    }

    // find kth number of two sorted array
    private int findKth(int[] A, int i, int[] B, int j, int k){
        if(i >= A.length){
            return B[j + k - 1];
        }
        if(j >= B.length){
            return A[i + k - 1];
        }

        if(k == 1){
            return Math.min(A[i], B[j]);
        }

        int newI = Math.min(i + k/2, A.length);
        int newJ = Math.min(j + k - k/2, B.length);

        if(A[newI - 1] < B[newJ - 1]){
            return findKth(A, newI, B, j, k - newI + i );
        }else{
            return findKth(A, i, B, newJ, k - newJ + j );
        }
    }
	
    
    
}
