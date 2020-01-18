package fgafa.game.majorityElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fgafa.util.Misc;

/**
 * 
 * Given an integer array of size n, find all elements that appear more than 
 * ⌊  n/3 ⌋ times. The algorithm should run in linear time and in O(1) space.
 *
 */
public class MajorityElementII {

    public List<Integer> majorityElement(int[] nums) {
    	//init
        List<Integer> result = new ArrayList<>();
        //check
        if(null == nums || 0 == nums.length){
        	return result;
        }
        
        int[] candidates = new int[2];
        int[] counts = new int[2]; //default both are 0
		for (int num : nums) {
			if (counts[0] != 0 && candidates[0] == num) {
				counts[0]++;
			} else if (counts[1] != 0 && candidates[1] == num) {
				counts[1]++;
			} else if (counts[0] == 0) {
				candidates[0] = num;
				counts[0]++;
			} else if (counts[1] == 0) {
				candidates[1] = num;
				counts[1]++;
			} else {
				counts[0]--;
				counts[1]--;
			}
		}
        
        //return
        int[] newCounts = new int[2]; //default both are 0
        for(int num: nums){
			if (counts[0] != 0 && candidates[0] == num) {
				newCounts[0]++;
			} else if (counts[1] != 0 && candidates[1] == num) {
				newCounts[1]++;
			}
        }
     
        int x = nums.length / 3;
        if(newCounts[0]>x)
        	result.add(candidates[0]);
        if(newCounts[1]>x)
        	result.add(candidates[1]);

        return result;
    }
    
    /**
     * @param nums: A list of integers
     * @return: The majority number that occurs more than 1/3
     */
    public int majorityNumber(ArrayList<Integer> nums) {
    	final int ERROR = Integer.MAX_VALUE;
        //check
        if(null == nums || 3 > nums.size()){
            return ERROR; // exception return
        }
        
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(Integer num : nums){
            if(map.containsKey(num)){
                map.put(num, map.get(num) + 1);
            }else if(map.size() < 2){
                map.put(num, 1);
            }else{
                for(Iterator<Integer> it = map.keySet().iterator(); it.hasNext(); ){
                	Integer key = it.next();
                    if(1 == map.get(key)){
                        //map.remove(key); // throw ConcurrentModificationException 
                    	it.remove();
                    }else{
                        map.put(key, map.get(key) - 1);
                    }
                }
            }
        }
        
        //return 
        if( !map.isEmpty() ){
	    	for(Integer num : map.keySet()){
	    		map.put(num, 0);
	    	}
	    	for(Integer num : nums){
	    		if(map.containsKey(num)){
	    			map.put(num, map.get(num) + 1);
	    		}
	    	}
	    	final int BAR = nums.size() / 3;
	    	for(Integer num : map.keySet()){
	    		if(map.get(num) > BAR){
	    			return num;
	    		}
	    	}
        }
        	
        return ERROR;
    }
    
	public static void main(String[] args) {
		MajorityElementII sv = new MajorityElementII();
		
		int[][] input = {
				{1},                         // 1
				{1, 1},                      // 1
				{1, 1, 1},                   // 1  
				{1, 2, 1},                   // 1
				{1, 2, 3, 1},                // 1
				{1, 2, 1, 1},                // 1    
				{1, 2, 3, 1, 2},             // 1, 2
				{1, 2, 3, 1, 2, 1},          // 1   
				{1, 2, 3, 1, 2, 1, 1},       // 1   
				{1, 2, 3, 1, 2, 1, 2},       // 1, 2
				{1, 2, 3, 4}                 //
		};
		
		for(int[] nums : input){
			System.out.println("Input: " + Misc.array2String(nums));
			Misc.printList(sv.majorityElement(nums));
		}

	}

}
