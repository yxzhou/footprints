package fgafa.design.systemdesign.SearchAutocomplete;

import java.util.*;

/**
 *
 * Design a search autocomplete system for a search engine.
 * Users may input a sentence (at least one word and end with a special character '#'). For each character they type
 * except '#', you need to return the top 3 historical hot sentences that have prefix the same as the part of sentence
 * already typed. Here are the specific rules:
 *
 * The hot degree for a sentence is defined as the number of times a user typed the exactly same sentence before.
 * The returned top 3 hot sentences should be sorted by hot degree (The first is the hottest one).
 * If several sentences have the same degree of hot, you need to use ASCII-code order (smaller one appears first).
 * If less than 3 hot sentences exist, then just return as many as you can.
 * When the input is a special character, it means the sentence ends, and in this case, you need to return an empty list.
 * Your job is to implement the following functions:
 *
 * The constructor function: AutocompleteSystem(String[] sentences, int[] times): This is the constructor.
 * The input is historical data. Sentences is a string array consists of previously typed sentences. Times is the
 * corresponding times a sentence has been typed. Your system should record these historical data.
 *
 * Now, the user wants to input a new sentence. The following function will provide the next character the user types:
 * List<String> input(char c): The input c is the next character typed by the user. The character will only be lower-case
 * letters ('a' to 'z'), blank space (' ') or a special character ('#'). Also, the previously typed sentence should be
 * recorded in your system. The output will be the top 3 historical hot sentences that have prefix the same as the part of sentence already typed.
 *
 *
 * Example:
 * Operation: AutocompleteSystem(["i love you", "island","ironman", "i love leetcode"], [5,3,2,2])
 * The system have already tracked down the following sentences and their corresponding times:
 * "i love you" : 5 times
 * "island" : 3 times
 * "ironman" : 2 times
 * "i love leetcode" : 2 times
 * Now, the user begins another search:
 *
 * Operation: input('i')
 * Output: ["i love you", "island","i love leetcode"]
 * Explanation:
 * There are four sentences that have prefix "i". Among them, "ironman" and "i love leetcode" have same hot degree.
 * Since ' ' has ASCII code 32 and 'r' has ASCII code 114, "i love leetcode" should be in front of "ironman".
 * Also we only need to output top 3 hot sentences, so "ironman" will be ignored.
 *
 * Operation: input(' ')
 * Output: ["i love you","i love leetcode"]
 * Explanation:
 * There are only two sentences that have prefix "i ".
 *
 * Operation: input('a')
 * Output: []
 * Explanation:
 * There are no sentences that have prefix "i a".
 *
 * Operation: input('#')
 * Output: []
 * Explanation:
 * The user finished the input, the sentence "i a" should be saved as a historical sentence in system.
 * And the following input will be counted as a new search.
 *
 *
 * Note:
 * The input sentence will always start with a letter and end with '#', and only one blank space will exist between two words.
 * The number of complete sentences that to be searched won't exceed 100. The length of each sentence including those in
 * the historical data won't exceed 100.
 * Please use double-quote instead of single-quote when you write test cases even for a character input.
 * Please remember to RESET your class variables declared in class AutocompleteSystem, as static/class variables are
 * persisted across multiple test cases. Please see here for more details.
 *
 */

public class AutocompleteSystem {
    Map<String, Integer> map;

    class TrieNode {
        TrieNode[] nexts = new TrieNode[256]; // ' ' is 32, and 'z' is 122
        TreeSet<String> treeSet = new TreeSet<>((s1, s2) -> (map.get(s1) == map.get(s2)? s1.compareTo(s2) : map.get(s2) - map.get(s1)) );
    }

    TrieNode root;
    TrieNode curr;
    StringBuilder sb;

    public AutocompleteSystem(String[] sentences, int[] times) {
        root = new TrieNode();
        curr = root;
        sb = new StringBuilder();

        map = new HashMap<>();

        for(int i = 0; i < sentences.length; i++){
            map.put(sentences[i], times[i]);

            add(sentences[i], 0);
        }
    }

    public List<String> input(char c) {
        List<String> result = new ArrayList<>();

        if(c == '#'){
            add(sb.toString(), 1);

            curr = root;
            sb = new StringBuilder();
        }else {
            sb.append(c);

            if(curr != null){
                curr = curr.nexts[c];

                if(curr != null){
                    for(String s : curr.treeSet){
                        result.add(s);
                    }
                }
            }
        }

        return result;
    }


    private void add(String s, int count){
        if(map.containsKey(s)){
            count += map.get(s);
        }
        map.put(s, count);

        TrieNode curr = root;
        for(char c : s.toCharArray()){
            if(curr.nexts[c] == null){
                curr.nexts[c] = new TrieNode();
            }
            curr = curr.nexts[c];

            TreeSet<String> treeSet = curr.treeSet;

            if(treeSet.contains(s)){
                treeSet.remove(s);
            }
            treeSet.add(s);

            if(treeSet.size() > 3){
                treeSet.pollLast();
            }
        }

    }


    public static void main(String[] args){
//        TreeSet<String> treeSet = new TreeSet<>();
//
//        treeSet.add("i love you");
//        treeSet.add("island");
//        treeSet.add("ironman");
//        treeSet.add("i love leetcode");
//
//        System.out.println(treeSet.size());
//
//        List<String> list = new ArrayList<>();
//        for(String s : treeSet){
//            list.add(s);
//        }
//
//        System.out.println(list);
//        System.out.println(treeSet.size());


        AutocompleteSystem sv = new AutocompleteSystem(new String[]{"i love you", "island","ironman", "i love leetcode"}, new int[]{5,3,2,2});

        System.out.println(sv.input('i')); //["i love you", "island","i love leetcode"]
        System.out.println(sv.input(' ')); //["i love you","i love leetcode"]
        System.out.println(sv.input('a')); //
        System.out.println(sv.input('#')); //

        System.out.println(sv.input('i')); //["i love you", "island","i love leetcode"]
        System.out.println(sv.input(' ')); //["i love you","i love leetcode"]
        System.out.println(sv.input('a')); //["i a"]
        System.out.println(sv.input('#')); //



//        ["AutocompleteSystem","input","input","input","input","input","input","input","input","input","input","input","input","input","input"]
//        [[["abc","abbc","a"],[3,3,3]],["b"],["c"],["#"],["b"],["c"],["#"],["a"],["b"],["c"],["#"],["a"],["b"],["c"],["#"]]

        sv = new AutocompleteSystem(new String[]{"abc","abbc","a"}, new int[]{3,3,3});
        System.out.println(sv.input('b')); //[]
        System.out.println(sv.input('c')); //[]
        System.out.println(sv.input('#')); //[]

        System.out.println(sv.input('b')); //["bc"]
        System.out.println(sv.input('c')); //["bc"]
        System.out.println(sv.input('#')); //["bc"]

        System.out.println(sv.input('a')); //["abc","abbc","a"]
        System.out.println(sv.input('b')); //["abc","abbc"]
        System.out.println(sv.input('c')); //["abc","abbc"]
        System.out.println(sv.input('#')); //["abc","abbc"]

        System.out.println(sv.input('a')); //["abc","abbc","a"]
        System.out.println(sv.input('b')); //["abc","abbc"]
        System.out.println(sv.input('c')); //["abc","abbc"]
        System.out.println(sv.input('#')); //["abc","abbc"]
    }
}

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */
