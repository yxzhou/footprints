package dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import junit.framework.Assert;
import util.Misc;

/**
 *
 * Find all possible combinations of k numbers that add up to a number n, 
 * given that only numbers from 1 to 9 can be used and each combination should be a unique set of numbers.
 *
 * Ensure that numbers within the set are sorted in ascending order.
 *
 * Example 1: 
 * Input: k = 3, n = 7 
 * Output: [[1,2,4]]
 * 
 * Example 2: 
 * Input: k = 3, n = 9 
 * Output: [[1,2,6], [1,3,5], [2,3,4]]
 *
 */
public class CombinationSumIII {

    public List<List<Integer>> combinationSum_DFS(int k, int n) {
        if (k < 1 || k > 9 || n < k || n >= k * 9) {
            return Collections.EMPTY_LIST;
        }
                
        List<List<Integer>> result = new ArrayList<>();

        dfs(k, n, 1, new int[k], 0, result);
        
        return result;
    }

    private void dfs(int k, int target, int last, int[] path, int p, List<List<Integer>> result) {

        if (k == 0) {
            if(target == 0){
                List<Integer> list = new ArrayList<>();

                for(int i = 0; i < p; i++){
                    list.add(path[i]);
                }

                result.add(list);
            }

            return;
        } 

        for (int i = last; i < 10; i++) {
            if (target < i * k || target > k*9 || i > target) { // all the rest of elements are too great or too small
                break;
            }

            path[p] = i;

            dfs(k - 1, target - i, i + 1, path, p + 1, result);
        }
    }

    public static void main(String[] args) {
        
        int[][][][] inputs = {
            //{ {{k, n}}, expect }
            {
                {{3, 7}},
                {{1,2,4}}
            },
            {
                {{3, 9}},
                {{1,2,6}, {1,3,5}, {2,3,4}}
            }
        };
        
        CombinationSumIII sv = new CombinationSumIII();
        
        List<List<Integer>> result;
        for(int[][][] input : inputs){
            
            System.out.println(String.format("\nk = %d, n = %d", input[0][0][0], input[0][0][1]));
            
            result = sv.combinationSum_DFS(input[0][0][0], input[0][0][1]);
            Misc.sort(result);
            Assert.assertEquals( Misc.array2String(input[1], true), Misc.array2String(result, true).toString());
            
        }

    }

}
