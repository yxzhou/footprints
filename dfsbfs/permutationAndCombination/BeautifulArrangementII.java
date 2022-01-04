/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfsbfs.permutationAndCombination;

/**
 * _https://www.lintcode.com/problem/992
 * 
 * Given two integers n and k, you need to construct a list which contains n different positive integers ranging from 1 
 * to n and obeys the following requirement: Suppose this list is [a1, a2, a3, ... , an], 
 * then the list [|a1 - a2|, |a2- a3|, |a3 - a4|, ... , |an-1 - an|] has exactly k distinct integers.
 *
 * Note:
 *   If there are multiple answers, print any of them.
 *   The n and k are in the range 1 <= k < n <= 10^4. 
 * 
 * Example1: Input: n = 3, k = 1 Output: [1, 2, 3] 
 * Explanation: The different in [1, 2, 3] is [1, 1] has exactly 1 distinct integer: 1. 
 * 
 * Example2: Input: n = 3, k = 2 Output: [1, 3, 2] 
 * Explanation: The different in [1, 3, 2] is [2, 1], it exactly 2 distinct integers: 1 and 2.
 * 
 */
public class BeautifulArrangementII {
    /**
     * one beautiful arrangement is: 1, 2, 3, ... , n - k, n, n - k + 1, n - 1, n - k + 2, n - 2, ...
     * 
     * @param n: the number of integers
     * @param k: the number of distinct integers
     * @return any of answers meet the requirement
     */
    public int[] constructArray(int n, int k) {
        int[] result = new int[n];
        
        int p = 0;
        int m = n - k + 1;
        
        for(int i = 1; i < m; i++ ){
            result[p++] = i;
        }
    
        boolean flag = true;
        for(int i = k; i > 0; i--){
            result[p] = result[p - 1] + (flag? i : -i);
            p++;
            flag ^= true;
        }
        
        return result;
    }
    
}
