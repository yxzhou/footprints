/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package design.others;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.Assert;
import util.Misc;

/**
 *__https://www.lintcode.com/problem/855/?_from=collection&fromId=18
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
 *  pairs = [["great","good"],["fine","good"],["drama","acting"],["skills","talent"]]
 * Output: true 
 * Explanation: "great" is similar with "fine", "acting" is similar with "drama", "skills" is similar with "talent" 
 * 
 */
public class SentenceSimilarityII {
    
    /**
     * @param words1: 
     * @param words2: 
     * @param pairs: 
     * @return Whether sentences are similary or not?
     */
    public boolean areSentencesSimilarTwo(List<String> words1, List<String> words2, List<List<String>> pairs) {
        if(words1 == null || words2 == null || words1.size() != words2.size() ){
            return false;
        }

        int id = 1;
        Map<String, Integer> ids = new HashMap<>();
        int[] parents = new int[pairs.size() * 2 + 1];
        String from;
        String to;
        for(List<String> pair : pairs){
            from = pair.get(0);
            to = pair.get(1);

            if(!ids.containsKey(from)){
                parents[id] = id;
                ids.put(from, id++);
            }
            if(!ids.containsKey(to)){
                parents[id] = id;
                ids.put(to, id++);
            }

            union( ids.get(from), ids.get(to), parents );
        }

        int i;
        int j;
        for(int k = 0; k < words1.size(); k++ ){
            from = words1.get(k);
            to = words2.get(k);

            if(from.equals(to)){
                continue;
            }

            i = ids.getOrDefault(words1.get(k), -1);
            j = ids.getOrDefault(words2.get(k), -2);

            if( i < 0 || j < 0 || find(i, parents) != find(j, parents) ){
                return false;
            }

        }

        return true;
    }

    private void union(int i, int j, int[] parents){
        int p1 = find(i, parents);
        int p2 = find(j, parents);

        parents[p1] = p2;
    }

    private int find(int i, int[] parents){
        while( i != parents[i] ){
            parents[i] = parents[parents[i]];
            i = parents[i];
        }

        return i;
    }
    
    public static void main(String[] args){
        String[][][][] inputs = {
            {
                {
                    {"great","acting","skills"},
                    {"fine","drama","talent"}
                },
                {{"great","fine"},{"drama","acting"},{"skills","talent"}},  //symmetric
                {{"true"}}
            },
            {
                {
                    {"fine","skills","acting"}, 
                    {"fine","drama","talent"}
                }, 
                {{"great","fine"},{"drama","acting"},{"skills","talent"}},
                {{"false"}}
            },
            {
                {
                    {"fine","skills","acting"}, 
                    {"fine","drama","talent"}
                }, 
                {{"great","fine"},{"skills","acting"},{"skills","talent"}},
                {{"false"}}
            },
            {
                {
                    {"great","fine","skills"},
                    {"fine", "fine", "talent"}
                }, 
                {{"great","fine"},{"drama","fine"},{"skills","talent"}},
                {{"true"}}
            },
            {
                {
                    {"great","fine","great", "skills"},
                    {"fine", "drama", "drama", "talent"}
                }, 
                {{"great","fine"},{"fine","drama"},{"skills","talent"}}, //transitive
                {{"true"}}
            },
            {
                {
                    {"great","acting","skills"},
                    {"fine","drama","talent"}
                }, 
                {{"great","a"},{"b", "fine"},{"b","a"},{"drama","acting"},{"skills","talent"}},
                {{"true"}}
            }
        };
        
        SentenceSimilarityII sv = new SentenceSimilarityII();
        int i = 0;
        for(String[][][] input : inputs){
            System.out.println(String.format("\n- -Input--%d-- \n%s \n%s \n%s  ", i++, 
                    Misc.array2String(input[0][0]), 
                    Misc.array2String(input[0][1]), 
                    Misc.array2String(input[1]) ));
            
            Assert.assertEquals(input[2][0][0], String.valueOf(sv.areSentencesSimilarTwo(Arrays.asList(input[0][0]), Arrays.asList(input[0][1]), Misc.convert(input[1]))));
        }
    }
        
}
