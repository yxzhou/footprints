package design.others.stream;

/**
 * _https://www.lintcode.com/problem/360
 *
 * Given an array of n integer, and a moving window(size k), move the window at each iteration from the start of the
 * array, find the median of the element inside the window at each moving. (If there are even numbers in the array,
 * return the N/2-th number after sorting the element in the window. )
 *
 * Example #1
 * Input array [1,2,7,8,5], moving window size k = 3. 
 * Output [2,7,7] 
 * Explanation: 
 *   At first the window is at the start of the array like this [ | 1,2,7 | ,8,5] , return the median 2; 
 *   then the window move one step forward. [1, | 2,7,8 | ,5], return the median 7; 
 *   then the window move one step forward again. [1,2, | 7,8,5 | ], return the median 7;
 *
 * Example #2, 
 * Input array [1,3,-1,-3,5,3,6,7], and k = 3.
 *
 * Window position        Median
 * ---------------        ----- 
 * [1 3 -1] -3 5 3 6 7      1 
 * 1 [3 -1 -3] 5 3 6 7      -1 
 * 1 3 [-1 -3 5] 3 6 7      -1 
 * 1 3 -1 [-3 5 3] 6 7      3 
 * 1 3 -1 -3 [5 3 6] 7      5 
 * 1 3 -1 -3 5 [3 6 7]      6 
 * Therefore, return the median sliding window as [1,-1,-1,3,5,6].
 *
 * Note: 
 *   You may assume k is always valid, ie: 1 ≤ k ≤ input array's size for non-empty array.
 *
 * Challenge O(nlog(n)) time
 *
 * 
 * Solution #1, minHeap + maxHeap, PriorityQueue, Time O(n*k) 
 * Solution #2, minHeap + maxHeap, TreeSet, Time O(n*logk) 
 * 
 */

import java.util.TreeSet;
import org.junit.Assert;
import util.Misc;

public class SlideWindowMedian2 {

    class MyMedian {
        class Node{
            int id;
            int value;
            
            Node(int id, int value){
                this.id = id;
                this.value = value;
            }
        }

        //all node's value in maxHeap is smaller than that in minHeap
        //Here both are sorted in ascending order. the min is the first, the max is the last
        TreeSet<Node> bigger = new TreeSet<>((a, b) -> a.value == b.value? a.id - b.id : a.value - b.value ); 
        TreeSet<Node> smaller = new TreeSet<>((a, b) -> a.value == b.value? a.id - b.id : a.value - b.value ); 

        public void add(int id, int num) {
            smaller.add(new Node(id, num));

            rebalance();
        }

        /**
         * Time O(1)
         * 
         * @param id
         * @param num 
         */
        public void delete(int id, int num) {
            Node toDelete = new Node(id, num);
            //if (!bigger.isEmpty() && bigger.first().value <= num) {
                bigger.remove(toDelete);
            //} else {
                smaller.remove(toDelete);
            //}

            rebalance();
        }
        
        private void rebalance(){
            if (bigger.isEmpty() && smaller.isEmpty()) {
                return; // for case: nums = {1, 2}, k = 1
            }

            while (smaller.size() >= bigger.size()) {
                bigger.add(smaller.pollLast());
            }
            
            while (bigger.size() > smaller.size()) {
                smaller.add(bigger.pollFirst());
            }
        }

        public int findMedian() {

            if (0 == smaller.size()) {
                throw new IllegalArgumentException("No data input");
                //}else if(minHeap.size() == maxHeap.size()){
                //    return ((double)minHeap.peek() + maxHeap.peek()) / 2;
            } else {
                return smaller.last().value;
            }
        }
    }
    
    public int[] medianSlidingWindow(int[] nums, int k) {
        if(null == nums || 0 == nums.length || k <= 0){
            return new int[0]; 
        }
        
        MyMedian median = new MyMedian();
        int n = nums.length;
        int[] result = new int[n - k + 1];

        for (int i = 0, j = 0, s = k - 1; i < n; i++) {
            median.add(i, nums[i]);

            if (i >= s) {
                result[j++] = median.findMedian();
                median.delete(i - s, nums[i - s]);
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        
        int[][][] inputs = {
            //{ nums, {k}, {expectation} }
            {{1}, {1}, {1}},
            {{1, 2}, {2}, {1}},
            {{1, 2, 3}, {2}, {1, 2}},
            {{1, 2, 3, 4}, {2}, {1, 2, 3}},
            {{1, 2, 3, 4, 5}, {2}, {1, 2, 3, 4}},
            {{2, 1}, {2}, {1}},
            {{3, 2, 1}, {2}, {2, 1}},
            {{4, 3, 2, 1}, {2}, {3, 2, 1}},
            {{5, 4, 3, 2, 1}, {2}, {4, 3, 2, 1}},
            {{1, 2, 3}, {3}, {2}},
            {{1, 2, 3, 4}, {3}, {2, 3}},
            {{1, 2, 3, 4, 5}, {3}, {2, 3, 4}},
            {{3, 2, 1}, {3}, {2}},
            {{4, 3, 2, 1}, {3}, {3, 2}},
            {{5, 4, 3, 2, 1}, {3}, {4, 3, 2}},
            {{1, 2, 7, 7, 2}, {1}, {1, 2, 7, 7, 2}},
            {{1, 2, 7, 7, 2}, {2}, {1, 2, 7, 2}},
            {{1, 2, 7, 7, 2}, {3}, {2, 7, 7}},
            {{1, 7, 7, 7, 3, 4, 4}, {4}, {7, 7, 4, 4}},
            {{1, 3, -1, -3, 5, 3, 6, 7}, {3}, {1, -1, -1, 3, 5, 6}},
            {{6, 5, 9, 5, 4, 9, 1, 7, 5, 5}, {4}, {5, 5, 5, 4, 4, 5, 5}}
        };
     
        SlideWindowMedian2 sv2 = new SlideWindowMedian2();
        SlideWindowMedian sv = new SlideWindowMedian();
        SlideWindowMedian1 sv1 = new SlideWindowMedian1();
        SlideWindowMedian3 sv3 = new SlideWindowMedian3();
        
        for(int[][] input : inputs){
            System.out.println(String.format(" Input: [%s], \tk = %d", Misc.array2String(input[0]), input[1][0]));
            
            //System.out.println(String.format("Output: %s \n\t\t%s", Misc.array2String(sv.medianSlidingWindow(input[0], input[1][0])), Misc.array2String(input[2])));
            Assert.assertArrayEquals(input[2], sv2.medianSlidingWindow(input[0], input[1][0]));
            
            //System.out.println(String.format("Output: %s -- \t%s", Misc.array2String(sv2.medianSlidingWindow(input[0], input[1][0])), Misc.array2String(input[2])));
            Assert.assertEquals(Misc.array2String(input[2]).toString(), Misc.array2String(sv.medianSlidingWindow(input[0], input[1][0])).toString() );
            
            Assert.assertArrayEquals(input[2], sv1.medianSlidingWindow(input[0], input[1][0]));
            
            //Assert.assertArrayEquals(input[2], sv3.medianSlidingWindow(input[0], input[1][0]));
        }

    }
    
}

