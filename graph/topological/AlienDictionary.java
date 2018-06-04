package fgafa.graph.topological;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 
 * There is a new alien language which uses the latin alphabet. However, the 
 * order among letters are unknown to you. You receive a list of words from the 
 * dictionary, wherewords are sorted lexicographically by the rules of this new 
 * language. Derive the order of letters in this language.

    For example, Given the following words in dictionary,
    [ "wrt", "wrf", "er", "ett", "rftt" ] The correct order is: "wertf".
    
    Note:
    You may assume all letters are in lowercase. If the order is invalid, return 
    an empty string. There may be multiple valid order of letters, return any one of them is fine.
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
}
