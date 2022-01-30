/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.interval;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * another approach for DataStreamAsDisjointIntervals, 
 * 
 */
public class DataStreamAsDisjointIntervals2 {


    TreeMap<Integer, Interval> treeMap = new TreeMap<>();

    /**

     * @param val: An integer.
     * @return: nothing
     */
    public void addNum(int val) {
        Map.Entry<Integer, Interval> pre = treeMap.floorEntry(val);
        
        Interval curr;
        if(pre != null && pre.getValue().end + 1 == val){
            curr = pre.getValue();
            curr.end = val;
        }else{ //pre == null
            curr = new Interval(val, val);
            treeMap.put(val, curr);
        }

        Map.Entry<Integer, Interval> next = treeMap.higherEntry(val);
        if(next != null && next.getValue().start == val + 1){
            curr.end = next.getValue().end;
            treeMap.remove(next.getKey());
        }

    }

    /**
     * @return A list of intervals.
     */
    public List<Interval> getIntervals() {
        
        return new ArrayList<>(treeMap.values());

    }
}
