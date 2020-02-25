package fgafa.graph.topological;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;


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
    @Test public void test(){
//        Assert.assertEquals("wertf", alienOrder_n(new String[]{"wrt", "wrf", "er", "ett", "rftt"}));
//
//        Assert.assertEquals("zx", alienOrder_n(new String[]{"z", "x"}));
//
//        Assert.assertEquals("", alienOrder_n(new String[]{"z", "x", "z"}));
//
//        Assert.assertEquals("abcd", alienOrder_n(new String[]{"ab","adc"}));

        Assert.assertEquals("abcgilmnrstuvwyzxhqjdfo", alienOrder_n(new String[]{"ri","xz","qxf","jhsguaw","dztqrbwbm","dhdqfb","jdv","fcgfsilnb","ooby"}));


    }


    public String alienOrder_n(String[] words) {
        if (null == words || words.length < 2) {
            return "";
        }

        Map<Character, Set<Character>> edges = new HashMap<>();
        Map<Character, Integer> inDegrees = new HashMap<>();
        Set<Character> set = new HashSet<>();

        char cl;
        char cr;
        for(int i = 1, n = words.length; i < n; i++){
            String pre = words[i - 1];
            String curr = words[i];

            fill(set, pre);
            fill(set, curr);

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

        Map<Character, Set<Integer>> c2p = new HashMap<>();
        for(int i = 0; i < 5; i++){
            c2p.computeIfAbsent('a', x -> new HashSet<>()).add(i);
        }

        //is loop?
        return set.size() == result.length()? result.toString() : "";
    }

    private void fill(Set<Character> set, String s ){
        for(char c : s.toCharArray()){
            set.add(c);
        }
    }
}
