package fgafa.array.parenthese;

import java.util.Stack;

/**
 *
 * Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.
 * 
 * For "(()", the longest valid parentheses substring is "()", which has length = 2.
 * 
 * Another example is ")()())", where the longest valid parentheses substring is "()()", which has length = 4.
 * 
 */


public class LongestParenthese
{
  
  /*
   * With stack
   * Time O(n)   Space O(n)
   */
    public int longestValidParentheses_stack(String s) {
        if (null == s || s.length() < 2) {
            return 0;
        }

        int max = 0;
        Stack<Integer> opens = new Stack<Integer>();// the positions of '('
        opens.push(-1);

        for (int i = 0; i < s.length(); i++) {
            if ('(' == s.charAt(i)) {
                opens.push(i);
            } else { // s.charAt(i) == ')'
                opens.pop();

                if(opens.isEmpty()){
                    opens.push(i);
                }else{
                    max = Math.max(max, i - opens.peek());
                }
            }
        }

        return max;
    }

  
  /*
   * DP
   * Time O(n)  Space O(n) 
   */
    public int longestValidParentheses_dp(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }

        int max = 0;
        // dp[i] is the longest valid Parenthese by end of s.charAt(i)
        // e.g. ")()(" dp[i]= {0,0,2,4}
        int[] dp = new int[s.length()];  // default all are 0

        for (int i = 1; i < s.length(); i++) {

            if (s.charAt(i) == '(') {
                dp[i] = 0;
            } else if (s.charAt(i) == ')') {
                // i>dp is for case "())"
                if (i > dp[i - 1] && s.charAt(i - dp[i - 1] - 1) == '(') { 
                    dp[i] = 2 + dp[i - 1];

                    if (i > dp[i - 1] + 1 && s.charAt(i - dp[i - 1] - 2) == ')'){
                        dp[i] += dp[i - dp[i]];
                    }

                    max = Math.max(max, dp[i]);
                }
            } 
        }

        return max;
    }
  /*
   *  2 directional scan, 
   *  Time O(n), Space O(1)
   */
    public int longestValidParentheses_twoScans(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }

        int max = 0;
        
        int lastClose = -1; // the position of the last ')'
        int depth = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '('){
                depth++;
            }else{
                depth--;
                
                if(depth < 0){
                    lastClose = i;
                    depth = 0;
                }else if(depth == 0){
                    max = Math.max(max, i - lastClose);
                }
            }
        }
        
        int lastOpen = s.length();
        depth = 0;
        for(int i = s.length() - 1; i >= 0; i--){
            if(s.charAt(i) == ')'){
                depth++;
            }else{
                depth--;
                
                if(depth < 0){
                    lastOpen = i;
                    depth = 0;
                }else if(depth == 0){
                    max = Math.max(max, lastOpen - i);
                }
            }
        }
        
        return max;
    }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    LongestParenthese sv = new LongestParenthese();
    
    String[] s = {"()","((",")(","(()","(((","())",")()())", ")(()))"};

    for(int i=0; i< s.length; i++){
      
      System.out.println("Input:" + s[i]);
      System.out.println(sv.longestValidParentheses_stack(s[i]) + "\t" + sv.longestValidParentheses_dp(s[i]) + "\t" + sv.longestValidParentheses_twoScans(s[i]));
      
    }
    
  }

}
