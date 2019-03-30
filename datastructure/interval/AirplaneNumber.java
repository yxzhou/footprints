package fgafa.datastructure.interval;

import java.util.ArrayList;
import java.util.Arrays;
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
	 * @param airplanes
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
		if (null == airplanes) {
			return 0;
		}
		
		int size = airplanes.size();
		int[] starts = new int[size];
		int[] ends = new int[size];
		
		int k = 0;
		for (Interval x : airplanes) {
			starts[k] = x.start;
			ends[k] = x.end;
			k++;
		}

		Arrays.sort(starts);
		Arrays.sort(ends);

		int max = 0;
		int count = 0;
		for(int i = 0, j = 0; i < size && j < size; ){
			if(starts[i] < ends[j]){
				count++;
				i++;
				
				max = Math.max(max, count);
			}else if(starts[i] > ends[j]){
				count--;
				j++;
			}else{ // ==
				i++;
				j++;
			}
		}
		
		return max;
	}

	public int countOfAirplanes_x(List<Interval> airplanes) {
		if (null == airplanes) {
			return 0;
		}

		int size = airplanes.size();

		List<int[]> events = new ArrayList<>(size * 2);
		for(Interval interval : airplanes){
			events.add(new int[]{interval.start, 1});
			events.add(new int[]{interval.end, -1});
		}

		Collections.sort(events, (e1, e2) -> e1[0] == e2[0] ? e1[1] - e2[1] : e1[0] - e2[0]);

		int counter = 0;
		int max = 0;
		for(int[] event : events){
			counter += event[1];
			max = Math.max(max, counter);
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
