package array.subArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubarraySumEqualTo {

	/**
	 * 
	 * Given an integer array, find a subarray where the sum of numbers is zero.
	 * Your code should return the index of the first number and the index of
	 * the last number.
	 * 
	 * Example
	 * Given [-3, 1, 2, -3, 4], return [0, 2] or [1, 3].
	 *
	 * Note
	 * There is at least one subarray that it's sum equals to zero.
	 */
	/* Time O(n^2)*/
    public ArrayList<Integer> subarraySum(int[] nums) {
        ArrayList<Integer> ret = new ArrayList<>();
        
        //check
        if(null == nums || 0 == nums.length){
            return ret;
        }
        
        //main
        int sum = 0;
        for(int i = 0; i < nums.length; i++){
            sum = 0;
            for(int j = i; j < nums.length; j++){
                sum += nums[j];
                
                if(0 == sum){
                    ret.add(i);
                    ret.add(j);
                    return ret;
                }
            }
        }
        
        return ret;
    }
	
    /*Time O(n)  Space O(n)
     * 
     * Use a map to store the sub sum from 0 to i
     * if found the same sum from 0 to j. it means the sub sum are 0 from i+1 to j.
     *  
     * */
	public ArrayList<Integer> subarraySum_n(int[] nums) {
		ArrayList<Integer> ret = new ArrayList<>();

		// check
		if (null == nums || 0 == nums.length) {
			return ret;
		}

		// main
		int sum = 0;
		Map<Integer, Integer> map = new HashMap<>();
		map.put(0, -1);
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];

			if (map.containsKey(sum)) {
				ret.add(map.get(sum) + 1);
				ret.add(i);
				return ret;
			}

			map.put(sum, i);
		}

		return ret;
	}
	
    
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
