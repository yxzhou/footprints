package array.parenthese;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import org.junit.Assert;

/*
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 *
 * The brackets must close in the correct order,
 *    "()" and "()[]{}" are all valid
 *    "(]" and "([)]" are not.
 *
 */

public class ValidParenthesesII {

    /*
     * Time O(n)  Space O(n)
     */
    Map<Character, Character> open2close = new HashMap<>(3);
    char[][] validCharacters= {{'(',')'}, {'[',']'}, {'{','}'}};
    {
        for(char[] pair : validCharacters){
            open2close.put(pair[0], pair[1]);
        }
    }

    /**
     * 
     * 
     * @param s
     * @return 
     */
    public boolean isValid_n(String s) {
        if(null == s ){
            return true;
        }

        if( 1 == (s.length() & 1) ){
            return false;
        }

        Stack<Character> stack = new Stack<>();
        
        char c;
        for(int i = 0, n = s.length(); i < n; i++){
            c = s.charAt(i);
            
            if (open2close.containsKey(c)) {
                stack.push(c);
            } else if ( stack.isEmpty() || open2close.get(stack.pop()) != c) {
                return false;
            }// works when s only contains '(', ')', '{', '}', '[' and ']', no other characters.
        }

        return stack.isEmpty();
    }


    public boolean isValid(String s) {
        if(null == s ){
            return true;
        }

        if( 1 == (s.length() & 1) ){
            return false;
        }

        Stack<Character> stack = new Stack<>();
        
        char c;
        for(int i = 0, n = s.length(); i < n; i++){
            c = s.charAt(i);
            
            switch(c){
                case '(':
                    stack.add(')');
                    break;
                case '{':
                    stack.add('}');
                    break;
                case '[':
                    stack.add(']');
                    break;
                case ')':
                case '}':
                case ']':
                    if(stack.isEmpty() || c != stack.pop()){
                        return false;
                    }
                    break;
            }
        }

        return stack.isEmpty();
    }


    /*
     * Time O(n*n)  Space O(1)
     */
    public boolean isValid_withoutStack(String s) {
        if(null == s ){
            return true;
        }

        if( 1 == (s.length() & 1) ){
            return false;
        }

        return isValid_withoutStack(s, 0, s.length() - 1);
    }

    private boolean isValid_withoutStack(String s, int start, int end) {
        int count = 0;
        
        for(int l = start, r = start; r <= end; r++){
            if(open2close.containsKey(s.charAt(r))){
                count++;
            }else{
                count--;
            }

            if(count < 0){
                return false;
            }else if(count == 0){
                // to case "[(){}[]({})]",  here it's recursive to check "(){}[]({})"
                if(open2close.containsKey(s.charAt(l)) && open2close.get(s.charAt(l)) == s.charAt(r)){
                    if(!isValid_withoutStack(s, l + 1, r - 1)){
                        return false;
                    }
                }else{
                    return false;
                }

                l = r + 1;
            }
        }

        return 0 == count;
    }

    /*
     * Time O(n)  Space O(1)
     */
//    public boolean isValid_withoutStack2(char[] ss) {
//        if(null == ss || ss.length == 0 ){
//            return true;
//        }
//
//        if((ss.length % 2) != 0){
//            return false;
//        }
//
//        for(int l = 0, r = 0; r < ss.length; r++){
//            if()
//            r = l + 1;
//
//            for( r = l + 1; l >= 0; ){
//                if(ss[l] == '#')
//                ss[l] = '#';
//                ss[l] = '#';
//            }
//        }
//
//        return count == 0;
//    }

  public static void main(String[] args) {
    ValidParenthesesII sv = new ValidParenthesesII();

    String[][] inputs = {
        //{s, expect}
        {null, "true"},
        {"[]", "true"},
        {"[", "false"},
        {"[[", "false"},
        {"]", "false"},
        {"[()", "false"},
        {"()]", "false"},
        {"[()]", "true"},
        {"[()]]", "false"},
        {"[](){}", "true"},
        {"{([])}", "true"},
        {"[(){}[]({})]", "true"},

    };
    
    for(String[] input : inputs){
    	System.out.println( String.format("\n Input: s = %s", input[0]) );
        
        Assert.assertEquals( Boolean.parseBoolean(input[1]), sv.isValid(input[0]));
        Assert.assertEquals( Boolean.parseBoolean(input[1]), sv.isValid_n(input[0]));
        Assert.assertEquals( Boolean.parseBoolean(input[1]), sv.isValid_withoutStack(input[0]));
    }
  }

}
