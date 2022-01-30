package easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;

/**
 * _https://www.lintcode.com/problem/641
 * 
 * Given a sorted integer array where the range of elements are in the inclusive range [lower, upper], return its
 * missing ranges.
 * 
 * Example 1
 * Input: nums = [0, 1, 3, 50, 75], lower = 0 and upper = 99
 * Output: ["2", "4->49", "51->74", "76->99"]
 * Explanation:
 *   in range[0,99],the missing range includes:range[2,2],range[4,49],range[51,74] and range[76,99]
 * 
 * Example 2
 * Input: nums = [0, 1, 2, 3, 7], lower = 0 and upper = 7
 * Output: ["4->6"]
 * Explanation:
 *   in range[0,7],the missing range include range[4,6]
 *
 * Thoughts: 
 *   Think about the following cases:
 *   1)  nums == null
 *   2)  nums is empty
 *   3)  lower == nums[0]     like 0 == 0
 *       lower < nums[0]      like 0 < 1
 *       lower << nums[0]     like 0 < 2
 *   4)  nums include {0, 1, 3, 5}
 *   5)  nums[end] == upper   75 == 75
 *       nums[end] < upper    like 75 << 76
 *       nums[end] << upper   like 75 << 99
 *   6)  integer over flow, nums[end] == upper == Integer.Max_Value == Integer.Max_Value
 * 
 */

public class FindMissRange {

    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> result = new ArrayList<>();
        //List<String> result = new LinkedList<>(); //no bigger performance difference here

        long l = lower; // left
        if(nums != null){
            
            int r; //right
            for(int x : nums ){
                if(l < x){
                    r = x - 1;
                    if(l < r){
                        result.add(l + "->" + r);
                    }else{
                        result.add("" + l);
                    }
                }

                l = (long)x + 1; //define l as long to avoid integer over flow
            }
        }

        if(l <= upper){
            if(l < upper){
                result.add(l + "->" + upper);
            }else{
                result.add("" + l);
            }
        }

        return result;
    }
    
    public static void main(String[] args){
        int[][][] inputs = {
            {null, {0, 2}},
            {{}, {0, 2}},
            {{}, {0, 0}},
            {{0}, {0, 2}},
            {{0}, {0, 1}},
            {{0}, {0, 0}},
            {{0,1,3,50,75}, {0, 99}},
            {{1,3,50,75}, {0, 99}},
            {{3,50,75}, {0, 99}},
            {{0,1,3,50,75}, {0, 75}},
            {{0,1,3,50,75}, {0, 76}},
            {{2147483647}, {0, 2147483647}}
        };
        
        String[][] expects = {
            {"0->2"},
            {"0->2"},
            {"0"},
            {"1->2"},
            {"1"},
            {},
            {"2","4->49","51->74","76->99"},
            {"0","2","4->49","51->74","76->99"},
            {"0->2","4->49","51->74","76->99"},
            {"2","4->49","51->74"},
            {"2","4->49","51->74","76"},
            {"0->2147483646"},
        };
        
        FindMissRange sv = new FindMissRange();
        
        for(int i = 0; i< inputs.length; i++){
            int[][] input = inputs[i];
                    
            System.out.println(String.format("\n-%d- Input %s, lower=%d, upper=%d ", i, Arrays.toString(input[0]), input[1][0], input[1][1] ));
            
            //System.out.println(Misc.array2String(sv.findMissingRanges(input[0], input[1][0], input[1][1])));

            Assert.assertArrayEquals(expects[i], sv.findMissingRanges(input[0], input[1][0], input[1][1]).toArray() );
  
        }
    }
}
