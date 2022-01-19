package datastructure.LRU;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following
 * operations: get and set.
 *
 * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 *
 * set(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it
 * should invalidate the least recently used item before inserting a new item.
 * 
 * Map<key, value> + List<key>
 * the time of get(int key) and set(int key, int value) is O(n), because it's to remove by Object
 */
public class LRUCache {
    private int capacity;
    private Map<Integer, Integer> map; //<key, value>
    private List<Integer> linkedList; // list of key
    
    public LRUCache(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be bigger than 0");
        }

        this.capacity = capacity;

        map = new HashMap<>(capacity);
        linkedList = new LinkedList<>();
    }

    /*Time O(n), remove by Object*/
    public int get(int key) {
        int value = -1;
        if (map.containsKey(key)) {
            value = map.get(key);

            linkedList.remove(Integer.valueOf(key)); //remove by object, O(n)
            linkedList.add(key);
        }

        return value;
    }

    /*Time O(n), remove by Object, and it's WRONG when there is duplicated values*/
    public void set(int key, int value) {
        if (map.containsKey(key)) {
            linkedList.remove(Integer.valueOf(key)); //remove by object
        }

        while (map.size() >= capacity) {
            int oldKey = linkedList.get(0);

            map.remove(oldKey);
            linkedList.remove(0); //remove by index
        }

        map.put(key, value);
        linkedList.add(key);
    }


}
