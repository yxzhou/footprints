/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * Given a set of words without duplicates, find all word squares you can build from them.
 * A sequence of words forms a valid word square, pls refer to WordSquare
 * 
 * Notes: 
 *   There are at least 1 and at most 1000 words.
 *   All words will have the exact same length.
 *   Word length is at least 1 and at most 5.
 *   Each word contains only lowercase English alphabet a-z.
 *   
 * Example 1:
 * Input: ["area","lead","wall","lady","ball"]
 * Output: [["wall","area","lead","lady"],["ball","area","lead","lady"]]
 * Explanation:
 * The output consists of two word squares. The order of output does not matter (just the order of words in each word square matters).
 * 
 */
public class WordSquareII {
    class TrieNode{
        TrieNode[] children = new TrieNode[26]; //from a to z
        String word;
    }

    /**
     * @param words: a set of words without duplicates
     * @return: all word squares
     */
    public List<List<String>> wordSquares(String[] words) {
        if(words == null || words.length == 0){
            return Collections.EMPTY_LIST;
        }

        TrieNode root = new TrieNode();
        add(root, words);

        List<List<String>> result = new LinkedList<>();

        int m = words[0].length(); // all words have the same length, n == m
        char[] prefix = new char[m];
        String[] square = new String[m];
        for(String word : words){
            square[0] = word;
            buildSquare(root, square, 1, prefix, result);
        }

        return result;
    }

    private void add(TrieNode root, String[] words){
        
        int i;
        for(String word : words){

            TrieNode curr = root;
            for(char c : word.toCharArray()){
                i = c - 'a';
                if(curr.children[i] == null){
                    curr.children[i] = new TrieNode();
                }
                curr = curr.children[i];
            }

            curr.word = word;
        }
    }

    private List<String> search(TrieNode root, char[] prefix, int end){
        List<String> result = new LinkedList<>();

        TrieNode curr = root;
        for(int i = 0; i < end && curr != null; i++){
            curr = curr.children[prefix[i] - 'a'];
        }

        if(curr != null){
            getAll(curr, result);
        }
        
        return result;
    }

    private void getAll(TrieNode node, List<String> result){
        if(node.word != null){
            result.add(node.word);
            return;
        }

        for(TrieNode child : node.children){
            if(child != null){
                getAll(child, result);
            }
        }
    }

    private void buildSquare(TrieNode root, String[] square, int row, char[] prefix, List<List<String>> result){
        if(row == square.length){
            result.add( new ArrayList(Arrays.asList(square)) ); //Arrays.asList() is not a deep clone
            return;
        }

        for(int i = 0; i < row; i++){
            prefix[i] = square[i].charAt(row); 
        }
        
        for(String next : search(root, prefix, row)){
            square[row] = next; 
            buildSquare(root, square, row + 1, prefix, result);
        }
    }
}
