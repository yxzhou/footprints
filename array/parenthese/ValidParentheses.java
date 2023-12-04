package array.parenthese;

import junit.framework.Assert;

/**
 * 
 * Given a string containing just the characters '(', ')', determine if the input string is valid.
 * 
 * The brackets must close in the correct order, 
 *  "(())" and "()()" are all valid 
 *  "(（）" and "（））" are not.
 * 
 * @author yuanxi
 */

public class ValidParentheses
{   
    
    /*
     *
     * Time O(n)  Space O(1)
     */
    public boolean isValidParentheses(String s) {
        if (null == s) {
            return true;
        }
    
        int openCount = 0;
        char c;
        for(int i = 0, n = s.length(); i < n; i++){
            c = s.charAt(i);
            
            if(c == '('){
                openCount++;
            } else if(c == ')'){                
                if(openCount < 1){
                    return false;
                }
                
                openCount--;
            }
            
        }
        
        return 0 == openCount;
    }
    


  public static void main(String[] args) {
    ValidParentheses sv = new ValidParentheses();

    String[][] inputs = {
        //{s, expect}
        {null, "true"},
        {"()", "true"},
        {")", "false"},
        {"(()", "false"},
        {"())", "false"},
        {"(())", "true"},
        {"(()))", "false"},
        {"(()())", "true"},
                        
    };
    
    for(String[] input : inputs){
    	System.out.println( String.format("\n call isValidParentheses: s = %s ", input[0]) );
        
        Assert.assertEquals( Boolean.parseBoolean(input[1]), sv.isValidParentheses(input[0]));
    }
  }

}
