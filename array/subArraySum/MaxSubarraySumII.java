package fgafa.array.subArraySum;

import java.util.ArrayList;

import fgafa.util.Misc;

public class MaxSubarraySumII {

    /**
     * Given an integer array, find a continuous subarray where the sum of numbers is the biggest. 
     * Your code should return the index of the first number and the index of the last number. 
     * (If their are duplicate answer, return anyone)

        Example
        Give [-3, 1, 3, -3, 4], return [1,4].
     */
    
    public ArrayList<Integer> maxSubArrayII(int[] A) {
        assert(null != A && 0 < A.length);
        
        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(0,0);
        result.add(1,1);
        
        int localSubSum = Integer.MIN_VALUE; //the subsum that include the current one
        int globalSubSum = Integer.MIN_VALUE;
        int start = 0;
        for(int i = 0; i < A.length; i++){
            if(localSubSum < 0){
                localSubSum = A[i];
                start = i;
            }else{
                localSubSum += A[i];
            }
            
            if(localSubSum > globalSubSum ){
                globalSubSum = localSubSum;
                if(start <= i){
                    result.set(0, start);
                    result.set(1, i);
                }
            }
        }
        
        return result;
    }
    
    
    /**
     * Given an integer array, find a continuous rotate subarray where the sum
     * of numbers is the biggest. Your code should return the index of the first
     * number and the index of the last number. (If their are duplicate answer,
     * return anyone. The answer can be rotate array or non- rotate array)
     * 
     * Example Give [3, 1, -100, -3, 4], return [4,1].
     * 
     * refer: http://algorithm.yuanbin.me/zh-hans/problem_misc/continuous_subarray_sum_ii.html
     */
    public ArrayList<Integer> maxSubArrayIII(int[] A) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        if (A == null || A.length == 0)
            return result;
        
        // maximal subarray sum
        ArrayList<Integer> sub1 = subSum(A, 1);
        // minimal subarray sum
        ArrayList<Integer> sub2 = subSum(A, -1);
        
        int first = 0, last = 0;
        if (sub1.get(3) - sub2.get(2) > sub1.get(2)) {
            last = sub2.get(0) - 1;
            first = sub2.get(1) + 1;
        } else {
            first = sub1.get(0);
            last = sub1.get(1);
        }
        
        // corner case(all elements are negtive)
        if (last == -1 && first == A.length) {
            first = sub1.get(0);
            last = sub1.get(1);
        }

        result.add(first);
        result.add(last);
        return result;
    }

    private ArrayList<Integer> subSum(int[] A, int sign) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        
        // find the max/min subarray sum from [0...A.length]
        int sum = 0, minSum = 0, maxSub = Integer.MIN_VALUE;
        if (sign == -1){
            maxSub = Integer.MAX_VALUE;
        }
        
        int first = 0, last = 0;
        int first2 = 0; // candidate for first
        for (int i = 0; i < A.length; i++) {
            if (sign * minSum > sign * sum) {
                minSum = sum;
                first2 = i;
            }
            
            sum += A[i];
            if (sign * (sum - minSum) > sign * maxSub) {
                maxSub = sum - minSum;
                last = i;
                
                // update first if valid
                if (first2 <= last){
                    first = first2;
                }
            }
        }
        
        result.add(first);
        result.add(last);
        result.add(maxSub);
        result.add(sum);
        return result;
    }
    
    
    public static void main(String[] args) {

        MaxSubarraySumII sv = new MaxSubarraySumII();
    /**  **/
        //int[] arr = {-1, 1, -2, 3, 5, -1, 0};
        int[][] arr = {{-3,1,3,-3,4}, {-1, 1, -2, 3, 5, -1, 3, 0}, {0,-1}, {-2,-1, -3}, {2,1}, {-2,0}, {-1,2}, {0,0}, {1, -2, 3, 5, -3, 2 }, 
            {0, -2, 3, 5, -1, 2 },  {-9, -2, -3, -5, -3 }};


        for(int i = 0; i< 1; i++){
          System.out.println("\nThe original array is: "+Misc.array2String(arr[i]) );
          
          System.out.println("The value of max sub array is: "+ sv.maxSubArrayII(arr[i]) );  
        }
        
    }
}
