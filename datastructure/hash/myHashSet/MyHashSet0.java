package datastructure.hash.myHashSet;

/**
 *
 * Design a HashSet without using any built-in hash table libraries.
 *
 * To be specific, your design should include these functions:
 *
 * add(value): Insert a value into the HashSet.
 * contains(value) : Return whether the value exists in the HashSet or not.
 * remove(value): Remove a value in the HashSet. If the value does not exist in the HashSet, do nothing.
 *
 * Example:
 *
 * MyHashSet hashSet = new MyHashSet();
 * hashSet.add(1);
 * hashSet.add(2);
 * hashSet.contains(1);    // returns true
 * hashSet.contains(3);    // returns false (not found)
 * hashSet.add(2);
 * hashSet.contains(2);    // returns true
 * hashSet.remove(2);
 * hashSet.contains(2);    // returns false (already removed)
 *
 * Note:
 *
 * All values will be in the range of [0, 1000000].
 * The number of operations will be in the range of [1, 10000].
 * Please do not use the built-in HashSet library.
 *
 */

public class MyHashSet0 {
    boolean[] keys;

    /** Initialize your data structure here. */
    public MyHashSet0() {
        keys = new boolean[1000001];
    }

    public void add(int key) {
        if(!valid(key)){
            return;
        }

        keys[key] = true;
    }

    public void remove(int key) {
        if(!valid(key)){
            return;
        }

        keys[key] = false;
    }

    /** Returns true if this set contains the specified element */
    public boolean contains(int key) {
        if(!valid(key)){
            return false;
        }

        return keys[key];
    }

    private boolean valid(int key){
        return key > -1 && key < 1000001;
    }
}

/**
 * Your MyHashSet object will be instantiated and called as such:
 * MyHashSet1 obj = new MyHashSet1();
 * obj.add(key);
 * obj.remove(key);
 * boolean param_3 = obj.contains(key);
 */
