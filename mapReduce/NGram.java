/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapReduce;

import java.util.Iterator;

/**
 * Give a number of strings and the number N. Use the Map Reduce method to count all N-Grams and their occurrences. The letter is granular.
 * 
 * Example 1:
 * Input: N = 3
 * doc_1: "abcabc"
 * doc_2: "abcabc"
 * doc_3: "bbcabc"
 * Output:
 * [
 *   "abc": ５,
 *   "bbc": 1, 
 *   "bca": 3,
 *   "cab": 3
 * ]
 * 
 * Example 2:
 * Input: N=3
 * doc_1: "abcabc"
 * Output:
 * [
 *   "abc": 2, 
 *   "bca": 1,
 *   "cab": 1
 * ]
 * 
 */
public class NGram {
    public static class Map {
        public void map(String s, int n, String str,
                        OutputCollector<String, Integer> output) {
            for(int l = 0, r = n; r <= str.length(); l++, r++ ){
                output.collect(str.substring(l, r), 1);
            }
        }
    }

    public static class Reduce {
        public void reduce(String key, Iterator<Integer> values,
                           OutputCollector<String, Integer> output) {
            int count = 0;
            while(values.hasNext()){
                count += values.next();
            }

            output.collect(key, count);
        }
    }
}
