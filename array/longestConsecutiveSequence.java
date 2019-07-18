package fgafa.array;

import java.util.HashSet;
import java.util.Set;

/*
 * Given an unsorted array of integers, find the length of the longest
 * consecutive elements sequence.
 * 
 * For example, Given [100, 4, 200, 1, 3, 2], The longest consecutive
 * elements sequence is [1, 2, 3, 4]. Return its length: 4.
 * 
 * Your algorithm should run in O(n) complexity.
 */

public class longestConsecutiveSequence {


	/*
	 * Because it requires O(n) complexity, we can not solve the problem by
	 * sorting the array first. Sorting takes at least O(nlogn) time.
	 * 
	 * We can use a HashSet to add and remove elements. HashSet is implemented
	 * by using a hash table. Elements are not ordered. The add, remove and
	 * contains methods have constant time complexity O(1).
	 * 
	 * cases"
	 * duplicate elements ?
	 * 
	 */
    public int longestConsecutive(int[] num) {
        if(null == num || 0 == num.length)
        	return 0;
        
        Set<Integer> set = new HashSet<>(num.length);
        for(int i : num){
        	set.add(i);
        }
        
        int max = 0;
        int count = 0;
        int smaller;
        int bigger;
        for(int i: num){
        	if(set.contains(i)){
        		count = 1;
        		set.remove(i);
        		
        		smaller = i - 1;
        		while(set.contains(smaller)){
        			count ++;
        			set.remove(smaller);
        			
        			smaller--;
        		}
        		
        		bigger = i + 1;
        		while(set.contains(bigger)){
        			count++;
        			set.remove(bigger);
        			
        			bigger++;
        		}
        		
        		max = Math.max(max, count);
        	}
        }
        
        return max;
    }

    public int longestConsecutive_n(int[] nums) {
        if(null == nums){
            return 0;
        }

        if(nums.length < 2){
            return nums.length;
        }

        Set<Integer> set = new HashSet<>();
        for(int n : nums){
            set.add(n);
        }

        int max = 0;
        for(int n : nums){
            if(!set.contains(n - 1)){
                int count = 1;

                while(set.contains(++n)){
                    count++;
                }

                max = Math.max(max, count);
            }
        }

        return max;
    }
    

    
	public static void main(String[] args) {


	}

}
