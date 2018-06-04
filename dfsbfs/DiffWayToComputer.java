package fgafa.dfsbfs;

import java.util.ArrayList;
import java.util.List;

import fgafa.util.Misc;

/**
 * 
 * Given a string of numbers and operators, return all possible results from
 * computing all the different possible ways to group numbers and operators. The
 * valid operators are +, - and *.
 * 
 * 
 * Example 1  Input: "2-1-1".
 * 
 * ((2-1)-1) = 0 
 * (2-(1-1)) = 2 
 * Output: [0, 2]
 * 
 * 
 * Example 2 Input: "2*3-4*5"
 * 
 * (2*(3-(4*5))) = -34 
 * ((2*3)-(4*5)) = -14 
 * ((2*(3-4))*5) = -10 
 * (2*((3-4)*5)) = -10 
 * (((2*3)-4)*5) = 10 
 * Output: [-34, -14, -10, -10, 10]
 *
 */
public class DiffWayToComputer {

    public List<Integer> diffWaysToCompute(String input) {
        //check ignore, 
    	
    	return diffWaysToCompute(input, 0, input.length());
    }
    
    private List<Integer> diffWaysToCompute(String input, int start, int end){
    	List<Integer> result = new ArrayList<>();
    	boolean isNum = true;
    	char c;
    	for(int i=start; i<end; i++){
    		c = input.charAt(i);
    		
    		if( '+' == c || '-' == c || '*' == c ){
    			isNum = false;
    			
    			List<Integer> leftList = diffWaysToCompute(input, start, i);
    			List<Integer> rightList = diffWaysToCompute(input, i+1, end);
    			
    			for(int left : leftList){
    				for(int right : rightList){
    					result.add(calculate(left, c, right));
    				}
    			}
    		}//else ignore
    	}
    	
    	if(isNum){
    		result.add(Integer.valueOf(input.substring(start, end).trim()));
    	}
    	
    	return result;
    }
    
    private int calculate(int a, char sign, int b){
    	switch(sign){
    		case '+':
    			return a + b;
    		case '-':
    			return a - b;
    		default: //case "*":
    			return a * b;
    	}
    }
    
	public static void main(String[] args) {
	       String[] input = {
                   "2", //
                   "3+2*2", // 
                   "2-1-1", // 
                   "2*3 - 4*5" //
                   
       };
       
       DiffWayToComputer sv = new DiffWayToComputer();
       
       for(String s : input){
           System.out.println(String.format(" %s ", s ));
           Misc.printArrayList_Integer(sv.diffWaysToCompute(s));
       }

	}

}
