/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.trie;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * Compare to TypeAhead, it stored String of words in every TrieNode, if it's String object instead of String literal, 
 * it maybe need much more memory than String literal.
 * Here it simulate the String Pool. 
 * 
 */
public class Typeahead2 {
    class TrieNode{
        Map<Character, TrieNode> children = new HashMap<>();//
        Set<Integer> words = new HashSet<>(); //<wordId>
    } 

    TrieNode root;
    String[] pool;

    /*
    * @param dict: A dictionary of words dict
    */
    public Typeahead2(Set<String> dict) {
        root = new TrieNode();
        pool = new String[dict.size()];

        int wordId = 0;
        for(String word : dict){
            for(int i = word.length() - 1; i >= 0; i--){
                add(word.toCharArray(), i, wordId);
            }
            pool[wordId++] = word;
        }
    }

    /*
     * @param str: a string
     * @return: a list of words
     */
    public List<String> search(String str) {
        TrieNode curr = root;
        for(char c : str.toCharArray()){
            curr = curr.children.get(c);

            if(curr == null){
                return Collections.EMPTY_LIST;
            }
        }

        List<String> result = new ArrayList(curr.words.size());
        curr.words.forEach(wordId -> result.add(pool[wordId]));
        return result;
        //return curr.words.stream().map(wordId -> pool[wordId]).collect(Collectors.toList());
    }

    private void add(char[] str, int p, int wordId){
        TrieNode curr = root;

        for(; p < str.length; p++){
            curr.children.putIfAbsent(str[p], new TrieNode());
            curr = curr.children.get(str[p]);

            curr.words.add(wordId);
        }
    }
}
