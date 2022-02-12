package design.others.stream;

import java.util.ArrayList;
import java.util.Collections;
import util.Misc;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;

/**
 * _https://www.lintcode.com/problem/362
 * 
 * Given an array of n integer with duplicate number, and a moving window(size k), move the window at each iteration
 * from the start of the array, find the maximum number inside the window at each moving.
 *
 * Example 1:
 * Input:  [1 3 -1 -3 5 3 6 7], and w is 3.
 * Output: [3,3,5,5,6,7]
 * Explanation：
 * Window position                Max
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7      3
 * 1 [3  -1  -3] 5  3  6  7       3
 * 1  3 [-1  -3  5] 3  6  7       5
 * 1  3  -1 [-3  5  3] 6  7       5
 * 1  3  -1  -3 [5  3  6] 7       6
 * 1  3  -1  -3  5 [3  6  7]      7
 * Output: An array B[], B[i] is the maximum value of from A[i] to A[i+w-1]
 * 
 * Example 2:
 * Input: [1,2,7,7,8]  3 
 * Output: [7,7,8]
 * Explanation： 
 *   At first the window is at the start of the array like this [|1, 2, 7| ,7, 8] , return the maximum 7;
 *   then the window move one step forward. [1, |2, 7 ,7|, 8], return the maximum 7; 
 *   then the window move one step forward again. [1, 2, |7, 7, 8|], return the maximum 8; 
 * 
 * Example 3:
 * Input: [1,2,3,1,2,3]  5 
 * Output: [3,3]
 * Explanation: 
 *   At first, the state of the window is as follows: [,2,3,1,2,1 | , 3], a maximum of 3; 
 *   then the window to the right one. [1, | 2,3,1,2,3 |] , a maximum of 3 ;
 *
 *
 */
class SlidingWindowMaximum{

  
    /**
     * with double-ended queue, that is the perfect data structure 
     * 
     * Time O(n) Space O(k) 
     *
     * @param nums: A list of integers.
     * @param k: An integer
     * @return The maximum number inside the window at each moving.
     */
    public List<Integer> maxSlidingWindow(int[] nums, int k) {
        
        if(nums == null || nums.length < k || k == 0){
            return Collections.EMPTY_LIST;
        }

        int n = nums.length;
        List<Integer> result = new ArrayList<>(n - k + 1);

        Deque<Integer> deque = new LinkedList<>(); //

        for(int i = 0, k1 = k - 1; i < n; i++ ){

            while(!deque.isEmpty() && deque.getLast() < nums[i]){
                deque.removeLast();
            }

            deque.addLast(nums[i]);

            if(i >= k1){
                result.add(deque.getFirst());
                                
                if(deque.getFirst() == nums[i - k1]){
                    deque.removeFirst();         
                }
            }    
            
        }

        return result;
    }
  
   
        /**
     * @param nums: A list of integers.
     * @param k: An integer
     * @return The maximum number inside the window at each moving.
     */
    public List<Integer> maxSlidingWindow_2(int[] nums, int k) {
        
        if(nums == null || nums.length < k || k == 0){
            return Collections.EMPTY_LIST;
        }

        int n = nums.length;
        List<Integer> result = new ArrayList<>(n - k + 1);

        Deque<Integer> deque = new LinkedList<>(); //

        for(int i = 0, j = 1 - k; i < n; i++, j++){

            while(!deque.isEmpty() && deque.getLast() < nums[i]){
                deque.removeLast();
            }

            deque.addLast(nums[i]);

            if( j >= 0 ){
                result.add(deque.getFirst());

                if(deque.getFirst() == nums[j]){
                    deque.removeFirst();    
                }
            }    
            
        }

        return result;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        int[][][] inputs = {
            {{},{0},{}},
            {{1},{0},{}},
            {{1},{1},{1}},
            {{1, 2}, {2},{2}},
            {{1, 2, 3},{2},{2,3}},
            {{1, 2, 3, 4},{2},{2,  3, 4}},
            {{1, 2, 3, 4, 5},{2},{2, 3, 4, 5}},
            {{2, 1},{2},{2}},
            {{3, 2, 1}, {2},{3, 2}},
            {{4, 3, 2, 1},{2},{4, 3, 2}},
            {{5, 4, 3, 2, 1},{2},{5, 4, 3, 2}},
            {{1, 2, 3},{3},{3}},
            {{1, 2, 3, 4},{3},{3,4}},
            {{1, 2, 3, 4, 5},{3},{3,4,5}},
            {{3, 2, 1},{3},{3}},
            {{4, 3, 2, 1},{3},{4, 3}},
            {{5, 4, 3, 2, 1},{3},{5, 4, 3}},
            {{1577, 330, 1775, 206, 296, 356, 219, 999, 790, 1435, 1218, 1046, 745, 650, 1199, 1290, 442, 1767, 1098, 521, 854, 1718, 528, 1011}, {8}, {1775, 1775, 1775, 1435, 1435, 1435, 1435, 1435, 1435, 1435, 1767, 1767, 1767, 1767, 1767, 1767, 1767}}

        };

        SlidingWindowMaximum sv = new SlidingWindowMaximum();
        for (int[][] input : inputs) {

            System.out.println(String.format("\n[%s], k=%d", Misc.array2String(input[0]), input[1][0]));
            //System.out.print("Output: " + Misc.array2String(sv.maxSlidingWindow(input[0], input[1][0])) );

            Assert.assertEquals( Misc.array2String(input[2]).toString(), Misc.array2String(sv.maxSlidingWindow(input[0], input[1][0])).toString() );
            Assert.assertEquals( Misc.array2String(input[2]).toString(), Misc.array2String(sv.maxSlidingWindow_2(input[0], input[1][0])).toString() );
        }

    }

}
