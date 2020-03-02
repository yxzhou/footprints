package fgafa.array.parenthese;

import java.util.Stack;

public class LongestParenthese2 {

    /**
     * Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.
     * <p>
     * For "(ab(xy)", the longest valid parentheses substring is "ab(xy)".
     * <p>
     * Another example is ")a(ab)(xy)b)", where the longest valid parentheses substring is "a(ab)(xy)b".
     */

    public String longestValidParenthese_dp(String s) {
        final int N = s.length();
        Stack<Integer> stack = new Stack<Integer>();

        int[] opens = new int[N + 1];  //default it's 0
        int maxLen = 0;
        String maxParenthese = "";

        for (int i = 0; i < N; i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else if (s.charAt(i) == ')' && !stack.empty()) {

                int start = stack.pop();
                int len = i - start + 1;

                if (start > 0) {
                    len += opens[start - 1];
                }

                opens[i] = len;
            } else if (s.charAt(i) != ')') {
                if (i == 0) {
                    opens[i] = 1;
                } else {
                    opens[i] = opens[i - 1] + 1;
                }
            }

            if (maxLen < opens[i]) {
                maxLen = opens[i];
                maxParenthese = s.substring(i - maxLen + 1, i + 1);
            }
        }

        return maxParenthese;
    }


    public String longestValidParenthese_stack(String s) {
        final int len = s.length();
        Stack<Integer> opens = new Stack<Integer>();
        opens.push(-1);

        int maxR = 0;
        int max = 0;
        int curr;

        for (int i = 0; i < len; i++) {
            if (s.charAt(i) == '(') {
                opens.push(i);
            } else if(s.charAt(i) == ')' ) {
                opens.pop();

                if(opens.isEmpty()){
                    opens.push(i);
                }else{
                    curr = i - opens.peek();

                    if(curr > max){
                        max = curr;
                        maxR = i;
                    }
                }
            }
        }

        return maxR == 0 ? "" : s.substring( maxR + 1 - max, maxR + 1);
    }

    public String longestValidParenthese_twoScan(String s) {
        final int len = s.length();

        int max = 0;
        int maxR = 0;

        int depth = 0;
        int lastClose = -1;
        for(int i = 0; i < len; i++ ){
            if(s.charAt(i) == '('){
                depth--;

            }else if(s.charAt(i) == ')'){
                depth++;

                if(depth > 0){
                    lastClose = i;
                    depth = 0;
                }
            }

            if(depth == 0){
                int currLen = i - lastClose;
                if(currLen > max){
                    max = currLen;
                    maxR = i;
                }
            }
        }

        for(int i = len - 1; i >= 0; i--){
            if(s.charAt(i) == ')'){
                depth--;
            }else if(s.charAt(i) == '('){
                depth++;

                if(depth > 0){
                    lastClose = i;
                    depth = 0;
                }
            }

            if(depth == 0){
                int currLen = lastClose - i;
                if(currLen > max){
                    max = currLen;
                    maxR = lastClose - 1;
                }
            }
        }

        return maxR == 0 ? "" : s.substring( maxR + 1 - max, maxR + 1);
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        LongestParenthese2 sv = new LongestParenthese2();

        String[] s = {"()", "((", ")(", "(()", "(((", "())", ")()())", ")(()))", "(ab(xy)", ")a(ab)(xy)b)"};

        for (int i = 0; i < s.length; i++) {

            System.out.println("\nInput:" + s[i]);
            System.out.println(sv.longestValidParenthese_dp(s[i]));
            System.out.println(sv.longestValidParenthese_stack(s[i]));
            System.out.println(sv.longestValidParenthese_twoScan(s[i]));
        }


    }

}
