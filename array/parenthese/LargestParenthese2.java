package fgafa.array.parenthese;

import java.util.Stack;

public class LargestParenthese2
{

  /**
  *
  * Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.
  * 
  * For "(ab(xy)", the longest valid parentheses substring is "ab(xy)".
  * 
  * Another example is ")a(ab)(xy)b)", where the longest valid parentheses substring is "a(ab)(xy)b".
  * 
  * @author yxzhou
  *
  */
  
  public String longestValidParenthese(String s){
    final int N = s.length();
    Stack<Integer> stack = new Stack<Integer>();

    int[] opens = new int[N+1];  //default it's 0
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
      } else if(s.charAt(i) !=  ')'){
        if(i==0){
          opens[i] = 1;
        }else{
          opens[i] = opens[i-1] + 1;
        }
      }
        
      if(maxLen < opens[i]){
        maxLen = opens[i];
        maxParenthese = s.substring(i - maxLen + 1 ,i+1) ;
      }
    }

    return maxParenthese;
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    LargestParenthese2 sv = new LargestParenthese2();
    
    String[] s = {"()","((",")(","(()","(((","())",")()())", ")(()))", "(ab(xy)", ")a(ab)(xy)b)"};

    for(int i=0; i< s.length; i++){
      
      System.out.println("Input:" + s[i]);
      System.out.println(sv.longestValidParenthese(s[i]) );
      
    }
 

  }

}
