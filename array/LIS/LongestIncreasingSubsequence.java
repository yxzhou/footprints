package array.LIS;

import util.Misc;
import org.junit.Test;

import java.util.Arrays;

/**
 * 
 * LIS, Longest Increasing Subsequence
 * 
 * case #1, 给你一个数列，一次操作是指将某个数移到数列中别的位置上去，然后问最少要几次操作才能让数列变得有序。
 * 例如，数列7,1,3,2,6,5就只需要三次移动，把3移到2后面，把5移到6前面，再把7移到最后面即可。 
 * case #2 Given an array of integers without duplicates, 
 * remove elements such that the remaining elements are in ascending order. 
 * Minimize the number of removed elements. 
 * Your answer should consist of all possible combinations if more than one unique solution exists.
 * 
 * Solution #1, DP,  dp[i] = max{1, dp[j] + 1}.
 *   List: [4, 0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15]
 *   dp[]: [1, 1, 2, 2, 3,  2,  3, 3,  4, 2, 4, 3,  5, 3,  5, 4,  6]
 *   The result is: 6
 * 
 *   Time is O(n^2),  while it can easy to get all LIS string
 *   
 *   
 * Solution #2, Greedy + Binary Search
 * 1) create a array, put the first element in it. ( this array will store the Longest Increasing Subsequence )
 * 2) travel the input list, to every element, binary search the position in the array. 
 *    If it's bigger than the last one of array, add it in the tail.  ( the LIS grows )
 *    Or replace it with the related element in the array.  ( the LIS not grow, while it's better for the future. )
 *   
 *   Time O(nlogn)   Space O(n), it's not easy to get all lIS string
 * 
 * 
 * e.g #1: on a array 
 *  input {4, 0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15}
 * output 6,  {0, 2, 6, 9, 13, 15} or {0, 4, 6, 9, 11, 15}
 * 
 * e.g #2: on two arrays 
 *  input {4, 4, 3, 12, 3, 3, 12, 3, 4}
 *        {12, 2, 4, 4, 3, 4, 4, 12, 5, 12, 3, 4}
 * output 6,  {4, 4, 4) or (3, 3, 4} 
 * 
 *
 */

public class LongestIncreasingSubsequence
{

    /**
    * fetch the Longest increasing subsequence (LIS) with DP on a array
    *
    * 设A[i]表示序列中的第i个数， dp[i]表示从1到i这一段中以i结尾的最长上升子序列的长度，
    * 初始时设F[i] = 0(i = 1, 2, ..., len(A))。
    * 则有动态规划方程： dp[i] = max{1, dp[j] + 1}    (j = 1, 2, ..., i - 1, A[j] < A[i])。
    *
    * e.g.
    * List: [4, 0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15]
    * dp[]: [1, 1, 2, 2, 3,  2,  3, 3,  4, 2, 4, 3,  5, 3,  5, 4,  6]
    * The result is: 6
    *
    * time is O(n^2)
    */
    public int lengthOfLIS(int[] nums) {
      if (nums == null || 0 == nums.length) {
          return 0;
      }

      int n = nums.length;
      int[] result = new int[n];
      int end = 0;
      result[end++] = nums[0];

      for (int x : nums) {
          end = fill(nums, end, x);
      }

      return end;
    }

    private int fill(int[] nums, int len, int target){

        if(nums[len - 1] < target){
            nums[len++] = target;
            return len;
        }

        int i = len - 2;
        while(i >= 0 && nums[i] >= target){
            i--;
        }

        nums[i + 1] = target;
        return len;
    }


    public int lengthOfLIS_binarySearch(int[] nums) {
        if(nums == null || 0 == nums.length){
            return 0;
        }

        int n = nums.length;
        int[] result = new int[n];
        int end = 0;

        for(int x : nums){
            int i = Arrays.binarySearch(result, 0, end, x);

            if(i < 0){
                i = -(i + 1);
            }

            result[i] = x;

            end += (i == end? 1: 0);
        }

        return end;
    }

    @Test public void test(){
        int[] list = {};

        System.out.println(Arrays.binarySearch(list, 0, 0, 1));
        //System.out.println(Arrays.binarySearch(list, 0, 1, 1));  //ArrayIndexOutOfBoundsException

        list = new int[]{1, 3};
        System.out.println(Arrays.binarySearch(list, 0, 2, 0));
        System.out.println(Arrays.binarySearch(list, 0, 2, 1));
        System.out.println(Arrays.binarySearch(list, 0, 2, 2));
        System.out.println(Arrays.binarySearch(list, 0, 2, 3));
        System.out.println(Arrays.binarySearch(list, 0, 2, 4));
    }

