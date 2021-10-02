/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.subArray;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 
 * Given an integer arrays, find a contiguous subarray which has the largest sum and length should be between k1 and k2 (include k1 and k2).
 * Return the largest sum, return 0 if there are fewer than k1 elements in the array.
 * 
 * Notes: 
 *   The answer is an integer.
 * 
 * Example 1:
 * Input: [-2,2,-3,4,-1,2,1,-5,3],  2, 4
 * Output: 6
 * Explanation:
 *    the contiguous subarray `[4,-1,2,1]` has the largest sum = `6`.
 * 
 * Example 2:
 * Input: [1,1,2,3], 5, 10
 * Output:
 * 0
 * 
 * 
 * Solution:  refer to MaxSubarraySumIV,  continue
 * 
 * define maxSubarraySum[i, k1, k2] as the max sum of subarray with length between k1 and k2 in nums [0, i]
 *    maxSubarraySum[k1 - 1, k1, k2] = sum[k1] - 0  ( k1 - 1 is index, k1 is length, index from 0 to k1 - 1, total the length is k ) 
 *    maxSubarraySum[k1, k1, k2] = sum[k1] - min{0, sum[0]} ( the first k1 is index, the second k1 is length, index from 0 to k1, total the length is k1 + 1 ) 
 *    maxSubarraySum[k1 + 1, k1, k2] = sum[k1 + 1] - min{0, sum[0], sum[1]} 
 *    maxSubarraySum[k1 + 2, k1, k2] = sum[k1 + 2] - min{0, sum[0], sum[1], sum[2]} 
 *    ...
 *    maxSubarraySum[k2, k1, k2] = sum[k2] - min{sum[0], sum[1], .., sum[k2 - k1]}
 *    maxSubarraySum[k2 + 1, k1, k2] = sum[k2] - min{sum[1], sum[1], .., sum[k2 - k1 + 1]}
 *    ...
 *    maxSubarraySum[i, k1, k2] = sum[i] - min{ sum[i - k2], sum[1], .., sum[i - k1] }  (i > k2 >= k1)
 * 
 * 
 * define minSum[i] as the min sum in { sum[0], .. sum[i] }
 * define minSum[l, r] as the min sum in { sum[l], sum[l + 1], ..., sum[r] }
 *    minSum[l, l] = sum[l]
 *    minSum[l, l + 1] = Math.min( minSum[l, l], sum[l + 1] )
 *    minSum[l, r] = Math.min( minSum[l, r - 1], sum[r] )
 *    minSum[l, r] -> minSum[l + 1, r + 1] , it's 
 *    min of {0, sum[l], sum[l + 1], ..., sum[r] } -> min of {0, sum[l + 1], sum[l + 2], ..., sum[r], sum[r + 1] }
 * 
 *    It can do with a Deque to store the sum[i].
 *       when to add sum[r + 1], check from tail of Deque, remove all bigger and equal element
 *            to remove sum[l], if it's equals to the head of Deque, poll it. 
 *    so keep the header of Deque as the minSum[l, r] 
 * 
 * edge case:
 *   1) k2 == k1, the problem is: find the largest sum of subarray which length is k1.  maxSubarraySum[i, k1, k1] = sum[i] - sum[i - k1]
 *   2) k1 == nums.length or k1 < nums.length < k2
 * 
 */
public class MaxSubarraySumV {
    
    public int maxSubarray5(int[] nums, int k1, int k2) {
        if(nums == null || k1 > k2 || nums.length < k1){
            //throw new IllegalArgumentException(" nums is null or nums' length is smaller than k");
            return 0;
        }

        int n = nums.length;
        int[] sums = new int[n]; // the sum from 0 to r
        Deque<Integer> preMinSums = new LinkedList<>(); //< value of sums[i] >
        
        sums[0] = nums[0];
        for(int i = 1; i < k1; i++){
            sums[i] = sums[i - 1] + nums[i];
        }
        
        int result = sums[k1 - 1]; //init as the sum of {nums[0], nums[1] .., nums[k - 1]}, the length is k1
        for(int i = k1, r = 0, end = Math.min(k2, n); i < end; i++, r++){
            sums[i] = sums[i - 1] + nums[i];
            
            while(!preMinSums.isEmpty() && preMinSums.peekLast() > sums[r]){
                preMinSums.pollLast();
            }
            preMinSums.add(sums[r]);
            
            result = Math.max(result, sums[i] - Math.min(0, preMinSums.peekFirst()));
        }

        for(int i = k2, l = 0, r = k2 - k1; i < n; i++, r++){
            sums[i] = sums[i - 1] + nums[i];
            
            while(!preMinSums.isEmpty() && preMinSums.peekLast() > sums[r]){
                preMinSums.pollLast();
            }
            preMinSums.add(sums[r]);
            
            result = Math.max(result, sums[i] - preMinSums.peekFirst());
            
            if(!preMinSums.isEmpty() && preMinSums.peekFirst() == sums[l]){
                preMinSums.pollFirst();
            }
        }

        return result;        
    }
        
