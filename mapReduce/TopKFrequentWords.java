/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapReduce;

import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Find top k frequent words with map reduce framework.
 * 
 * The mapper's key is the document id, value is the content of the document, words in a document are split by spaces.
 * 
 * For reducer, the output should be at most k key-value pairs, which are the top k words and their frequencies in this reducer. 
 * The judge will take care about how to merge different reducers' results to get the global top k frequent words, 
 * so you don't need to care about that part.
 * 
 * The k is given in the constructor of TopK class.
 * 
 * For the words with same frequency, rank them with alphabet.
 * 
 * Example1
 * Input:
 * document A = "lintcode is the best online judge I love lintcode" and 
 * document B = "lintcode is an online judge for coding interview you can test your code online at lintcode"
 * Output: 
 *   "lintcode", 4
 *   "online", 3
 * 
 * Example2
 * Input:
 * document A = "a a a b b b" 和
 * document B = "a a a b b b"
 * Output: 
 *   "a", 6
 *   "b", 6
 * 
 */
public class TopKFrequentWords {
    public static class Map {
        public void map(String str, Document value,
                        OutputCollector<String, Integer> output) {
            StringTokenizer tokens = new StringTokenizer(value.content);

            while(tokens.hasMoreTokens()){
                output.collect(tokens.nextToken(), 1);
            }
        }
    }

    public static class Reduce {
        int k;
        HashMap<String, Integer> counts;

        public void setup(int k) {
            this.k = k;
            counts = new HashMap<>();
        }   

        public void reduce(String key, Iterator<Integer> values) {
            int sum = 0;
            while(values.hasNext()){
                sum += values.next();
            }

            counts.put(key, counts.getOrDefault(key, 0) + sum);
        }

        public void cleanup(OutputCollector<String, Integer> output) {
            PriorityQueue<String> minHeap = new PriorityQueue<>((s1, s2) ->{
                int i1 = counts.getOrDefault(s1, Integer.MIN_VALUE);
                int i2 = counts.getOrDefault(s2, Integer.MIN_VALUE);
                return i1 == i2 ? s2.compareTo(s1) : Integer.compare(i1, i2) ;
            });

            counts.keySet().forEach(key -> {
                if(minHeap.size() < k){
                    minHeap.add(key);
                }else if( counts.get(minHeap.peek()) <= counts.get(key) ) {
                    minHeap.add(key);
                    minHeap.poll();
                }
            });

            String[] result = new String[k];
            int i = 0;
            while(!minHeap.isEmpty()){
                result[i++] = minHeap.poll();
            }
            
            for( i-- ; i >= 0; i--){
                output.collect(result[i], counts.get(result[i]));
            }

        }
    }
}
