package array.subSum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Design and implement a TwoSum class. It should support the following operations: add and find.
    add(number) - Add the number to an internal data structure.
    find(target) - Find if there exists any pair of numbers which sum is equal to the value.
    For example,
    add(1); add(3); add(5);
    find(4) -> true
    find(7) -> false
    
    Understand the problem:
    The problem asks for designing a class TwoSum which supports two operations, add and find. There are two data structures / design decisions to think about. 
     1. Use two pointers. Requires the list to be sorted. 
           -- add: insert an element to a sorted array requires O(logn) time. 
           -- find: use two pointers. Takes O(n) time. 
    
    2. Use a hash map. 
           -- Add takes O(1) time. 
           -- Find takes O(n) time. 
    
    Note that we have to use  the map instead of the set because we need to counter the number of duplicates for a number. e.g. add(0), add(0) and find(0). 
 *
 */

class TwoSumII {
    private Map<Integer, Integer> map = new HashMap<>(); // <value, count>

    public void add(int number){
        if(map.containsKey(number)){
            map.put(number, map.get(number) + 1);
        }else{
            map.put(number, 1);
        }
    }
    
    public boolean find(int target) {
        for (Map.Entry<Integer, Integer> pair : map.entrySet()) {
            int other = target - pair.getKey();

            if ((pair.getKey() == other && pair.getValue() > 1)
                        || (pair.getKey() != other && map.containsKey(other))) {
                return true;
            }
        }
        return false;
    }
}

/**
 * Follow-up: 
    What if you add a few, but search very heavily? You need to make sure search only takes O(1) time. Add(num), add num with all the previous added numbers 
    search(target), only takes O(1) time.
*/
public class FasterSearchTwoSum{
    Set<Integer> twoSums = new HashSet<>();
    List<Integer> inputs = new ArrayList<>();
    
    public void add(int number){
        for(int input : inputs){
            twoSums.add(input + number);
        }
        
        inputs.add(number);
    }
    
    public boolean find(int target) {
        return twoSums.contains(target);
    }
}



