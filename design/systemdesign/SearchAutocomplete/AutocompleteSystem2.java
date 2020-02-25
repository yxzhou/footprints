package fgafa.design.systemdesign.SearchAutocomplete;

import org.junit.Assert;

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

public class AutocompleteSystem2 {
    class TrieNode{
        TrieNode[] nexts = new TrieNode[256];
        List<String> output = new ArrayList<>();
    }

    TrieNode head;
    Map<String, Integer> hots;
    static int K = 3;

    //for state
    StringBuilder input;
    TrieNode node;


    public AutocompleteSystem2(String[] sentences, int[] times) {
        head = new TrieNode();
        hots = new HashMap<>();

        for(int i = 0, end = sentences.length; i < end; i++){
            add(sentences[i], times[i]);
        }

        init();
    }

    private void init(){
        this.input = new StringBuilder();
        node = head;
    }

    public List<String> input(char c) {
        List<String> result = new ArrayList<>();

        if(c == '#'){
            add(input.toString(), 1);

            init();
            return result;
        }

        input.append(c);

        if(node.nexts[c] == null){
            node.nexts[c] = new TrieNode();
        }
        node = node.nexts[c];

        return node.output;
    }

    private void add(String sentence, int times){
        hots.put(sentence, hots.getOrDefault(sentence, 0) + times);

        TrieNode curr = head;
        for(char c : sentence.toCharArray()){
            if(curr.nexts[c] == null){
                curr.nexts[c] = new TrieNode();
            }

            curr = curr.nexts[c];

            add(curr.output, sentence);
        }
    }

    private int compare(String s1, String s2){
        int diff = hots.getOrDefault(s2, 0) - hots.getOrDefault(s1, 0);
        return diff == 0 ? s1.compareTo(s2) : diff;
    }

    private void add(List<String> list, String sentence){
        list.remove(sentence);

        int end = list.size();
        int diff;
        for( int i = 0; i < end; i++ ){

            diff = compare(list.get(i), sentence);
            if( diff > 0){
                list.add(i, sentence);
                break;
            }
        }

        if( list.size() < K && end == list.size()){
            list.add(sentence);
        }else if(list.size() > K){
            list.remove(list.size() - 1);
        }
    }

