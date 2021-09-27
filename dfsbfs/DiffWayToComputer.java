package dfsbfs;

import util.Misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Leetcode #241
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

	public List<Integer> diffWaysToCompute_n(String input) {
		final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";
		String[] tokens = input.split(String.format(WITH_DELIMITER, "\\+") + "|" + String.format(WITH_DELIMITER, "-") + "|" + String.format(WITH_DELIMITER, "\\*"));

		int n = tokens.length;
		List<Integer>[][] cache = new ArrayList[n][n + 1];

		List<Integer> result = dfs(tokens, 0, n, cache);

		Collections.sort(result);

		return result;
	}

	private List<Integer> dfs(String[] tokens, int s, int e, List<Integer>[][] cache){
		if(cache[s][e] != null){
			return cache[s][e];
		}

		cache[s][e] = new ArrayList<>();

		if(s + 1 == e){
			cache[s][e].add(Integer.valueOf(tokens[s]));
			return cache[s][e];
		}

		for(int i = s + 1; i < e; i += 2){
			List<Integer> left = dfs(tokens, s, i, cache);
			List<Integer> right = dfs(tokens, i + 1, e, cache);

			for(int l : left){
				for(int r : right){
					cache[s][e].add(calculate(l, tokens[i], r));
				}
			}
		}

		return cache[s][e];
	}

	private int calculate(int a, String sign, int b){
		switch(sign){
			case "+":
				return a + b;
			case "-":
				return a - b;
			default: //case "*":
				return a * b;
		}
	}


	/**         **/

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
	       String[] inputs = {
                   "2", //
                   "3+2*2", // 
                   "2-1-1", // 
                   "2*3 - 4*5" //
                   
       };



       DiffWayToComputer sv = new DiffWayToComputer();
       
       for(String s : inputs){
           System.out.println(String.format(" %s ", s ));

		   //System.out.println(Arrays.toString(split(s)));

           Misc.printArrayList_Integer(sv.diffWaysToCompute(s));
       }

	}

	private static String[] split(String input){
		final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";

		return input.split(String.format(WITH_DELIMITER, "\\+") + "|" + String.format(WITH_DELIMITER, "-") + "|" + String.format(WITH_DELIMITER, "\\*"));
	}

}
