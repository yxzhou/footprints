package dailyCoding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 *
 * The power set of a set is the set of all its subsets. Write a function that, given a set, generates its power set.
 * For example, given the set {1, 2, 3}, it should return {{}, {1}, {2}, {3}, {1, 2}, {1, 3}, {2, 3}, {1, 2, 3}}.
 *
 */

public class Subsets {
    public List<List<Integer>> subsets(int[] nums){
        List<List<Integer>> result = new ArrayList<>();

        if(null == nums || nums.length == 0 ){
            return result;
        }

        dfs(nums, 0, new ArrayList<>(), result);

        return result;
    }

    private void dfs(int[] nums, int i, List<Integer> subset, List<List<Integer>> subsets){
        if(i == nums.length){
            subsets.add(subset);
            return;
        }

        dfs(nums, i + 1, subset, subsets);

        List<Integer> newSubset = new ArrayList<>(subset);
        newSubset.add(nums[i]);
        dfs(nums, i + 1, newSubset, subsets);
    }

    public List<List<Integer>> subsets_2(int[] nums){
        List<List<Integer>> result = new ArrayList<>();

        if(null == nums || nums.length == 0 ){
            return result;
        }

        result.add(new ArrayList<>());
        for(int n : nums){
            for(int i = result.size(); i >= 0; i--){
                List<Integer> newSubset = new ArrayList<>(result.get(i));

                newSubset.add(n);
                result.add(newSubset);
            }
        }

        return result;
    }

}
