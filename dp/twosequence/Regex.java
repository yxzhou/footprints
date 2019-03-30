package fgafa.dp.twosequence;

/**
 * <pre>
 *
 * This class provides a method for the programmar to test whether a file matches a wildcard.
 * 
 * A "." can match any single character
 * A "*" can match zero or more of the preceding element,
 * 
 * e.g.
 * a*b match : b, ab, aab, aaaaaaaaaaab
 * b.*b match : bb, bab, b12345b
 * 
 * isMatch(“aa”,”a”)    → false
 * isMatch(“aa”,”aa”)   → true
 * isMatch(“aaa”,”aa”)  → false
 * isMatch(“aa”, “a*”)  → true
 * isMatch(“aa”, “.*”)  → true
 * isMatch(“ab”, “.*”)  → true                 ??
 * isMatch(“aab”, “c*a*b”)  → true             ??
 * isMatch(“abbbc”, “ab*c”) → true 
 * isMatch(“ac”, “ab*c”)    → true
 * isMatch(“abbc”, “ab*bbc”)    → true
 * isMatch(“abcbcd”, “a.*c.*d”) → false
 * isMatch(“a”, “ab*”)    → true
 * isMatch(“”, “b*”)    → true                 ?
 * isMatch("a", "ab*a") -> false
 * isMatch("",".*") ->  true
 * isMatch("","..*") -> true                   ?
 * isMatch("","*") ->                    ??
 * 
 * Note, "*" in regular expression is different from wildcard matching, 
 * It seems that some readers are confused about why the regex pattern “.*” matches the string “ab”. 
 * “.*” means repeat the preceding element 0 or more times. 
 * Here, the “preceding” element is the dot character in the pattern, which can match any characters. 
 * Therefore, the regex pattern “.*” allows the dot to be repeated any number of times, 
 * which matches any string (even an empty string).
 * 
 * Description:
 * @version 1.0
 */

public class Regex {
    /**
     * @param args
     */
    public static void main(String[] args) {

        // test
        System.out.println("a".substring(0));
        System.out.println("a".substring(1));
        System.out.println("a".substring(0, 1));
        // //System.out.println("a".substring(2));
        // System.out.println("b*".substring(2));
        // boolean b1 = true;
        // boolean b2 = true;
        // if(b1 == b2)
        // System.out.println("??");

        String[] t = {
                    null,
                    null,
                    "",
                    "a",
                    "a",
                    "a",
                    "aa",
                    "aa",
                    "aaa",
                    "aa",
                    "aa",
                    "ab",
                    "aab",
                    "abbbc",
                    "ac",
                    "abbc",
                    "abcbcd",
                    "a",
                    "bb",
                    "",
                    "bb",
                    "a",
                    "",
                    "bbaaaaaabbbbbabbabbaabbbbababaaabaabbababbbabbababbaba",
                    "abbabaaabbabbaababbabbbbbabbbabbbabaaaaababababbbabababaabbababaabbbbbbaaaabababbbaabbbbaabbbbababababbaabbaababaabbbababababbbbaaabbbbbabaaaabbababbbbaababaabbababbbbbababbbabaaaaaaaabbbbbaabaaababaaaabb",
                    "a*.*a*a.*aaaa", "a.*a.*a.*a.*a.*a.*a.*a.*aaaaaa"

        };
        String[] p = {
                    null,
                    "",
                    ".*",
                    ".*",
                    ".",
                    "..",
                    "a",
                    "aa",
                    "aa",
                    "a*",
                    ".*",
                    ".*",
                    "c*a*b",
                    "ab*c",
                    "ab*c",
                    "ab*bbc",
                    "a.*c.*d",
                    "ab*",
                    "a*",
                    "b*",
                    ".bab",
                    "ab*a",
                    "..*",
                    "b.*b.*ba.*a.*aaa.*a.*b.*bbaa",
                    "aa.*ba.*a.*bb.*aa.*ab.*a.*aaaaaa.*a.*aaaa.*bbabb.*b.*b.*aaaaaaaaa.*a.*ba.*bbb.*a.*ba.*bb.*bb.*a.*b.*bb",
                    "aaaa", "aaaaaaaa" };

        boolean[] ans = { true, false, true, true, true, false, false, true,
                    false, true, true, true, true, true, true, true, true,
                    true, false, true, false, false, false, false, false, false, false };
        boolean result;

        Regex sv = new Regex();

        for (int i = 0; i < t.length; i++) {
            result = isMatch(t[i], p[i]);
            System.out.print(i + "--The Text is: " + t[i]
                        + " \t\t the pattern is: " + p[i]
                        + " \n\t are they matched:" + isMatch(t[i], p[i])
                        + "\t" + sv.isMatch_dp(t[i], p[i]) + "\t"
                        + sv.isMatch_dp_x(t[i], p[i]) + "\t" + ans[i]);
            if (result != ans[i])
                System.out.println("\t??");
            else
                System.out.println(" ");
        }

    }

