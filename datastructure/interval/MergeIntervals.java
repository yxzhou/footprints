package fgafa.datastructure.interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * Given a collection of intervals, merge all overlapping intervals.
 * 
 * For example,
 * Given [1,3],[2,6],[8,10],[15,18],
 * return [1,6],[8,10],[15,18].
 * 
 * The related issue see InsertInterval.java
 * 
 * @author yxzhou
 *
 */

public class MergeIntervals
{
  public class Interval
  {
    int start;
    int end;

    Interval() {
      start = 0;
      end = 0;
    }

    Interval(int s, int e) {
      start = s;
      end = e;
    }
  }
  
  /*
   * 
   * 
   */
	public ArrayList<Interval> merge_n(ArrayList<Interval> intervals) {
		ArrayList<Interval> result = new ArrayList<Interval>();

		// check
		if (null == intervals || 0 == intervals.size())
			return result;

		// order the input intervals by start
		Collections.sort(intervals, new Comparator<Interval>() {
            @Override  
            public int compare(Interval o1, Interval o2) {  
                return o1.start - o2.start;     // ascend order
            } 
		});

		// insert one by one
		Interval top = intervals.get(0);
		result.add(top);
		for (Interval next: intervals) {
			if (top.end < next.start) {
				top = next;
				result.add(top);
			} else {
				top.end = Math.max(top.end, next.end);
			}
		}

		return result;
	}
  
    public List<Interval> merge_2(List<Interval> intervals) {
        List<Interval> result = new ArrayList<Interval>();
        //check
        if(null == intervals || 0 == intervals.size()){
            return result;
        }
        
        Collections.sort(intervals, new Comparator<Interval>(){
            @Override
            public int compare(Interval i1, Interval i2){
                if(i1.start == i2.start){
                	return i1.end - i2.end;
                }else { // !=
                    return i1.start - i2.start;
                }
            }
            });
        
        Interval top = intervals.get(0);
        
        for(Interval next : intervals){
            if(top.end < next.start){
                result.add(top);
                top = next;
            }else{
                top.end = Math.max(top.end, next.end);
            }
        }
        result.add(top);
        
        return result;
    }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
