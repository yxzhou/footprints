/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.subSum;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Design and implement a TwoSum class. It should support the following operations: add and find.
 *   add - Add the number to an internal data structure.
 *   find - Find if there exists any pair of numbers which sum is equal to the value.
 * 
 * Example 1:
 *   add(1); 
 *   add(3); 
 *   add(5);
 *   find(4) // return true
 *   find(7) // return false
 * 
 */
public class TwoSumIII {
    Map<Integer, Integer> datas = new HashMap<>();

    /**
     * @param number: An integer
     * @return: nothing
     */
    public void add(int number) {
        datas.put(number, datas.getOrDefault(number, 0) + 1);
    }

    /**
     * @param value: An integer
     * @return: Find if there exists any pair of numbers which sum is equal to the value.
     */
    public boolean find(int value) {
        int diff;
        for (int v : datas.keySet()) {
            diff = value - v;
            if ((diff == v && datas.get(v) > 1) || (diff != v && datas.containsKey(diff))) {
                return true;
            }
        }

        return false;
    }
}