    /**
    *
    * fetch the Longest Increasing Subsequence with GREEDY on two array
    * It's refer to calLis_Greedy and calLCS_DP
    *
    * input arr1={a1, a2, ---, am},  arr2={b1, b2, ---, bn}  where m>=n
    *
    * opt[i][j] = 0                              if i = M or j = N
    *           = opt[i+1][j+1] + 1              if arr1[i] = arr2[j]
    *           = max(opt[i][j+1], opt[i+1][j])  otherwise
    *
    * pure DP, Time O(n*m*m) (m>n) and Space O(n*m)
    *          Time O(n*m*logn) (m>n) and Space O(n)
    *
    * e.g: on two arrays
    *  input {12, 3, 4, 4, 3, 4, 4, 12, 5, 12, 3, 4}
    *        {4, 4, 3, 12, 3, 3, 12, 3, 4}
    * output 3,  {4, 4, 4) or (3, 3, 4}
    *
    * @return
    */
    public int calLIS_DP(int[] arr1, int[] arr2) {

      int M = arr1.length;
      int N = arr2.length;

      if (M < N) {
          calLIS_DP(arr2, arr1);
      }

      //main program (M> N)
      int top = 0;

      // opt[i][j] = length of LCS of x[0..i] and y[0..j]
      int[][] opt = new int[N + 1][M + 1];

      // compute length of LCS and all subproblems via dynamic programming
      for (int i = 0; i < N; i++) {
          for (int j = 0; j < M; j++) {
              if (arr1[j] == arr2[i]) {
                  for (int k = 0; k < j; k++) {
                      if (arr1[k] < arr1[j] && opt[i + 1][j + 1] < opt[i + 1][k + 1] + 1) {
                          opt[i + 1][j + 1] = opt[i + 1][k + 1] + 1;
                      }
                  }

                  top = Math.max(top, opt[i + 1][j + 1]);
              } else {
                  opt[i + 1][j + 1] = Math.max(opt[i + 1][j], opt[i][j + 1]);
              }
          }
      }

      // recover LCS itself and print it to standard output
      //System.out.println("The LIS(opt) is: " + Misc.array2String(opt));

      return top;  // note, it's top instead of top+1
    }


    public int calLIS_Greedy(int[] arr1, int[] arr2) {

        int M = arr1.length;
        int N = arr2.length;

        if (M > N) {
            calLIS_Greedy(arr2, arr1);
        }

        //init


        //main program (M> N)
        int top = 0;
        int[] stack = new int[M];

        stack[top] = Integer.MAX_VALUE;

        //StringBuffer sb2 = new StringBuffer();
        int tmpTop;
        // compute length of LCS and all subproblems via dynamic programming
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (arr1[j] == arr2[i]) {
                    tmpTop = LIS_insert(stack, top, arr2[i]);
                    top = Math.max(tmpTop, top);
                }
            }
        }

        System.out.println("The LIS(stack) is: " + Misc.array2String(stack));

        return top + 1;
    }

    /**
     * binary search, if seqN is biggest, insert; else replace.
     *
     * @param lis, a int array in ascent order
     * @param top, the index of the biggest element of lis
     * @param seqN, the new element.
     * @return the "new" biggest element in lis
     */
    private int LIS_insert(int[] lis, int top, int seqN){
        int low = 0, high = top;
        while (low <= high) {
            int mid =  low + ((high - low)  >> 1);    //(low + high) / 2;
            if (lis[mid] < seqN)
                low = mid + 1;
            else
                high = mid - 1;
        }

        lis[low] = seqN;

        return low > top ? top + 1 : top;
    }

  /**
   * @param args
   */
  public static void main(String[] args) {
    int[] testdata = {1, 5, 8, 3, 6, 7};
                     //{4, 0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15, 8};

    LongestIncreasingSubsequence s = new LongestIncreasingSubsequence();
    System.out.println("Input " + Misc.array2String(testdata));
    
    
    int ret = s.lengthOfLIS(testdata);
    System.out.println("The result of List_DP is: " + ret);
    ret = s.lengthOfLIS_binarySearch(testdata);
    System.out.println("The result of List_BST is: " + ret);
    
    // int[] a1 = {12, 3, 4, 4, 3, 4, 4, 12, 5, 12, 3, 4};
    // int[] a2 = {4, 4, 3, 12, 3, 3, 12, 3, 4};
    int[] a1 = {3, 4, 5, 6, 7, 8, 9, 10, 11, 7};
    int[] a2 = {4, 4, 6, 9, 8, 9, 13, 10, 12, 7};
    // output 4, (4, 6, 9, 10}

    System.out.println("\nList_Greedy of " + Misc.array2String(a1) + " and "
        + Misc.array2String(a2));
    System.out.println("The result is: " + s.calLIS_DP(a1, a2));
    System.out.println("The result is: " + s.calLIS_Greedy(a1, a2));
  }

  
  
}
