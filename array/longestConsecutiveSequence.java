package fgafa.array;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    

    
    public int longestConsecutive_2(int[] num) {
        //check
        if(null == num || 0 == num.length){
            return 0;
        }
        
        int max = 0;
        Map<Integer, Interval> map = new HashMap<Integer, Interval>();
        Interval interval;
        for(int n : num){
            if(!map.containsKey(n)){
                interval = new Interval(n, n);
                map.put(n, interval);
                
                if(map.containsKey(n-1)){
                    interval.merge(map.get(n-1));
                }
                if(map.containsKey(n+1)){
                    interval.merge(map.get(n+1));
                }
                
                map.put(interval.start, interval);
                map.put(interval.end, interval);
                
                max = Math.max(max, interval.size());
            }
        }
        
        return max + 1;
    }
    
    class Interval{
        int start;
        int end;
        
        public Interval(int s, int e){
            this.start = s;
            this.end = e;
        }
        
        public void merge(Interval other){
//            if(this.end < other.start - 1 || other.end < this.start - 1){
//                return;
//            }
            
            this.start = Math.min(this.start, other.start);
            this.end = Math.max(this.end, other.end);
        }
        
        public int size(){
            return end - start;
        }
    }
    
    
	public static void main(String[] args) {


	}

}
