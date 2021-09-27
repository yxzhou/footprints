package dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import util.Misc;

/**
 *
 * Given a set of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate
 * numbers sums to T.
 *
 * The same repeated number may be chosen from C unlimited number of times.
 *
 * Note: 
 *  All numbers (including target) will be positive integers. 
 *  Elements in a combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak). 
 *  The solution set must not contain duplicate combinations.<br>
 *
 * For example, given candidate set 2,3,6,7 and target 7, A solution set is: [7] [2, 2, 3]
 *
 * Solution: 
 * 1 sorted C 
 * 2 dfs 
 *
 *
 */
public class CombinationSum {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        
        Arrays.sort(candidates);

        List<List<Integer>> result = new LinkedList<>();

        helper(candidates, 0, new int[candidates.length], 0, target, result);

        return result;
    }

    private void helper(int[] candidates, int i, int[] path, int j, int target, List<List<Integer>> result){
        if(0 == target){
            List<Integer> list = new ArrayList<>(j);
            for(int k = 0; k < j; k++){
                list.add(path[k]);
            }
            result.add(list);

            return;
        }

        for(int k = i, end = candidates.length; k < end; k++){
            if(candidates[k] > target){
                break;
            }
            if(k > i && candidates[k] == candidates[i] ){//for case [1,1,2] and 3, avoid to get the duplicated [1, 2]
                continue;
            }

            path[j] = candidates[k];
            helper(candidates, k, path, j + 1, target - candidates[k], result);
        }
    }

    public static void main(String[] args) {

        System.out.println("==========start==========" + System.currentTimeMillis());

        int[][] arr = {null, {}, {2}, {2}, {1, 2, 3}, {2, 3, 6, 7},
             {28, 23, 41, 49, 21, 47, 48, 35, 36, 44, 26, 20, 37, 24, 46, 33, 30, 22, 25, 31, 32, 45, 43, 42, 40, 39, 34, 29, 27},
             {36, 48, 43, 21, 41, 44, 30, 35, 42, 26, 25, 22, 28, 38, 29, 34, 24, 37, 46, 32, 33, 39, 47, 49, 40, 45, 27, 31}};
        int[] target = {0, 0, 1, 3, 3, 7, 69, 60};

        CombinationSum sv = new CombinationSum();
        for (int i = 0; i < arr.length; i++) {
            System.out.println("\n  Input:");
            System.out.println(Misc.array2String(arr[i]) + "\t" + target[i]);

            List<List<Integer>> result = sv.combinationSum(arr[i], target[i]);

            System.out.println(" Output:");
            Misc.printListList(result);

            //sv.init();
        }

        System.out.println("===========end===========" + System.currentTimeMillis());
    }

}
