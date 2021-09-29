package array.subArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class SubarraySumClosest {

	/**
	 * 
	 * Given an integer array, find a subarray with sum closestPair to zero. Return
	 * the indexes of the first number and last number.
	 * 
	 * Example
	 * Given [-3, 1, 1, -3, 5], return [0, 2], [1, 3], [1, 1], [2, 2] or [0, 4]
	 * 
	 * Challenge
	 *    O(nlogn) time
	 */
    public ArrayList<Integer> subarraySumClosest(int[] nums) {
    	ArrayList<Integer> ret = new ArrayList<>();
    	
        //check
    	if(null == nums || 0 == nums.length){
    		return ret;
    	}
    	
    	long sum = 0;
    	Map<Long, Integer> sums = new HashMap<>();
    	sums.put(0L, -1);
    	
    	for(int i = 0; i < nums.length; i++){
    		sum += nums[i];
    		
    		if(sums.containsKey(sum)){ // diff is 0
    			ret.add(sums.get(sum) + 1);
    			ret.add(i);
    			
    			return ret;
    		}
    		
    		sums.put(sum, i);
    	}
    	
    	List<Long> list = new ArrayList<Long>(sums.keySet());
    	Collections.sort(list);
    	
    	long minDiff = Long.MAX_VALUE;
    	long currDiff;
        long[] indexs = new long[2];
    	for(int i = 1; i < list.size(); i++){
    		currDiff = Math.abs(list.get(i) - list.get(i - 1));
    		if(minDiff > currDiff){
    			indexs[0] = list.get(i);
    			indexs[1] = list.get(i - 1);
    			
    			minDiff = currDiff;
    		}  		
    	}
    	
    	int left = Math.min(sums.get(indexs[0]), sums.get(indexs[1]));
    	int right = Math.max(sums.get(indexs[0]), sums.get(indexs[1]));
    	ret.add(left + 1);
    	ret.add(right);
    	return ret;
    }
	
    
    public ArrayList<Integer> subarraySumClosest_2(int[] nums) {
    	ArrayList<Integer> ret = new ArrayList<>();
    	
        //check
    	if(null == nums || 0 == nums.length){
    		return ret;
    	}
    	
    	long sum = 0;
    	List<SubArray> list = new ArrayList<>(nums.length + 1);
    	list.add(new SubArray(-1, 0));
    	for(int i = 0; i < nums.length; i++){
    		sum += nums[i];
    		
    		list.add(new SubArray(i, sum));
    	}
    	
    	Collections.sort(list);
    	
    	long minDiff = Long.MAX_VALUE;
    	long currDiff;
        int[] indexs = new int[2];
    	for(int i = 1; i < list.size(); i++){
    		currDiff = list.get(i).sum - list.get(i - 1).sum;
    		if(minDiff > currDiff){
    			indexs[0] = list.get(i).endIndex;
    			indexs[1] = list.get(i - 1).endIndex;
    			
    			minDiff = currDiff;
    		}  		
    	}
    	
    	int left = Math.min(indexs[0], indexs[1]);
    	int right = Math.max(indexs[0], indexs[1]);
    	ret.add(left + 1);
    	ret.add(right);
    	return ret;
    }
    
    class SubArray implements Comparable<SubArray>{
        int endIndex;
        long sum;
        
        SubArray(int endIndex, long sum){
            this.endIndex = endIndex;
            this.sum = sum;
        }
        
        @Override
        public int compareTo(SubArray o){
            return (int)(this.sum - o.sum);
        }
    }
    
    public ArrayList<Integer> subarraySumClosest_treeset(int[] nums) {
        ArrayList<Integer> ret = new ArrayList<>();
        
        //check
        if(null == nums || 0 == nums.length){
            return ret;
        }
        
        long sum = 0;
        Map<Long, Integer> sumToIndex = new HashMap<>();
        sumToIndex.put(0L, -1);
        
        TreeSet<Long> treeset = new TreeSet<>();
        treeset.add(0L);
        
        long minDiff = Long.MAX_VALUE;
        long curr;
        long currDiff;
        long[] indexs = new long[2];
        
        for(int i = 0; i < nums.length; i++){
            sum += nums[i];

            long floor = treeset.floor(sum) != null ? treeset.floor(sum) : Long.MAX_VALUE;
            long ceil = treeset.ceiling(sum) != null ? treeset.ceiling(sum) : Long.MAX_VALUE;
            
            if(sum - floor > ceil - sum){
                curr = ceil;
                currDiff = ceil - sum;
            }else{
                curr = floor;
                currDiff = sum - floor;
            }
            
            if(minDiff > currDiff){
                indexs[0] = curr;
                indexs[1] = sum;
                
                minDiff = currDiff;
            }  

            
            sumToIndex.put(sum, i);
            treeset.add(sum);
        }
        
        int left = Math.min(sumToIndex.get(indexs[0]), sumToIndex.get(indexs[1]));
        int right = Math.max(sumToIndex.get(indexs[0]), sumToIndex.get(indexs[1]));
        ret.add(left + 1);
        ret.add(right);
        return ret;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
