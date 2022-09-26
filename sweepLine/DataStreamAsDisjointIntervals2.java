/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sweepLine;

import sweepLine.interval.Interval;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * another approach for DataStreamAsDisjointIntervals, 
 * 
 */
public class DataStreamAsDisjointIntervals2 {


    TreeMap<Integer, Interval> map = new TreeMap<>();//map<start, Interval(start, end)>, because it invokes getIntervals() frequently

    /**

     * @param val: An integer.
     * @return: nothing
     */
    public void addNum(int val) {
        
        Map.Entry<Integer, Interval> floor = map.floorEntry(val);
        Interval curr = null;
        int diff = -1;
        if(floor == null || (diff = floor.getValue().end + 1 - val) < 0){
            curr = new Interval(val, val);
            map.put(val, curr);
        }else if(diff == 0){ //floor != null && diff == 0
            curr = floor.getValue();
            curr.end = val;
        }//else floor != null && diff > 0 

        Map.Entry<Integer, Interval> higher = map.higherEntry(val);
        if(curr != null && higher != null && higher.getKey() == val + 1){
            curr.end = higher.getValue().end;
            map.remove(higher.getKey());
        } 

    }
    
    public void addNum2(int val) {
        Map.Entry<Integer, Interval> floor = map.floorEntry(val);
        if(floor != null && floor.getValue().end >= val){
            return; 
        }

        if(floor != null && floor.getValue().end + 1 < val){
            floor = null;
        }

        Map.Entry<Integer, Interval> higher = map.higherEntry(val);
        if(higher != null && higher.getKey() > val + 1){
            higher = null;
        }

        if(floor == null && higher == null){
            map.put(val, new Interval(val, val));
        }else if(floor != null && higher != null){
            floor.getValue().end = higher.getValue().end;

            map.remove(higher.getKey());
        }else if(floor != null){
            floor.getValue().end = val;
        }else{ //higher != null
            higher.getValue().start = val;
            map.put(val, higher.getValue());
            map.remove(higher.getKey());
        }

    }

    /**
     * @return A list of intervals.
     */
    public List<Interval> getIntervals() {
        return new ArrayList<>(map.values());
    }
}
