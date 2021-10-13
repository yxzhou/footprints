/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapReduce;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 *
 * Use map reduce to build inverted index for given documents.
 * 
 * Example
 *   Look at the program.
 * 
 * 
 */
public class InvertedIndex {
    public static class Map {
        
        public void map(String str, Document value,
                OutputCollector<String, Integer> output) {
            
            //String[] tokens = value.content.split("[\\s\\W]+");
            //for (int i = 0; i < tokens.length; i++) {    
            //    String word = tokens[i];
            StringTokenizer tokens = new StringTokenizer(value.content);
            while (tokens.hasMoreTokens()) {
                String word = tokens.nextToken();
                output.collect(word, value.id);
            }
        }
    }

    public static class Reduce {

        public void reduce(String key, Iterator<Integer> values,
                OutputCollector<String, List<Integer>> output) {

//            Set<Integer> ids = new HashSet<>();
//            while (values.hasNext()) {
//                ids.add(values.next());
//            }
//
//            output.collect(key, new ArrayList<>(ids));
            
            List<Integer> results = new ArrayList<Integer>();
            int pre = -1;
            while (values.hasNext()) {
                int curr = values.next();
                if(pre != curr) {
                    results.add(curr);

                    pre = curr;
                }
            }
            output.collect(key, results);
        }
    }
}
