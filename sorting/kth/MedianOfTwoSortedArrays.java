package sorting.kth;

import org.junit.Assert;
import util.Misc;

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
 * Thoughts:
 *  A median is the middle number of the array after it is sorted.
 *  Define k = (n+m)/2, ( assume there is odd elements in the sorted array here )
 *  m1) A median is the k_th element.  
 *    to get f(A[0, n), B[0, m), k)
 *    if A[k/2] == B[k - k/2], return A[k/2]
 *    if A[k/2] < B[k - k/2], exclude k/2 elements in A, return f(A[k/2, n), B[0, m), k - k/2)
 *    if A[k/2] > B[k - k/2], exclude k - k/2 elements in B, return f(A[0, n), B[k - k/2, m), k/2)
 *  Time O( logk ),  k = (m + n) / 2
 * 
 *  m2) A median is a number x, which equals a element in the sorted array, and there is k elements smaller than x.
 *    binary search with the min_element and max_element, 
 *  Time O( log(range) ),  range = max(A[n-1], B[m-1]) - min(A[0], B[0])
 * 
 * 
 *A1: 一个可以保证最坏运行时间为O(n)的算法，叫做 "Median of Medians algorithm" 
*1.将这n个元素分为5个一组，找出每组里的中间数，形成新的n/5亿个中间值组成的集合 
*2.对这n/5个值再分为5个一组，找出每组中间的数...重复这些步骤，只至找的到最后的中间值m 
*3.以m为中值，将n个数分为L,R两组，L集合里的数都小于m,R集合里的数都大于m 
* 如果m=n/2，则返回m 
* 否则 如果L集合里的数多余一半，则从L集合中找出第n/2小的数 
*     如果R集合里的数多余一半，R集合的元素个数为k, 则从R集合中找出第k-n/2小的数. 
*4.如此重复迭代调用，直至找到中值.   
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
        
        if(A == null || A.length == 0){
            return findMedianSortedArray(B);
        }else if(B == null || B.length == 0 ){
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
    
    
    
	
    public static void main(String[] args) {
        int[][][] inputs = {
            //{ A, B, median }, 
            {
                {5}, {9}, {}
            }, 
            {
                {5,8}, {3,9}, {}
            },
            {
                {5,8}, {3,6}, {}
            },
            {
                {3,5}, {2,3}, {}
            },
            {
                {3,5}, {2,5}, {}
            },
            {
                {3,8}, {5,7}, {}
            },
            {
                {1, 2, 3, 6, 8}, {6, 7, 8, 9, 10}, {}
            },
            {
                {1, 3, 5, 7, 9, 11}, {2, 4, 6, 8, 10, 12}, {}
            },
            {
                {1, 2, 2, 2, 2, 2, 3}, {9, 12, 13, 14, 15, 17, 19}, {}
            },
            {
                {1, 2, 3}, {}, {}
            },
            {
                {1, 3, 5, 7, 8, 9, 10}, {2, 4, 6, 10, 11, 12, 13, 14, 17, 19, 20}, {}
            },
            {
                {1, 2, 3, 4, 5, 6, 7}, {5, 6, 7, 8, 9}, {}
            },
            {
                {1, 2, 2, 2, 2, 2, 3}, {9, 12, 13, 14, 15, 17, 19}, {}
            },
            {
                {1, 3}, {9, 12, 13, 14, 15, 17, 19}, {}
            }
            
        };
        

        MedianOfTwoSortedArrays sv = new MedianOfTwoSortedArrays();

        for (int[][] input : inputs ) {
            System.out.println(String.format("\n %s\n %s " , Misc.array2String(input[0]), Misc.array2String(input[1])));
            
            System.out.println(sv.findMedianSortedArrays(input[0], input[1]));
            
            //Assert.assertEquals(input[2][0], sv.findMedianSortedArrays(input[0], input[1]));
        }

    }
    
}
