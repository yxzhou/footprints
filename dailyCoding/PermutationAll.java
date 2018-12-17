package fgafa.dailyCoding;

import java.util.ArrayList;
import java.util.List;

import fgafa.util.Misc;
import org.junit.Test;

/**
 *
 * Given a number in the form of a list of digits, return all possible permutations.

 For example, given [1,2,3], return [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]].
 *
 * tags: Microsoft
 *
 */

public class PermutationAll {

    public List<int[]> all(int[] nums){
        if(null == nums || 0 == nums.length){
            return new ArrayList<>();
        }

        List<int[]> result = new ArrayList<>();

        dfs(nums, 0, result);

        return result;
    }

    private void dfs(int[] nums, int index, List<int[]> result){
        if(index >= nums.length){
            result.add(nums.clone());
            return;
        }

        for(int i = index; i < nums.length; i++){
            swap(nums, index, i);

            dfs(nums, index + 1, result);

            swap(nums, index, i);
        }

    }

    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    @Test public void test(){

        printOut(all(new int[]{1}));

        printOut(all(new int[]{1, 2}));

        printOut(all(new int[]{1, 2, 3}));

    }

    private void printOut(List<int[]> forPrint){
        for(int[] line : forPrint){
            StringBuilder sb = new StringBuilder();
            sb.append('[');

            for(int num : line){
                sb.append(num).append(',');
            }

            sb.replace(sb.length() - 1, sb.length(), "]");
            System.out.println(sb.toString());
        }
    }
}
