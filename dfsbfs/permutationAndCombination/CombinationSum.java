package fgafa.dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import fgafa.util.Misc;

/**
 * 
 * Given a set of candidate numbers (C) and a target number (T), 
 * find all unique combinations in C where the candidate numbers sums to T.
 * 
 * The same repeated number may be chosen from C unlimited number of times.
 * 
 * Note:
 * All numbers (including target) will be positive integers.
 * Elements in a combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak).
 * The solution set must not contain duplicate combinations.<br>
 * 
 * For example, given candidate set 2,3,6,7 and target 7, 
 * A solution set is: 
 * [7] 
 * [2, 2, 3] 
 *
 * Solution:
 *   1 sorted C
 *   2 define P[C, T, n-1] as the combinations,  
 *   P[C, T, index] 
 *   => 
 *      P[C_sorted, T-C[n-1], n-2], n-1
 *      P[C_sorted, T-C[n-2], n-3], n-2
 *      ---
 *   =>
 *      P[C_sorted, T-C[n-1]-C[n-2], n-3]
 *      P[C_sorted, T-C[n-1], n-3]
 *      P[C_sorted, T-C[n-2], n-3]
 *      P[C_sorted, T, n-3]

 *
 */
public class CombinationSum
{
  
  /**
   * 
   * @param candidates
   * @param target
   * @return
   */
  public List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<Integer>> result = new ArrayList<>();
    //check
    if(candidates == null || candidates.length == 0 || target < 1)
      return result;
    
    //make candidates in nondecreasing order
    //TODO, filte the duplicate . 
    ArrayList<Integer> list = new ArrayList<Integer>();
    for(int i=0; i<candidates.length; i++){
      list.add(candidates[i]);    
    }
    Collections.sort(list);
        
    //recurative 
    Stack<Integer> stack = new Stack<Integer>();
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

    @SuppressWarnings("unchecked")
	Stack<Integer> clone1= (Stack<Integer>)stack.clone();
    combinationSum(result, list, end - 1, target, clone1);
    
    int candidate = list.get(end);
    int gap = target - candidate;
    
    stack.add(candidate);
    if(gap == 0){
      addStack(result, stack);
      return;
    }else{
      combinationSum(result, list, end, gap, stack);
    }  
    
  }
  
  
  private void addStack(List<List<Integer>> result, Stack<Integer> stack ){
    List<Integer> list = new ArrayList<>();
    
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
	  *      P[C_sorted, T-C[0], 0], path{0} 
	  *      ---
	  *      P[C_sorted, T - C[i], i], path{i} 
	  *      ---
	  *      P[C_sorted, T - C[in-1], n-1], path{n-1} 
	*/
	public List<List<Integer>> combinationSum_X(int[] candidates, int target) {
		List<List<Integer>> res = new ArrayList<>();

		if (candidates == null || candidates.length < 1 || target < 1) {
			return res;
		}

		Arrays.sort(candidates);

		helper(res, new ArrayList<Integer>(), candidates, target, 0);

		return res;
	}

	private void helper(List<List<Integer>> result,
			List<Integer> path, int[] nums, int target, int index) {
		if (target == 0) {
			result.add(new ArrayList<Integer>(path));
			return;
		}
		
		int prev = -1; //for case, there is duplicate number
		for (int i = index; i < nums.length; i++) {
			if (nums[i] > target) {
				break; // all the rest of elements are greater than target 
			}

			if (nums[i] != prev) {	
				path.add(nums[i]);
				helper(result, path, nums, target - nums[i], i);
				path.remove(path.size() - 1);
	
				prev = nums[i];
			}
		}

		return;
	}
  
	
    /**
     * @param candidates: A list of integers
     * @param target:An integer
     * @return: A list of lists of integers
     */
    public List<List<Integer>> combinationSum_n(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        
        //check
        if(null == candidates || 0 == candidates.length || target < 1){
            return result;
        }
        
        Arrays.sort(candidates);
        
        combinationSum(candidates, 0, target, new ArrayList<Integer>(), result);
        
        return result;
    }
    
    private void combinationSum(int[] candidates, int index, int target, List<Integer> list, List<List<Integer>> result){
        if(0 == target){
            result.add(new ArrayList<Integer>(list));
            return;
        }
        
        for(int i = index; i < candidates.length; i++){
            if(i > 0 && candidates[i] == candidates[i - 1]){
                continue;
            }
                
            if(candidates[i] > target){
                break;
            }
            
            list.add(candidates[i]);
            
            combinationSum(candidates, i, target - candidates[i], list, result);
            
            list.remove(list.size() - 1);
        }
    }
    
    
	
  public static void main(String[] args){
    
    System.out.println("==========start=========="+System.currentTimeMillis());
    
    int[][] arr = {null, {}, {2}, {2}, {1,2,3}, {2, 3, 6, 7}
        ,{28,23,41,49,21,47,48,35,36,44,26,20,37,24,46,33,30,22,25,31,32,45,43,42,40,39,34,29,27}
        ,{36,48,43,21,41,44,30,35,42,26,25,22,28,38,29,34,24,37,46,32,33,39,47,49,40,45,27,31}};
    int[] target = {0,0,1,3,3,7, 69, 60};
    
    CombinationSum sv = new CombinationSum();
    for(int i=0; i< arr.length; i++){
      System.out.println("\n  Input:");
      System.out.println(Misc.array2String(arr[i]) + "\t" + target[i]);
      
      //List<List<Integer>> result = sv.combinationSum(arr[i], target[i]);
      List<List<Integer>> result = sv.combinationSum_X(arr[i], target[i]);
      
      System.out.println(" Output:");
      Misc.printListList(result);
      
      //sv.init();
    }

    System.out.println("===========end==========="+System.currentTimeMillis());
  }
  
  
}
