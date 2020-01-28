package fgafa.slideWindow;

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 *  Leetcode 727
 * Given strings S and T, find the minimum (contiguous) substring W of S, so that T is a subsequence of W.
 *
 * If there is no such window in S that covers all characters in T, return the empty string "". If there are multiple such minimum-length windows, return the one with the left-most starting index.
 *
 * Example 1:
 *
 * Input:
 * S = "abcdebdde", T = "bde"
 * Output: "bcde"
 * Explanation:
 * "bcde" is the answer because it occurs before "bdde" which has the same length.
 * "deb" is not a smaller window because the elements of T in the window must occur in order.
 *
 *
 * Note:
 *
 * All the strings in the input will only contain lowercase letters.
 * The length of S will be in the range [1, 20000].
 * The length of T will be in the range [1, 100].
 *
 */

public class MininumWindowSubsequence {

    @Test public void test(){
        Assert.assertEquals("bcde", minWindow("abcdebdde", "bde"));

        String S = "ffynmlzesdshlvugsigobutgaetsnjlizvqjdpccdylclqcbghhixpjihximvhapymfkjxyyxfwvsfyctmhwmfjyjidnfryiyaj" +
                "mtakisaxwglwpqaxaicuprrvxybzdxunypzofhpclqiybgniqzsdeqwrdsfjyfkgmejxfqjkmukvgygafwokeoeglanevavyrpduig" +
                "itmrimtaslzboauwbluvlfqquocxrzrbvvplsivujojscytmeyjolvvyzwizpuhejsdzkfwgqdbwinkxqypaphktonqwwanapouqyj" +
                "dbptqfowhemsnsl";
        String T = "ntimcimzah";

        String expect = "nevavyrpduigitmrimtaslzboauwbluvlfqquocxrzrbvvplsivujojscytmeyjolvvyzwizpuhejsdzkfwgqdbwinkxqypaph";

        Assert.assertEquals(expect, minWindow(S, T));


        S = "pcaimomtmveadexvauerxryuisraxixqbgnbwrjqidfdcnrlljirsioxlholkytimqtzubqommizbdxaunnydfylyerxmzatcedymqxgao" +
                "chxnfeplecowiuwrqsaxjhqjfxpotrnytfteghimxlalhjclvnszzudxigaiozwduuudufxiupehkbumnlcempydugrsnqwfutycpf" +
                "ctlshyjlelszhtkaowhiweisjejisslhwcbwjdabmfnievmufkuvalpdufglzzwtvgqthbgzqqqxxzklhxvwygmrpoqlbwgopjnfym" +
                "tcgxvtfqhztffdch";
        T = "olbmyncpiu";

        expect = "olkytimqtzubqommizbdxaunnydfylyerxmzatcedymqxgaochxnfeplecowiuwrqsaxjhqjfxpotrnytfteghimxlalhjclvnszzu";
        Assert.assertEquals(expect, minWindow(S, T));

    }

    public String minWindow(String S, String T) {
        int m = S.length();
        int n = T.length();
        int[][] dp = new int[n][m];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(i > j){
                    dp[i][j] = -1;
                    continue;
                }

                if(i == 0 || j == 0) {
                    if(S.charAt(j) == T.charAt(i)){
                        dp[i][j] = j;
                    }  else {
                        dp[i][j] = (j == 0 ? -1 : dp[i][j - 1]);
                    }

                    continue;
                }

                if(S.charAt(j) == T.charAt(i)){
                    dp[i][j] = dp[i - 1][j - 1];
                }  else {
                    dp[i][j] = dp[i][j - 1];
                }

            }
        }

        String result = "";
        int min = Integer.MAX_VALUE;
        for(int i = n - 1, j = 0; j < m; j++) {
            if(dp[i][j] > -1){
                int w = j - dp[i][j] + 1;
                if(min > w) {
                    min = w;
                    result = S.substring(dp[i][j], j + 1);
                }
            }
        }

        return result;
    }
}
