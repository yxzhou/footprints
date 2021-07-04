package fgafa.game.palindrome;

/**
 *  Palindrome:
 *  A palindrome is a word, phrase, number, or other sequence of units that may be read the same way in either direction.
 *  example "abcba" , "1234321",
 *
 *  Problem #1:
 *    Given a string, determine if it is a palindrome
 *    Examples:
 *    Input "abcba"  Output True
 *    Input "ab"     Output False
 *
 *    Furthermore:
 *    Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
 *    For example,
 *    "A man, a plan, a canal: Panama" is a palindrome.
 *    "race a car" is not a palindrome.
 *
 *  Problem #2,
 *    Given a positive number, Check if it is a palindrome or not.
 *    _https://www.lintcode.com/problem/491/?_from=ladder&fromId=190
 *
 *    Example 1:
 *    Input:11   Output:true
 *
 *    Example 2:
 *    Input:1232  Output:false
 *    Explanation:  1232!=2321
 *
 *    Notes:
 *    It's guaranteed the input number is a 32-bit integer, but after reversion, the number may exceed the 32-bit integer.
 *
 *  Problem #3,
 *    Give a a non-negative integer n, determines whether a binary representation of n is a palindrome
 *
 *    Example1
 *    Input: n = 0  Output: True
 *    Explanation:  The binary representation of 0 is: 0
 *
 *    Example2
 *    Input: n = 3  Output: True
 *    Explanation:  The binary representation of 3 is: 11
 *
 *    Example3
 *    Input: n = 4  Output: False
 *    Explanation:  The binary representation of 4 is: 100
 *
 *    Example4
 *    Input: n = 6  Output: False
 *    Explanation:  The binary representation of 6 is: 110
 *
 *
 *
 */

public class Palindromic {
    public static void main(String[] args) {
        //int input = Integer.parseInt(args[0]);

        Palindromic sv = new Palindromic();

        System.out.println();

        String[] s1 = {"abcbd", "aba", "aa", "abeba"};
        for (int i = 0; i < s1.length; i++) {
            System.out.println(s1[i] + " is a palindromic string: " + isPalindrome(s1[i]));
        }

        System.out.println();

        String[] s2 = {"A man, a plan, a canal: Panama", "race a ca"};
        for (int i = 0; i < s2.length; i++) {
            System.out.println(s2[i] + " is a palindromic string: " + sv.isPalindrome_II(s2[i]));
        }

        System.out.println();

        int[] n = {-1, 10, -10, 1, 2, 3, 4, 5, 11, 12};
        for (int i = 0; i < n.length; i++) {
            //System.out.println((new StringBuilder().append(x[i])).reverse().toString());
            System.out.println(n[i] + " is a palindromic number: " + isPalindrome(n[i]));
        }

    }


    /**
     * Problem #1, determine whether a string is a palindrome.
     */
    public static boolean isPalindrome(String s) {
        return s.equals((new StringBuilder().append(s)).reverse().toString());
    }

    public static boolean isPalindrome_2(String s){
        int n = s.length();
        for (int  i = 0, j = n - 1; i < j; i++, j--) {
            if(s.charAt(i) != s.charAt(j)){
                return false;
            }
        }

        return true;
    }

    /**
     * Problem #1, Furthermore,
     * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
     *
     */
    public boolean isPalindrome_II(String s) {
        if (null == s || 0 == s.length()) {
            return true;
        }

        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            while (i < j && !isAlphanumeric(s.charAt(i))) {
                i++;
            }
            while (i < j && !isAlphanumeric(s.charAt(j))) {
                j--;
            }

            int diff = s.charAt(i) - s.charAt(j);
            if (0 != diff && 32 != Math.abs(diff)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Problem #2, determine whether an integer is a palindrome. Do this without extra space.
     *
     */
    public static boolean isPalindrome(int x) {
        return String.valueOf(x).equals((new StringBuilder().append(x)).reverse().toString());

        /* the following is not good because overflow (when x is Integer.MAX_VALUE, Integer.MIN_VALUE) */
        //return x == Integer.valueOf((new StringBuilder().append(x)).reverse().toString());
    }

    /**
     * Problem #3, determines whether a binary representation of a non-negative n is a palindrome.
     *
     */
    public boolean isPalindrome_p3(int n) {
        String s = Integer.toBinaryString(n);
        return new StringBuilder(s).reverse().toString().equals(s);
    }
    public boolean isPalindrome_p3_2(int n) {
        String s = Integer.toBinaryString(n);

        for(int i = 0, j = s.length() - 1; i < j; i++, j--){
            if(s.charAt(i) != s.charAt(j)){
                return false;
            }
        }

        return true;
    }

    /** fundermental test */
    private boolean isNumeric(char c) {
        return (c >= 0x30 && c <= 0x39);
    }

    private boolean isAlpha(char c) {
        return (c >= 0x41 && c <= 0x5a) || (c >= 0x61 && c <= 0x71);
    }

    private boolean isAlphanumeric(char c) {
        //return (c >= '0' && c <= '9') || ((c >= 'A') && c <= 'Z') || (c >= 'a' && c <= 'z');
        //return (c >= 0x30 && c <= 0x39) || (c >= 0x41 && c <= 0x5a) || (c >= 0x61 && c <= 0x7a);
        return (c > 47 && c < 58) || (c > 64 && c < 91) || (c >= 96 && c <= 123);                       //best
    }

    private boolean isAlphanumber_n(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isEqual_n(char c1, char c2) {
        return Character.toLowerCase(c1) == Character.toLowerCase(c2);
    }


}
