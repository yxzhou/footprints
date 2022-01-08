/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp.twosequence;

import java.util.Arrays;
import junit.framework.Assert;

/**
 *_https://www.lintcode.com/problem/1016 
 * Minimum Swaps To Make Sequences Increasing
 * 
 * We have two integer sequences A and B of the same non-zero length.
 * 
 * We are allowed to swap elements A[i] and B[i]. Note that both elements are in the same index position in their respective sequences.
 * 
 * After some number of swaps, A and B are both strictly increasing. (A sequence is strictly increasing if and only if 
 * A[0] < A[1] < A[2] < ... < A[A.length - 1].)
 * 
 * Given A and B, return the minimum number of swaps to make both sequences strictly increasing. It is guaranteed that 
 * the given input always makes it possible.
 * 
 * Notes:
 *   A, B are arrays with the same length, and that length will be in the range of [1, 1000].
 *   A[i], B[i] are integer values in the range of [0, 2000].
 * 
 * Example 1:
 * Input: A = [1,3,5,4], B = [1,2,3,7]
 * Output: 1
 * Explanation: Swap A[3] and B[3]. Then the sequences are: A = [1,3,5,7] and B = [1,2,3,4], which are both strictly increasing.
 * 
 * Example 2:
 * Input: A = [2,4,5,7,10], B = [1,3,4,5,9]
 * Output: 0
 * 
 * Thoughts:
 *   Because it only allow to swap A[i] and B[i], so think about the following status:
 *     It's strictly increasing from A[0] to A[i - 1], and from B[0] to B[i - 1]. 
 *        .... A[i - 1], A[i]
 *        .... B[i - 1], B[i] 
 *    There are 4 possiblility,  
 *       1st, A[i - 1] <  A[i] && B[i - 1] <  B[i]
 *       2nd, A[i - 1] <  A[i] && B[i - 1] >= B[i]
 *       3rd, A[i - 1] >= A[i] && B[i - 1] <  B[i]
 *       4th, A[i - 1] >= A[i] && B[i - 1] >= B[i]
 * 
 *    Because It is guaranteed that the given input always makes it possible, to make both sequences strictly increasing,
 *    the above 4th is not possible.
 *    And 2nd is B[i - 1] < A[i] <= A[i - 1] < B[i] 
 *        3rd is A[i - 1] < B[i] <= B[i - 1] < A[i] 
 *    They are B[i - 1] < A[i] && A[i - 1] < B[i], meanwhile it means ok to swap(A[i - 1], B[i - 1]) or swap(A[i], B[i]) to make both squences strictly increasing
 * 
 * Solutions:
 *  1) DFS,  Time O(2^n),  Space O(1) that can be optimized to O(1) 
 *     
 *  2) DP, Time O(n),  Space O(n) that can be optimized to O(1) 
 * 
 */
public class NondecreasingArrays {
    
    int min = Integer.MAX_VALUE;
    public int minSwap_DFS(int[] A, int[] B) {
        min = Integer.MAX_VALUE; //re-init for every invoking
        
        dfs(A, B, 1, 0);
        
        if(A[1] > B[0] && B[1] > A[0]){// swap the A[0] and B[0] to make A and B are both strictly increasing. for case: [3,2,4][1,4,5]
            dfs(A, B, 2, 1);
        }
        
        return min;
    }
    
    private void dfs(int[] A, int[] B, int i, int swapTimes){
        if(swapTimes >= min){
            return;
        }

        if(i == A.length){
            min = Math.min(min, swapTimes);
            return;
        }
        
        if(A[i] > A[i - 1] && B[i] > B[i - 1]){//Till to i, A and B are both strictly increasing
            dfs(A, B, i + 1, swapTimes);    // case 0, no swap
        }
        
        if(A[i] > B[i - 1] && B[i] > A[i - 1]){//A[i] and B[i] are ok to swap. 
            swap(A, B, i);                   
            dfs(A, B, i + 1, swapTimes + 1);
            swap(A, B, i);
        }
        
    }
    
    private void swap(int[] A, int[] B, int i){
        int tmp = A[i];
        A[i] = B[i];
        B[i] = tmp;
    }
    
