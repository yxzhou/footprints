package array.lis;

import util.Misc;

import java.util.*;
import org.junit.Assert;

/**
 * 
 * You have a number of envelopes with widths and heights given as a pair of integers (w, h). 
 * One envelope can fit into another if and only if both the width and height of one envelope is greater than 
 * the width and height of the other envelope.

    What is the maximum number of envelopes can you Russian doll? (put one inside other)
    
    Example:
    Given envelopes = [[5,4],[6,4],[6,7],[2,3]], 
    the maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
 *
 *
 * Thoughts:
 * when input, [[w1, h1], [w2, h2]]
 *
 * If it's: [w1, h1] -> [w2, h2],  it need: w1 < w2 and h1 < h2
 *    It can be sorted by w or h, ( in fact pick which one, no difference logically )
 *
 *    Let's sort by w, it get: [[1,1], [2, 3], [2, 2], [2, 4]]
 *    for the further selection, the best choice is [2,2], intead of [2, 3] and [2, 4]
 *
 *
 */

public class RussianDollEnvelope {
    
    /*Time Complexity O(n^2) Space O(1)*/
    public int maxEnvelopes_DP(int[][] envelopes) {
        if(null == envelopes || envelopes.length == 0){
            return 0;
        }
        
        Arrays.sort(envelopes, (p1, p2) -> (p1[0] == p2[0] ? p2[1] - p1[1] : p1[0] - p2[0]));
        //Arrays.sort(envelopes, (p1, p2) -> p1[0] == p2[0]? Integer.compare(p2[1], p1[1]) : Integer.compare(p1[0], p2[0]) );

        int max = 0;
        int n = envelopes.length;
        int[] depths = new int[n];
        
        int[] pre;
        int[] curr;
        for(int i = 0; i < n; i++){
            curr = envelopes[i];
            
            for(int j = 0; j < i; j++){
                pre = envelopes[j];
                
                if(pre[0] < curr[0] && pre[1] < curr[1]){
                    depths[i] = Math.max(depths[i], depths[j] + 1);
                }
            }
            
            max = Math.max(max, depths[i]);
        }
        
        return max + 1;
    }


    /*Time Complexity O(nlogn) Space O(n)*/
    public int maxEnvelopes_Greedy(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0) {
            return 0;
        }

        Arrays.sort(envelopes, (p1, p2) -> (p1[0] == p2[0] ? p2[1] - p1[1] : p1[0] - p2[0]));
        //Arrays.sort(envelopes, (p1, p2) -> p1[0] == p2[0]? Integer.compare(p2[1], p1[1]) : Integer.compare(p1[0], p2[0]) );

        int n = envelopes.length;

        int[] sequence = new int[n];
        sequence[0] = envelopes[0][1];
        int r = 0;

        int p;
        for (int[] curr : envelopes) {
            p = Arrays.binarySearch(sequence, 0, r + 1, curr[1]);

            if (p < 0) {
                p = -p - 1;

                sequence[p] = curr[1];

                r = Math.max(r, p);
            }
        }

        return r + 1;
    }

    public static void main(String[] args) {
        RussianDollEnvelope sv = new RussianDollEnvelope();
        
        //int[][] input = {{5,4},{6,4},{6,7},{2,3}};
        int[][][] input = {
                    {},
                    {{1,1}},
                    {{5,4},{5,9},{5,7},{2,3}},
                    {{5,4},{6,4},{6,7},{2,3}},
                    {{4,5},{4,6},{6,7},{2,3},{1,1}},
                    {{46,89},{50,53},{52,68},{72,45},{77,81}},
                    {{1,3},{3,5},{6,7},{6,8},{8,4},{9,5}},
                    {{30,50},{12,2},{3,4},{12,15}},
                    {{2,100},{3,200},{4,300},{5,500},{5,400},{5,250},{6,370},{6,360},{7,380}}
        };
        
        int[] expects = {0, 1, 2, 3, 4, 3, 3, 3, 5};
        
        for(int i = 0; i < input.length; i++ ){
            System.out.println(String.format("Input: %s\n", Misc.array2String(input[i])));
            //System.out.println(String.format("Output: %d, %d", sv.maxEnvelopes_Greedy(envelopes), sv.maxEnvelopes_DP(envelopes)));
            
            Assert.assertEquals(expects[i], sv.maxEnvelopes_DP(input[i]));
            Assert.assertEquals(expects[i], sv.maxEnvelopes_Greedy(input[i]));
            
        }
    }

}
