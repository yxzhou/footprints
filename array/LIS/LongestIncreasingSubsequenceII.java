package array.lis;

import util.Misc;
import org.junit.Test;

import java.util.Arrays;

/**
 * 
 * LIS, Longest Increasing Subsequence
 * 
 * 
 * e.g #2: on two arrays 
 *  input {4, 4, 3, 12, 3, 3, 12, 3, 4}
 *        {12, 2, 4, 4, 3, 4, 4, 12, 5, 12, 3, 4}
 * output 6,  {4, 4, 4) or (3, 3, 4} 
 * 
 *
 */

public class LongestIncreasingSubsequenceII {

    /**
    *
    * fetch the Longest Increasing Subsequence with GREEDY on two array
    * It's refer to LongestIncreasingSubsequenceI
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
    private int LIS_insert(int[] lis, int top, int seqN) {
        int low = 0, high = top;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);    //(low + high) / 2;
            if (lis[mid] < seqN) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        lis[low] = seqN;

        return low > top ? top + 1 : top;
    }

  /**
   * @param args
   */
    public static void main(String[] args) {
        LongestIncreasingSubsequenceII s = new LongestIncreasingSubsequenceII();

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
