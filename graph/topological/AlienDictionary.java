package graph.topological;

import org.junit.Assert;

import java.util.*;
import static org.apache.commons.lang3.CharSetUtils.count;


/**
 *
 *
 * There is a new alien language which uses the latin alphabet. However, the order among letters are unknown to you.
 * You receive a list of non-empty words from the dictionary, where words are sorted lexicographically by the rules of 
 * this new language. Derive the order of letters in this language.
 * 
 * Example 1:
 *   Input:[ "wrt", "wrf", "er", "ett", "rftt" ]
 *   Output: "wertf"
 *
 * Example 2: 
 *   Input: [ "z", "x" ] 
 *   Output: "zx"
 *
 * Example 3: 
 *   Input: [ "z", "x", "z" ] 
 *   Output: "" 
 *   Explanation: The order is invalid, so return "".
 * 
 * Note:
 *   You may assume all letters are in lowercase.
 *   You may assume that if a is a prefix of b, then a must appear before b in the given dictionary.
 *   If the order is invalid, return an empty string.
 *   There may be multiple valid order of letters, return any one of them is fine.
 *
 * Further:
 *   How to return the smallest in normal lexicographical order
 * 
 */

public class AlienDictionary {

    /**
     * 
     * @param words
     * @return any one of valid order of letters
     */
    public String alienOrder_pureTopological(String[] words) {
        if (null == words || words.length < 2) {
            return "";
        }

        Set<Character> set = new HashSet<>();
        for(String w : words){
            for(int i = 0; i < w.length(); i++){
                set.add(w.charAt(i));
            }
        }
                
        Map<Character, Set<Character>> edges = new HashMap<>();
        Map<Character, Integer> inDegrees = new HashMap<>();

        char cl;
        char cr;
        for(int i = 1, n = words.length; i < n; i++){
            String pre = words[i - 1];
            String curr = words[i];

            for(int j = 0, m = Math.min(pre.length(), curr.length()); j < m; j++){
                cl = pre.charAt(j);
                cr = curr.charAt(j);

                if(cl != cr){
                    edges.computeIfAbsent(cl, x -> new HashSet<>()).add(cr);
                    inDegrees.put(cr, inDegrees.getOrDefault(cr, 0) + 1);

                    break;
                }
            }
        }

        Queue<Character> queue = new LinkedList<>();
        for(char key : set){
            if(!inDegrees.containsKey(key)){
                queue.add(key);
            }
        }

        StringBuilder result = new StringBuilder();
        char from;
        while(!queue.isEmpty()){
            from = queue.poll();
            result.append(from);

            if(!edges.containsKey(from)){
                continue;
            }

            for(char to : edges.get(from)){
                if(inDegrees.get(to) == 1){
                    queue.add(to);
                }else{
                    inDegrees.put(to, inDegrees.get(to) - 1);
                }
            }

        }

        //is loop?
        return set.size() == result.length()? result.toString() : "";
    }

    /**
     * 
     * 
     * @param words
     * @return the smallest in normal lexicographical order
     */
    public String alienOrder_TopologicalAndLexicographical(String[] words) {
        if(words == null){
            return "";
        }

        Map<Character, Integer> inDegrees = new HashMap<>();
        for(String w : words){
            for(char c : w.toCharArray() ){
                inDegrees.put(c, 0);
            }
        }
        
        Map<Character, Set<Character>> graph = new HashMap<>();
        int k;
        int end;
        for(int i = 1, n = words.length; i < n; i++ ){
            String w1 = words[i - 1];
            String w2 = words[i];

            for(  k = 0, end = Math.min(w1.length(), w2.length() ); k < end; k++ ){
                char from = w1.charAt(k);
                char to = w2.charAt(k);

                if(from != to ){
                    if(!(graph.containsKey(from) && graph.get(from).contains(to)) ){
                        inDegrees.put(to, inDegrees.getOrDefault(to, 0) + 1);

                        graph.putIfAbsent(from, new HashSet<>());
                        graph.get(from).add(to);
                    }

                    break;
                }
            }

            if(k == end && w1.length() > w2.length() ){ //The dictionary is invalid, if a is prefix of b and b is appear before a.
                return "";
            }
        }

        char[] letters = new char[26];
        int m = 0;
        int count = 0; //count how many letters can be ordered
        for(char c = 'a'; c <= 'z'; c++){
           if(inDegrees.containsKey(c) ){
               letters[m++] = c;
               
               if(inDegrees.get(c) == 0){
                   count++;
               }
           }
        }

        char c;
        for(int i = 0 ; i < m; i++){
            for(int j = i; j < m; j++){
                c = letters[j];
                if(inDegrees.containsKey(c) && inDegrees.get(c) == 0 ){
                    //if(i < j){
                        System.arraycopy(letters, i, letters, i + 1, j - i);
                        letters[i] = c;
                    //}

                    if( graph.containsKey(c) ){
                        for(Character next : graph.get(c) ){
                            inDegrees.put(next, inDegrees.get(next) - 1 );

                            if(inDegrees.get(next) == 0){
                                count++;
                            }
                        }
                    }

                    break; //already added one character, jump out, m--
                }
            }
        }

        return count == m? new String(letters, 0, m) : "";
    }
    
