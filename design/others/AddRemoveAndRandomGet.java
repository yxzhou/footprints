/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design.others;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * Design a data structure that supports all the following methods in average O(1) time: 1, add(int val), adds an item
 * val, return false if already present, true if success. 2, remove(int val), removes an item val, return false if not
 * present, true if success. 3, getRandom(), returns a random element, each element must have the same probability.
 *
 * follow up, the data maybe duplicated, see AddARemoveAndRandomGetII
 *
 */
public class AddRemoveAndRandomGet {

    Map<Integer, Integer> positions; // Map< value, index in the values >
    List<Integer> values; //List<value>

    Random random;

    public AddRemoveAndRandomGet() {
        positions = new HashMap<>();
        values = new ArrayList<>();

        random = new Random();
    }

    /*
     * @param val: a value to the set
     * @return: true if the set did not already contain the specified element or false
     */
    public boolean insert(int val) {
        if (positions.containsKey(val)) {
            return false;
        }

        positions.put(val, values.size());
        return values.add(val);
    }

    /*
     * @param val: a value from the set
     * @return: true if the set contained the specified element or false
     */
    public boolean remove(int val) {
        if (!positions.containsKey(val)) {
            return false;
        }

        int p = positions.remove(val);
        int pTail = values.size() - 1;
        int tail = values.get(pTail);
        
        values.remove(pTail);

        if (val != tail) {
            values.set(p, tail);
            positions.put(tail, p);
        }

        return true;
    }

    /*
     * @return: Get a random element from the set
     */
    public int getRandom() {
        //how to do if array is empty ?
        assert !values.isEmpty();

        int i = random.nextInt(values.size());
        return values.get(i);
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param = obj.insert(val);
 * boolean param = obj.remove(val);
 * int param = obj.getRandom();
 */
