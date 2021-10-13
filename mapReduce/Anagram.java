/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapReduce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * Use Map Reduce to find anagrams in a given list of words.
 * 
 * Example 1:
 * Input: "lint lint lnit ln"
 * Output: 
 *   ["lint", "lint", "lnit"]
 *   ["ln"]
 * 
 * Example 2:
 * Input: "ab ba cab"
 * Output:
 *   ["ab", "ba"]
 *   ["cab"]
 * 
 */
public class Anagram {

     public static class Map {

        public void map(String key, String value,
                OutputCollector<String, String> output) {
            StringTokenizer tokenizer = new StringTokenizer(value);
            while (tokenizer.hasMoreTokens()) {
                String word = tokenizer.nextToken();
                char[] chars = word.toCharArray();
                Arrays.sort(chars);
                output.collect(new String(chars), word);
            }
        }
    }

    public static class Reduce {

        public void reduce(String key, Iterator<String> values,
                OutputCollector<String, List<String>> output) {
            List<String> results = new ArrayList<String>();
            while (values.hasNext()) {
                results.add(values.next());
            }
            output.collect(key, results);
        }
    }
    
}
