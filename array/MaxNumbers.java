package array;

import java.util.Arrays;
import java.util.PriorityQueue;
import org.junit.Assert;

/**
 * Given a array of length n with digits 0-9 representing two numbers. Create the maximum number of length k <= n from 
 * digits of the two. The relative order of the digits from the same array must be preserved. 
 * Return an array of the k digits. You should try to optimize your time and space complexity.
 * 
 * input: [9, 1, 2, 5, 8, 3] 
 * when k = 1, return [9] 
 * when k = 2, return [9, 8] 
 * when k = 3, return [9, 8, 3] 
 * when k = 4, return [9, 5, 8, 3]
 * 
 * Thoughts:
 *   To case: nums = {9, 1, 2, 5, 8, 3}, k = 4.
 *   Define n as the length of nums, 
 *   step 1, get the max from [0, n - k], the max index is i1;
 *   step 2, get the max from [i1 + 1, n - k + 1], the max index is i2
 *   step 3, get the max from [i2 + 1, n - k + 2]
 *   ...
 *   step k, get the max from [ik + 1, n - k + (k - 1)]
 * 
 *   m1) straightforward, every step, compare elements in [i+1, n-k] to get the max. 
 *   The time complexity is O(n-k-i)
 *   The total time complexity is (n-k)+(n-k)+...+(n-k-(k-1)), it's O(n*k),   Space O(1)
 *   When k = n, it's O(n*n) Example nums = {9,8,7,6,5,4,3,2,1}, k = 9.
 *   
 *   How to optimize the time and space complexity on get the max 
 *   m21) maxHeap, Time complextiy is O(k*n), It takes O(n) to remove , Space O(n)
 *   m22)interval tree, Time complextiy is O(k*logn) Space O(n)
 *   m23) bucket sort, because all numbers in nums are digits 0-9. the Time complextiy is O(n),  Space O(10)
 *   m24) monotonous stack,  the Time complextiy is O(n),  Space O(10)
 * 
 */
public class MaxNumbers {
    
    /**
     * Time Complexity O(n)
     */    
    public int[] maxSubArray_n(int[] nums, int k) {
        
        int[] result = new int[k];

        for (int i = 0, j = 0, n = nums.length;  i < n; i++) {
            while (j > 0 && i < n - k + j && result[j - 1] < nums[i]) {
                j--;
            }
            
            if (j < k){
                result[j++] = nums[i];
            }
        }
        return result;
    }
    
    /**
     * Time Complexity O(k * n)
     * 
     */
    public int[] maxSubArray_Heap(int[] nums, int k) {
        
        int[] result = new int[k];
        
        int n = nums.length;
        
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>( (a, b) -> nums[b] == nums[a]? a - b : nums[b] - nums[a] );
        for(int j = 0, end = n - k; j < end; j++ ){
            maxHeap.add(j);
        }
        
        for(int i = 0, j = 0, maxIndex = 0; i < k; i++){
            maxHeap.add(n - k + i);
            
            maxIndex = maxHeap.poll();
            
            result[i] = nums[maxIndex];
            
            for( ; j < maxIndex; j++){
                maxHeap.remove(j);   // finding the element with a certain key in a heap is in O(n) . Thus, removal by key value is in O(n) because of the find complexity
            }
                    
        }
        
        return result;
    }
    
    
    /**
     * Time Complexity O(n)
     * 
     */
    public int[] maxSubArray_bucket(int[] nums, int k) {
        
        int[] result = new int[k];
        
        int n = nums.length;
        
        int[] counts = new int[10]; // bucket, all numbers in nums are digits 0-9
        for(int j = 0, end = n - k; j < end; j++ ){
            counts[nums[j]]++;
        }
        
        for(int i = 0, j = 0, max = 0; i < k; i++){
            counts[nums[n - k + i]]++;
            
            for(int v = 9; v >= 0; v--){
                if(counts[v] > 0){
                    max = v;
                    break;
                }
            }
            
            result[i] = max;
            
            for( ; nums[j] != max; j++){
                counts[nums[j]]--;
            }
            
            counts[nums[j]]--;
            j++;

        }
        
        return result;
    }
    
    public static void main(String[] args) {
        int[][][] inputs = {
            //{nums, {k}, expect}
            {{9, 1, 2, 5, 8, 3}, {2}, {9, 8}},
            {{9, 1, 2, 5, 8, 3}, {4}, {9, 5, 8, 3}},
            {{1, 2, 3, 5, 8, 9}, {4}, {3, 5, 8, 9}},
            {{9, 8, 5, 3, 2, 1}, {4}, {9, 8, 5, 3}},
        };
        
        MaxNumbers sv = new MaxNumbers();
        
        MaxNumbers2 sv2 = new MaxNumbers2();
        
        for(int[][] input : inputs){
            System.out.println(String.format("\nnums:%s, k = %d", Arrays.toString(input[0]), input[1][0] ));
            
            Assert.assertArrayEquals(input[2], sv.maxSubArray_n(input[0], input[1][0]));
            
            Assert.assertArrayEquals(input[2], sv.maxSubArray_Heap(input[0], input[1][0]));
            Assert.assertArrayEquals(input[2], sv.maxSubArray_bucket(input[0], input[1][0]));
            
            
            Assert.assertArrayEquals(input[2], sv2.maxSubArray_IntervalTree(input[0], input[1][0]));
            
        }

    }

}
