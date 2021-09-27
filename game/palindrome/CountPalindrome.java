package game.palindrome;

/**
 * _https://www.lintcode.com/problem/837/description?_from=ladder&fromId=29
 * _https://leetcode.com/problems/palindromic-substrings/
 *
 * Given a string, your task is to count how many palindromic substrings in this string.
 *
 * The substrings with different start indexes or end indexes are counted as different substrings even they consist of same characters.
 *
 * Example 1:
 * Input: "abc"
 * Output: 3
 * Explanation: Three palindromic strings: "a", "b", "c".
 *
 *
 * Example 2:
 * Input: "aaa"
 * Output: 6
 * Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
 *
 *
 * Note:
 * The input string length won't exceed 1000.
 *
 *
 */

public class CountPalindrome {

    public int countSubstrings(String s) {
        if(null == s ){
            return 0;
        }


        char[] chars = s.toCharArray();
        int length = chars.length;
        int count = length;
        for(int p = 1; p < length; p++){
            for(int i = p - 1, j = p + 1; i >= 0 && j < length && chars[i] == chars[j]; i--, j++){
                count++;
            }

            for(int i = p - 1, j = p; i >= 0 && j < length && chars[i] == chars[j]; i--, j++){
                count++;
            }
        }

        return count;
    }

    public int countSubstrings_n(String s) {
        if(null == s ){
            return 0;
        }

        int length = s.length();

        int count = length;

        for(int p = 1, end = length * 2 - 1; p < end; p++){
            for(int i = (p >> 1) - 1, j = i + 1 + (p & 1); i >= 0 && j < length && s.charAt(i) == s.charAt(j); i--, j++){
                count++;
            }
        }


        return count;
    }

}
