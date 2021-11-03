package array;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
 * 
 * For example, 
 *   Given [100, 4, 200, 1, 3, 2], 
 *   Return: 4.
 *   The longest consecutive elements sequence is [1, 2, 3, 4]. 
 * 
 * Your algorithm should run in O(n) complexity.
 * 
 * 
 */
public class longestConsecutiveSequence {


    /**
     * Because it requires O(n) complexity, we can not solve the problem by sorting the array first. Sorting takes at
     * least O(nlogn) time.
     *
     * We can use a HashSet to add and remove elements. HashSet is implemented by using a hash table. Elements are not
     * ordered. The add, remove and contains methods have constant time complexity O(1).
     *
     * cases" duplicate elements ?
     *
     */
    public int longestConsecutive_twopass(int[] num) {
        if (null == num || 0 == num.length) {
            return 0;
        }

        Set<Integer> set = new HashSet<>(num.length);
        for (int i : num) {
            set.add(i);
        }

        int max = 0;
        int count = 0;
        int smaller;
        int bigger;
        for (int i : num) {
            if (!set.contains(i)) {
                continue;
            }
            
            set.remove(i);
            count = 1;

            smaller = i - 1;
            while (set.contains(smaller)) {
                count++;
                set.remove(smaller);

                smaller--;
            }

            bigger = i + 1;
            while (set.contains(bigger)) {
                count++;
                set.remove(bigger);

                bigger++;
            }

            max = Math.max(max, count);
            
        }

        return max;
    }

    public int longestConsecutive_onepass(int[] nums) {
        if(nums == null){
            return 0;
        }

        Map<Integer, Integer> counts = new HashMap<>();
        int max = 0;
        int count;
        int big;
        int small;
        for(int x : nums){
            if(counts.containsKey(x)){ //for case, there are duplicate in the num
                continue;
            }

            small = x;
            if(counts.containsKey(x - 1)){
                small = x - counts.get(x - 1);
            }

            big = x;
            if(counts.containsKey(x + 1)){
                big = x + counts.get(x + 1);
            }

            count = big - small + 1;

            counts.put(small, count);
            counts.put(big, count);
            counts.put(x, count);

            max = Math.max(max, count);
        }

        return max;
    }

    public static void main(String[] args) {

    }

}
