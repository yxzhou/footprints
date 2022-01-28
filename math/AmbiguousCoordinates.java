/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package math;

import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1372
 *
 * We had some 2-dimensional coordinates, like "(1, 3)" or "(2, 0.5)". Then, we removed all commas, decimal points, and
 * spaces, and ended up with the string S. Return a list of strings representing all possibilities for what our original
 * coordinates could have been.
 *
 * Our original representation never had extraneous zeroes, so we never started with numbers like "00", "0.0", "0.00",
 * "1.0", "001", "00.01", or any other number that can be represented with less digits. Also, a decimal point within a
 * number never occurs without at least one digit occurring before it, so we never started with numbers like ".1".
 *
 * The final answer list can be returned in any order. Also note that all coordinates in the final answer have exactly
 * one space between them (occurring after the comma.)
 *
 * Constraints: 
 *   4 <= S.length <= 12. 
 *   S[0] = "(", S[S.length - 1] = ")", and the other elements in S are digits.
 *
 * Example 1:
 * Input："(00011)" 
 * Output：["(0.001, 1)", "(0, 0.011)"] 
 * Explanation：0.0, 00, 0001 or 00.01 are not allowed. 
 * 
 * Example 2:
 * Input："(100)" 
 * Output：[(10, 0)] 
 * Explanation：1.0 is not allowed.
 * 
 */
public class AmbiguousCoordinates {
    /**
     * @param S: An string
     * @return An string
     */
    public List<String> ambiguousCoordinates(String S) {

        List<String> result = new LinkedList<>();

        List<String> first;
        List<String> second;
        for (int i = 2, end = S.length() - 1; i < end; i++) {
            first = valid(S.substring(1, i));
            second = valid(S.substring(i, end));
            if (first != null && second != null) {
                for (String s1 : first) {
                    for (String s2 : second) {
                        result.add(String.format("(%s, %s)", s1, s2));
                    }
                }
            }
        }

        return result;
    }

    private List<String> valid(String s) {

        List<String> list = new LinkedList<>();
        int n = s.length();

        if (n == 1) {
            list.add(s);
            return list;
        }

        if (s.charAt(0) == '0') {
            if (s.charAt(n - 1) == '0') {
                return null;
            }

            list.add("0." + s.substring(1));
            return list;
        }

        if (s.charAt(n - 1) == '0') {
            list.add(s);
            return list;
        }

        list.add(s);
        for (int i = 1; i < n; i++) {
            list.add(s.substring(0, i) + "." + s.substring(i));
        }
        return list;
    }
    
    public static void main(String[] args){
        String[][][] inputs = {
            {
                {"(100)"},
                {"(10, 0)"}
            },
            { 
                {"(00011)"},
                {"(0, 0.011)", "(0.001, 1)"}
            },
            { 
                {"(123)"}, 
                {"(1, 23)","(1, 2.3)","(12, 3)","(1.2, 3)"}
            },
        };
        
        AmbiguousCoordinates sv = new AmbiguousCoordinates();
        
        for(String[][] input : inputs){
            System.out.println("\n" + input[0][0] );
            
            Assert.assertEquals(Misc.array2String(input[1]).toString(), Misc.array2String(sv.ambiguousCoordinates(input[0][0])).toString() );
        }
    }
}
