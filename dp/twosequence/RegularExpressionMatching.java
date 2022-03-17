package dp.twosequence;

import junit.framework.Assert;

/**
 * Regex Matching
 * _https://www.lintcode.com/problem/154
 *
 * The implementation supports regular expression matching for '.' and '*'. 
 *   '.' matches any single character. 
 *   '*' matches zero or more of the preceding elements, 
 * before '*' is guaranteed to be a non-'*' element. 
 * The match should cover the entire input string, not just a part of it. 
 * 
 * 
 * Example
 * a*b match : b, ab, aab, aaaaab
 * b.*b match : bb, bab, b12345b
 * 
 * isMatch(“aa”,”a”)    -> false
 * isMatch(“aa”,”aa”)   -> true
 * isMatch(“aaa”,”aa”)  -> false
 * 
 * isMatch(“aa”, “a*”)  -> true                '*' can repeat 'a'
 * isMatch(“aab”, “c*a*b”)  → true             
 *      "c*" matches 0'c' as a whole, which is "", 
 *      "a*" matches 2'a' as a whole, which is "aa"
 *      "b" matches "b"
 *      So "c*a*b" can match "aab"
 * 
 * isMatch(“”, “b*”)    -> true                 "b*" matches zero 'b', which is ""
 * isMatch("a", "ab*a") -> false
 * 
 * isMatch(“aa”, “.*”)  -> true                '.' matches 'a', '*' can repeat a 
 * isMatch("",".*") ->  true
 * 
 * isMatch("","..*") -> true                   ?
 * 
 * isMatch(“ab”, “.*”)  -> true                 ??
 * 
 * isMatch(“abcbcd”, “a.*c.*d”) -> false       ??
 * isMatch(“bbbba”, ".*a*a") -> true
 * isMatcg("aab", "c.*b") -> false
 * 
 * isMatch("","*") ->                    ??  This is a IllegalArgumentException()
 * 
 * Note:
 * "*" in regular expression is different from wildcard matching, 
 * It seems that some readers are confused about why the regex pattern “.*” matches the string “ab”. 
 * “.*” means repeat the preceding element 0 or more times. 
 * Here, the “preceding” element is the dot character in the pattern, which can match any characters. 
 * Therefore, the regex pattern “.*” allows the dot to be repeated any number of times, 
 * which matches any string (even an empty string).
 * 
 */

public class RegularExpressionMatching {



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

    public static boolean isMatch(String txt, String pattern) {
        if (txt == null && pattern == null) {
            return true;
        }
        if (txt == null || pattern == null) {
            return false; // return false when either is null
        }

        return isMatch_recursive(txt, 0, pattern, 0);

    }

    private static boolean isMatch_recursive(String txt, int i, String pattern, int j) {
        int m = txt.length(); // i
        int n = pattern.length(); // j

        if (j >= n) {
            return i >= m;
        }

        char pj = pattern.charAt(j);
        if (j + 1 == n || pattern.charAt(j + 1) != '*') {
            if (i >= m || (pj != '.' && pj != txt.charAt(i))) {
                return false;
            }
            return isMatch_recursive(txt, i + 1, pattern, j + 1);
        } else { // pattern.charAt(j+1)=='*'
            int k = i - 1;
            while (k < m && (k < i || pj == '.' || pj == txt.charAt(k))) {
                if (isMatch_recursive(txt, k + 1, pattern, j + 2)) {
                    return true;
                }
                k++;
            }
            return false;
        }
    }

    /**
     * @param s: A string 
     * @param p: A string includes "." and "*"
     * @return true if s and p is Regex matching 
     */
    public boolean isMatch_dp(String s, String p) {
        if(s == null && p == null){ 
            return true;
        }else if( s == null || p == null || (p.length() > 0 && p.charAt(0) == '*') ){
            return false;
        }

        int n = s.length();
        int m = p.length();

        boolean[][] isMatch = new boolean[n + 1][m + 1];
        isMatch[0][0] = true;

        for(int j = 1; j < m; j++){ // the first character is not *
            if( '*' == p.charAt(j)){
                isMatch[0][j + 1] = isMatch[0][j - 1];
            }
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(s.charAt(i) == p.charAt(j) || '.' == p.charAt(j) ){
                    isMatch[i + 1][j + 1] = isMatch[i][j]; 
                }else if( '*' == p.charAt(j)  ){
                    isMatch[i + 1][j + 1] = isMatch[i + 1][j - 1];

                    if(isMatch[i + 1][j + 1]){
                        continue;
                    }

                    if( s.charAt(i) == p.charAt(j - 1) || '.' == p.charAt(j - 1) ){
                        isMatch[i + 1][j + 1] = isMatch[i][j + 1]; 
                    }

                }
                
            }
        }

        return isMatch[n][m];
    }

    public static void main(String[] args) {

        String[][] inputs = {
            //{s, t, expect}
            { null, null, "true" },
            { null, "", "false" },
            { "", "", "true" },
            { "", "a", "false" },
            { "a", "", "false" },
            { "a", "*a", "false" },
            
            { "aa", "a", "false" },
            { "aa", "aa", "true" },
            { "aaa", "aa", "false" },
            { "a", ".", "true" },
            { "a", "..", "false" },
            { "", "a*", "true" },
            { "a", "a*", "true" },
            { "aa", "a*", "true" },
            { "aab", "c*a*b", "true" },
            
            { "aa", ".*", "true" },
            { "ab", ".*", "true" },
            { "", ".*", "true" },
            { "", ".*", "true" },
            { "aab", "c.*b", "false" },
            { "bbbba", ".*a*a", "true" },
            { "abcbcd", "a.*c.*d", "true" },
            { "", "..*", "false" },
            { "aaaa", "a*.*a*a.*aaaa", "false" },
            { "aaaaaaaa", "a.*a.*a.*a.*a.*a.*a.*a.*aaaaaa", "false" },
            { 
                "bbaaaaaabbbbbabbabbaabbbbababaaabaabbababbbabbababbaba", 
                "\"b.*b.*ba.*a.*aaa.*a.*b.*bbaa\"", 
                "false" 
            },
            { 
                "abbabaaabbabbaababbabbbbbabbbabbbabaaaaababababbbabababaabbababaabbbbbbaaaabababbbaabbbbaabbbbababababbaabbaababaabbbababababbbbaaabbbbbabaaaabbababbbbaababaabbababbbbbababbbabaaaaaaaabbbbbaabaaababaaaabb", 
                "aa.*ba.*a.*bb.*aa.*ab.*a.*aaaaaa.*a.*aaaa.*bbabb.*b.*b.*aaaaaaaaa.*a.*ba.*bbb.*a.*ba.*bb.*bb.*a.*b.*bb",
                "false" 
            },
        };

        RegularExpressionMatching sv = new RegularExpressionMatching();

        for (String[] input : inputs) {
            System.out.println(String.format("\nThe String: %s\nThe Regex: %s", input[0], input[1]));
            
            
            Assert.assertEquals(input[2], sv.isMatch_dp(input[0], input[1])? "true" : "false");
        }

    }
        
}
