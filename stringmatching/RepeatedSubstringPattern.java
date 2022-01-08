/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringmatching;

/**
 * _https://www.lintcode.com/problem/1227
 *
 * Given a non-empty string check if it can be constructed by taking a substring of it and appending multiple copies of 
 * the substring together. 
 * You may assume the given string consists of lowercase English letters only and its length will not exceed 10000.
 * 
 * Example 1:
 * Input: "abab" Output: True
 * Explanation: It's the substring "ab" twice.
 * 
 * Example 2:
 * Input: "aba"
 * Output: False
 * 
 * Example 3:
 * Input: "abcabcabcabc"
 * Output: True
 * Explanation: It's the substring "abc" four times. (And the substring "abcabc" twice.)
 * 
 */
public class RepeatedSubstringPattern {
    /**
     * @param s: a string
     * @return a boolean
     */
    public boolean repeatedSubstringPattern(String s) {
        if(s == null){
            return false;
        }

        int n = s.length();
        char[] arr = s.toCharArray();
        for(int i = 1; i < n; i++){
            if(n % i == 0){
                if(helper(arr, 0, i, n / i)){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean helper(char[] arr, int i, int j, int k){
        int q = j;
        for(  ; k > 1 ; k-- ){
            for(int p = i; p < j; ){
                if(arr[p++] != arr[q++]){
                    return false;
                }
            }
        }

        return true;
    }
    
    public boolean repeatedSubstringPattern_2(String s) {
        if(s == null){
            return false;
        }

        return (s + s).indexOf(s, 1) < s.length();
    }
        
}
