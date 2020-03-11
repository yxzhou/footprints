package fgafa.array.LIS;

/**
 * Leetcode #712
 *
 * Given two strings s1, s2, find the lowest ASCII sum of deleted characters to make two strings equal.
 *
 * Example 1:
 * Input: s1 = "sea", s2 = "eat"
 * Output: 231
 * Explanation: Deleting "s" from "sea" adds the ASCII value of "s" (115) to the sum.
 * Deleting "t" from "eat" adds 116 to the sum.
 * At the end, both strings are equal, and 115 + 116 = 231 is the minimum sum possible to achieve this.
 *
 * Example 2:
 * Input: s1 = "delete", s2 = "leet"
 * Output: 403
 * Explanation: Deleting "dee" from "delete" to turn the string into "let",
 * adds 100[d]+101[e]+101[e] to the sum.  Deleting "e" from "leet" adds 101[e] to the sum.
 * At the end, both strings are equal to "let", and the answer is 100+101+101+101 = 403.
 * If instead we turned both strings into "lee" or "eet", we would get answers of 433 or 417, which are higher.
 *
 *
 * Note:
 * 0 < s1.length, s2.length <= 1000.
 * All elements of each string will have an ASCII value in [97, 122].
 *
 *
 * Thought:
 *
 * 1) find out the LIS, example to s1 = "delete", s2 = "leet"
 *
 * define f[i][j] as the LIS of s1[0..i] and s2[0..j]
 *   d e l e t e
 * l 0 0 1 1 1 1
 * e 0 1 1 2 2 2
 * e 0 1 1 2 2 3
 * t 0 1 1 2 3 3
 *
 */

public class MinimumDeleteSum {

    public int minimumDeleteSum(String s1, String s2) {

        //todo
        return -1;
    }

}
