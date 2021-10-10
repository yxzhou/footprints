/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.trie;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implement typeahead. Given a string and a dictionary, return all words that contains the string as a substring. 
 * The dictionary will give at the initialize method and won't be changed. The method to find all words with given 
 * substring would be called multiple times.
 * 
 * Example 1
 * Input: dict=["Jason Zhang", "James Yu", "Lee Zhang", "Yanny Li"]
 * search("Zhang")
 * search("James")
 * 
 * Output:
 * ["Jason Zhang","Lee Zhang"]
 * ["James Yu"]
 * 
 * Example 2
 * Input: dict=["San Zhang","Lisi","Li Ma","Jimmy Wang"]
 * search("Li")
 * 
 * Output:
 * ["Li Ma","Lisi"]
 * 
 */
public class Typeahead {

    class TrieNode{
        Map<Character, TrieNode> children = new HashMap<>();;
        Set<String> words = new HashSet<>();
    } 

    TrieNode root;

    /*
    * @param dict: A dictionary of words dict
    */
    public Typeahead(Set<String> dict) {
        root = new TrieNode();

        char[] str;
        for(String word : dict){
            str = word.toCharArray();
            for(int i = word.length() - 1; i >= 0; i--){
                add(str, i, word);
            }
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

        return new LinkedList<>(curr.words);
    }

    private void add(char[] str, int p, String word){
        TrieNode curr = root;

        for(; p < str.length; p++){
            curr.children.putIfAbsent(str[p], new TrieNode());
            curr = curr.children.get(str[p]);

            curr.words.add(word);
        }
    }
    
}
