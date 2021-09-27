package todo;

import java.util.*;
import java.util.Map.Entry;

import util.Misc;

/**
 * Given a integer array (every element value >= 0 ) and a target value t,
 * With the inputed array and operations ( + and - ), it can build lots of expressions.
 *
 * How many expressions whose result equals to the target value?
 *
 * input data range:
 *    the inputed array length is smaller than 20
 *    the sum of the inputed array element is smaller than 1000
 *    the output will be a integer.
 *
 * Example:
 *  Input:  array {1,1,1,1,1},  target = 3
 *  Output: 5
 *
 *  becuase the expressions can be
 *  1+1+1+1-1,
 *  1+1+1-1+1,
 *  1+1-1+1+1,
 *  1-1+1+1+1,
 *  -1+1+1+1+1
 *
 */
public class SubSum {

    public int findExpressions_recursive(int[] nums, int t){
        if(null == nums || 0 == nums.length){
            return 0;
        }

        return findExpressions_recursive(nums, nums.length - 1, t);
    }

    private int findExpressions_recursive(int[] nums, int end, int t){
        if( -1 == end){
            return ( t == 0 ) ? 1 : 0;
        }

        return findExpressions_recursive(nums, end - 1, t - nums[end]) + findExpressions_recursive(nums, end - 1, t + nums[end]);

    }

    public int findExpressions_dp0(int[] nums, int target){
        if(null == nums || 0 == nums.length){
            return 0;
        }

        Map<Integer, Integer> pre = new HashMap<>(); //
        add(pre, nums[0], 1);
        add(pre, 0 - nums[0], 1);

        int num;
        int count;
        for(int i = 1; i < nums.length; i++){
            
            Map<Integer, Integer> curr = new HashMap<>();
            
            for(Entry<Integer, Integer> entry : pre.entrySet()){
                
                num = entry.getKey();
                count = entry.getValue();
                add(curr, num + nums[i], count);
                add(curr, num - nums[i], count);
                
            }
            
            pre = curr;
        }
        

        return pre.containsKey(target) ? pre.get(target) : 0;
    }

    private void add(Map<Integer, Integer> map, int key, int value){
        if(map.containsKey(key)){
            map.put(key, map.get(key) + value);
        }else{
            map.put(key, value);
        }
    }
    
    /* 
     * Note: 
     *   every element value >= 0
     *   the sum of the inputed array element is smaller than 1000
     */
    public int findExpressions_dp1(int[] nums, int target){
        if(null == nums || 0 == nums.length){
            return 0;
        }
        
        int sum = 0;
        for(int num : nums){
            sum += num;
        }
        
        if(target > sum || target + sum < 0){
            return -1;
        }
        
        int doubleSum = ( sum << 1 );
        int[][] dp = new int[nums.length][doubleSum + 1]; // default all are 0
        
        if( 0 == nums[0] ){
            dp[0][0] = 2;
        }else{
            dp[0][nums[0]] = 1;
            dp[0][doubleSum - nums[0]] = 1;
        }

        int newNum = 0;
        for(int i = 1; i < nums.length; i++){
            for(int j = 0; j <= doubleSum; j++){
                if( dp[i - 1][j] == 0 ) {
                    continue;
                }
                
                newNum = j + nums[i];
                if(newNum > doubleSum){
                    newNum -= doubleSum;
                }
                dp[i][newNum] += dp[i - 1][j];

                
                newNum = j - nums[i];
                if(newNum < 0){
                    newNum += doubleSum;
                }
                dp[i][newNum] += dp[i - 1][j];
            }
        }
        
        return dp[nums.length - 1][target];
    }
    

    public static void main(String[] args){
        int[] nums = {1,1,1,1,1};
        int target = 3;
        
        SubSum sv = new SubSum();
        
        System.out.println(String.format("Input: %s,  %d", Misc.array2String(nums).toString(), target));
        System.out.println(String.format("Output: %d,  %d,  %d", sv.findExpressions_recursive(nums, target), sv.findExpressions_dp0(nums, target), sv.findExpressions_dp1(nums, target)));
    }
}
