package array.subArray;

import util.Misc;

/**
 * There are N gas stations along a circular route, where the amount of gas at station i is gas[i].
 * You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station i to its next station (i+1).
 * You begin the journey with an empty tank at one of the gas stations.
 * 
 * Return the starting gas station's index if you can travel around the circuit once, otherwise return -1.
 * 
 * Note: The solution is guaranteed to be unique.
 *
 */

public class GasStation {
    public int canCompleteCircuit_n(int[] gas, int[] cost) {
    	int index = -1;
        if(null == gas || null == cost || 0 == gas.length || gas.length != cost.length){
            return index;  // error code
        }
        
        int subSum = -1;
        int totalSum = 0;
        int diff;
        for(int i = 0; i < gas.length; i++){
        	diff = gas[i] - cost[i];
        	if(subSum >= 0){
        		subSum += diff;
        	}else{
        		subSum = diff;
        		index = i;
        	}
        	totalSum += diff;
        }
        
        return totalSum >= 0 ? index : -1;
    }
	
	public static int canCompleteCircuit(int[] gas, int[] cost) {
		// check
		if (null == gas || 0 == gas.length || null == cost
				|| gas.length != cost.length)
			return -1;

		int curSum = 0;
		int total = 0;
		int startIndex = 0;

		for (int i = 0; i < gas.length; i++) {
			int curRemain = gas[i] - cost[i];
			if (curSum >= 0) {
				curSum += curRemain;
			} else {
				curSum = curRemain;
				startIndex = i;
			}
			total += curRemain;
		}

		return total >= 0 ? startIndex : -1;
	}

	public static void main(String[] args) {
		System.out.println("===start====");

		int[][][] input = { { { 2, 2, 2, 2, 2 }, { 1, 1, 3, 2, 3 } },
				{ { 2, 2, 1, 2, 2 }, { 1, 3, 2, 3, 0 } },
				{ { 2, 2, 2, 2, 2 }, { 2, 1, 1, 3, 3 } },
				{ { 2, 2, 2, 2, 2 }, { 3, 1, 3, 1, 3 } } };

		GasStation sv = new GasStation();

		for (int i = 0; i < input.length; i++) {
			int[] gas = input[i][0];
			int[] cost = input[i][1];
			System.out.println(String.format(
					"%d, input: %s and %s \t output: %d", i,
					Misc.array2String(gas), Misc.array2String(cost), sv.canCompleteCircuit(gas, cost)));
		}

		System.out.println("===end====");
	}
}
