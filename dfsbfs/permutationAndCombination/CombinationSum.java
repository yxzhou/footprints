package dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import junit.framework.Assert;

import util.Misc;

/**
 * _https://www.lintcode.com/problem/135
 *
 * Given a set of candidate numbers candidates and a target number target. Find all unique combinations in candidates
 * where the numbers sums to target.
 *
 * The same repeated number may be chosen from C unlimited number of times.
 *
 * Note: 
 *  All numbers (including target) will be positive integers. 
 *  Elements in a combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak). 
 *  The solution set must not contain duplicate combinations.<br>
 *
 * Example 1:
 * Input: candidates = [2, 3, 6, 7], target = 7 
 * Output: [[7], [2, 2, 3]] 
 * 
 * Example 2:
 * Input: candidates = [1], target = 3 
 * Output: [[1, 1, 1]]
 *
 * Solution: 
 *   To the candidates = [2, 3, 6, 7], target = 7  
 * 
 *  m1) dfs
 *      ( / means -2, | mean -3, \ means -6, -- means -7 )
 *                7  -- 0
 *             /  |  \
 *            5   4   1
 *          / | / |  
 *         3  2   1
 *       / | / 
 *      1  0 
 * 
 *   (7 - 2 - 2 - 3)  and (7 - 3 - 2 - 2) are duplicate combinations. To pruning 
 *   
 *                7  -- 0
 *             /  |  \
 *            5   4   1
 *          / |   |  
 *         3  2   1
 *       / |  
 *      1  0 
 * 
 * 
 *  m2) DP, similar with backpack
 *
 *
 */
public class CombinationSum {

    /**
     * @param candidates: A list of integers
     * @param target: An integer
     * @return: A list of lists of integers
     */
    public List<List<Integer>> combinationSum_DP(int[] candidates, int target) {
        if(candidates == null || target < 1){
            return Collections.EMPTY_LIST;
        }

        Arrays.sort(candidates);

        List<List<Integer>>[] dp = new ArrayList[target + 1];
        dp[0] = new ArrayList<>();
        dp[0].add(new ArrayList<>());

        List<List<Integer>> curr;
        int x;
        int y;
        for(int c = 0, n = candidates.length; c < n; c++){
            x = candidates[c];
            if(c > 0 && x == candidates[c - 1]){
                continue;
            }
            
            for(int i = target - x; i >= 0; i--){
                if(dp[i] == null){
                    continue;
                }

                curr = deepClone(dp[i]); //deep clone
                
                for( y = i + x; y <= target; y += x ){
                    for(List<Integer> list : curr){
                        list.add(x);
                    }

                    if(dp[y] == null){
                        dp[y] = deepClone(curr);
                    } else {
                        dp[y].addAll(deepClone(curr));
                    }
                }
            }
        }

        return dp[target] == null? Collections.EMPTY_LIST : dp[target];
    }
    
    private List<List<Integer>> deepClone(List<List<Integer>> lists){
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> subs;

        for (List<Integer> list : lists) {
            subs = new ArrayList<>();

            for (Integer z : list) {
                subs.add(z);
            }

            result.add(subs);
        }

        return result;
    }
    
    
    public List<List<Integer>> combinationSum_DFS(int[] candidates, int target) {
        if(candidates == null || candidates.length < 1 || target < 1){
            return Collections.EMPTY_LIST;
        }
        
        Arrays.sort(candidates);

        List<List<Integer>> result = new LinkedList<>();

        dfs(candidates, 0, new int[target / candidates[0]], 0, target, result);

        return result;
    }

    private void dfs(int[] candidates, int i, int[] path, int j, int target, List<List<Integer>> result){
        if(target == 0){
            List<Integer> subResult = new ArrayList<>(j);
            for(int k = 0; k < j; k++){
                subResult.add(path[k]);
            }
            result.add(subResult);

            return;
        }

        for(int k = i, n = candidates.length; k < n; k++){
            if(candidates[k] > target){
                break;
            }
            if(k > i && candidates[k] == candidates[i] ){//for case [1,1,2] and 3, avoid to get the duplicated [1, 2]
                continue;
            }       

            path[j] = candidates[k];
            dfs(candidates, k, path, j + 1, target - candidates[k], result);
        }
    }

    public static void main(String[] args) {

        System.out.println("==========start==========" + System.currentTimeMillis());

        int[][][][] inputs = {
            //{{candidate, {target}}, expect}
            {{null, {0}}, {}},
            {{{}, {0}}, {}},
            {{{2}, {1}}, {}},
            {{{2}, {3}}, {}},
            {{{1}, {3}}, {{1, 1, 1}}},
            {{{1, 2, 3}, {3}}, {{1, 1, 1}, {1, 2}, {3}}},
            {{{1, 2, 3}, {4}}, {{1, 1, 1, 1}, {1, 1, 2}, {1, 3}, {2, 2}}},
            {{{2, 3, 6, 7}, {7}}, {{2,2,3}, {7}}},
            {
                {
                    {36, 48, 43, 21, 41, 44, 30, 35, 42, 26, 25, 22, 28, 38, 29, 34, 24, 37, 46, 32, 33, 39, 47, 49, 40, 45, 27, 31}, 
                    {60}
                }, 
                {{21, 39}, {22, 38}, {24, 36}, {25, 35}, {26, 34}, {27, 33}, {28, 32}, {29, 31}, {30, 30}}
            },
            {
                {
                    {28, 23, 41, 49, 21, 47, 48, 35, 36, 44, 26, 20, 37, 24, 46, 33, 30, 22, 25, 31, 32, 45, 43, 42, 40, 39, 34, 29, 27}, 
                    {69}
                }, 
                {{20, 20, 29}, {20, 21, 28}, {20, 22, 27}, {20, 23, 26}, {20, 24, 25}, {20, 49}, {21, 21, 27}, {21, 22, 26}, {21, 23, 25}, {21, 24, 24}, {21, 48}, {22, 22, 25}, {22, 23, 24}, {22, 47}, {23, 23, 23}, {23, 46}, {24, 45}, {25, 44}, {26, 43}, {27, 42}, {28, 41}, {29, 40}, {30, 39}, {32, 37}, {33, 36}, {34, 35}
}
            }

        };

        CombinationSum sv = new CombinationSum();
        
        List<List<Integer>> result;
        for (int[][][] input : inputs) {
            System.out.println(String.format("\n Input candidates: %s, target = %d", Arrays.toString(input[0][0]), input[0][1][0]));

            result = sv.combinationSum_DP(input[0][0], input[0][1][0]);
            Misc.sort(result);
            
            //System.out.println(Misc.array2String(result, true).toString());
            
            Assert.assertEquals( Misc.array2String(input[1], true), Misc.array2String(result, true).toString());
            
            result = sv.combinationSum_DFS(input[0][0], input[0][1][0]);
            Misc.sort(result);
            Assert.assertEquals( Misc.array2String(input[1], true), Misc.array2String(result, true).toString());
        }

        System.out.println("===========end===========" + System.currentTimeMillis());
    }
    


}
