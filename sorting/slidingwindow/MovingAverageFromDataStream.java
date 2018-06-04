package fgafa.sorting.slidingwindow;

import java.util.LinkedList;
import java.util.Queue;

import fgafa.sorting.TopKFrequent;
import fgafa.util.Misc;

/**
 * 
 * Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.
    For example,
    MovingAverage m = new MovingAverage(3);
    m.next(1) = 1
    m.next(10) = (1 + 10) / 2
    m.next(3) = (1 + 10 + 3) / 3
    m.next(5) = (10 + 3 + 5) / 3
 *
 */

public class MovingAverageFromDataStream {

    Queue<Integer> queue;
    int size;
    double pre; // the average of the previous sliding window
     
    /** Initialize your data structure here. */
    public MovingAverageFromDataStream(int size) {
        this.queue = new LinkedList<>();
        this.size = size;
        this.pre = 0;
    }
     
    public double next(int val) {
        queue.offer(val);

        if (queue.size() <= size) {
            pre = (double) (pre * (queue.size() - 1) + val) / queue.size();
        } else {
            int remove = queue.poll();

            pre += (double) (val - remove) / size;
        }
         
        return pre;
    }
    
    public static void main(String[] args) {
        MovingAverageFromDataStream sv = new MovingAverageFromDataStream(3);
        
        int[] input = {1,10, 3, 5, 8};
        
        System.out.println(String.format("Input: %s", Misc.array2String(input)));
        System.out.println("Output:");
        for(int num : input){
            System.out.print("\t" + sv.next(num));
        }

    }

}
