package fgafa.array.parenthese;


public class ValidParentheses
{   
    
    /*
     * Given a string containing just the characters '(', ')', determine if the input string is valid.
     * 
     * The brackets must close in the correct order, 
     *  "(())" and "()()" are all valid 
     *  "(（）" and "（））" are not.
     */
    /*
     * Time O(n)  Space O(1)
     */
    public boolean isValidParentheses(String s) {
        if (null == s) {
            return true;
        }
    
        int sum = 0;
        for(char c : s.toCharArray()){
            if(c == '('){
                sum++;
            } else if(c == ')'){
                sum--;
                
                if(sum < 0){
                    return false;
                }
            }
        }
        
        return sum == 0;
    }
    


  public static void main(String[] args) {
    ValidParentheses sv = new ValidParentheses();

    String[] input = {
    		null,
    		"()",
    		")",
    		"(()",
    		"())",
    		"(())",
    		"(()))",
    		"(()())"};
    
    for(int i =0; i< input.length; i++){
    	System.out.println("\n Input: " + input[i] + "\t"+ sv.isValidParentheses(input[i])  );
    }
  }

}
