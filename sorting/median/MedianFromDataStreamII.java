package sorting.median;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * 
 * Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle value.

    Examples: 
    [2,3,4] , the median is 3
    [2,3], the median is (2 + 3) / 2 = 2.5
    
    Design a data structure that supports the following two operations:
    void addNum(int num) - Add a integer number from the data stream to the data structure.
    double findMedian() - Return the median of all elements so far.
    
    For example:
    add(1)
    add(2)
    findMedian() -> 1.5
    add(3) 
    findMedian() -> 2
 *
 */

public class MedianFromDataStreamII {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(10, Collections.reverseOrder());
    
    public void addNum(int num){
        minHeap.add(num);
        
        while(minHeap.size() > maxHeap.size()){
            maxHeap.add(minHeap.poll());
        }
        
        while(maxHeap.size() > minHeap.size()){
            minHeap.add(maxHeap.poll());
        }
    }
    
    public double findMedian(){
        if(0 == minHeap.size()){
            throw new IllegalArgumentException("No data input");
        }else if(minHeap.size() == maxHeap.size()){
            return ((double)minHeap.peek() + maxHeap.peek()) / 2;
        }else{
            return minHeap.peek();
        }
    }
}
