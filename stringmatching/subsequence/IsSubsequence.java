/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stringmatching.subsequence;

/**
 * _https://www.lintcode.com/problem/1263
 *
 * Given a string s and a string t, check if s is subsequence of t.
 *
 * You may assume that there is only lower case English letters in both s and t. t is potentially a very long (length ~=
 * 500,000) string, and s is a short string (length <= 100).
 *
 * A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (ie, "ace" is a subsequence of "abcde" while "aec" is not).
 *
 *
 * Example 1:
 * Input: s = "abc", t = "ahbgdc"
 * Output: true
 * 
 * Example 2:
 * Input: s = `"axc"`, t = `"ahbgdc"`
 * Output: false
 * 
 * 
 * Challenge
 * If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B, and you want to check one by one to see if T has
 * its subsequence. In this scenario, how would you change your code?
 *   <NumberOfSubsequence and LongestSubsequence>
 *
 */
public class IsSubsequence {
    
    /**
     * define n as the length of s, m as the length of t.
     * Time O(n + m)
     * 
     * @param s: the given string s
     * @param t: the given string t
     * @return check if s is subsequence of t
     */
    public boolean isSubsequence(String s, String t) {
        if(s == null || t == null || s.length() > t.length()){
            return false;
        }

        int i = 0;
        for(int j = 0; j < t.length() && i < s.length(); j++){
            if(s.charAt(i) == t.charAt(j)){
                i++;
            }
        }

        return i == s.length();
    }
    
}
