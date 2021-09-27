package design.others;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Leetcode #432
 *
 * Implement a data structure supporting the following operations:
 *
 * Inc(Key) - Inserts a new key with value 1. Or increments an existing key by 1. Key is guaranteed to be a non-empty string.
 * Dec(Key) - If Key's value is 1, remove it from the data structure. Otherwise decrements an existing key by 1. If the key does not exist, this function does nothing. Key is guaranteed to be a non-empty string.
 * GetMaxKey() - Returns one of the keys with maximal value. If no element exists, return an empty string "".
 * GetMinKey() - Returns one of the keys with minimal value. If no element exists, return an empty string "".
 * Challenge: Perform all these in O(1) time complexity.
 *
 */

public class AllOne {

    Map<String, Integer> keys;
    Map<Integer, Set<String>> counts;
    int max;
    int min;

    /** Initialize your data structure here. */
    public AllOne() {
        keys = new HashMap<>();
        counts = new HashMap<>();

        max = Integer.MIN_VALUE;
        min = Integer.MAX_VALUE;
    }

    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {

        int curr = keys.getOrDefault(key, 0);
        int next = curr + 1;

        if(counts.containsKey(curr)){
            if(counts.get(curr).size() == 1){
                counts.remove(curr);
            }else{
                counts.get(curr).remove(key);
            }
        }

        counts.computeIfAbsent(next, x -> new HashSet<>()).add(key);
        keys.put(key, next);
        max = Math.max(max, next);
        min = Math.min(min, next);
        while(min <= max && !counts.containsKey(min)){
            min++;
        }
    }

    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public void dec(String key) {
        if(!keys.containsKey(key)){
            return;
        }

        int curr = keys.get(key);
        int pre = curr - 1;

        if(counts.get(curr).size() == 1){
            counts.remove(curr);
        }else{
            counts.get(curr).remove(key);
        }

        keys.remove(key);

        if(pre > 0){
            counts.computeIfAbsent(pre, x -> new HashSet<>()).add(key);
            keys.put(key, pre);

            min = Math.min(min, pre);
            max = Math.max(max, pre);
        }

        while (min <= max && !counts.containsKey(max)){
            max--;
        }
    }

    /** Returns one of the keys with maximal value. */
    public String getMaxKey() {
        if(counts.containsKey(max)){
            return counts.get(max).stream().findFirst().get();
        }

        return "";
    }

    /** Returns one of the keys with Minimal value. */
    public String getMinKey() {
        if(counts.containsKey(min)){
            return counts.get(min).stream().findFirst().get();
        }

        return "";
    }
}

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */
