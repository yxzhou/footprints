/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.lis;

import java.util.Arrays;
import org.junit.Assert;
import util.Misc;

/**
 * You are given n pairs of numbers. In every pair, the first number is always smaller than the second number.
 *
 * Now, we define a pair (c, d) can follow another pair (a, b) if and only if b < c. Chain of pairs can be formed in this fashion.
 *
 * Given a set of pairs, find the length longest chain which can be formed. You needn't use up all the given pairs. You can select pairs in any order.
 *
 * Example 1:
 * Input: [[2,3], [3,4], [1,2] ]
 * Output: 2
 * Explanation: The longest chain is [1,2] -> [3,4]
 *
 * Note:
 * The number of given pairs will be in the range [1, 1000].
 *
 * Thoughts:
 * when input, [[a1, a2], [b1, b2]]
 *
 * If the chain is: [a1, a2] -> [b1, b2],  it need: a2 < b1
 *   from input,  a1 < a2 and b1 < b2
 *
 *
 *   It can be sorted by a or b
 * 
 */
public class MaximumLengthPairChain {
    public int findLongestChain_DP(int[][] pairs) {
        if (pairs == null || pairs.length == 0) {
            return 0;
        }

        Arrays.sort(pairs, (p1, p2) -> p1[0] == p2[0] ? p2[1] - p1[1] : p1[0] - p2[0]);

        int n = pairs.length;
        int[] depth = new int[n];

        int max = 1;
        for (int i = 0; i < n; i++) {
            depth[i] = 1;

            for (int j = 0; j < i; j++) {
                if (pairs[i][0] > pairs[j][1]) {
                    depth[i] = Math.max(depth[i], depth[j] + 1);
                }
            }

            max = Math.max(max, depth[i]);
        }

        return max;
    }
    
    public int findLongestChain_greedy(int[][] pairs) {
        if (pairs == null || pairs.length == 0) {
            return 0;
        }

        Arrays.sort(pairs, (a, b) -> a[1] - b[1]); //order by pairs[i][1]

        int curr = Integer.MIN_VALUE;
        int count = 0;

        for (int[] pair : pairs) {
            if (curr < pair[0]) {
                count++;
                
                curr = pair[1];
            }
        }

        return count;
    }
    
    public static void main(String[] args) {
        MaximumLengthPairChain sv = new MaximumLengthPairChain();
        
        //int[][] input = {{5,4},{6,4},{6,7},{2,3}};
        int[][][] input = {
                    {},
                    {{1,1}},
                    {{3,4},{2,3},{1,2}},
                    {{531823,943947},{264934,996537},{276106,717622},{720071,767846},{922092,932368},{578930,690702},{644620,965707},{649127,956032}},
                    {{-6,9},{1,6},{8,10},{-1,4},{-6,-2},{-9,8},{-5,3},{0,3}}
        };
        
        int[] expects = {0, 1, 2, 3, 3}; //, 4, 3, 3, 3, 5};
        
        for(int i = 0; i < input.length; i++ ){
            System.out.println(String.format("Input: %s\n", Misc.array2String(input[i])));
            //System.out.println(String.format("Output: %d, %d", sv.maxEnvelopes_Greedy(envelopes), sv.maxEnvelopes_DP(envelopes)));
            
            Assert.assertEquals(expects[i], sv.findLongestChain_DP(input[i]));
            Assert.assertEquals(expects[i], sv.findLongestChain_greedy(input[i]));
            
        }
        
    }
}