    public static void main(String[] args){

        AutocompleteSystem2 sv = new AutocompleteSystem2(new String[]{"i love you", "island","ironman", "i love leetcode"}, new int[]{5,3,2,2});

        System.out.println(sv.input('i')); //["i love you", "island","i love leetcode"]
        System.out.println(sv.input(' ')); //["i love you","i love leetcode"]
        System.out.println(sv.input('a')); //
        System.out.println(sv.input('#')); //

        System.out.println(sv.input('i')); //["i love you", "island","i love leetcode"]
        System.out.println(sv.input(' ')); //["i love you","i love leetcode", "i a"]
        System.out.println(sv.input('a')); //["i a"]
        System.out.println(sv.input('#')); //[]


        sv = new AutocompleteSystem2(new String[]{"abc","abbc","a"}, new int[]{3,3,3});
        System.out.println(sv.input('b')); //[]
        System.out.println(sv.input('c')); //[]
        System.out.println(sv.input('#')); //[]

        System.out.println(sv.input('b')); //["bc"]
        System.out.println(sv.input('c')); //["bc"]
        System.out.println(sv.input('#')); //[]

        System.out.println(sv.input('a')); //["abc","abbc","a"]
        System.out.println(sv.input('b')); //["abc","abbc"]
        System.out.println(sv.input('c')); //["abc"]
        System.out.println(sv.input('#')); //[]

        System.out.println(sv.input('a')); //["abc","abbc","a"]
        System.out.println(sv.input('b')); //["abc","abbc"]
        System.out.println(sv.input('c')); //["abc"]
        System.out.println(sv.input('#')); //[]


        String[] sentences = {"ccc","cc ccccc c ccccc","c ccc ccc cc","c cc cc cc","ccc c","c ccccc ccc ccccc","cccc cccc","ccccc","cc cc cc","c ccc cccc","c cccc ccccc ccccc","ccc ccccc ccccc","c ccc","cc","ccc cc ccccc","ccccc ccccc cc ccc","cccc","ccc cc cccc","cc c ccccc cc","cccc cc","c ccc ccc","ccccc ccc ccccc ccc","ccccc ccc","c ccccc c","ccccc cccc ccc","cccc c ccc","cc ccccc ccccc","ccc ccc cccc c","ccccc ccccc","c ccccc cccc","ccc c c","cc ccc ccc","ccc ccccc ccc","ccccc ccc c","ccc cc c","cccc cccc c c","ccccc cccc ccccc","c cc ccc","cccc ccc c","cc cc c cccc","cc ccccc ccc cccc","cc ccccc","cccc c ccccc","ccc cc cc","c cc c ccccc"};
        int[] times = {1,3,1,5,3,2,2,3,1,5,2,4,2,3,3,2,1,2,5,2,1,1,3,2,5,5,3,2,2,2,3,1,3,2,5,1,3,2,4,4,3,5,3,1,5};

        sv = new AutocompleteSystem2(sentences, times);

        String[] cmds = {"input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input","input"};
        String[][] inputs = {{"c"},{"c"},{"c"},{" "},{"c"},{"c"},{"c"},{"c"},{"c"},{" "},{"c"},{"c"},{"c"},{"#"},{"c"},{"c"},{"c"},{"#"},{"c"},{"c"},{"c"},{" "},{"c"},{"c"},{"c"},{"c"},{"c"},{" "},{"c"},{"#"},{"c"},{"c"},{"#"},{"c"},{"c"},{" "},{"c"},{"c"},{"c"},{"c"},{" "},{"c"},{"c"},{"c"},{"c"},{"c"},{" "},{"c"},{"c"},{"c"},{"c"},{"#"},{"c"},{"c"},{"#"},{"c"},{"c"},{"c"},{"#"},{"c"},{"c"},{"c"},{"c"},{"c"},{" "},{"c"},{"c"},{"c"},{"c"},{"c"},{" "},{"c"},{"c"},{"#"},{"c"},{"c"},{"c"},{"c"},{" "},{"c"},{" "},{"c"},{"c"},{" "},{"c"},{"c"},{"c"},{"c"},{"c"},{"#"},{"c"},{"c"},{"#"},{"c"},{"c"},{"c"},{" "},{"c"},{" "},{"c"},{"c"},{" "},{"c"},{"c"},{"#"},{"c"},{"c"},{"c"},{" "},{"c"},{"c"},{"c"},{"c"},{"c"},{"#"},{"c"},{"c"},{"c"},{"c"},{"c"},{" "},{"c"},{"c"},{"c"},{"c"},{"c"},{"#"},{"c"},{"c"},{" "},{"c"},{"c"},{"#"},{"c"},{"c"},{" "},{"c"},{"c"},{"c"},{"c"},{"c"},{" "},{"c"},{"c"},{"c"},{" "},{"c"},{"#"},{"c"},{"c"},{"c"},{" "},{"c"},{"c"},{"c"},{"c"},{"c"},{" "},{"c"},{"c"},{"c"},{"c"},{" "},{"c"},{"c"},{"c"},{"c"},{"c"},{"#"},{"c"},{" "},{"c"},{"c"},{"c"},{"#"},{"c"},{"c"},{"c"},{"c"},{"c"},{" "},{"c"},{"c"},{"c"},{"c"},{" "},{"c"},{"c"},{"#"},{"c"},{"c"},{"c"},{"c"},{"c"},{"#"},{"c"},{"c"},{"c"},{" "},{"c"},{"#"}};

        String[][] output =  {{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc c ccccc cc","cc ccccc","ccc cc c"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"ccc cc c","ccc ccccc ccccc","ccc c"},{"ccc cc c","ccc ccccc ccccc","ccc c"},{"ccc cc c","ccc ccccc ccccc","ccc cc ccccc"},{"ccc ccccc ccccc","ccc ccccc ccc","ccc ccc cccc c"},{"ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccccc","ccc ccccc ccc"},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc c ccccc cc","cc ccccc","ccc cc c"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc c ccccc cc","cc ccccc","ccc cc c"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccc cccc c"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc ccc"},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc c ccccc cc","cc ccccc","ccc cc c"},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc c ccccc cc","cc ccccc","ccc cc c"},{"cc c ccccc cc","cc ccccc","cc cc c cccc"},{"cc c ccccc cc","cc ccccc","cc cc c cccc"},{"cc ccccc","cc cc c cccc","cc ccccc c ccccc"},{"cc ccccc","cc ccccc c ccccc","cc ccccc ccc cccc"},{"cc ccccc","cc ccccc c ccccc","cc ccccc ccc cccc"},{},{},{},{},{},{},{},{},{},{},{},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc c ccccc cc","cc ccccc","ccc cc c"},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"cccc c ccc","ccccc cccc ccc","cccc ccc c"},{"ccccc cccc ccc","ccccc","ccccc ccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc cccc ccccc","ccccc ccccc"},{"ccccc ccccc","ccccc ccccc cc ccc"},{"ccccc ccccc cc ccc"},{"ccccc ccccc cc ccc"},{"ccccc ccccc cc ccc"},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"cccc c ccc","ccccc cccc ccc","cccc ccc c"},{"cccc c ccc","cccc ccc c","cccc c ccccc"},{"cccc c ccc","cccc ccc c","cccc c ccccc"},{"cccc c ccc","cccc c ccccc"},{"cccc c ccc","cccc c ccccc"},{"cccc c ccc","cccc c ccccc"},{},{},{},{},{},{},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc","cc c ccccc cc","cc ccccc"},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc c c"},{"ccc c c"},{},{},{},{},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccc cccc c"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc ccc"},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"cccc c ccc","ccccc cccc ccc","cccc ccc c"},{"ccccc cccc ccc","ccccc","ccccc ccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc cccc ccccc","ccccc ccccc"},{"ccccc ccccc","ccccc ccccc cc ccc","ccccc ccccc cc"},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"cc c ccccc cc","cc ccccc","cc cc c cccc"},{"cc c ccccc cc","cc ccccc","cc cc c cccc"},{"cc ccccc","cc cc c cccc","cc ccccc c ccccc"},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"cc c ccccc cc","cc ccccc","cc cc c cccc"},{"cc c ccccc cc","cc ccccc","cc cc c cccc"},{"cc ccccc","cc cc c cccc","cc ccccc c ccccc"},{"cc ccccc","cc ccccc c ccccc","cc ccccc ccc cccc"},{"cc ccccc","cc ccccc c ccccc","cc ccccc ccc cccc"},{"cc ccccc","cc ccccc c ccccc","cc ccccc ccc cccc"},{"cc ccccc c ccccc","cc ccccc ccc cccc","cc ccccc ccccc"},{"cc ccccc c ccccc","cc ccccc ccc cccc","cc ccccc ccccc"},{"cc ccccc ccc cccc","cc ccccc ccccc"},{"cc ccccc ccc cccc","cc ccccc ccccc"},{"cc ccccc ccc cccc"},{"cc ccccc ccc cccc"},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccc cccc c"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccccc"},{},{},{},{},{},{},{},{"cc","c cc c ccccc","c cc cc cc"},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"c ccc cccc","c ccc","c cccc ccccc ccccc"},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"cccc c ccc","ccccc cccc ccc","cccc ccc c"},{"ccccc cccc ccc","ccccc","ccccc ccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc cccc ccccc","ccccc ccccc"},{"ccccc cccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc cccc ccccc"},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"cccc c ccc","ccccc cccc ccc","cccc ccc c"},{"ccccc cccc ccc","ccccc","ccccc ccc"},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{}};
        String[][] expects = {{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc c ccccc cc","cc ccccc","ccc cc c"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"ccc cc c","ccc ccccc ccccc","ccc c"},{"ccc cc c","ccc ccccc ccccc","ccc c"},{"ccc cc c","ccc ccccc ccccc","ccc cc ccccc"},{"ccc ccccc ccccc","ccc ccccc ccc","ccc ccc cccc c"},{"ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccccc","ccc ccccc ccc"},{"ccc ccccc ccccc","ccc ccccc ccc"},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc c ccccc cc","cc ccccc","ccc cc c"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc c ccccc cc","cc ccccc","ccc cc c"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccc cccc c"},{"ccc ccccc ccc","ccc ccccc ccccc"},{"ccc ccccc ccc","ccc ccccc ccccc"},{"ccc ccccc ccc","ccc ccccc ccccc"},{"ccc ccccc ccc","ccc ccccc ccccc"},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc c ccccc cc","cc ccccc","ccc cc c"},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc c ccccc cc","cc ccccc","ccc cc c"},{"cc c ccccc cc","cc ccccc","cc cc c cccc"},{"cc c ccccc cc","cc ccccc","cc cc c cccc"},{"cc ccccc","cc cc c cccc","cc ccccc c ccccc"},{"cc ccccc","cc ccccc c ccccc","cc ccccc ccc cccc"},{"cc ccccc","cc ccccc c ccccc","cc ccccc ccc cccc"},{},{},{},{},{},{},{},{},{},{},{},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc c ccccc cc","cc ccccc","ccc cc c"},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"cccc c ccc","ccccc cccc ccc","cccc ccc c"},{"ccccc cccc ccc","ccccc","ccccc ccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc cccc ccccc","ccccc ccccc"},{"ccccc ccccc","ccccc ccccc cc ccc"},{"ccccc ccccc cc ccc"},{"ccccc ccccc cc ccc"},{"ccccc ccccc cc ccc"},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"cccc c ccc","ccccc cccc ccc","cccc ccc c"},{"cccc c ccc","cccc ccc c","cccc c ccccc"},{"cccc c ccc","cccc ccc c","cccc c ccccc"},{"cccc c ccc","cccc c ccccc"},{"cccc c ccc","cccc c ccccc"},{"cccc c ccc","cccc c ccccc"},{},{},{},{},{},{},{},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"cc","cc c ccccc cc","cc ccccc"},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc c c"},{"ccc c c"},{},{},{},{},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccc cccc c"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc c"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc c"},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"cccc c ccc","ccccc cccc ccc","cccc ccc c"},{"ccccc cccc ccc","ccccc","ccccc ccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc cccc ccccc","ccccc ccccc"},{"ccccc ccccc","ccccc ccccc cc ccc","ccccc ccccc cc"},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"cc c ccccc cc","cc ccccc","cc cc c cccc"},{"cc c ccccc cc","cc ccccc","cc cc c cccc"},{"cc ccccc","cc cc c cccc","cc ccccc c ccccc"},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"cc c ccccc cc","cc ccccc","cc cc c cccc"},{"cc c ccccc cc","cc ccccc","cc cc c cccc"},{"cc ccccc","cc cc c cccc","cc ccccc c ccccc"},{"cc ccccc","cc ccccc c ccccc","cc ccccc ccc cccc"},{"cc ccccc","cc ccccc c ccccc","cc ccccc ccc cccc"},{"cc ccccc","cc ccccc c ccccc","cc ccccc ccc cccc"},{"cc ccccc c ccccc","cc ccccc ccc cccc","cc ccccc ccccc"},{"cc ccccc c ccccc","cc ccccc ccc cccc","cc ccccc ccccc"},{"cc ccccc ccc cccc","cc ccccc ccccc"},{"cc ccccc ccc cccc","cc ccccc ccccc"},{"cc ccccc ccc cccc"},{"cc ccccc ccc cccc"},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccc cccc c"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc c"},{"ccc ccccc ccc","ccc ccccc ccccc","ccc ccccc c"},{"ccc ccccc ccc","ccc ccccc ccccc"},{"ccc ccccc ccc","ccc ccccc ccccc"},{"ccc ccccc ccccc"},{},{},{},{},{},{},{},{"cc","c cc c ccccc","c cc cc cc"},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"c cc c ccccc","c cc cc cc","c ccc cccc"},{"c ccc cccc","c ccc","c cccc ccccc ccccc"},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"cccc c ccc","ccccc cccc ccc","cccc ccc c"},{"ccccc cccc ccc","ccccc","ccccc ccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc cccc ccccc","ccccc ccccc"},{"ccccc cccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc cccc ccccc"},{"ccccc cccc ccc","ccccc cccc ccccc"},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"cccc c ccc","ccccc cccc ccc","cccc ccc c"},{"ccccc cccc ccc","ccccc","ccccc ccc"},{},{"cc","c cc c ccccc","c cc cc cc"},{"cc","cc c ccccc cc","cc ccccc"},{"ccc cc c","cccc c ccc","ccccc cccc ccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{"ccc cc c","ccc ccccc ccc","ccc ccccc ccccc"},{}};

        for(int i = 0, end = cmds.length; i < end; i++){
            List<String> result = sv.input(inputs[i][0].toCharArray()[0]);

            System.out.println("--"+i);
            System.out.println(Arrays.toString(expects[i]));
            System.out.println(result.toString());

            Assert.assertEquals(Arrays.toString(expects[i]), result.toString());
        }
    }
}

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */
