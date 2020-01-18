package fgafa.game.majorityElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * Given an array of integers and a number k, the majority number is the number that occurs more than 1/k of the size of the array.

    Find it.
    
    Example
    Given [3,1,2,3,2,3,3,4,4,4] and k=3, return 3.
    
    Note
    There is only one majority number in the array.
    
    Challenge
    O(n) time and O(k) extra space
 *
 */

public class MajorityElementIII {
    /**
     * @param nums: A list of integers
     * @param k: As described
     * @return: The majority number
     */
    public int majorityNumber(ArrayList<Integer> nums, int k) {
    	final int ERROR = Integer.MAX_VALUE;
        //check
    	if(null == nums || k < 2 || k > nums.size()){
    		return ERROR;
    	}
    		
    	//main
    	Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    	Integer key;
    	for(Integer num : nums){
    		if(map.containsKey(num)){
    			map.put(num, map.get(num) + 1);
    		}else{
    			if(map.size() < k){
    				map.put(num, 1);
    			}else{
    				for(Iterator<Integer> it = map.keySet().iterator(); it.hasNext(); ){
    					key = it.next();
    					if(1 == map.get(key)){
    						it.remove();
    					}else{
    						map.put(key, map.get(key) - 1);
    					}
    				}
    			}
    		}
    	}
    	
    	//return
    	if(!map.isEmpty()){
	    	for(Integer num : map.keySet()){
	    		map.put(num, 0);
	    	}
	    	for(Integer num : nums){
	    		if(map.containsKey(num)){
	    			map.put(num, map.get(num) + 1);
	    		}
	    	}
	    	final int BAR = nums.size() / k;
	    	for(Integer num : map.keySet()){
	    		if(map.get(num) > BAR){
	    			return num;
	    		}
	    	}
	    }
    	
    	return ERROR;
    }
    
    public static void main(String[] args){
    	Map<Integer, Integer> map = new HashMap<>();
    	map.put(1, 1);
    	map.put(2, 2);
    	
    	for(Integer key : map.keySet()){
    		map.put(key, 11);
    	}
    	
    	System.out.println(map.values().toArray(new Integer[2]));
    }
}
