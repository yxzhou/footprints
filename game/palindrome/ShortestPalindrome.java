package game.palindrome;

/**
 *
 * Given a string S, you are allowed to convert it to a palindrome by adding
 * characters in front of it. Find and return the shortest palindrome you can
 * find by performing this transformation.
 *
 * For example:
 *
 * Given "aacecaaa", return "aaacecaaa".
 *
 * Given "abcd", return "dcbabcd".
 * 
 * 
 * 
 */
public class ShortestPalindrome {
     
    /*brute force,  Time O(n^2)*/
    public String shortestPalindrome(String s) {
        if (null == s || 0 == s.length()) {
            return s;
        }

        //main
        int end = s.length() - 1;
        for (; end > 0; end--) {
            if (isPalindrome(s, 0, end)) {
                break;
            }
        }

        //return
        StringBuilder sb = new StringBuilder(s.length() * 2 - end - 1);
        for (int i = s.length() - 1; i > end; i--) {
            sb.append(s.charAt(i));
        }
        sb.append(s);
        return sb.toString();
    }

    private boolean isPalindrome(String s, int start, int end) {
        if (start >= 0 && end < s.length() && start <= end) {
            for (; start < end; start++, end--) {
                if (s.charAt(start) != s.charAt(end)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    /*KMP   Time O(n)*/
    public String shortestPalindrome_x(String s) {
        StringBuilder r = new StringBuilder(s);
        r.reverse();
        StringBuilder t = new StringBuilder(s);
        t.append('#');
        t.append(r);

        int[] p = new int[t.length()]; //default all are 0
        for (int i = 1; i < t.length(); ++i) {
            int j = p[i - 1];
            while (j > 0 && t.charAt(i) != t.charAt(j)) {
                j = p[j - 1];
            }

            if (t.charAt(i) == t.charAt(j)) {
                j++;
            }

            p[i] = j;

        }
        return r.substring(0, s.length() - p[t.length() - 1]) + s;
    }

    public static void main(String[] args) {
        ShortestPalindrome sv = new ShortestPalindrome();
        String[] input = {"aacecaaa", "abcd"};

        for (String s : input) {
            System.out.println(s + " => " + sv.shortestPalindrome(s));
        }
    }
}
