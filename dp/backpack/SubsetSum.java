package dp.backpack;

import util.Misc;

import java.util.*;

/**
 *
 * Given a list of integers S and a target number k, write a function that returns a subset of S that adds up to k. 
 * If such a subset cannot be made, then return null.
 * Integers can appear more than once in the list. You may assume all numbers in the list are positive.
 * For example, given S = [12, 1, 61, 5, 9, 2] and k = 24, return [12, 9, 2, 1] since it sums up to 24.
 *
 * Tags: google, CombinationSum
 *
 * Clarification:
 *    duplicate number in input ?
 *    return need in order ?
 */

public class SubsetSum {

    /**
     *
     *  bug when  {{12, 1, 61, 5, 9, 2, 13}, {37}},  return {13, 12, 12}
     *  because compress the path
     */
    public List<Integer> subsetSum_wrong(int[] nums, int target){

        if(null == nums || 0 == nums.length){
            return null;
        }

        Arrays.sort(nums);

        Map<Integer, Integer> curr = new HashMap<>();

        for(int num : nums){
            Map<Integer, Integer> next = new HashMap<>(curr);
            for(Integer key : curr.keySet()){
                next.put(key + num, key);
            }
            next.put(num, 0);

            if(next.containsKey(target)){
                List<Integer> result = new ArrayList<>();

                result.add(target - next.get(target));
                target = next.get(target);

                while(target != 0){
                    result.add(target - curr.get(target));
                    target = curr.get(target);
                }

                return result;
            }

            curr = next;
        }

        return null;
    }

    /**
     *  The method is only ok when the input all are positive
     */
    public List<Integer> subsetSum(int[] nums, int target) {
        if (null == nums || 0 == nums.length) {
            return null;
        }

        Arrays.sort(nums);

        return dfs(nums, target, 0, new boolean[nums.length]);
    }

    private List<Integer> dfs(int[] nums, int target, int i, boolean[] includes){
        if(target == 0){
            List<Integer> result = new ArrayList<>();

            for(int j = 0; j < includes.length; j++){
                if(includes[j]){
                    result.add(nums[j]);
                }
            }
            return result;
        }

        if(target < 0 || i > nums.length || target < nums[i]){
            return null;
        }

        includes[i] = true;
        List<Integer> result = dfs(nums, target - nums[i], i + 1, includes);
        if( result != null ){
            return result;
        }

        includes[i] = false;
        result = dfs(nums, target, i + 1, includes);
        if( result != null ){
            return result;
        }

        return null;
    }


    public static void main(String[] args){

        int[][][] cases = {
                {{12, 1, 61, 5, 9, 2}, {24}},
                {{12, 1, 61, 5, 9, 2, 13}, {37}},
                {{-1, -2, 2, 1, 3}, {0}},
                {{-1, -2, 0, 1, 3}, {0}}
        };

        SubsetSum sv = new SubsetSum();

        for(int i = 0; i < cases.length; i++){

            System.out.println(String.format("%d: {%s}, %d\n{%s}", i, Misc.array2String(cases[i][0]), cases[i][1][0], Misc.array2String(sv.subsetSum(cases[i][0], cases[i][1][0]))));
        }


        /**  ConcurrentModificationException
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 0);
        map.put(2, 0);

        Iterator<Integer> iterator = map.keySet().iterator();
        while(iterator.hasNext()){
            int x = iterator.next();

            map.put(x + 2, x);
        }

        map.put(3, 0);

        System.out.println(map.size());
        */
    }
}
