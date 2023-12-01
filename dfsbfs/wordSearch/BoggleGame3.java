/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dfsbfs.wordSearch;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 */
public class BoggleGame3 {
    
    final int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    
    /*
     * @param board: a list of lists of character
     * @param words: a list of string
     * @return: an integer
     *
     * m1, DFS
     */

    public int boggleGame(char[][] board, String[] words) {
        if(board == null || board.length == 0 || words == null){
            return 0;
        }
        
        //build Trie tree
        TrieNode root = new TrieNode();
        for(String word : words){
            add(root, word);
        }

        //find all words' positions 
        Map<String, List<BitSet> > wordPositions = new HashMap<>();
        BitSet state = new BitSet();
        
        int n = board.length;
        int m = board[0].length;
        for(int r = 0; r < n; r++ ){
            for(int c = 0; c < m; c++){
                find(board, r, c, root, state, wordPositions);
            }
        }
        
        //find the largest collection of words on the board
        int max = 0;
            
        Map<BitSet, Integer> dp = new HashMap<>();
        List<BitSet> list = new ArrayList<>();
        list.add(new BitSet());
        BitSet curr;
        BitSet next;
        int x;
        
        for(String word : wordPositions.keySet()){
            for(BitSet toAdd : wordPositions.get(word)){
                        
                for(int i = list.size() - 1; i >= 0; i-- ){
                    curr = list.get(i);
                    if(!curr.intersects(toAdd) ){
                        next = (BitSet)curr.clone();
                        next.or(toAdd);
                        
                        if(( x = dp.getOrDefault(curr, 0) + 1) > dp.getOrDefault(next, 0) ){
                            dp.put(next, x);
                            list.add(next);
                            
                            max = Math.max(max, x);
                        }
                    }
                }
            }
            
        }
        
        return max;
    }
    
    class TrieNode{
        TrieNode[] children = new TrieNode[26];

        String word = null;
    }
    
    private void add(TrieNode root, String word){
        TrieNode curr = root;
        
        int p;
        for(int i = 0, len = word.length(); i < len; i++){
            p = word.charAt(i) - 'a';
            
            if(curr.children[p] == null){
                curr.children[p] = new TrieNode();
            }
            
            curr = curr.children[p];
        }
        
        curr.word = word;
    }


    private void find(char[][] board, int r, int c, TrieNode node, BitSet position, Map<String, List<BitSet>> wordPositions){
        if(r < 0 || r >= board.length || c < 0 || c >= board[0].length){
            return;
        }
        
        int index = r * board[0].length + c;
                
        if(position.get(index)){
            return;
        }

        node = node.children[board[r][c] - 'a'];
        if( node == null ){
            return;
        }
        
        position.set(index);
        
        if(node.word != null){
            wordPositions.computeIfAbsent(node.word, k -> new ArrayList<>()).add((BitSet)position.clone());
            //When found a word, it needn't continue to find the longer word. It would be better to leave more space for more words.
        }else{
            for(int[] diff : diffs){            
                find(board, r + diff[0], c + diff[1], node, position, wordPositions);
            }
        }

        position.set(index, false);
    }
    
}
