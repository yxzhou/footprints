/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfsbfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * Give a string, you can choose to split the string after one character or two adjacent characters, and make the string
 * to be composed of only one character or two characters. Output all possible results.
 *
 * Example1 Input: "123" Output: [["1","2","3"],["12","3"],["1","23"]]
 *
 * Example2 Input: "12345" Output:
 * [["1","23","45"],["12","3","45"],["12","34","5"],["1","2","3","45"],["1","2","34","5"],["1","23","4","5"],["12","3","4","5"],["1","2","3","4","5"]]
 *
 */
public class SplitString {

    /*
     * @param : a string to be split
     * @return: all possible split string array
     */
    public List<List<String>> splitString(String s) {
        List<List<String>> result = new LinkedList<>();

        if (s == null || s.length() == 0) {
            result.add(new LinkedList<>());
            return result;
        }

        helper(s, s.length(), 0, new int[s.length()], 0, result);
        return result;
    }

    private void helper(String s, int n, int p, int[] curr, int i, List<List<String>> result) {
        if (p == n) {
            List<String> list = new ArrayList<>(i);
            for (int j = 0, l = 0, r = 0; j < i; j++) {
                r = l + curr[j];
                list.add(s.substring(l, r));
                l = r;
            }

            result.add(list);
            return;
        } else if (p > n) {
            return;
        }

        curr[i] = 1;
        helper(s, n, p + 1, curr, i + 1, result);

        curr[i] = 2;
        helper(s, n, p + 2, curr, i + 1, result);
    }
}
