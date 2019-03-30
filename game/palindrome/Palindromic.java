package fgafa.game.palindrome;

/*
 *  Palindrome:
 *  A palindrome is a word, phrase, number, or other sequence of units that may be read the same way in either direction.
 *  example "abcba" , "1234321",
 *
 * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
 *
 * For example,
 *
 * "A man, a plan, a canal: Panama" is a palindrome.
 * "race a car" is not a palindrome.
 *
 */

public class Palindromic {
    public static void main(String[] args) {
        //int input = Integer.parseInt(args[0]);

        Palindromic sv = new Palindromic();

        System.out.println();

        String[] s1 = {"abcbd", "aba", "aa", "abeba"};
        for (int i = 0; i < s1.length; i++) {
            System.out.println(s1[i] + " is a palindormic number: " + isPalindrome(s1[i]));
        }

        System.out.println();

        String[] s2 = {"A man, a plan, a canal: Panama", "race a ca"};
        for (int i = 0; i < s2.length; i++) {
            System.out.println(s2[i] + " is a palindormic number: " + sv.isPalindrome_x(s2[i]));
        }

        System.out.println();
        /* fundmental test */
        int[] x = {-1, 10, -10};
        for (int i = 0; i < x.length; i++) {
            System.out.println((new StringBuilder().append(x[i])).reverse().toString());
        }

    }


    /**
     * check if s1 is a palindrome <br>
     * A palindrome is a word, phrase, number, or other sequence of units that
     * may be read the same way in either direction.
     */

    public static boolean isPalindrome(String s1) {
        return s1.equals((new StringBuilder().append(s1)).reverse().toString());
    
    /*
    int i, l;
    char ch;
    String s2 = "";
    
    l = s1.length();

    for (i = 0; i < l; i++) {
      ch = s1.charAt(i);
      s2 = ch + s2;
    }
    
    if (s1.equals(s2))
      return true;
    else
      return false;
      */
    }

    /**
     * Determine whether an integer is a palindrome. Do this without extra space.
     *
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        return String.valueOf(x).equals((new StringBuilder().append(x)).reverse().toString());

        /* the following is not good because overflow (when x is Integer.MAX_VALUE, Integer.MIN_VALUE) */
        //return x == Integer.valueOf((new StringBuilder().append(x)).reverse().toString());

    }

    /**
     * check if s1 is a palindrome <br>
     * Given a string, determine
     * if it is a palindrome, considering only alphanumeric characters and
     * ignoring cases.
     * <p>
     * For example, "A man, a plan, a canal: Panama" is a palindrome.
     * "race a car" is not a palindrome.
     */
    public boolean isPalindrome_x(String s) {
        if (null == s || 0 == s.length()) {
            return true;
        }

        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            while (i < j && !isAlphanumeric(s.charAt(i))) i++;
            while (i < j && !isAlphanumeric(s.charAt(j))) j--;

            int diff = s.charAt(i) - s.charAt(j);
            if (0 != diff && 32 != Math.abs(diff)) {
                return false;
            }
        }

        return true;
    }

    public boolean isPalindrome_x2(String s) {
        if (null == s || 0 == s.length()) {
            return true;
        }

        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            while (i < j && !Character.isLetterOrDigit(s.charAt(i))) {
                i++;
            }
            while (i < j && !Character.isLetterOrDigit(s.charAt(j))) {
                j--;
            }

            if (i < j && Character.toLowerCase(s.charAt(i)) == Character.toLowerCase(s.charAt(j))) {
                return false;
            }
        }

        return true;
    }

    private boolean isNumeric(char c) {
        return (c >= 0x30 && c <= 0x39);
    }

    private boolean isAlpha(char c) {
        return (c >= 0x41 && c <= 0x5a) || (c >= 0x61 && c <= 0x71);
    }

    private boolean isAlphanumeric(char c) {
        //return (c >= '0' && c <= '9') || ((c >= 'A') && c <= 'Z') || (c >= 'a' && c <= 'z');
        return (c >= 0x30 && c <= 0x39) || (c >= 0x41 && c <= 0x5a) || (c >= 0x61 && c <= 0x7a);
        //return (c > 47 and c < 58) || (c > 64 && c < 91) || (c >= 96 && c <= 123);
    }

    private boolean isAlphanumber_n(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isEqual_n(char c1, char c2) {
        return Character.toLowerCase(c1) == Character.toLowerCase(c2);
    }


}
