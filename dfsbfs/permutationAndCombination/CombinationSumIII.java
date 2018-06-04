package fgafa.dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Find all possible combinations of k numbers that add up to a number n,
 * given that only numbers from 1 to 9 can be used and each combination
 * should be a unique set of numbers.
 * 
 * Ensure that numbers within the set are sorted in ascending order.
 * 
 * Example 1:
 * Input: k = 3, n = 7
 * Output: [[1,2,4]]
 * 
 * Example 2:
 * Input: k = 3, n = 9
 * Output: [[1,2,6], [1,3,5], [2,3,4]]
 * 
 */

public class CombinationSumIII {


	public List<List<Integer>> combinationSum3(int k, int n) {
		List<List<Integer>> result = new ArrayList<>();
		
		if(n < k || n > 9 * k){
			return result;
		}
		
		helper(result, new ArrayList<>(), 1, k, n);
		
		return result;
	}
	
	private void helper(List<List<Integer>> result, List<Integer> path, int num, int k, int target){
		if( 0 == target){
			result.add(new ArrayList<>(path));
			return;
		}
		
		for(int i=num; i<10; i++){
			if (i*k > target) {
				break; // all the rest of elements are too great
			}
			
			path.add(i);
			helper(result, path, i, k, target - i);
			path.remove(path.size() - 1);
		}
	}
	
    public List<List<Integer>> combinationSum3_n(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        
        //check
        if(k < 1 || k > 9 || n < k || n >= k * 9){
            return result;
        }
        
        combinationSum3(k, n, 1, new ArrayList<Integer>(), result);
        return result;
    }
    
    private void combinationSum3(int k, int n, int num, List<Integer> list, List<List<Integer>> result){

        if(0 == k&& 0 == n){
            result.add(new ArrayList<>(list));
            return;
        }else if(k <= 0){
            return;
        }
        
        for( int i = num; i < 10; i++ ){
            if(n < i * k){
                break;// all the rest of elements are too great
            }
                
            if( n >= i && n < k * 9){
                list.add(i);
                
                combinationSum3(k - 1, n - i, i + 1, list, result);
                
                list.remove(list.size() - 1);
            }
        }
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
