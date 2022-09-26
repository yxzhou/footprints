/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sweepLine;

import java.util.Map;
import java.util.TreeMap;

/**
 * _https://www.lintcode.com/problem/1063/
 *
 * 
 * 
 * 
 * 
 */
public class MyCalendarIII2 {
    TreeMap<Integer, Integer> map; //map, key is start or end, value is the number of booking from the key
    int max; //the max number of booking in the whole calendar

    public MyCalendarIII2() {
        map = new TreeMap<>();
        max = 0;
    }
    
    public int book(int start, int end) {
        Map.Entry<Integer, Integer> beforeStart = map.floorEntry(start);
        Map.Entry<Integer, Integer> beforeEnd = map.floorEntry(end);

        map.put(start, map.getOrDefault(start, beforeStart == null? 0 : beforeStart.getValue()) + 1);
        map.put(end, map.getOrDefault(end, beforeEnd == null? 0 : beforeEnd.getValue()));
  
        max = Math.max(max, map.get(start));
        for(Map.Entry<Integer, Integer> event : map.subMap(start, false, end, false).entrySet() ){
            map.put(event.getKey(), event.getValue() + 1);

            max = Math.max(max, map.get(event.getKey()));
        }
        
        return max;
    }
}
