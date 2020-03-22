package fgafa.dp.twosequence;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Leetcode #97
 * <p>
 * Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
 * <p>
 * Example 1:
 * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
 * Output: true
 * <p>
 * Example 2:
 * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
 * Output: false
 */
public class Interleaving {

    /** Time O(m*n)  Space O(m*n) */
    public boolean isInterleave_dfs(String s1, String s2, String s3) {
        if (s1 == null && s2 == null) {
            return s3 == null;
        }
        if (s3 == null) {
            return false;
        }
        if (s1 == null) {
            return s3.equals(s2);
        }
        if (s2 == null) {
            return s3.equals(s1);
        }

        //s1, s2, s3 all are not null
        int m = s1.length();
        int n = s2.length();
        int k = s3.length();
        if (m + n != k) {
            return false;
        }

        int[][] dp = new int[m + 1][n + 1]; // default it's 0; 1 means matched, 2 means not matched
        dp[m][n] = 1;

        dfs(s1, 0, s2, 0, s3, dp);

        return dp[0][0] == 1;
    }

    private int dfs(String s1, int i, String s2, int j, String s3, int[][] dp) {
        if (dp[i][j] > 0) {
            return dp[i][j];
        }

        dp[i][j] = 2;
        int k = i + j;

        if (i < s1.length() && s1.charAt(i) == s3.charAt(k)) {
            if (dfs(s1, i + 1, s2, j, s3, dp) == 1) {
                dp[i][j] = 1;
                return 1;
            }
        }

        if (j < s2.length() && s2.charAt(j) == s3.charAt(k)) {
            if (dfs(s1, i, s2, j + 1, s3, dp) == 1) {
                dp[i][j] = 1;
                return 1;
            }
        }

        return dp[i][j];
    }

