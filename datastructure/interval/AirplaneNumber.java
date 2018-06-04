package fgafa.datastructure.interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * Given an interval list which are flying and landing time of the flight. How
 * many airplanes are on the sky at most?
 * 
 * Example For interval list [[1,10],[2,3],[5,8],[4,7]], return 3
 * 
 * Note If landing and flying happens at the same time, we consider landing
 * should happen at first.
 *
 */

public class AirplaneNumber {

	/**
	 * @param intervals
	 *            : An interval array
	 * @return: Count of airplanes are in the sky.
	 */
	public int countOfAirplanes(List<Interval> airplanes) {
		// check
		if (null == airplanes) {
			return 0;
		}

		int[] count = new int[25]; // 24 hours, default all count are 0
		for (Interval x : airplanes) {
			for (int i = x.start; i <= x.end; i++) {
				count[i]++;
			}
		}

		int max = 0;
		for (int num : count) {
			max = Math.max(max, num);
		}

		return max;
	}
	    
	public int countOfAirplanes_n(List<Interval> airplanes) {
		// check
		if (null == airplanes) {
			return 0;
		}
		
		int size = airplanes.size();
		List<Integer> starts = new ArrayList<Integer>(size);
		List<Integer> ends = new ArrayList<Integer>(size);
		for (Interval x : airplanes) {
			starts.add(x.start);
			ends.add(x.end);
		}
		
		Collections.sort(starts);
		Collections.sort(ends);
		
		int max = 0;
		int count = 0;
		for(int i = 0, j = 0; i < size && j < size; ){
			if(starts.get(i) < ends.get(j)){
				count++;
				i++;
				
				max = Math.max(max, count);
			}else if(starts.get(i) > ends.get(j)){
				count--;
				j++;
			}else{ // ==
				i++;
				j++;
			}
		}
		
		return max;
	}
/**
	class Pair implements Comparable<Pair>{
		Integer key;
		boolean type;
		
		Pair(Integer key, boolean type){
			this.key = key;
			this.type = type;
		}

		@Override
		public int compareTo(Pair other) {
					
			if(this.key != other.key){
				return this.key - other.key;
			}
			if(this.type){
				return other.type ? 0 : 1; 
			}else{
				return other.type ? -1 : 0; 
			}
			
		}
	}
*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Definition of Interval:
	 */
	class Interval {
		int start, end;

		Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}
}
