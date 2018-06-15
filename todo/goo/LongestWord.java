package fgafa.todo.goo;

import java.util.*;

/**
 *
 * 给定一个字符串列表words，找到words最长的word，使得这个word可用words中的其他word一次一个字符地构建。
 * 如果有多个可选答案，则返回最长的且具有最小字典序的word。
 *
 *
 * Example:
 * Ⅰ. Input: words = ["w","wo","wor","worl", "world"]
      Output: "world"
      Explanation: “world”可通过”w”, “wo”, “wor”, “worl”一次一个字符进行构建。

   Ⅱ . Input: words = ["a", "banana", "app", "appl", "ap", "apply", "apple"]
       Output:"apple"
       Explanation: “apply”和”apple”都可以由其他字符构建。但”apple”的字典序要小于”apply”。
 *
 * Note:
 *     所有的输入字符只包含小写字符。
 *     words的长度在[1, 1000]范围内。
 *     words[i]的长度在[1, 30]范围内。
 */

public class LongestWord {

    public String findLongestWord_hashtable_prefix(String[] words){
        if(null == words || 0 == words.length){
            return null;
        }

        List<String> startPoints = new ArrayList<>();
        Map<String, Set<String>> prefix2Words = new HashMap<>();

        for(String word : words){
            if( 1 == word.length()){
                startPoints.add(word);
            }else{
                String prefix = word.substring(0, word.length() - 1);
                if(!prefix2Words.containsKey(prefix)){
                    prefix2Words.put(prefix, new HashSet<>());
                }
                prefix2Words.get(prefix).add(word);
            }
        }

        Stack<String> results = new Stack<>();
        for(String startPoint : startPoints){
            dfs(results, startPoint, prefix2Words);
        }

        return results.isEmpty() ? null : results.peek();
    }

    private void dfs(Stack<String> result, String word,  Map<String, Set<String>> prefix2Words ){
        if(!prefix2Words.containsKey(word)){
            if(result.isEmpty()){
                result.add(word);
            }else{
                result.add(getLongest(result.pop(), word));
            }

            return;
        }

        for(String next : prefix2Words.get(word)){
            dfs(result, next, prefix2Words);
        }
    }

    public String findLongestWord_hashtable(String[] words) {
        if (null == words || 0 == words.length) {
            return null;
        }

        Map<String, Boolean> map = new HashMap<>(words.length);
        for(String word : words){
            if(word.length() == 1){
                map.put(word, true);
            }else{
                map.put(word, false);
            }
        }

        for(String word : words){
            dfs(map, word);
        }

        String result = "z";
        for(String word : map.keySet()){
            if(map.get(word)){
                result = getLongest(result, word);
            }
        }

        return result;
    }

    private void dfs(Map<String, Boolean> map, String word){
        if(map.get(word).equals(true) ){
            return;
        }

        String prefix = word.substring(0, word.length() - 1);

        if(map.containsKey(prefix)){
            dfs(map, prefix);
            map.put(word, map.get(prefix));
        }
    }

    private String getLongest(String o1, String o2) {
        if(o1.length() > o2.length()){
            return o1;
        }else if(o1.length() < o2.length()){
            return o2;
        }

        //o1.length == o2.length
        for(int i = 0; i < o1.length(); i++){
            int diff = o1.charAt(i) - o2.charAt(i);
            if(diff > 0){
                return o1;
            }else if(diff < 0){
                return o2;
            }
        }
        return o1;
    }

    public String findLongestWord_trie(String[] words){
        if (null == words || 0 == words.length) {
            return null;
        }

        TrieNode root = new TrieNode();
        for(String word : words){
            TrieNode curr = root;
            for(char c : word.toCharArray()){
                int i = c - 65;

                if(null == curr.children[i]){
                    curr.children[i] = new TrieNode();
                }

                curr = curr.children[i];
            }

            curr.word = word;
        }

        Stack<String> result = new Stack<>();
        dfs(root, result);
        return result.isEmpty()? null : result.peek();
    }

    private void dfs(TrieNode node, Stack<String> result){
        if(null != node.word){
            if(result.isEmpty()){
                result.add(getLongest(result.pop(), node.word));
            }else{
                result.add(node.word);
            }
        }

        for(int i = 0; i < node.children.length; i++){
            if(null != node.children[i]){
                dfs(node.children[i], result);
            }
        }
    }

    class TrieNode{
        TrieNode[] children = new TrieNode[26];
        String word = null;
    }
}