    /**
     * Time O(n),  Space O(n) that can be optimized to O(1) 
     * @param A
     * @param B
     * @return the minimum number of swaps to make both sequences strictly increasing
     */
    public int minSwap_DP(int[] A, int[] B) {
        if(A == null || A.length < 2){
            return 0;
        }
        
        int n = A.length;
        
        int[] keep = new int[n]; // keep[i] stores the minimum swap when keep i_th
        int[] swap = new int[n]; // swap[i] stores the mininum swap when swap i_th
        Arrays.fill(keep, Integer.MAX_VALUE);
        Arrays.fill(swap, Integer.MAX_VALUE);
        keep[0] = 0;
        swap[0] = 1;
        
        for(int i = 1; i < n; i++){
            if(A[i] > A[i - 1] && B[i] > B[i - 1]){
                keep[i] = keep[i - 1]; //to keep A[i] and B[i], only when A[i - 1] and B[i - 1] were keeped
                swap[i] = swap[i - 1] + 1; //to swap swap A[i] and B[i], only when swapped A[i - 1] and B[i - 1] were swapped 
            }
            
            if(B[i] > A[i - 1] && A[i] > B[i - 1]){
                keep[i] = Math.min(keep[i], swap[i - 1]);//to keep A[i] and B[i], only when A[i - 1] and B[i - 1] were swapped
                swap[i] = Math.min(swap[i], keep[i - 1] + 1);//to swap A[i] and B[i], only when A[i - 1] and B[i - 1] weren't swapped.
            }
        }
        
        return Math.min(keep[n-1], swap[n-1]);
    }
    
    public static void main(String[] args){
        NondecreasingArrays sv = new NondecreasingArrays();
        
        int[][][] inputs = {
            {
                {1,3,5,4},  //A
                {1,2,3,7},  //B
                {1}         //expected min swaped times
            },
            {
                {2,4,5,7,10},
                {1,3,4,5,9},
                {0}
            },
            {
                {0,7,8,10,10,11,12,13,19,18},
                {4,4,5,7,11,14,15,16,17,20},
                {4}
            },
            {
                {0,4,3,8,6,13,14,15,16,17,20,23,24,29,30,33,34,37,37,40},
                {0,1,6,5,12,7,8,9,11,13,15,23,24,26,28,33,36,35,38,39},
                {3}
            },
            {
                {3,2,4},
                {1,4,5},
                {1}
            },
            {
                {3,2,5},
                {1,4,4},
                {1}
            },
        };
        
        for (int i = 0; i < inputs.length; i++) {
            System.out.println(String.format("\n[%s]\n[%s]", Arrays.toString(inputs[i][0]), Arrays.toString(inputs[i][1])));

            System.out.println(String.format("expected:%d, \t%b", inputs[i][2][0], inputs[i][2][0] == sv.minSwap_DFS(inputs[i][0], inputs[i][1])));
            System.out.println(String.format("expected:%d, \t%b", inputs[i][2][0], inputs[i][2][0] == sv.minSwap_DP(inputs[i][0], inputs[i][1])));
        }
        

        Assert.assertEquals(1, sv.minSwap_DFS(new int[]{1,3,5,4}, new int[]{1,2,3,7}));
        Assert.assertEquals(0, sv.minSwap_DFS(new int[]{2,4,5,7,10}, new int[]{1,3,4,5,9}));

        /**
            【0,7,8,10,10,11,12,13,19,18】
            【4,4,5, 7,11,14,15,16,17,20】
         swap:  y y  y                y
         */    
        
        Assert.assertEquals(4, sv.minSwap_DFS(
                new int[]{0,7,8,10,10,11,12,13,19,18}, 
                new int[]{4,4,5,7,11,14,15,16,17,20})
        );
        Assert.assertEquals(3, sv.minSwap_DFS(
                new int[]{0,4,3,8,6,13,14,15,16,17,20,23,24,29,30,33,34,37,37,40}, 
                new int[]{0,1,6,5,12,7,8,9,11,13,15,23,24,26,28,33,36,35,38,39})
        );

        Assert.assertEquals(1, sv.minSwap_DFS(new int[]{3,2,4}, new int[]{1,4,5}));
         
        Assert.assertEquals(1, sv.minSwap_DFS(new int[]{3,2,5}, new int[]{1,4,4}));
       
       
    }
    
}
