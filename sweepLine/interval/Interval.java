/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sweepLine.interval;

import java.util.List;

/**
 *
 * @author yuanxi
 */
public class Interval {
    public int start;
    public int end;

    public Interval() {
        this(0, 0);
    }

    public Interval(int s, int e) {
        start = s;
        end = e;
    }
    
    @Override
    public int hashCode(){
        return start ^ end;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Interval){
            Interval other = (Interval) obj;
            
            return this.start == other.start && this.end == other.end;
        }
        
        return false;
    }
    
    @Override
    public String toString(){
        return toString(this);
    }
            
    public static Interval build(int[] interval) {
        return new Interval(interval[0], interval[1]);
    }
        
    public static Interval[] build(int[][] intervals) {
        if (null == intervals || 0 == intervals.length) {
            return null;
        }

        Interval[] ret = new Interval[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            ret[i] = build(intervals[i]);
        }
        return ret;
    }
    
    public static String toString(Interval interval){
        return String.format("[%d, %d]", interval.start, interval.end);
    }
    
    public static String toString(Interval[] intervals){
        StringBuilder sb = new StringBuilder();
        
        for(Interval p : intervals){
            sb.append(toString(p));
        }
        
        return sb.toString();
    }
    

    
}
