package fgafa.array.parenthese;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class ValidParenthesesII
{   
    /*
     * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', 
     * determine if the input string is valid.
     * 
     * The brackets must close in the correct order, 
     *    "()" and "()[]{}" are all valid
     *    "(]" and "([)]" are not.
     * 
     */
    /*
     * Time O(n)  Space O(n)
     */
    public boolean isValidParentheses(String s) {
        // check
        if (null == s || 0 == s.length()) {
            return true;
        }

        Stack<Character> stack = new Stack<>();
        char top;
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (!stack.isEmpty()) {
                top = stack.pop();
                if ((c == ')' && top == '(') || (c == '}' && top == '{')
                            || (c == ']' && top == '[')) {
                    continue;
                } else { //
                    return false;
                }
            }else { 
                return false;
            }
        }
        return stack.isEmpty();
    }
    
    public boolean isValid_n2(String s) {
        if (null == s) {
            return true;
        }

        Map<Character, Character> map = new HashMap<>(3);
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');

        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (map.containsKey(c)) {
                stack.push(c);
            } else if (!stack.isEmpty()) {
                char top = stack.pop();
                if (map.get(top) != c) {
                    return false;
                }
            } else { 
                return false;
            }
        }

        return stack.isEmpty();
    }
	

  public static void main(String[] args) {
    ValidParenthesesII sv = new ValidParenthesesII();

    String[] input = {
    		null,
    		"[]",
    		"]",
    		"[[]",
    		"[]]",
    		"[[]]",
    		"[[]]]"};
    
    for(int i =0; i< input.length; i++){
    	System.out.println("\n Input: " + input[i] + "\t"+ sv.isValid_n2(input[i]) + "\t"+ sv.isValidParentheses(input[i])  );
    }
  }

}
