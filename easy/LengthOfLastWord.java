package easy;

import java.util.StringTokenizer;

/**
 * Given a string s consists of upper/lower-case alphabets and empty space
 * characters ' ', return the length of last word in the string. If the last
 * word does not exist, return 0. Note: A word is defined as a character
 * sequence consists of non-space characters only. 
 * 
 * For example, 
 * Given s = "Hello World", return 5.
 */

public class LengthOfLastWord {

    public int lengthOfLastWord(String s) {
        int result = 0;

        if (s == null || s.length() == 0) {
            return result;
        }

        StringTokenizer st = new StringTokenizer(s);
        String token = "";
        while (st.hasMoreTokens()) {
            token = st.nextToken();
        }

        return token.length();
    }

    public int lengthOfLastWord2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int end = s.length() - 1;
        // while(end > -1 && s.charAt( end ) == ' ') end --;
        for (; end > -1; end--) {
            if (s.charAt(end) != ' ') {
                break;
            }
        }

        int i = end;
        // while(i > -1 && s.charAt( i ) != ' ') i --;
        for (; i > -1; i--) {
            if (s.charAt(i) == ' ') {
                break;
            }
        }

        return end - i;
    }

    public int lengthOfLastWord_2(String s) {
        if (null == s)
            return 0;
        s = s.trim();
        return Math.min(s.length(), s.length() - 1 - s.lastIndexOf(' '));
    }

    public int lengthOfLastWord_3(String s) {
        if (null == s)
            return 0;

        int lastWordEnd = s.length() - 1;
        for (; lastWordEnd >= 0 && s.charAt(lastWordEnd) == ' '; lastWordEnd--)
            ;
        
        int lastSpaceIndex = lastWordEnd;
        for (; lastSpaceIndex >= 0 && s.charAt(lastSpaceIndex) != ' '; lastSpaceIndex--)
            ;

        return lastWordEnd - lastSpaceIndex;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        LengthOfLastWord sv = new LengthOfLastWord();

        String[] input = { null, " ", "  ", "last word", "last word ", "word",
                    "word  " };
        for (int i = 0; i < input.length; i++) {
            System.out.println(i + " input: " + input[i]);
            
            System.out.println("lengthOfLastWord_2: " + sv.lengthOfLastWord_2(input[i]));
            
            System.out.println("lengthOfLastWord_3: " + sv.lengthOfLastWord_3(input[i]));
        }
    }

}
