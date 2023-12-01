/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp.twosequence;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/623
 *
 * Given a set of strings which just has lower case letters and a target string, output all the strings for each the
 * edit distance with the target no greater than k. You have the following 3 operations permitted on a word:
 *   Insert a character
 *   Delete a character
 *   Replace a character
 *   
 * Example 1:
 * Given words = ["abc", "abd", "abcd", "adc"] and target = "ac", k = 1
 * Return ["abc", "adc"]
 * Explanation: "abc" remove "b"  "adc" remove "d"
 * 
 * Example 2:
 * Input: ["acc","abcd","ade","abbcd"]  "abc"  2
 * Output: ["acc","abcd","ade","abbcd"]
 * Explanation:
 *   "acc" turns "c" into "b"
 *   "abcd" remove "d"
 *   "ade" turns "d" into "b" turns "e" into "c"
 *   "abbcd" gets rid of "b" and "d"
 * 
 */
public class EditDistanceK {
    
    /**
     *
     * based on EditDistance.minDistance_DP_n()
     */
    public List<String> kDistance(String[] words, String target, int k) {
        if (words == null) {
            return Collections.EMPTY_LIST;
        }

        List<String> result = new LinkedList<>();
        if (target == null || target.length() == 0) {
            for (String w : words) {
                if (w.length() <= k) {
                    result.add(w);
                }
            }

            return result;
        }

        int m = target.length();

        int[] pre = new int[m + 1];
        int[] cur = new int[m + 1];
        int[] tmp;
        for (String word : words) {
            
            for(int c = m; c >= 0; c--){
                pre[c] = m - c;
            }
            
            for (int n = word.length(), r = n - 1 ; r >= 0; r--) {
                //cur[m] = n - r;
                cur[m] = pre[m] + 1;

                for(int c = m - 1; c >= 0; c--){
                    cur[c] = Math.min(cur[c + 1], pre[c]) + 1;
                    
                    cur[c] = Math.min(cur[c], pre[c + 1] + (word.charAt(r) == target.charAt(c) ? 0 : 1) ); 
                }
                
                tmp = pre;
                pre = cur;
                cur = tmp;
            }

            if (pre[0] <= k) {
                result.add(word);
            }
        }

        return result;
    }

    
    public static void main(String[] args){
        String[][][] inputs = {
            //{ word, {target, k}, expect }
            {
                {"abc", "abd", "abcd", "adc"},
                {"ac", "1"},
                {"abc", "adc"}
            },
            {
                {"acc", "abcd", "ade", "abbcd"},
                {"abc", "2"},
                {"acc", "abcd", "ade", "abbcd"}
            }
        };
        
        EditDistanceK sv = new EditDistanceK();
        EditDistanceK3 sv2 = new EditDistanceK3();
        EditDistanceK2 sv22 = new EditDistanceK2();
        
        for(String[][] input : inputs){
            System.out.println(String.format("\nwords: %s, target=%s, k=%s", Arrays.toString(input[0]), input[1][0], input[1][1]));
            System.out.println( Misc.array2String(sv22.kDistance(input[0], input[1][0], Integer.parseInt(input[1][1]) )).toString() );
            
            Assert.assertTrue("sv ", CollectionUtils.isEqualCollection( Arrays.asList(input[2]) , sv.kDistance(input[0], input[1][0], Integer.parseInt(input[1][1])) ));

            Assert.assertTrue("sv2 ", CollectionUtils.isEqualCollection( Arrays.asList(input[2]) , sv2.kDistance(input[0], input[1][0], Integer.parseInt(input[1][1])) ));
            
            //Assert.assertTrue("sv22 ", CollectionUtils.isEqualCollection( Arrays.asList(input[2]) , sv22.kDistance(input[0], input[1][0], Integer.parseInt(input[1][1])) ));
        }
        
    }
    
}
