package fgafa.todo.micoo;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Q: A car is driving on a straight road and it has original units of gasoline.
 * There are n gas stations on this straight road, and the distance between the i-th gas station and the starting position of the car is distance[i] unit distance, which can add apply[i] unit gasoline to the car.
 * The vehicle consumes 1 unit of gasoline for every 1 unit traveled, assuming that the car's fuel tank can hold an unlimited amount of gasoline.
 * The distance from the starting point of the car to the destination is target. Will the car arrive at the destination? If it can,
 * return the minimum number of refuelings, or return -1.
 *
 *
 */

public class GasStationII {

    public int getMinTimes(int original, int target, int[] distance, int[] apply){
        if(target < 0 || null == distance || null == apply || distance.length == 0  || distance.length != apply.length ){
            return -1;
        }

        if(original >= target){
            return 0;
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        int oilDistance = original;
        int totalDistance = 0;
        int count = 0;
        int i = 0;
        for ( ; i < distance.length && oilDistance < target; i++) {
            maxHeap.add(apply[i]);
            totalDistance += distance[i];

            while(oilDistance < totalDistance && !maxHeap.isEmpty()){
                oilDistance += maxHeap.poll();
                count++;
            }

            if(oilDistance < totalDistance){
                break;
            }
        }

        if( i == distance.length && oilDistance > totalDistance && oilDistance > target){
            return count;
        }else{
            return -1;
        }
    }

}
