package fgafa.todo.goo;

import fgafa.util.Misc;

/**
 * Given a array of integer, check if it's a increasing sequence after one change
 * To array[1..n], if array[i-1]<=array[i]) when  0<i<n, it's a increasing sequence.
 *
 * e.g:
 * input [4,2,3],  output True, [4,2,3] => [1,2,3]
 * input [4,2,1],  output False
 */

public class IncreasingSequence {

    public boolean check(int[] nums){
        if(null == nums || nums.length < 2){
            return true;
        }

        int pre = Integer.MIN_VALUE;
        int curr = nums[0];
        int next;
        int count = 0;

        for(int i = 1; i < nums.length; i++){
            next = nums[i];

            if (curr > next){
                if(count > 0){
                    return false;
                }

                count++;

                if(pre > next){
                    next = curr;
                }else{
                    curr = pre;
                }
            }

            pre = curr;
            curr = next;
        }

        return true;
    }

    /**
     *  from jiuzhange
     */
    public boolean check_jiuzhang(int[] nums){
        int n = nums.length;
        int pos = 0;
        int count = 0;
        for(int i = 1; i < n; i++){
            if(nums[i - 1] > nums[i]){
                pos = i;
                count++;
            }
        }

        if(count == 0){
            return true;
        }else if(count > 1){
            return false;
        }else{
            return pos == 1 || pos == n - 1 || nums[pos - 2] <= nums[pos] || nums[pos - 1] <= nums[pos + 1];
        }

    }

    public static void main(String[] args){

        int[][] input = {
                {1,2,4},
                {1,4,2},
                {2,1,4},
                {2,4,1},
                {4,1,2},
                {4,2,1},
                {2,4,1,3},
                {2,4,1,5}
        };

        boolean[] expects = {
                true,
                true,
                true,
                true,
                true,
                false,
                false,
                true
        };

        IncreasingSequence sv = new IncreasingSequence();

        for(int i = 0; i < input.length; i++){
            int[] nums = input[i];

            System.out.print(String.format("%s : %b, %b\n", Misc.array2String(nums), sv.check(nums), sv.check_jiuzhang(nums)));
            assert sv.check(nums) == expects[i];
        }

    }

}
