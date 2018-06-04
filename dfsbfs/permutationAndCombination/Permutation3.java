package fgafa.dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import fgafa.util.Misc;

public class Permutation3 {

    /**
     * @param nums: A list of integers.
     * @return: A list of permutations.
     */
    public ArrayList<ArrayList<Integer>> permute_swap(ArrayList<Integer> nums) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        //check
        if(null == nums || 0 == nums.size()){
            return result;
        }
        
        permute_swap(nums, 0, result);
        
        return result;
    }
    
    private void permute_swap(ArrayList<Integer> nums, int i, ArrayList<ArrayList<Integer>> result){
        if(i == nums.size() - 1){
            result.add(new ArrayList<Integer>(nums));
            return;
        }
        
        for(int j = i; j < nums.size(); j++){
            swap(nums, i, j);
            
            permute_swap(nums, i+1, result);
            
            swap(nums, i, j);
        }
    }
    
    private void swap(ArrayList<Integer> nums, int i, int j){
        int tmp = nums.get(i);
        nums.set(i, nums.get(j));
        nums.set(j, tmp);
    }
    
    /**
     * -- 插入递归全排列算法, 先取数组的第一个元素，作成数组，结果是 [0] 接下来取数组的第二个元素，按顺序插入以上数组的"空位"，结果是
     * e.g. input [1,2,3],  output 3! 
     * 
     * Step 1, insert 1:  [1]
     * Step 2, insert 2:  [12], [21] 
     * Step 3, insert 3:  [123], [132], [312], [213] [231], [321]
     */
    public ArrayList<ArrayList<Integer>> permute_insert(ArrayList<Integer> nums) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        //check
        if(null == nums || 0 == nums.size()){
            return result;
        }
        
        result.add(new ArrayList<Integer>());
        ArrayList<Integer> curr;
        ArrayList<Integer> next;
        for(int i = 0; i < nums.size(); i++){
        	for(int j = result.size() - 1; j >= 0 ; j--){
        		curr = result.get(j);
        		result.remove(j);
        		
            	for(int k = curr.size(); k >= 0; k--){
            		next = new ArrayList<>(curr);
            		next.add(k, nums.get(i));
            		result.add(next);
            	}
        	}
        }
        
        return result;
    }
    
    public ArrayList<ArrayList<Integer>> permuteUnique_swap(ArrayList<Integer> nums) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        //check
        if(null == nums || 0 == nums.size()){
            return result;
        }
        
        Collections.sort(nums);
        permuteUnique_swap(nums, 0, result);
        
        return result;
    }
    
    private void permuteUnique_swap(ArrayList<Integer> nums, int i, ArrayList<ArrayList<Integer>> result){
        if(i == nums.size() - 1){
            result.add(new ArrayList<Integer>(nums));
            return;
        }
        
        for(int j = i; j < nums.size(); j++){
        	if(j > i && nums.get(j) == nums.get(j - 1)){
        		continue;
        	}
            swap(nums, i, j);
            
            permuteUnique_swap(nums, i+1, result);
            
            swap(nums, i, j);
            
        }
    }
    
    public ArrayList<ArrayList<Integer>> permuteUnique_insert(ArrayList<Integer> nums) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        //check
        if(null == nums || 0 == nums.size()){
            return result;
        }
        
        Collections.sort(nums);
        
        result.add(new ArrayList<Integer>());
        ArrayList<Integer> curr;
        ArrayList<Integer> next;
        for(int i = 0; i < nums.size(); i++){
        	for(int j = result.size() - 1; j >= 0 ; j--){
        		curr = result.get(j);
        		result.remove(j);
        		
            	for(int k = curr.size(); k >= 0; k--){
            		if(k < curr.size() && curr.get(k) == nums.get(i)){
            			break;
            			//k = -1;
            		}
            		
            		next = new ArrayList<>(curr);
            		
            		next.add(k, nums.get(i));
            		
            		result.add(next);
            		
            	}
        	}
        }
        
        return result;
    }
    
	public static void main(String[] args) {
		Permutation3 sv = new Permutation3();

		Integer[][] input_unique = { { 1 }, {0,1}, { 1, 2 }, { 1, 2, 3 } };
/**  
		for (Integer[] num : input_unique) {
			System.out.println("\nInput:" + Misc.array2String(num));

			System.out.println("--permutation_swap--");
			Misc.printListList(sv.permute_swap(new ArrayList<Integer>(Arrays.asList(num))));
			
			System.out.println("--permutation_insert--");
			Misc.printListList(sv.permute_insert(new ArrayList<Integer>(Arrays.asList(num))));

			System.out.println("--permuteUnique_swap--");
			Misc.printListList(sv.permuteUnique_swap(new ArrayList<Integer>(Arrays.asList(num))));
			
			System.out.println("--permuteUnique_insert--");			
			Misc.printListList(sv.permuteUnique_insert(new ArrayList<Integer>(Arrays.asList(num))));
		}
*/

		Integer[][] input_duplicate = { { 1, 2, 2 },
				{ 1, 2, 2, 2 }, { 1, 2, 2, 2, 3 }, { 0, 1, 0, 0, 9 },
				{ 3, 3, 0, 0, 2, 3, 2 } };
		
		for (Integer[] num : input_duplicate) {
			System.out.println("\nInput:" + Misc.array2String(num));

//			System.out.println("--permutation_swap--");
//			Misc.printListList(sv.permute_swap(num));
//			
//			System.out.println("--permutation_insert--");
//			Misc.printListList(sv.permute_insert(num));

			System.out.println("--permuteUnique_swap--");
			Misc.printListList(sv.permuteUnique_swap(new ArrayList<Integer>(Arrays.asList(num))));
			
			System.out.println("--permuteUnique_insert--");			
			Misc.printListList(sv.permuteUnique_insert(new ArrayList<Integer>(Arrays.asList(num))));
		}
	}

}
