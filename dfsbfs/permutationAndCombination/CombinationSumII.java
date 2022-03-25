package dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import junit.framework.Assert;

import util.Misc;

/**
 * _https://www.lintcode.com/problem/153
 * 
 * Given an array num and a number target. Find all unique combinations in num where the numbers sum to target.
 *
 * Note: 
 *   Each number in C may only be used once in the combination.
 *   All numbers (including target) will be positive integers. 
 *   Elements in a combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak). 
 *   The solution set must not contain duplicate combinations. 
 * 
 * Example 1:
 * Input: num = [7,1,2,5,1,6,10], target = 8 
 * Output: [[1,1,6],[1,2,5],[1,7],[2,6]] 
 * 
 * Example 2:
 * Input: num = [1,1,1], target = 2 
 * Output: [[1,1]] 
 * Explanation: The solution set must not contain duplicate combinations.
 *
 *
 *
 */
public class CombinationSumII {

    
    /**
     * @param num: Given the candidate numbers
     * @param target: Given the target number
     * @return: All the combinations that sum to target
     */
    public List<List<Integer>> combinationSum_DP(int[] num, int target) {
        if(num == null || num.length == 0){
            return Collections.EMPTY_LIST;
        }
        
        Arrays.sort(num);
        
        int n = num.length;
        
        List<int[]>[] dp = new ArrayList[target + 1];
        dp[0] = new ArrayList<>();
        dp[0].add(new int[Math.min(n, target / num[0]) + 1]); // "+1", the last elemet is to store the array real size, default it's 0 
        
        int k;
        int[] path;
        int m;
        for(int i = 0; i < n; i++){
            for(int j = target - num[i]; j >= 0; j--){
                if(dp[j] == null){
                    continue;
                }
                
                if(i > 0 && num[i] == num[i -1]){ //for case [1,1,2] and 3, avoid to get the duplicated [1, 2]
                    path = dp[j].get(dp[j].size() - 1);
                    m = path.length;
                    if(path[m - 1] == 0 || path[path[m - 1] - 1] != num[i]){
                        continue;
                    }
                }
                
                k = j + num[i];
                if(dp[k] == null){
                    dp[k] = new ArrayList<>();
                } 
                
                //deep clone and append num[i]                
                for(int[] path2 : dp[j]){
                    m = path2.length;
                    path = Arrays.copyOf(path2, m);

                    path[path[m - 1]] = num[i];
                    path[m - 1]++; 
                            
                    dp[k].add(path);
                }
                        
            }
        }
        
        List<List<Integer>> result = new LinkedList<>();
        if (dp[target] != null) {
            List<Integer> subList;
            for (int[] path1 : dp[target]) {
                
                m = path1.length;
                subList = new ArrayList<>();
                for(int p = 0; p < m; p++){
                    if(path1[p] == 0){
                        break;
                    }else{
                        subList.add(path1[p]);
                    }
                }
                
                result.add(subList);
            }
        }
        
        return  result;
    }
    
    
    /**
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum_DFS(int[] num, int target) {
        if(num == null || num.length == 0 ){
            return Collections.EMPTY_LIST;
        }

        Arrays.sort(num);

        List<List<Integer>> result = new LinkedList<>();

        dfs(num, 0, target, new int[Math.min(num.length, target / num[0] + 1)], 0, result);

        return result;
    }

    private void dfs(int[] num, int i, int target, int[] path, int ip, List<List<Integer>> result){
        if(target == 0){
            List<Integer> list = new LinkedList<>();

            for(int k = 0; k < ip; k++){
                list.add(path[k]);
            }
            result.add(list);
            return;
        }

        for( int j = i, n = num.length; j < n; j++ ){
            if(num[j] > target){
                break;
            }
            if(j > i && num[j] == num[j - 1]){ ////for case [1,1,2] and 3, avoid to get the duplicated [1, 2]
                continue;
            }

            path[ip] = num[j];
            dfs(num, j + 1, target - num[j], path, ip + 1, result);
        }
    }

    public static void main(String[] args) {

        System.out.println("==========start==========" );
        long startTime = System.currentTimeMillis(); 

        int[][][][] inputs = {
            //{{num, {target}}, expect}
            {{null, {0}}, {}},
            {{{}, {0}}, {}},
            {{{2}, {1}}, {}},
            {{{2}, {3}}, {}},
            {{{1}, {3}}, {}},
            {{{1, 2, 3}, {3}}, {{1, 2}, {3}}},
            {{{2, 3, 6, 7}, {7}}, {{7}}},
            {{{10, 1, 2, 7, 6, 1, 5}, {8}}, {{1, 1, 6}, {1, 2, 5}, {1, 7}, {2, 6}}},
            
        };
    
        int[][] arr = {null, {}, {2}, {2}, {1, 2, 3}, {2, 3, 6, 7}, {10, 1, 2, 7, 6, 1, 5}};
        int[] target = {0, 0, 1, 3, 3, 7, 8};

        CombinationSumII sv = new CombinationSumII();
        
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

        System.out.println("===========end===========" + (System.currentTimeMillis() - startTime));
    }

}
