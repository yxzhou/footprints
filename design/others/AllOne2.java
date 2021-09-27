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

public class AllOne2 {

    Map<String, Integer> datas = new HashMap<>();; // <key, value>
    Map<Integer, Set<String>> ranks = new HashMap<>();; //<value, set of key>
    int max = 0;
    int min = 1;

    /** Initialize your data structure here. */
    public AllOne2() {
    }

    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {
        int rank = datas.getOrDefault(key, 0);
        ranks.get(rank).remove(key);

        rank++;
        datas.put(key, rank);
        ranks.computeIfAbsent(rank, x -> new HashSet<>()).add(key);

        min = Math.min(min, rank);
        if(ranks.get(min).isEmpty()){
            min = rank;
        }

        max = Math.max(max, rank);
    }

    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public void dec(String key) {
        int rank = datas.getOrDefault(key, 0);
        if(rank == 0){
            return;
        }

        ranks.get(rank).remove(key);

        if(ranks.get(max).isEmpty()){
            max--;
        }

        if(rank == 1){
            datas.remove(key);

            while(min < max && ranks.get(min).isEmpty()){
                min++;
            }
        }else{
            rank--;

            datas.put(key, rank);
            ranks.computeIfAbsent(rank, x -> new HashSet<>()).add(key);

            min = Math.min(min, rank);
        }
    }

    /** Returns one of the keys with maximal value. */
    public String getMaxKey() {
        if(datas.isEmpty()){
            return "";
        }

        return ranks.get(max).stream().findFirst().get();
    }

    /** Returns one of the keys with Minimal value. */
    public String getMinKey() {
        if(datas.isEmpty()){
            return "";
        }

        return ranks.get(min).stream().findFirst().get();
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
