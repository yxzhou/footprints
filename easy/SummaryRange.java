package easy;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import util.Misc;

/**
 * _https://www.lintcode.com/problem/1315
 * 
 * Given an ordered integer array without repetition, the range of the array is returned in the form of a series of
 * continuous intervals.
 *
 * The result is in ascending order 
 * 
 * Example1
 * Input: [0,1,2,4,5,7] 
 * Output: ["0->2","4->5","7"] 
 * 
 * Example2
 * Input: [0,2,3,4,6,8,9] 
 * Output: ["0","2->4","6","8->9"]
 * 
 */
public class SummaryRange {

    /**
     * @param nums:  a sorted integer array without duplicates
     * @return: the summary of its ranges
     */
    public List<String> summaryRanges(int[] nums) {
        if(nums == null || nums.length == 0){
            return Collections.EMPTY_LIST;
        }
        if(nums.length == 1){
            return Arrays.asList("" + nums[0]);
        }

        List<String> result = new LinkedList<>();

        for( int l = 0, r = 0, n = nums.length; r < n; r++){
            while(r + 1 < n && nums[r + 1] == nums[r] + 1){
                r++;
            }

            if(l == r){
                result.add("" + nums[l]);
            }else{
                result.add(nums[l] + "->" + nums[r]);
            }
            
            l = r + 1;
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] input = {
            null,
            {0},
            {0, 1, 2},
            {0, 2},
            {0, 1, 2, 4, 5, 7},
            {0,2,3,4,6,8,9}
        };

        SummaryRange sv = new SummaryRange();

        for (int[] nums : input) {
            System.out.println("\n Input: " + Misc.array2String(nums));
            System.out.println("Output: " + Misc.array2String(sv.summaryRanges(nums)));

        }
    }

}
