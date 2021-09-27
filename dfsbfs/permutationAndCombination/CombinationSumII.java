package dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import util.Misc;

/**
 * _https://www.lintcode.com/problem/153
 * Given a collection of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.
 *
 *
 * Note: 
 * Each number in C may only be used once in the combination.
 * All numbers (including target) will be positive integers. 
 * Elements in a combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak). 
 * The solution set must not contain duplicate combinations. 
 * 
 * For example, given candidate set 10,1,2,7,6,1,5 and target 8, 
 * A solution set is:  [1, 7] [1, 2, 5] [2, 6] [1, 1, 6]
 *
 *
 *
 */
public class CombinationSumII {

    /**
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] num, int target) {
        if(num == null){
            return Collections.EMPTY_LIST;
        }

        Arrays.sort(num);

        List<List<Integer>> result = new LinkedList<>();

        helper(num, target, 0, new int[num.length], 0, result);

        return result;
    }

    private void helper(int[] num, int target, int i, int[] path, int ip, List<List<Integer>> result){
        if(target == 0){
            List<Integer> list = new LinkedList<>();

            for(int k = 0; k < ip; k++){
                list.add(path[k]);
            }
            result.add(list);
            return;
        }

        for( int j = i ; j < num.length; j++ ){
            if(num[j] > target){
                break;
            }
            if(j > i && num[j] == num[j - 1]){ ////for case [1,1,2] and 3, avoid to get the duplicated [1, 2]
                continue;
            }

            path[ip] = num[j];
            helper(num, target - num[j], j + 1, path, ip + 1, result);
        }
    }

    public static void main(String[] args) {

        System.out.println("==========start==========" );
                long startTime = System.currentTimeMillis(); 

        int[][] arr = {null, {}, {2}, {2}, {1, 2, 3}, {2, 3, 6, 7}, {10, 1, 2, 7, 6, 1, 5}};
        int[] target = {0, 0, 1, 3, 3, 7, 8};

        CombinationSumII sv = new CombinationSumII();
        for (int i = 0; i < arr.length; i++) {
            System.out.println("\n  Input:");
            System.out.println(Misc.array2String(arr[i]) + "\t" + target[i]);

            List<List<Integer>> result = sv.combinationSum2(arr[i], target[i]);
            System.out.println(" Output:");
            Misc.printListList(result);

            //sv.init();
        }

        System.out.println("===========end===========" + (System.currentTimeMillis() - startTime));
    }

}
