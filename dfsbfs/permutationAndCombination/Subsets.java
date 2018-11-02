package fgafa.dfsbfs.permutationAndCombination;

import fgafa.util.Misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Subsets
{

  /*
   * 
   * Given a set of distinct integers, S, return all possible subsets.
   * 
   * Note:
   * Elements in a subset must be in non-descending order.
   * The solution set must not contain duplicate subsets.
   * For example,
   * If S = [1,2,3], a solution is:
   * 
   * [
   *   [3],
   *   [1],
   *   [2],
   *   [1,2,3],
   *   [1,3],
   *   [2,3],
   *   [1,2],
   *   []
   * ]
   * 
   */
  
	/**
	 * 1, sorted, get {x, y}
	 * 2, 
	 *    init,      {} 
	 * => add s[1],  {}, {x} 
	 * => add s[2],  {}, {x}, {y}, {x,y} 
	 * =>
	 * 
	 */
  public List<List<Integer>> subsets_insert(int[] nums) {
      List<List<Integer>> ret = new LinkedList<>();
      
      //check
      if(null == nums || 0 == nums.length){
          return ret;
      }
      
      //main
      Arrays.sort(nums);
      
      List<Integer> subset = new LinkedList<Integer>();
      ret.add(subset);

      for(Integer num : nums){
          for(int i = ret.size() - 1; i >= 0; i--){
              subset = new LinkedList<>(ret.get(i)); //deep clone
              subset.add(num);
              ret.add(subset);
          }
      }
      
      return ret;
  }

	/**
	 * 1, sorted,
	 * 2, dfs(S, index, subset, result)
	 * 
	 */
  public List<List<Integer>> subsets_recursive(int[] nums) {
	  List<List<Integer>> result = new ArrayList<>();
      if(null == nums || 0 == nums.length){
    	  return result;
      }
      
      Arrays.sort(nums);
      
      dfs(nums, 0, new LinkedList<>(), result);
      
      return result;
  }

  private void dfs(int[] nums, int pos, List<Integer> subset, List<List<Integer>> result){
	  result.add(new LinkedList<>(subset)); //deep clone
	  
	  for( ; pos< nums.length; pos++){
		  subset.add(nums[pos]);
		  dfs(nums, pos+1, subset, result);
		  subset.remove(subset.size() - 1);
	  }
  }
  
	/**
	 * example:  S is {x, y, z} , 
	 * 2, sorted,
	 * 3, length is 3, the binary is 000 to 111, 2^n 
	 * 
	 * 000 - {}
	 * 001 - {z}
	 * 011 - {y, z}
	 * ---
	 * 111 - {x, y, z}
	 */
	public List<List<Integer>> subsets_binary(int[] nums) {
		List<List<Integer>> result = new ArrayList<>();
		if (null == nums || 0 == nums.length) {
			return result;
		}
		
		Arrays.sort(nums);
		
		List<Integer> subset;
		for(int n = (2 << nums.length) - 1 ; n >= 0; n-- ){
			subset = new ArrayList<>();
			for(int i = 0; i < nums.length; i++){
				if((n & 1) == 1 ){
					subset.add(nums[i]);
				}
				
				n >>= 1;
			}
			result.add(subset);
		}
			
		return result;
	}
	
  /*
   * 
   * Given a collection of integers that might contain duplicates, S, return all possible subsets.
   * 
   * Note:
   * Elements in a subset must be in non-descending order.
   * The solution set must not contain duplicate subsets.
   * For example,
   * If S = [1,2,2], a solution is:
   * 
   * [
   *   [2],
   *   [1],
   *   [1,2,2],
   *   [2,2],
   *   [1,2],
   *   []
   * ]
   * 
   */
  public List<List<Integer>> subsetsWithDup_insert(int[] nums) {
      List<List<Integer>> ret = new ArrayList<>();
      
      //check
      if(null == nums || 0 == nums.length){
          return ret;
      }
      
      Arrays.sort(nums);
      
      ArrayList<Integer> subset = new ArrayList<>();
      ret.add(subset);
      
      int size = 0;
      int start = 0;
      for(int i = 0; i < nums.length; i++){
          if(i == 0 || nums[i] != (nums[i - 1])){
              start = 0;
          }
          
          size = ret.size();
          for( ; start < size; start++){
              subset = new ArrayList<>(ret.get(start));
              subset.add(nums[i]);
              ret.add(subset);
          }
      }
      
      return ret;
  }
  
  public List<List<Integer>> subsetsWithDup_recursive(int[] nums) {
	  List<List<Integer>> result = new ArrayList<>();
      if(null == nums || 0 == nums.length){
    	  return result;
      }
      
      Arrays.sort(nums);
      
      dfsWithDup(nums, 0, new ArrayList<>(), result);
      
      return result;
  }

  private void dfsWithDup(int[] nums, int i, List<Integer> subset, List<List<Integer>> result){	  
	  result.add(new ArrayList<>(subset));
	  for( int j=i ; j< nums.length; j++){
		  if(j>i && nums[j] == nums[j-1]){
			  continue;
		  }
		  
		  subset.add(nums[j]);
		  dfsWithDup(nums, j+1, subset, result);
		  subset.remove(subset.size() - 1);
	  }
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
	Subsets sv = new Subsets();  
	  
    int[][] input = {
    		{3},
    		{1,3},
    		{1,3,6}
    		
    };

    for(int[] nums : input){
    	System.out.println("\n" + Misc.array2String(nums));
    	System.out.println(sv.subsets_insert(nums));
    	
    	System.out.println(sv.subsets_recursive(nums));
    	System.out.println(sv.subsets_binary(nums));
    }
    
	
    int[][] input2 = {
    		{3,3},
    		{1,3,3},
    		{1,3,3,3}
    		
    };

    for(int[] nums : input2){
    	System.out.println("\n" + Misc.array2String(nums));
    	
    	System.out.println(sv.subsetsWithDup_insert(nums));
    	System.out.println(sv.subsetsWithDup_recursive(nums));
    }
  }

}