    /** faster than maxSubarray5 because it's MyDeque here */ 
    public int maxSubarray5_MyDeque(int[] nums, int k1, int k2) {
        if(nums == null || k1 > k2 || k1 < 1 || nums.length < k1){
            //throw new IllegalArgumentException(" nums is null or nums' length is smaller than k");
            return 0;
        }

        int n = nums.length;
        int[] sums = new int[n]; // the sum from 0 to r
        MyDeque preMinSums = new MyDeque(k2 - k1 + 1); //< value of sums[i] >
        
        sums[0] = nums[0];
        for(int i = 1; i < k1; i++){
            sums[i] = sums[i - 1] + nums[i];
        }
        
        int result = sums[k1 - 1]; //init as the sum of {nums[0], nums[1] .., nums[k - 1]}, the length is k1
        for(int i = k1, r = 0, end = Math.min(k2, n); i < end; i++, r++){
            sums[i] = sums[i - 1] + nums[i];
            
            while(!preMinSums.isEmpty() && preMinSums.peekLast() > sums[r]){
                preMinSums.pollLast();
            }

            preMinSums.add(sums[r]);
            
            result = Math.max(result, sums[i] - Math.min(0, preMinSums.peekFirst()));
        }

        for(int i = k2, l = 0, r = k2 - k1; i < n; i++, l++, r++){
            sums[i] = sums[i - 1] + nums[i];
            
            while(!preMinSums.isEmpty() && preMinSums.peekLast() > sums[r]){
                preMinSums.pollLast();
            }
            preMinSums.add(sums[r]);
            
            result = Math.max(result, sums[i] - preMinSums.peekFirst());

            if(!preMinSums.isEmpty() && preMinSums.peekFirst() == sums[l]){
                preMinSums.pollFirst();
            }
        }

        return result;        
    }


    class MyDeque{
        int capacity;
        int[] datas;
        int h = 0; // header
        int t = -1; // tail
        int size = 0;

        MyDeque(int capacity){
            this.capacity = capacity;
            this.datas = new int[capacity];
        }

        void add(int v){
            addLast(v);
        }
        void addLast(int v){
            // if(isFull()){
            //     throw new IllegalArgumentException("It's full");
            // }

            t = (t + 1) % capacity;
            datas[t] = v;
            size++;
        }
        int peekLast(){
            // if(isEmpty()){
            //     throw new IllegalArgumentException("It's empty");
            // }

            return datas[t];
        }
        int pollLast(){
            // if(isEmpty()){
            //     throw new IllegalArgumentException("It's empty");
            // }

            int v = datas[t];
            t = (t - 1 + capacity) % capacity;
            size--;
            return v;
        }

        int peekFirst(){
            // if(isEmpty()){
            //     throw new IllegalArgumentException("It's empty");
            // }

            return datas[h];
        }
        int pollFirst(){
            // if(isEmpty()){
            //     throw new IllegalArgumentException("It's empty");
            // }

            int v = datas[h];
            h = (h + 1) % capacity;
            size--;
            return v;
        }
        void addFirst(int v){
            // if(isFull()){
            //     throw new IllegalArgumentException("It's full");
            // }

            h = (h - 1 + capacity) % capacity;
            datas[h] = v;
            size++;
        }

        boolean isFull(){
            return size() == capacity;
        }

        boolean isEmpty(){
            return size() == 0;
        }

        int size(){
            return size;
        }
    }
    
}
