package array.parenthese;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/780/
 * 
 * Remove the minimum number of invalid parentheses in order to make the input string valid. 
 * Return all possible results.
 *
 * Note:
 *   The input string may contain letters other than the parentheses ( and ). 
 * 
 * Example 1:
 * Input: "()())()" 
 * Output: ["(())()","()()()"] 
 * 
 * Example 2:
 * Input: "(a)())()" 
 * Output: ["(a)()()", "(a())()"] 
 * 
 * Example 3:
 * Input: ")(" 
 * Output: [""]
 *
 */

public class RemoveInvalidParenthesesII {

    /**
     * @param s: The input string
     * @return: Return all possible results
     */
    public List<String> removeInvalidParentheses(String s) {
        if (s == null) {
            return Collections.EMPTY_LIST;
        }

        Set<String> result = new HashSet<>();
        Set<String> visited = new HashSet<>();

        removeClose(s, 0, result, visited);

        return new ArrayList<>(result);
    }

    //remove invalid the close parenthesis from left to right
    private void removeClose(String s, int start, Set<String> result, Set<String> visited) {
        int close = 0;
        char c;
        for (int i = start, n = s.length(); i < n; i++) {
            c = s.charAt(i);
            if (c == '(') {
                close--;
            } else if (c == ')') {
                close++;
            }

            if (close > 0) {
                for (int j = i; j >= 0; j--) {
                    if (s.charAt(j) == ')') {
                        String state = s.substring(0, j) + s.substring(j + 1);
                        if (!visited.contains(state)) {
                            visited.add(state);
                            removeClose(state, i, result, visited);
                        }
                    }
                }

                return;
            }
        }

        if (close < 0) {
            removeOpen(s, s.length() - 1, result, visited);
        } else {
            result.add(s);
        }
    }

    //remove invalid the open parenthesis from right to left
    private void removeOpen(String s, int start, Set<String> result, Set<String> visited) {
        int open = 0;
        char c;
        for (int i = s.length() - 1; i >= 0; i--) {
            c = s.charAt(i);
            if (c == '(') {
                open++;
            } else if (c == ')') {
                open--;
            }

            if (open > 0) {
                for (int j = s.length() - 1; j >= i; j--) {
                    if (s.charAt(j) == '(') {
                        String state = s.substring(0, j) + s.substring(j + 1);
                        if (!visited.contains(state)) {
                            visited.add(state);
                            removeOpen(state, i - 1, result, visited);
                        }
                    }
                }
                return;
            }
        }

        result.add(s);
    }


    public List<String> removeInvalidParentheses_x(String s) {
        if (null == s ) {
            return Collections.EMPTY_LIST;
        }
                
        List<String> result = new ArrayList<>();

        helper(s, result, 0, 0, new char[]{'(', ')'});

        return result;
    }

    public void helper(String s, List<String> result, int last_i, int last_j, char[] par) {
        for (int counter = 0, i = last_i; i < s.length(); ++i) {
            if (s.charAt(i) == par[0]) {
                counter++;
            } else if (s.charAt(i) == par[1]) {
                counter--;
            }

            if (counter >= 0) {
                continue;
            }

            for (int j = last_j; j <= i; ++j) {
                if (s.charAt(j) == par[1] && (j == last_j || s.charAt(j - 1) != par[1])) {
                    helper(s.substring(0, j) + s.substring(j + 1, s.length()), result, i, j, par);
                }
            }

            return;
        }

        String reversed = new StringBuilder(s).reverse().toString();
        if (par[0] == '(') {// finished left to right
            helper(reversed, result, 0, 0, new char[]{')', '('});
        } else { // finished right to left
            result.add(reversed);
        }
    }


    public static void main(String[] args) {
        RemoveInvalidParenthesesII sv = new RemoveInvalidParenthesesII();

        String[][][] inputs = {
            //{ s, expects }
            { {null}, {} },
            { {""}, {""}},
            { {"("}, {""} },
            { 
                {")()("}, 
                {"()"} 
            },
            { 
                {"()())()"}, 
                {"()()()", "(())()"} 
            },
            { 
                {"(a)())()"}, 
                {"(a)()()", "(a())()"} 
            },
            { 
                {")("}, 
                {""} 
            },
            { 
                {"())())"}, 
                {"()()", "(())"} 
            },
            {
                {"()())())"}, 
                {"()()()","()(())","(())()","(()())"}
                    
            },
            {
                {"())(()"},
                {"()()"}
            }
        };

        for (String[][] input : inputs) {
            System.out.println(String.format("\nInput: %s, Output: %s", input[0][0], Arrays.toString(input[1]) ));
            
            Assert.assertArrayEquals(input[1], sv.removeInvalidParentheses(input[0][0]).stream().sorted(Collections.reverseOrder()).toArray(String[]::new) );
            Assert.assertArrayEquals(input[1], sv.removeInvalidParentheses_x(input[0][0]).stream().sorted(Collections.reverseOrder()).toArray(String[]::new) );
        }
    }
}
