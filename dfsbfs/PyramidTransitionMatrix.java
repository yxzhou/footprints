/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dfsbfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1049
 *
 * We are stacking blocks to form a pyramid. Each block has a color which is a one letter string, like 'Z'.
 *
 * For every block of color C we place not in the bottom row, we are placing it on top of a left block of color A and
 * right block of color B. We are allowed to place the block there only if (A, B, C) is an allowed triple.
 *
 * We start with a bottom row of bottom, represented as a single string. We also start with a list of allowed triples
 * allowed. Each allowed triple is represented as a string of length 3.
 *
 * Return true if we can build the pyramid all the way to the top, otherwise false.
 *
 * Constraints: 
 * bottom will be a string with length in range [2, 8]. 
 * allowed will have length in range [0, 200]. 
 * Letters in all strings will be chosen from the set {'A', 'B', 'C', 'D', 'E', 'F', 'G'}.
 *
 * Example 1: 
 * Input: bottom = "BCG", allowed = ["BCD", "CGE", "DEA", "FFF"] 
 * Output: true
 *
 * Explanation: We can stack the pyramid like this:
        A
       / \
      D   E
     / \ / \
    B   C   G

 * This works because ('B', 'C', 'D'), ('C', 'G', 'E'), and ('D', 'E', 'A') are allowed triples.
 *
 * Example 2:
 * Input: bottom = "XXYX", allowed = ["XXX", "XXY", "XYX", "XYY", "YXZ"] 
 * Output: false
 *
 * Note that there could be allowed triples (A, B, C) and (A, B, D) with C != D.
 *
 * Thoughts:
 *   Letters in all strings will be chosen from the set {'A', 'B', 'C', 'D', 'E', 'F', 'G'}, and bottom will be a string
 * with length in range [2, 8]. So 3 bits can represent a letter, and a int (32 bits) can represent a bottom string.
 * 
 *   for the above example. 
 *   check BC, if it's not valid, return false.  or continue;
 *   check BCG, from bottom to up, 
 *     check CG, if it's not valid, return false.  or continue;
 *     check DE, if it's not valid, return false.  or continue;
 *   
 * 
 */
public class PyramidTransitionMatrix {

    /**
     *
     * @param bottom: a string
     * @param allowed: a list of strings
     * @return true if we can build the pyramid all the way to the top, otherwise false
     */
    public boolean pyramidTransition(String bottom, List<String> allowed) {

        Map<Integer, List<Integer>> cache = new HashMap<>();
        int xy;
        for (String triple : allowed) {
            xy = (code(triple.charAt(0)) << 3) | code(triple.charAt(1));
            cache.computeIfAbsent(xy, a -> new ArrayList<>()).add(code(triple.charAt(2)));
        }

        int state = code(bottom.charAt(0));
        for (int i = 1; i < bottom.length(); i++) {
            if (!helper(cache, state, code(bottom.charAt(i)))) {
                return false;
            }

            state = (state << 3) | code(bottom.charAt(i));
        }

        return true;
    }

    private int code(char c) {
        return c - 'A' + 1;
    }

    private boolean helper(Map<Integer, List<Integer>> cache, int state, int color) {
        int result = (state << 3) | color;

        if (cache.containsKey(result)) {
            return cache.get(result) != null;
        }

        List<Integer> rights = cache.get(((state & 0b111) << 3) | color);
        if (rights == null) {
            cache.put(result, null);
            return false;
        }
        
        if(state < 10){
            return true;
        }
        List<Integer> lefts = cache.get(state);
        if(lefts == null){
            return false;
        }

        List<Integer> merges = new ArrayList<>(lefts.size() * rights.size());
        for (int left : lefts) {
            for (int right : rights) {
                if (!helper(cache, left, right)) {
                    continue;
                }

                merges.add((left << 3) | right);
            }
        }

        cache.put(result, merges.isEmpty() ? null : merges);
        return !merges.isEmpty();

    }


    public static void main(String[] args) {

        String[][][] inputs = {
//            {
//                {"XYZ"},
//                {"XYD", "YZE", "DEA", "FFF"},
//                {"true"}
//            },
//            {
//                {"XXYX"},
//                {"XXX", "XXY", "XYX", "XYY", "YXZ"},
//                {"false"}
//            },
            
            
            {
                {"BCG"},
                {"BCD", "CGE", "DEA", "FFF"},
                {"true"}
            },
            {
                {"AABA"},
                {"AAA", "AAB", "ABA", "ABB", "BAC"},
                {"false"}
            },
            {
                {"ABC"},
                {"ABD", "BCE", "DEF", "FFF"},
                {"true"}
            }

        };

        PyramidTransitionMatrix sv = new PyramidTransitionMatrix();

        for (String[][] input : inputs) {
            System.out.println(String.format("\nbottom: %s, allowed: %s", input[0][0], Misc.array2String(input[1])));
            
            System.out.println(String.format("\n %s - %b", input[2][0], sv.pyramidTransition(input[0][0], Arrays.asList(input[1])) ));

            if (input[2][0].equals("true")) {
                Assert.assertTrue(sv.pyramidTransition(input[0][0], Arrays.asList(input[1])));
            } else {
                Assert.assertFalse(sv.pyramidTransition(input[0][0], Arrays.asList(input[1])));
            }

        }

    }

}