    /**
     * check if the pattern is match with the txt 1 If the next character of p
     * is NOT ‘*’, then it must match the current character of s. Continue
     * pattern matching with the next character of both s and p. 2 If the next
     * character of p is ‘*’, then we do a brute force exhaustive matching of 0,
     * 1, or more repeats of current character of p… Until we could not match
     * any more characters.
     * 
     * @param txt
     * @param pattern
     * @return
     * @Note It will return false, if txt or pattern is NULL.
     */

    public static boolean isMatch(String txt,
                                  String pattern) {
        if (txt == null && pattern == null){
            return true;
        }
        if (txt == null || pattern == null){
            return false; // return false when either is null
        }

        return isMatch_recursive(txt, 0, pattern, 0);

    }

    private static boolean isMatch_recursive(String txt,
                                             int i,
                                             String pattern,
                                             int j) {
        int m = txt.length(); // i
        int n = pattern.length(); // j

        if (j >= n){
            return i >= m;
        }

        char pj = pattern.charAt(j);
        if (j + 1 == n || pattern.charAt(j + 1) != '*') {
            if (i >= m || (pj != '.' && pj != txt.charAt(i))){
                return false;
            }
            return isMatch_recursive(txt, i + 1, pattern, j + 1);
        } else { // pattern.charAt(j+1)=='*'
            int k = i - 1;
            while (k < m && (k < i || pj == '.' || pj == txt.charAt(k))) {
                if (isMatch_recursive(txt, k + 1, pattern, j + 2)){
                    return true;
                }
                k++;
            }
            return false;
        }
    }

    public boolean isMatch_dp(String s, String p) {
        if (null == s) {
            return null == p;
        }
        if (null == p) {
            return false;
        }

        boolean dp[][] = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;

        for (int i = 0; i <= s.length(); i++)
            for (int j = 0; j <= p.length(); j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (j > 1 && dp[i][j - 2] && p.charAt(j - 1) == '*') { // "a" match "ab*", when * match zero the preceding element
                    dp[i][j] = true;
                }
                if (i > 0 && j > 0 && dp[i - 1][j - 1]
                            && isMatch(s.charAt(i - 1), p.charAt(j - 1))) { // ab match ab or ab match a.
                    dp[i][j] = true;
                }
                if (i > 0 && j > 1 && dp[i - 1][j] && p.charAt(j - 1) == '*'
                            && isMatch(s.charAt(i - 1), p.charAt(j - 2))) { // * repeat p.charAt(j - 2)
                    dp[i][j] = true;
                }
            }

        return dp[s.length()][p.length()];
    }

    private boolean isMatch(char a,
                            char b) {
        return a == b || b == '.';
    }

    public boolean isMatch_dp_x(String s, String p) {
        if(null == s){
            return null == p;
        }

        if(null == p){
            return false;
        }

        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;

        for(int i = 0; i <= s.length(); i++){
            for(int j = 0; j <= p.length();j++){
                if(i == 0 && j == 0){
                    continue;
                }

                if(j > 1 && p.charAt(j - 1) == '*'){
                    dp[i][j] = dp[i][j - 2];
                }
                if(i > 0 && j > 0 && (p.charAt(j - 1) == s.charAt(i - 1) || p.charAt(j - 1) == '.' )){
                    dp[i][j] = dp[i - 1][j - 1] ? true : dp[i][j];
                }
                if(j > 1 && p.charAt(j - 1) == '*' && i > 0 && ( p.charAt(j - 2) == s.charAt(i - 1) || p.charAt(j - 2) == '.' )){
                    dp[i][j] = dp[i - 1][j] ? true : dp[i][j];
                }
            }
        }

        return dp[s.length()][p.length()];
    }

    /**
     * @param s: A string
     * @param p: A string includes "." and "*"
     * @return: A boolean
     */
    public boolean isMatch_dp_n(String s, String p) {

        if (null == s) {
            return null == p;
        }
        if (null == p) {
            return false;
        }

        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;

        for (int j = 2; j <= p.length(); j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            } else {
                break;
            }
        }
        
        for (int i = 0; i <= s.length(); i++) {
            for (int j = 1; j <= p.length(); j++) {
                if (j > 1 && '*' == p.charAt(j - 1)) {
                    dp[i][j] = dp[i][j - 2] || (i > 0 && dp[i - 1][j] && isMatch(s.charAt(i - 1), p.charAt(j - 2)));
                } else {
                    dp[i][j] = i > 0 && dp[i - 1][j - 1] && isMatch(s.charAt(i - 1), p.charAt(j - 1));
                }
            }
        }

        return dp[s.length()][p.length()];
    }

}
