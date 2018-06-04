package fgafa.dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import fgafa.util.Misc;

/**
 * 
 * Given a collection of candidate numbers (C) and a target number (T), 
 * find all unique combinations in C where the candidate numbers sums to T.
 * 
 * Each number in C may only be used once in the combination.
 * 
 * Note:
 * All numbers (including target) will be positive integers.
 * Elements in a combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak).
 * The solution set must not contain duplicate combinations.
 * For example, given candidate set 10,1,2,7,6,1,5 and target 8, 
 * A solution set is: 
 * [1, 7] 
 * [1, 2, 5] 
 * [2, 6] 
 * [1, 1, 6] 
 * 
 * 
 *      
 */
public class CombinationSumII
{

  /**
   * 
   * @param candidates
   * @param target
   * @return
   */
  public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    List<List<Integer>> result = new ArrayList<>(); 
    //check
    if(candidates == null || candidates.length == 0 || target < 1)
      return result;
    
    //make candidates in nondecreasing order
    List<Integer> list = new ArrayList<Integer>();
    for(int i=0; i<candidates.length; i++){
      list.add(candidates[i]);    
    }
    Collections.sort(list);
        
    // recursion 
    Stack<Integer> stack = new Stack<>();
    combinationSum(result, list, list.size() - 1, target, stack);
    
    return result;
  }
  

private void combinationSum(List<List<Integer>> result, List<Integer> list, int end, int target, Stack<Integer> stack) {

    //the low-board is list.get(0), get the high-border x based on target.  ( list.get(x) <= target ) 
    if(end < 0 || target < 0 || list.get(0) > target)
      return;
       
    /* this can be improved by binary selection */
    while(list.get(end) > target)
      end -- ;

	Stack<Integer> clone1= (Stack<Integer>)stack.clone();
    
    int candidate = list.get(end);
    int gap = target - candidate;
    
    stack.add(candidate);
    if(gap == 0){
      addStack(result, stack);
      //return;
    }
    end--;
    
    combinationSum(result, list, end, gap, stack);      
    
    //skip the duplicate
    while(end >=0 && candidate == list.get(end))
      end --;
    
    combinationSum(result, list, end, target, clone1);
    
  }
  
  private void addStack(List<List<Integer>> result, Stack<Integer> stack ){
	    List<Integer> list = new ArrayList<Integer>();
	    
	    while(!stack.isEmpty()){
	      list.add(stack.pop());
	    }
	      
	    result.add(list);
 }
  
  
  /** Solution:
	  *   1 sorted C
	  *   2 define P[C, T, index] as the combinations,  
	  *   P[C, T, 0] 
	  *   => 
	  *      P[C_sorted, T-C[0], 1], path{0} 
	  *      ---
	  *      P[C_sorted, T - C[i], i+1], path{i} 
	  *      ---
	  *      P[C_sorted, T - C[in-1], n], path{n-1} 
	*/
	public List<List<Integer>> combinationSum2_X(int[] num, int target) {
		List<List<Integer>> res = new ArrayList<>();

		//check
		if (num == null || num.length < 1 || target < 1) {
			return res;
		}

		Arrays.sort(num);

		helper(res, new ArrayList<Integer>(), num, target, 0);

		return res;
	}

	private void helper(List<List<Integer>> result, List<Integer> path, int[] num,
			int target, int index) {

		if (target == 0) {
			result.add(new ArrayList<>(path));
			return;
		}

		if (index >= num.length) {
			return;
		}

		int prev = -1; //for case, there is duplicate number
		for (int i = index; i < num.length; i++) {
			if (num[i] > target) {
				break; // all the rest of elements are greater than target 
			}
			
			if (num[i] != prev) {
				path.add(num[i]);
				helper(result, path, num, target - num[i], i + 1);
				path.remove(path.size() - 1);
				
				prev = num[i];
			}
		}
		return;
	}
  
    /**
     * @param num: Given the candidate numbers
     * @param target: Given the target number
     * @return: All the combinations that sum to target
     */
    public List<List<Integer>> combinationSum2_n(int[] num, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        
        //check
        if(null == num || 0 == num.length || target < 1){
            return result;
        }
        
        Arrays.sort(num);
        
        combinationSum2(num, 0, target, new ArrayList<Integer>(), result);
        
        return result;
    }
    
    private void combinationSum2(int[] candidates, int index, int target, List<Integer> list, List<List<Integer>> result){
        if(0 == target){
            result.add(new ArrayList<Integer>(list));
            return;
        }
        
        int pre = -1;
        for(int i = index; i < candidates.length; i++){
            if(candidates[i] == pre){
                continue;
            }
                
            if(candidates[i] > target){
                break;
            }
            
            list.add(candidates[i]);
            
            combinationSum2(candidates, i+1, target - candidates[i], list, result);
            
            list.remove(list.size() - 1);
            pre = candidates[i];
        }
    }
	
  public static void main(String[] args){
    
    System.out.println("==========start=========="+System.currentTimeMillis());
    
    int[][] arr = {null, {}, {2}, {2}, {1,2,3}, {2, 3, 6, 7}, {10,1,2,7,6,1,5}};
    int[] target = {0,0,1,3,3,7, 8};
    
    CombinationSumII sv = new CombinationSumII();
    for(int i=0; i< arr.length; i++){
      System.out.println("\n  Input:");
      System.out.println(Misc.array2String(arr[i]) + "\t" + target[i]);
      
      List<List<Integer>> result = sv.combinationSum2(arr[i], target[i]);
      System.out.println(" Output:");
      Misc.printListList(result);
      
      //sv.init();
    }

    System.out.println("===========end==========="+System.currentTimeMillis());
  }

}