    public String alienOrder_TopologicalAndLexicographical_n(String[] words) {
        if(words == null){
            return "";
        }

        Map<Character, Integer> inDegrees = new HashMap<>();
        for(String w : words){
            for(char c : w.toCharArray() ){
                inDegrees.put(c, 0);
            }
        }
        
        Map<Character, Set<Character>> graph = new HashMap<>();
        int k;
        int end;
        for(int i = 1, n = words.length; i < n; i++ ){
            String w1 = words[i - 1];
            String w2 = words[i];

            for(  k = 0, end = Math.min(w1.length(), w2.length() ); k < end; k++ ){
                char from = w1.charAt(k);
                char to = w2.charAt(k);

                if(from != to ){
                    if(!(graph.containsKey(from) && graph.get(from).contains(to)) ){
                        inDegrees.put(to, inDegrees.getOrDefault(to, 0) + 1);

                        graph.putIfAbsent(from, new HashSet<>());
                        graph.get(from).add(to);
                    }

                    break;
                }
            }

            if(k == end && w1.length() > w2.length() ){ //The dictionary is invalid, if a is prefix of b and b is appear before a.
                return "";
            }
        }

        Queue<Character> minHeap = new PriorityQueue<>();
        for(char c = 'a'; c <= 'z'; c++){
           if(inDegrees.containsKey(c) ){               
               if(inDegrees.get(c) == 0){
                   minHeap.add(c);
               }
           }
        }

        StringBuilder result = new StringBuilder();
        char c;
        while(!minHeap.isEmpty()){
            c = minHeap.poll();
            result.append(c);
                    
            if( !graph.containsKey(c) ){
                continue;
            }
            
            for(Character next : graph.get(c) ){
                inDegrees.put(next, inDegrees.get(next) - 1 );

                if(inDegrees.get(next) == 0){
                    minHeap.add(next);
                }
            }
        }

        return result.length() == inDegrees.size()? result.toString() : "";
    }
    
    public static void main(String[] args){
        Map<Character, Set<Integer>> c2p = new HashMap<>();
        for(int i = 0; i < 5; i++){
            c2p.computeIfAbsent('a', x -> new HashSet<>()).add(i);
        }
        
        String[][] inputs = {
            {"wrt","wrf","er","ett","rftt","te"},
            {"wrt","wrf","er","ett","rftt"},
            {"ab", "abc"},
            {"abc", "ab"},
            {"z","x"},
            {"zy","zx"},
            {"abc","bcd","qwert","ab"}
        };
        
        String[] expects = {
            "wertf",
            "wertf",
            "abc",
            "",
            "zx",
            "yxz",
            ""    
        };
        
        AlienDictionary sv = new AlienDictionary();
         
        for(int i = 0; i < inputs.length; i++){
            Assert.assertEquals(expects[i], sv.alienOrder_TopologicalAndLexicographical(inputs[i]));
            Assert.assertEquals(expects[i], sv.alienOrder_TopologicalAndLexicographical_n(inputs[i]));
            
            System.out.println(String.format("\n%s\n%s", expects[i], sv.alienOrder_pureTopological(inputs[i])));
        } 

    }
}