    /** Time O(m*n)  Space O(m*n) */
    public boolean isInterleave_bfs(String s1, String s2, String s3) {
        if(s1 == null && s2 == null){
            return s3 == null;
        }
        if(s3 == null){
            return false;
        }
        if(s1 == null){
            return s3.equals(s2);
        }
        if(s2 == null){
            return s3.equals(s1);
        }

        //s1, s2, s3 all are not null
        int m = s1.length();
        int n = s2.length();
        int k = s3.length();
        if(m + n != k){
            return false;
        }

        //bfs
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0});

        boolean[][] visited = new boolean[m + 1][n + 1];
        visited[0][0] = true;

        int[] curr;
        for(int p = 0; p < k && !queue.isEmpty(); p++){
            for(int q = queue.size(); q > 0; q--){
                curr = queue.poll();

                if(curr[0] < m && s3.charAt(p) == s1.charAt(curr[0]) && !visited[curr[0] + 1][curr[1]]){
                    queue.add(new int[]{curr[0] + 1, curr[1]});
                    visited[curr[0] + 1][curr[1]] = true;
                }

                if(curr[1] < n && s3.charAt(p) == s2.charAt(curr[1]) && !visited[curr[0]][curr[1] + 1]){
                    queue.add(new int[]{curr[0], curr[1] + 1});
                    visited[curr[0]][curr[1] + 1] = true;
                }
            }
        }

        return visited[m][n];
    }

    /** Time O(m*n)  Space O(m*n) */
    public boolean isInterleave_dp_1(String s1, String s2, String s3) {
        if(s1 == null && s2 == null){
            return s3 == null;
        }
        if(s3 == null){
            return false;
        }
        if(s1 == null){
            return s3.equals(s2);
        }
        if(s2 == null){
            return s3.equals(s1);
        }

        //s1, s2, s3 all are not null
        int m = s1.length();
        int n = s2.length();
        int k = s3.length();
        if(m + n != k){
            return false;
        }

        //create indicator
        boolean[][] match = new boolean[m + 1][n + 1];   //default it's false

        //initialization the first row and the first column
        match[0][0] = true;
        for (int i = 1; i <= m; ++i) {
            if (s1.charAt(i - 1) == s3.charAt(i - 1)) {
                match[i][0] = true;
            } else {
                break;
            }
        }
        for (int j = 1; j <= n; ++j) {
            if (s2.charAt(j - 1) == s3.charAt(j - 1)) {
                match[0][j] = true;
            } else {
                break;
            }
        }

        //work through the rest of matrix using the formula
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                match[i][j] = (s3.charAt(i + j - 1) == s2.charAt(j - 1) && match[i][j - 1])
                        || (s3.charAt(i + j - 1) == s1.charAt(i - 1) && match[i - 1][j]);
            }
        }

        //the last element is the result
        return match[m][n];
    }

    /**
     * Time O(n * m), Space O( math.min(m, n))
     */
    public boolean isInterleave_dp_n(String s1, String s2, String s3) {
        if (s1 == null && s2 == null) {
            return s3 == null;
        }
        if (s3 == null) {
            return false;
        }
        if (s1 == null) {
            return s3.equals(s2);
        }
        if (s2 == null) {
            return s3.equals(s1);
        }

        //s1, s2, s3 all are not null
        int m = s1.length();
        int n = s2.length();
        int k = s3.length();
        if (m + n != k) {
            return false;
        }

        if (m > n) {
            return isInterleave_dp_n(s2, s1, s3);
        }

        boolean[] match = new boolean[m + 1]; //default all are false
        match[0] = true;

        for (int i = 0; i < m; i++) {
            if (s1.charAt(i) == s3.charAt(i)) {
                match[i + 1] = true;
            } else {
                break;
            }
        }

        for (int j = 0; j < n; j++) { // s2
            match[0] = (s3.charAt(j) == s2.charAt(j) && match[0]);

            for (int i = 0; i < m; i++) { // s1
                match[i + 1] = (match[i] && s1.charAt(i) == s3.charAt(i + j + 1))
                        || (match[i + 1] && s2.charAt(j) == s3.charAt(i + j + 1));
            }
        }

        return match[m];
    }


    public static void main(String[] args) {
        long startnano = System.nanoTime();
        long startmill = System.currentTimeMillis();

        System.out.println("=====start====" + startnano);
        System.out.println("=====start====" + startmill);

//    boolean[][] path = new boolean[2][2];
//
//    for (int i = 0; i < path.length; i++) {
//      for (int j = 0; j < path[i].length; j++) {
//        System.out.print("\t"+ path[i][j]);
//      }
//    }
//
//    if(true) return;

        String strA = "aab";
        String strB = "aac";

        //String strA = "bbbbbabbbbabaababaaaabbababbaaabbabbaaabaaaaababbbababbbbbabbbbababbabaabababbbaabababababbbaaababaa";
        //String strB = "babaaaabbababbbabbbbaabaabbaabbbbaabaaabaababaaaabaaabbaaabaaaabaabaabbbbbbbbbbbabaaabbababbabbabaab";

        String[] strDst = {"ABCAABCEF", "AABBCCAEF", "ABACBCAEF", "ABCABCEF", "ABCAEF", "ABCEAF", "ABCEF"
                , "aaabac"
                , "babbbabbbaaabbababbbbababaabbabaabaaabbbbabbbaaabbbaaaaabbbbaabbaaabababbaaaaaabababbababaababbababbbababbbbaaaabaabbabbaaaaabbabbaaaabbbaabaaabaababaababbaaabbbbbabbbbaabbabaabbbbabaaabbababbabbabbab"};

        Interleaving s = new Interleaving();

        //check if it's interleaving
        for (int i = 0; i < strDst.length; i++) {
            System.out.println("\n-" + i + "- " + strA + " and " + strB);
            System.out.println("to " + strDst[i].toLowerCase());
            System.out.println(s.isInterleave_dfs(strA, strB, strDst[i].toLowerCase()));
            System.out.println(s.isInterleave_bfs(strA, strB, strDst[i].toLowerCase()));

            System.out.println(s.isInterleave_dp_1(strA, strB, strDst[i].toLowerCase()));
            System.out.println(s.isInterleave_dp_n(strA, strB, strDst[i].toLowerCase()));

        }

        System.out.println("===== end ====" + (System.nanoTime() - startnano));
        System.out.println("===== end ====" + (System.currentTimeMillis() - startmill));

    }


    /*
     * TODO
     *
     * e.g.  [a,b,c], [A,B]  output: (3+2)! / (3! * 2!) ==> it's similar with selecting 2 from 5. Combination.
     * The final result is 12345, AB can take:
     * [12], [13], [14], [15], [23], [24], [25], [34], [35], [45]
     *
     */


}
