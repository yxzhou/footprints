/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design.others;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import junit.framework.Assert;
import util.Misc;

/**
 *
 * 
 * Given two sentences words1, words2 (each represented as an array of strings), and a list of similar word pairs pairs,
 * determine if two sentences are similar. Detail see example #1
 *
 * Note that the similarity relation is not transitive. For example, if "great" and "fine" are similar, and "fine" and
 * "good" are similar, "great" and "good" are not necessarily similar.
 *
 * However, similarity is symmetric. For example, "great" and "fine" being similar is the same as "fine" and "great"
 * being similar.
 *
 * Also, a word is always similar with itself. For example, the sentences words1 = ["great"], words2 = ["great"], pairs
 * = [] are similar, even though there are no specified similar word pairs.
 *
 * Finally, sentences can only be similar if they have the same number of words. So a sentence like words1 = ["great"]
 * can never be similar to words2 = ["doubleplus","good"].
 *
 * Notes:
 * 1.The length of words1 and words2 will not exceed 1000. 
 * 2.The length of pairs will not exceed 2000. 
 * 3.The length of each pairs[i] will be 2. 
 * 4.The length of each words[i] and pairs[i][j] will be in the range [1, 20]. 
 * 
 * Example1
 * Input: words1 = ["great","acting","skills"], words2 = ["fine","drama","talent"] and 
 *  pairs = [["great","fine"],["drama","acting"],["skills","talent"]] 
 * Output: true 
 * Explanation: "great" is similar with "fine", "acting" is similar with "drama", "skills" is similar with "talent" 
 * 
 * Example2
 * Input: words1 = ["fine","skills","acting"], words2 = ["fine","drama","talent"] and 
 *   pairs = [["great","fine"],["drama","acting"],["skills","talent"]] 
 * Output: false 
 * Explanation: "fine" is the same as "fine", "skills" is not similar with "drama", "acting" is not similar with "talent"
 * 
 */
public class SentenceSimilarity {
    /**
     * @param words1: a list of string
     * @param words2: a list of string
     * @param pairs: a list of string pairs
     * @return a boolean, denote whether two sentences are similar or not
     */
    public boolean isSentenceSimilarity(String[] words1, String[] words2, List<List<String>> pairs) {
        if(words1 == null || words2 == null || words1.length != words2.length){
            return false;
        }

        Map<String, Integer> ids = new HashMap<>();
        Set<Integer> edges = new HashSet<>();
        int id = 1;
        for(List<String> pair : pairs){
            ids.putIfAbsent(pair.get(0), id++);
            ids.putIfAbsent(pair.get(1), id++);
            
            edges.add( hashcode(ids.get(pair.get(0)), ids.get(pair.get(1))) );
        }

        int i;
        int j;
        for(int k = 0, n = words1.length; k < n; k++){
            i = ids.getOrDefault(words1[k], 0);
            j = ids.getOrDefault(words2[k], 0);

            if( i != j && ( i*j == 0 || !edges.contains(hashcode(i, j)) ) ){
                return false;
            }
        }

        return true;
    }

    private int hashcode(int i, int j){
        return (Math.min(i, j) << 15 ) | Math.max(i, j);
    }
    
        /**
     * @param words1: a list of string
     * @param words2: a list of string
     * @param pairs: a list of string pairs
     * @return a boolean, denote whether two sentences are similar or not
     */
    public boolean isSentenceSimilarity_2(String[] words1, String[] words2, List<List<String>> pairs) {
        if(words1 == null || words2 == null || words1.length != words2.length){
            return false;
        }

        Map<String, Set<String>> map = new HashMap<>();
        for(List<String> pair : pairs){
            map.putIfAbsent(pair.get(0), new HashSet<>());
            map.get(pair.get(0)).add(pair.get(1));
        }

        for(int k = 0, n = words1.length; k < n; k++){
            if(words1[k].equals(words2[k])){
                continue;
            }

            if(map.containsKey(words1[k]) && map.get(words1[k]).contains(words2[k]) 
            || map.containsKey(words2[k]) && map.get(words2[k]).contains(words1[k]) ){
                continue;
            }

            return false;
        }

        return true;
    }
    
    public boolean isSentenceSimilarity_wrong(String[] words1, String[] words2, List<List<String>> pairs) {
        if(words1 == null || words2 == null || words1.length != words2.length){
            return false;
        }

        Map<String, Integer> ids = new HashMap<>();
        int id = 1;
        for(List<String> pair : pairs){
            ids.putIfAbsent(pair.get(0), id);
            ids.putIfAbsent(pair.get(1), id);
            id++;
        }

        int i;
        int j;
        for(int k = 0, n = words1.length; k < n; k++){
            i = ids.getOrDefault(words1[k], 0);
            j = ids.getOrDefault(words2[k], 0);

            if( i != j ){
                return false;
            }
        }

        return true;
    }
    
    public static void main(String[] args){
        String[][][][] inputs = {
            {
                {
                    {"great","acting","skills"},
                    {"fine","drama","talent"}
                },
                {{"great","fine"},{"drama","acting"},{"skills","talent"}} //true
            },
            {
                {
                    {"fine","skills","acting"}, 
                    {"fine","drama","talent"}
                }, 
                {{"great","fine"},{"drama","acting"},{"skills","talent"}} //false
            },
            {
                {
                    {"great","fine","skills"},
                    {"fine", "drama", "talent"}
                }, 
                {{"great","fine"},{"drama","fine"},{"skills","talent"}} //true
            },
            {
                {
                    {"great","drama", "skills"},
                    {"fine", "fine", "talent"}
                }, 
                {{"great","fine"},{"fine","drama"},{"skills","talent"}} // true
            },
            {
                {
                    {"great","fine","great", "skills"},
                    {"fine", "drama", "drama", "talent"}
                }, 
                {{"great","fine"},{"fine","drama"},{"skills","talent"}} //false
            },
            {
                {
                    {"great","drama", "talent"},
                    {"fine", "fine", "talent"}
                }, 
                {{"great","fine"},{"fine","drama"}} //true
            }
        };
        
        boolean[] expects = {
            true,
            false, 
            true,
            true,
            false,
            true
        };
        
        SentenceSimilarity sv = new SentenceSimilarity();
        for(int i = 0; i < expects.length; i++){
            System.out.println(String.format("\n- -Input--%d-- \n%s \n%s \n%s  ", i, Misc.array2String(inputs[i][0][0]), Misc.array2String(inputs[i][0][1]), Misc.array2String(inputs[i][1]) ));
            
            Assert.assertEquals(expects[i], sv.isSentenceSimilarity(inputs[i][0][0], inputs[i][0][1], Misc.convert(inputs[i][1])));
            
            Assert.assertEquals(expects[i], sv.isSentenceSimilarity_2(inputs[i][0][0], inputs[i][0][1], Misc.convert(inputs[i][1])));
            
            //Assert.assertEquals(expects[i], sv.isSentenceSimilarity_wrong(inputs[i][0][0], inputs[i][0][1], Misc.convert(inputs[i][1])));
        }
        
        
    }
    
    
}
