package fgafa.game.palindrome;

//import fgafa.util.Misc;

/**
 *  Q: Given a string S, find the longest palindromic substring in S.
 *
 *  Palindrome: 
 *  A palindrome is a word, phrase, number, or other sequence of units that may be read the same way in either direction.  
 *  example "abcba" and "1234321" is a palindome. "abc" is not.
 *
 *  Solution11: Brute force, Time O(N^3), Space O(1)  
 *      The obvious brute force is to pick all possible starting and ending positions for a substring. 
 *      and verify if it's a palindrome, There are a total of C(N, 2) such substrings. 
 *      And verifying each substring takes O(N).
 *
 *  Solution12: DP, Time O(N^2), Space O(N^2) 
 *      Define dp[i, j] = true, if the substring S[i, j] is a palindrome. 
 *      The recurrence relation
 *      dp[i][j] = true,  when i == j,  one character is palindromic.  
 *               = true,  when i+1 = j, and s[i] == s[j]
 *               = true,  when dp[i+1][j-1] = truee and s[i] = s[j]    
 *      the max dp means the largest palindrome.
 *
 *  Solution21: Simple approach.  Time O(N^2), Space O(1) 
 *      There are total (2n-1) center, to find the largest palindrome to every center, it takes O(N)  
 *
 *  S22: Manacher's ALGORITHM, Time O(N), Space O(N) 
 *      1. Transform the input string, e.g.  "abaaba" -> "#a#b#a#a#b#a#"
 *      2. Define p[i] equals to the length of the palindrome centers at s[i]
 *
 * @author yxzhou
 *
 */

public class LargestPalindromic {

    /*
     * get Largest Palindromic Substring
     * Define n as the length of input. There are total (2n - 1) center.
     * To find the largest palindrome to every center, it takes O(N)
     *
     * Time O(n^2)  Space O(1)
     */
    public static String largetPalindrome_21(String word) {
        //check if str is null or str.length < 2, return itself
        if (word == null) {
            return "";
        }
    
    /*check the (2n-1) center, get the largest palindrome to every center, 
    the max of them is what we want */
        String largetP = "";
        String currMaxP = "";
        for (int pivot = 0; pivot < word.length(); pivot++) {

            currMaxP = largetPalindrome(word, pivot, pivot);
            if (currMaxP.length() > largetP.length()) {
                largetP = currMaxP;
            }

            currMaxP = largetPalindrome(word, pivot, pivot + 1);
            if (currMaxP.length() > largetP.length()) {
                largetP = currMaxP;
            }

        }

        return largetP;
    }

    private static String largetPalindrome(String word, int left, int right) {
        if (left > right) {
            throw new IllegalArgumentException("The left should not be greater than the right");
        }

        while (left > -1 && right < word.length() && word.charAt(left) == word.charAt(right)) {
            left--;
            right++;
        }

        return word.substring(left + 1, right);
    }

    /**
     * get Largest Palindromic Substring
     *
     * Manacher’s Algorithm ---
     *
     * e.g.
     * id:        0--1--2--3--4--5--6--7--8--9-10-11-12-13-14-15-16-17-18-19-20
     * Origin:    12212321
     * New:       ^  #  w  #  a  #  a  #  b  #  w  #  s  #  w  #  f  #  d  #  $
     * p[]:          1  2  1  2  3  2  1  2  1  2  1  4  1  2  1  2  1  2  1
     *
     * output:    12321
     *
     * Time O(n)  Space O(n)
     */
    public static String largetPalindrome_Manacher(String str) {
        //check
        if (str == null)
            return "";
        if (str.length() < 2)
            return str;

        //preprocess
        char[] newStr = preProcess(str);
        int length = newStr.length;

        int[] p = new int[length];  //p[i] equals to the length of the palindrome centers at newStr[i].
        int pivot = 0, rightBorder = 0; //

        //main
        for (int i = 1; i < length - 1; i++) {
            if (rightBorder > i)
                p[i] = Math.min(rightBorder - i, p[(pivot << 1) - i]); //int i_mirror = pivot - (i - pivot);
            else
                p[i] = 1;    // # + itself + #

            while (newStr[i + p[i]] == newStr[i - p[i]])  //the ^ and $ will void bound checking
                p[i]++;

            if (p[i] + i > rightBorder) {
                rightBorder = p[i] + i;
                pivot = i;
            }
        }

        //find the max element in p[]
        int maxLen = 0, centerIndex = 0;  //it's based on the strN instead of original str.
        for (int i = 1; i < length - 1; i++) {  //exclude the ends
            if (p[i] > maxLen) {
                maxLen = p[i];
                centerIndex = i;
            }
        }

        return str.substring((centerIndex - maxLen) / 2, (centerIndex + maxLen) / 2 - 1);
    }

    /*transform s into T, 
     *e.g.  s="abba",  T="^#a#b#b#a#$"  
     *( ^ and $ sign are sentinels appended to each end to avoid bound checking )      
     */
    private static char[] preProcess(String str) {
        int n = str.length();

        char[] ret = new char[(n << 1) + 3];
        int k = 0;
        ret[k] = '^';

        for (int i = 0; i < n; i++) {
            ret[++k] = '#';
            ret[++k] = str.charAt(i);
        }
        ret[++k] = '#';
        ret[++k] = '$';

        return ret;
    }

    public static void main(String[] args) {
        String[] ss = { null, "", "a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa", "ab", "aba", "abba", "12212321",
                "waabwswfd", "gabaabaf", "gababaf" };

        for (int i = 0; i < ss.length; i++) {
            System.out.println("\n " + i + "input: " + ss[i]);
            System.out.println(
                    " the longest palindormic substring of " + ss[i] + " is: " + largetPalindrome_Manacher(ss[i]));
            System.out.println(" the longest palindormic substring of " + ss[i] + " is: " + largetPalindrome_21(ss[i]));
        }

    }

}
