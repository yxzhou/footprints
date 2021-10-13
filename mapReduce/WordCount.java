/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapReduce;

import java.util.Iterator;
import java.util.StringTokenizer;

/**
 *
 * Using map reduce to count word frequency.
 * 
 * https://hadoop.apache.org/docs/r1.2.1/mapred_tutorial.html#Example%3A+WordCount+v1.0
 * 
 * Example 1:
 * Input:
 *     chunk1: "Google Bye GoodBye Hadoop code"
 *     chunk2: "lintcode code Bye"
 * Output:
 *     Bye: 2
 *     GoodBye: 1
 *     Google: 1
 *     Hadoop: 1
 *     code: 2
 *     lintcode: 1
 * 
 * Example 2:
 * Input:
 *     chunk1: "Lintcode is so so good"
 * Output:
 *     Lintcode: 1
 *     good: 1
 *     is: 1
 *     so: 2
 * 
 * 
 */
public class WordCount {
   public static class Map {
        public void map(String key, String value, OutputCollector<String, Integer> output) {
            StringTokenizer tokenizer = new StringTokenizer(value);
            while (tokenizer.hasMoreTokens()) {
                String word = tokenizer.nextToken();
                output.collect(word, 1);
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
