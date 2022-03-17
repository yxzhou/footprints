package dp.twosequence;


import junit.framework.Assert;


/**
 * <pre>_https://www.lintcode.com/problem/192
 *
 * Implement wildcard pattern matching with support for '?' and '*'.The matching rules are as follows：
 *   '?' Matches any single character.
 *   '*' Matches any sequence of characters (including the empty sequence).
 * The matching should cover the entire input string (not partial).
 *
 * Notes:
 *  0 <= |s|, |p| <= 1000
 *  It is guaranteed that 𝑠 only contains lowercase Latin letters and p contains lowercase Latin letters , ? and *
 * 
 * Example 
 * "abc"    &   "a*"        matches
 * "abc"    &   "*"         matches
 * "abc"    &   "b*"        doesn't match
 * "abcd"   &   "a*d"       matches
 * "abcd"   &   "a??d"      matches
 * <more see the testcases in main()>
 *
 * 
 * 
 * 
 */

public class WildcardMatching {

    /**
     *  Time Limitation Exceed
     */
    public boolean isMatch_recursive(String txt, int i, String pattern, int j) {

        int m = txt.length(); // i
        int n = pattern.length(); // j

        if ((i >= m && j >= n) || (j == n - 1 && pattern.charAt(j) == '*')) {
            return true;
        }
        if ((i >= m && j < n) || (i < m && j >= n)) {
            return false;
        }

        char pj = pattern.charAt(j);
        if (pj != '*') {
            return (pj == txt.charAt(i) || pj == '?')
                    && isMatch_recursive(txt, i + 1, pattern, j + 1);
        }

        int k = i;
        while (k < m) {
            if (isMatch_recursive(txt, k, pattern, j + 1)) {
                return true;
            }

            k++;
        }

        return false;
    }

        
    /**
     * @param s: A string
     * @param p: A string includes "?" and "*"
     * @return: A boolean
     */
    public boolean isMatch_dp_1(String s, String p) {
        if(s == null && p == null ){ 
            return true;
        }else if(s == null || p == null){
            return false;
        }

        boolean[][] isMatch = new boolean[s.length() + 1][p.length() + 1]; //default all are false
        isMatch[0][0] = true;

        for (int i = 0; i <= s.length(); i++) {
            for (int j = 0; j <= p.length(); j++) {

                if (i > 0 && j > 0 && isMatch[i - 1][j - 1] && 
                        (s.charAt(i - 1) == p.charAt(j - 1) || '?' == p.charAt(j - 1) )) { //It's equal or ?
                    isMatch[i][j] = true;
                }else if (j > 0 && isMatch[i][j - 1] && '*' == p.charAt(j - 1)) { //It's *, and * match the empty 
                    isMatch[i][j] = true;
                }else if (i > 0 && j > 0 && isMatch[i - 1][j] && '*' == p.charAt(j - 1)) { //It's *, and * match any sequence of characters
                    isMatch[i][j] = true;
                }

            }
        }

        return isMatch[s.length()][p.length()];
    }

    /**
     * Define n as the length of s, m of the length of p, 
     * Time Complexity O( n * m ), Space: O(n * m)
     *
     * @param s: A string 
     * @param p: A string includes "?" and "*"
     * @return true if s and p is WildcardMatching
     */
    public boolean isMatch_dp_n(String s, String p) {
        if(s == null && p == null ){ 
            return true;
        }else if(s == null || p == null){
            return false;
        }

        int n = s.length();
        int m = p.length();

        boolean[][] isMatch = new boolean[n + 1][m + 1];
        isMatch[0][0] = true;

        for(int j = 0; j < m; j++){
            if('*' == p.charAt(j) ){
                isMatch[0][j + 1] = true;
            }else{
                break;
            }
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(s.charAt(i) == p.charAt(j) || '?' == p.charAt(j)){
                    isMatch[i + 1][j + 1] = isMatch[i][j];
                } else if( '*' == p.charAt(j) ) {
                    isMatch[i + 1][j + 1] = isMatch[i + 1][j] || isMatch[i][j + 1] ; //empty sequence or any sequence
                }  
            }
        }

        return isMatch[n][m];
    }

 

    public static void main(String[] args) {
        /* fundmental testing */
        System.out.println("ab".substring(2).equals(""));
        //System.out.println("a".substring(2));

        String[][] inputs = {
            // { s, p, expect }
            { null, null, "true" },
            { null, "", "false" },
            { "aa", "a", "false" },
            { "aa", "aa", "true" },
            { "aaa", "aa", "false" },
            { "aa", "*", "true" },
            { "abc", "*c", "true" },
            { "abc", "a*", "true" },
            { "aab", "c*a*b", "false" },
            { "", "*", "true" },
            { "a", "a*", "true" },
            { "ab", "ab**", "true" },
            { "ac", "ab*c", "false" },
            { "abcd", "a*d", "true" },
            { "abcd", "a??d", "true" },
            { "ab", "?*", "true" },
            { "aa", "?*",  "true" },
            { "abc", "*?",  "true" },
            { "c", "*?*", "true" },
            { "abcbcd", "a?*c?*d", "true" },
            { "aaabbaabbaab", "*aabbaa*a*", "true" },
            { "screeeewywxd", "scr*w?d", "true" },
            {
                "bbaaaaaabbbbbabbabbaabbbbababaaabaabbababbbabbababbaba",
                "b*b*ba**a*aaa*a*b**bbaa",
                "false"
            },
            {
                "abbabaaabbabbaababbabbbbbabbbabbbabaaaaababababbbabababaabbababaabbbbbbaaaabababbbaabbbbaabbbbababababbaabbaababaabbbababababbbbaaabbbbbabaaaabbababbbbaababaabbababbbbbababbbabaaaaaaaabbbbbaabaaababaaaabb",
                "**aa*****ba*a*bb**aa*ab****a*aaaaaa***a*aaaa**bbabb*b*b**aaaaaaaaa*a********ba*bbb***a*ba*bb*bb**a*b*bb",
                "false"
            }
        };
        
        WildcardMatching sv = new WildcardMatching();
                
        for(String[] input : inputs){
            System.out.println(String.format("\ns: %s\np: %s", input[0], input[1]));
            
            Assert.assertEquals(input[2], sv.isMatch_dp_n(input[0], input[1])? "true" : "false" );
            
            Assert.assertEquals(input[2], sv.isMatch_dp_1(input[0], input[1])? "true" : "false" );
        }


    }
}
