/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dfsbfs.permutationAndCombination;

/**
 * _https://www.lintcode.com/problem/884
 *
 * By now, you are given a secret signature consisting of character 'D' and 'I'. 'D' represents a decreasing
 * relationship between two numbers, 'I' represents an increasing relationship between two numbers. And our secret
 * signature was constructed by a special integer array, which contains uniquely all the different number from 1 to n (n
 * is the length of the secret signature plus 1). For example, the secret signature "DI" can be constructed by array
 * [2,1,3] or [3,1,2], but won't be constructed by array [3,2,4] or [2,1,3,4], which are both illegal constructing
 * special string that can't represent the "DI" secret signature.
 *
 * On the other hand, now your job is to find the lexicographically smallest permutation of [1, 2, ... n] could refer to
 * the given secret signature in the input.
 *
 * Constraints:
 *   The input string will only contain the character 'D' and 'I'. 
 *   The length of input string is a positive integer and will not exceed 10,000.
 *
 * Example 1: 
 * Input: str = "DI" 
 * Output: [2,1,3]
 *
 * Example 2: 
 * Input: str = "I" 
 * Output: [1,2]
 *
 * Thoughts:
 *   Define n as the length of s. The smallest permutation is [1, 2, ..., n]. 
 *   To find the lexicographically smallest one, it can start from [1, 2, ..., n], whose default meet 'I', we just need 
 *   pay attention to 'D'. 
 *
 *   
 * 
 */
public class FindPermutation {
    /**
     * @param s: a string
     * @return the lexicographically smallest permutation
     */
    public int[] findPermutation(String s) {
        if(s == null){
            return new int[]{1};
        }

        int n = s.length();
        int[] result = new int[n + 1];

        for(int i = 0; i <= n; i++){
            result[i] = i + 1;
        }

        for(int i = 0, j; i < n; i++){
        
            for( j = i; j < n && s.charAt(j) == 'D'; j++ );

            if(i < j){
                reverse(result, i, j);

                i = j;
            }

        }

        return result;
    }

    private void reverse(int[] arr, int l, int r){
        int tmp;
        for( ; l < r; l++, r--){
            tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
        }
    }
    
    public int[] findPermutation_n(String s) {
        if (s == null) {
            return new int[]{1};
        }
        
        int n = s.length();
        int[] result = new int[n + 1];

        for(int i = 0, j, k; i < n; i++){
        
            for( j = i; j < n && s.charAt(j) == 'D'; j++ );

            if(i == j){
                result[i] = i + 1;
            }else{
                for( k = j + 1 ; i <= j; i++, k--){
                    result[i] = k;
                }

                i = j;
            }
        }

        if(result[n] == 0){
            result[n] = n + 1;
        }

        return result;
    }
}
