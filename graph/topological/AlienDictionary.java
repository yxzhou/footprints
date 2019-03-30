package fgafa.graph.topological;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


/**
 *
 *
 * There is a new alien language which uses the latin alphabet. However, the order among letters are unknown to you.
 * You receive a list of non-empty words from the dictionary, where words are sorted lexicographically by the rules of this new language.
 * Derive the order of letters in this language.

 Example 1:
 Input:
 [
 "wrt",
 "wrf",
 "er",
 "ett",
 "rftt"
 ]

 Output: "wertf"


 Example 2:
 Input:
 [
 "z",
 "x"
 ]
 Output: "zx"

 Example 3:
 Input:
 [
 "z",
 "x",
 "z"
 ]
 Output: ""
 Explanation: The order is invalid, so return "".

 Note:
 You may assume all letters are in lowercase.
 You may assume that if a is a prefix of b, then a must appear before b in the given dictionary.
 If the order is invalid, return an empty string.
 There may be multiple valid order of letters, return any one of them is fine.
 *
 *
 */

public class AlienDictionary {
    
    public String alienOrder(String[] words) {
        
        //check
        if(null == words || 1 >= words.length){
            return "";
        }
        
        //build the graph
        Map<Character, List<Character>> graph = new HashMap<>();
        Map<Character, Integer> inDegrees = new HashMap<>();
        
        buildGraph(graph, inDegrees, words);
        
        //topological sort
        String result = topologicalsort( graph, inDegrees);
        
        return result;
    }
    
    private void buildGraph(Map<Character, List<Character>> graph, Map<Character, Integer> inDegrees, String[] words){
        
        initInDegree(inDegrees, words[0]);
        
        for(int i = 1; i < words.length; i++){
            initInDegree(inDegrees, words[i]);
            
            char[] edge = getEdge(words[i - 1], words[i]);
            
            if(0 == edge.length){
                continue;
            }
            
            List<Character> next = graph.get(edge[0]);
            if(null == next){
                next = new ArrayList<>();
                graph.put(edge[0], next);
            }
            next.add(edge[1]);
            
            inDegrees.put(edge[1], inDegrees.get(edge[1]) + 1);
        }
    }
    
    private char[] getEdge(String word1, String word2){
        int i = 0;
        int min = Math.min(word1.length(), word2.length());
        for( ; i < min && word1.charAt(i) == word2.charAt(i); i++);
        
        if(i == min){
            return new char[0];
        }else{
            return new char[]{word1.charAt(i), word2.charAt(i)};
        }
    }
    
    private void initInDegree(Map<Character, Integer> inDegrees, String word){
        for(char c : word.toCharArray()){
            if(!inDegrees.containsKey(c)){
                inDegrees.put(c, 0);
            }
        }
    }
    
    private String topologicalsort(Map<Character, List<Character>> graph, Map<Character, Integer> inDegrees){
        
        Queue<Character> queue = new LinkedList<>();
        
        for(Map.Entry<Character, Integer> entry : inDegrees.entrySet()){
            if(0 == entry.getValue()){
                queue.add(entry.getKey());
            }
        }
        
        StringBuilder result = new StringBuilder();
        Character curr;
        while(!queue.isEmpty()){
            curr = queue.poll();
            result.append(curr);
            
            if(graph.containsKey(curr)){
                for(Character neighbor : graph.get(curr)){
                    if(1 == inDegrees.get(neighbor)){
                        queue.add(neighbor);
                    }
                    
                    inDegrees.put(neighbor, inDegrees.get(neighbor) - 1);
                }
            }
        }
        
        //check if there is loop
        for(Map.Entry<Character, Integer> entry : inDegrees.entrySet()){
            if(0 != entry.getValue()){
                return "";
            }
        }
        
        return result.toString();
    }



    public String alienOrder_n(String[] words) {
        if (null == words || 1 >= words.length) {
            return "";
        }

        Map<Character, Set<Character>> edges = new HashMap<>();
        Map<Character, Integer> inDegrees = new HashMap<>();

        for(int i = 1; i < words.length; i++){
            String pre = words[i - 1];
            String curr = words[i];
            for(int j = 0; j < Math.min(pre.length(), curr.length()); j++){
                if(pre.charAt(j) != curr.charAt(j)){
                    if(!edges.containsKey(pre.charAt(j))){
                        edges.put(pre.charAt(j), new HashSet<>());
                    }

                    if( edges.get(pre.charAt(j)).add(curr.charAt(j))){
                        inDegrees.put(curr.charAt(j), inDegrees.containsKey(curr.charAt(j)) ? inDegrees.get(curr.charAt(j)) + 1 : 1);
                    }
                }
            }
        }

        Queue<Character> queue = new LinkedList<>();
        for(char key : edges.keySet()){
            if(!inDegrees.containsKey(key)){
                queue.add(key);
            }
        }

        StringBuilder result = new StringBuilder();
        while(!queue.isEmpty()){
            char from = queue.poll();
            result.append(from);

            for(char to : edges.get(from)){
                if(inDegrees.get(to) == 1){
                    queue.add(to);
                }else{
                    inDegrees.put(to, inDegrees.get(to) - 1);
                }
            }

            edges.remove(from);
        }

        //is loop?
        return edges.isEmpty()? result.toString() : "";
    }
}
