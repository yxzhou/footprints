/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sweepLine;

import java.util.Map;
import java.util.TreeMap;
        
/**
 *
 * same as MyCalendarII
 * 
 * scan line
 * 
 */
public class MyCalendarII2 {
    
    TreeMap<Integer, Integer> map = new TreeMap<>();//map: start or end to change, which is 1 when key is start, -1 when key is end
    /**
     * @param start: 
     * @param end: 
     * @return nothing
     */
    public boolean book(int start, int end) {
        Integer pre = map.floorKey(start);
        pre = ( pre == null? start : pre );

        int count = 0;
        int x;
        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            x = entry.getKey();

            if(x >= end){
                break;
            }

            count += entry.getValue();

            if(x >= pre && count > 1){
                return false;
            }

        }

        map.put(start, map.getOrDefault(start, 0) + 1);
        map.put(end, map.getOrDefault(end, 0) - 1);
        return true;
    }
    
   /**
     * @param start: 
     * @param end: 
     * @return nothing
     */
    public boolean book2(int start, int end) {
        map.put(start, map.getOrDefault(start, 0) + 1);
        map.put(end, map.getOrDefault(end, 0) + 1);
        
        boolean flag = false;
        int sum = 0;
        for(int x : map.values()){
            sum += x;
            
            if(sum > 2){
                flag = true;
                break;
            }
        }
        
        if(flag){
            map.put(start, map.get(start) - 1);
            map.put(end, map.get(end) + 1);
            return false;
        }
        
        return true;
    }
    
}
