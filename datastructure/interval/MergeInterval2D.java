package fgafa.datastructure.interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given a collection of 2D intervals, merge all overlapping intervals.
 */

public class MergeInterval2D {
    class Point {
        int x;
        int y;
    };
    
    class Interval2D {
        Point start;
        Point end;
    };
    
    public List<Interval2D> merger(List<Interval2D> intervals){
        if(null == intervals || intervals.size() < 2){
            return intervals;
        }
        
        //group by slope
        Map<Double, List<Interval2D>> groups = new HashMap<>();
        
        for(Interval2D interval : intervals){
            double slope = calSlope(interval.start, interval.end);
            
            if(!groups.containsKey(slope)){
                groups.put(slope, new ArrayList<Interval2D>());
            }
            
            groups.get(slope).add(interval);
        }

        List<Interval2D> result = new ArrayList<>();
        
        for(List<Interval2D> list : groups.values()){
            //order by start.x
            Collections.sort(list, new Comparator<Interval2D>() {
                @Override  
                public int compare(Interval2D o1, Interval2D o2) {  
                    return o1.start.x - o2.start.x; //ascend order
                } 
            });
            
            Interval2D pre = list.get(0);
            result.add(pre);
            for(Interval2D curr : list){
                if(curr.start.x < pre.end.x){
                    pre = curr;
                    result.add(pre);
                }else{
                    pre.end.x = Math.max(pre.end.x, curr.end.x);
                    pre.end.y = Math.max(pre.end.y, curr.end.y);
                }
            }
        }
        
        return result;
    }

    private double calSlope(Point start, Point end){
        if(start.x == end.x){
            return Double.NaN;
        }
        
        return (double)(start.y - end.y) / (start.x - end.x) ;
    }
}
