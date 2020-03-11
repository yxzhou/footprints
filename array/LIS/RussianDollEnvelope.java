package fgafa.array.LIS;

import fgafa.util.Misc;

import java.util.*;

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
    public int maxEnvelopes_memorycache(int[][] envelopes) {
        if(null == envelopes){
            return 0;
        }
        
        //TODO
        return -1;
        
    }


    /*Time Complexity O(nlogn) Space O(n)*/
    public int maxEnvelopes_Greedy(int[][] envelopes) {
        if (null == envelopes || 0 == envelopes.length) {
            return 0;
        }

        Arrays.sort(envelopes, (p1, p2) -> (p1[0] == p2[0]? p2[1] - p1[1] : p1[0] - p2[0]));

        int max = 0;
        int[] dp = new int[envelopes.length];
        int end = 0;

        for(int[] pair : envelopes){
            int top = Arrays.binarySearch(dp, 0, end, pair[1]);
            if(top < 0){
                top = 0 - top - 1;
            }
            
            max = Math.max(max, top + 1);
            dp[top] = pair[1];
            if(top == end){
                end++;
            }
        }

        return max;
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
        
        
        for(int[][] envelopes : input ){
            System.out.println(String.format("Input: %s", Misc.array2String(envelopes)));
            System.out.println(String.format("Output: %d, %d, %d", sv.maxEnvelopes_Greedy(envelopes)));
        }
    }

}
