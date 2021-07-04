package fgafa.game.palindrome;

/**
 * _https://www.lintcode.com/problem/891/?_from=ladder&fromId=29
 * _https://leetcode.com/problems/valid-palindrome-ii/
 *
 * Valid Palindrome II
 *
 * Description
 * Given a non-empty string s, you may delete at most one character. Judge whether you can make it a palindrome.
 *
 * The string will only contain lowercase characters.
 * The maximum length of the string is 50000.
 *
 * Example
 * Example 1:
 * Input: s = "aba"
 * Output: true
 * Explanation: Originally a palindrome.
 *
 * Example 2:
 * Input: s = "abca"
 * Output: true
 * Explanation: Delete 'b' or 'c'.
 *
 * Example 3:
 * Input: s = "abc"
 * Output: false
 * Explanation: Deleting any letter can not make it a palindrome.
 *
 */

public class PalindromicII {

    /**
     * @param s: a string
     * @return: whether you can make s a palindrome by deleting at most one character
     */
    public boolean validPalindrome(String s) {
        char[] cs = s.toCharArray();
        for(int l = 0, r = cs.length - 1; l < r; l++, r--){
            if(cs[l] != cs[r]){
                return validPalindrome(cs, l + 1, r) || validPalindrome(cs, l, r - 1);
            }
        }

        return true;
    }

    private boolean validPalindrome(char[] cs, int l, int r){
        while(l < r){
            if(cs[l] != cs[r]){
                return false;
            }

            l++;
            r--;
        }
        return true;
    }

}
