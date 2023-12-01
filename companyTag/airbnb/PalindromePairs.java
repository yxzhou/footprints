/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collector;
import org.junit.Assert;
import util.Misc;

/**
 *
 * Given a list of unique words, find all pairs of distinct indices (i, j) in the given list, so that the concatenation
 * of the two words, i.e. words[i] + words[j] is a palindrome.
 *
 * Thoughts
 *   Define n as the length of words, m as the average length of word
 * 
 *   s1: brute force, check every pair 
 *   Time complexity O(n * n * m),  Space O(1)
 * 
 *   s2: prefix tree, Trie tree 
 *   Time complexity O(n * m * m + n * m ),  Space O(1)
 * 
 */
public class PalindromePairs {
    
    public List<List<Integer>> palindromePairs(String[] words) {
        if(words == null){
            return Collections.EMPTY_LIST;
        }
        
        List<List<Integer>> result = new ArrayList<>();
        
        for(int i = 0, n = words.length; i < n; i++){
            for(int j = i + 1; j < n; j++){
                if(isPalindrome(words[i] + words[j])){
                    result.add(Arrays.asList(i, j));
                }
                
                if(isPalindrome(words[j] + words[i])){
                    result.add(Arrays.asList(j, i));
                }
            }
        }
        
        return result;
    }
    
    private boolean isPalindrome(String s){        
        return isPalindrome(s, 0, s.length() - 1);
    }
    
    private boolean isPalindrome(String s, int start, int end){
        for(int l = start, r = end; l < r; l++, r--){
            if(s.charAt(l) != s.charAt(r)){
                return false;
            }
        }
        
        return true;
    }
    
    /**
     *  Notes, 
     *    1 no duplicate word
     * 
     * @param words
     * @return 
     */
    public List<List<Integer>> palindromePairs_TrieTree(String[] words) {
        if(words == null){
            return Collections.EMPTY_LIST;
        }
        
        int n = words.length;
        String word;
                
        //calculate the palindrome of each word, O(n * m * m)
        boolean[][] palindromes = new boolean[n][]; 
        for(int i = 0, m; i < n; i++){
            word = words[i];
            m = word.length();
            palindromes[i] = new boolean[m];
            
            for(int j = 0; j < m; j++){
                palindromes[i][j] = isPalindrome(word, 0, j);
            }
        }
        
        //build Trie tree,  O(n * m)
        Node root = new Node();
        for(int i = 0; i < n; i++){
            add(root, words[i], i);
        }
        
        // O(n * m)
        List<List<Integer>> result = new ArrayList<>();
        Node curr;
        for(int i = 0; i < n; i++){
            word = words[i];
            curr = root;
                    
            for(int j = word.length() - 1; j >= 0; j--){
                
                if( palindromes[i][j] && curr.wordId >= 0 ){
                    result.add(Arrays.asList(curr.wordId, i));
                    
                    if(curr == root){ //special for case { "aba", "" }
                        result.add(Arrays.asList(i, curr.wordId));
                    }
                }
                
                curr = curr.nexts[word.charAt(j)];
                if(curr == null){
                    break;
                }
                
            }
            
            if(curr != null && curr.wordId >= 0 && curr.wordId != i){
                result.add(Arrays.asList(curr.wordId, i));
            }
        }
        
        return result;
    }
    
    class Node{
        Node[] nexts = new Node[128];
        int wordId = -1;
    }
    
    private void add(Node root, String word, int id) {
        Node curr = root;

        int c;
        for (int i = 0, m = word.length(); i < m; i++) {
            c = word.charAt(i);

            if (curr.nexts[c] == null) {
                curr.nexts[c] = new Node();
            }

            curr = curr.nexts[c];
        }

        curr.wordId = id;
    }
    
    public static void main(String[] args){
        
        String[][][] inputs = {
            {
                {"bat", "tab", "cat"},
                {"0, 1", "1, 0"}
            },
            {
                {"abcd", "dcba", "lls", "s", "sssll"},
                {"0, 1", "1, 0", "2, 4", "3, 2"}
            },
            {
                {"a", ""},
                {"0, 1", "1, 0"}
            }
        };
        
        PalindromePairs sv = new PalindromePairs();
        
        String expect;
        List<List<Integer>> result;
        for(String[][] input : inputs){
            System.out.println(String.format("words: %s", Arrays.toString(input[0])) );
            
            expect = Arrays.stream(input[1]).collect(Collector.of(() -> new StringJoiner(""), (j, p) -> j.add(String.format("[%s]", p)), (j1, j2) -> j1.merge(j2), StringJoiner::toString ));
                         
            result = sv.palindromePairs(input[0]);
            Collections.sort(result, (a, b) -> Objects.equals(a.get(0), b.get(0)) ? Integer.compare(a.get(1), b.get(1)) : Integer.compare(a.get(0), b.get(0)) );
            Assert.assertEquals(String.format("words: %s", Arrays.toString(input[0])), expect, Misc.array2String(result).toString());
            
            result = sv.palindromePairs_TrieTree(input[0]);
            Collections.sort(result, (a, b) -> Objects.equals(a.get(0), b.get(0)) ? Integer.compare(a.get(1), b.get(1)) : Integer.compare(a.get(0), b.get(0)) );
            Assert.assertEquals(String.format("words: %s", Arrays.toString(input[0])), expect, Misc.array2String(result).toString());
            
        }
        
        
    }
    
}
