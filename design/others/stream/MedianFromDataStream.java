package design.others.stream;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 *
 * You are given a data stream in this problem, and you need to implement two functions as following:
 * function add(val) : receive a num from the data stream. 
 * function getMedian() : return the median of the all numbers which you have received from the data stream. 
 * 
 * The median is not equal to median in math. The median is the number that in the middle of a sorted array, if there 
 * are n numbers in a sorted array A, the median is A[(n - 1) / 2] . 
 * For example, if A=[1,2,3], the median is A[(3-1)/2] = A[1] = 2, if A=[1,19], median is A[(2-1)/2] = A[0] = 1.
 *
 * Example #1
 * For numbers coming list: [1, 2, 3, 4, 5], return [1, 1, 2, 2, 3].
 *
 * Example #2
 * For numbers coming list: [4, 5, 1, 3, 2, 6, 0], return [4, 4, 4, 3, 3, 3, 3].
 *
 * Example #3 
 * For numbers coming list: [2, 20, 100], return [2, 2, 20].
 *
 * Challenge Total run time in O(nlogn).
 *
 * 
 * Solution #1,
 *   maxHeap + minHeap 
 *
 * Solution #2, 
 *   segment tree, especially benefit when there are lots of duplicate numbers in the stream
 * 
 */

public class MedianFromDataStream {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(10, Collections.reverseOrder());
    
    /**
     * add the (n-1)/2 and (n+1)/2 in the maxHeap
     * 
     * @param num 
     */
    public void add(int num){
        maxHeap.add(num);
        
        while(maxHeap.size() >= minHeap.size()){
            minHeap.add(maxHeap.poll());
        }
        while(minHeap.size() > maxHeap.size()){
            maxHeap.add(minHeap.poll());
        }
    }
    
    /**
     * get from the maxHeap
     * 
     * @return 
     */
    public int getMedian(){
        if(0 == maxHeap.size()){
            throw new IllegalArgumentException("No data input");
        //}else if(minHeap.size() == maxHeap.size()){
        //    return ((double)minHeap.peek() + maxHeap.peek()) / 2;
        }else{
            return maxHeap.peek();
        }
    }
}
