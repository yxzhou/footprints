/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dfsbfs.wordSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/635
 * 
 * Given a board which is a 2D matrix includes a-z and dictionary dict, find the largest collection of words on the
 * board, the words can not overlap in the same position. return the size of largest collection.
 *
 * The words in the dictionary are not repeated. You can reuse the words in the dictionary. 
 * 
 * Example 1:
 * Input: ["abc","def","ghi"] ["abc","defi","gh"] 
 * Output: 3
 * Explanation: we can get the largest collection`["abc", "defi", "gh"]` 
 * 
 * Example 2:
 * Input: ["aaaa","aaaa","aaaa","aaaa"] ["a"] 
 * Output: 16
 * Explanation: we can get the largest collection`["a", "a","a","a","a","a","a","a","a","a","a","a","a","a","a","a"]`
 * 
 * Thoughts:
 *   compare to WordSearchIII, here it can reuse the words.
 * 
 * 
 */
public class BoggleGame {
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
        
        TrieNode root = new TrieNode();
        for(String word : words){
            add(root, word);
        }

        Map<String, List<BitSet> > wordPositions = new HashMap<>();
        BitSet state = new BitSet();
        
        int n = board.length;
        int m = board[0].length;
        for(int r = 0; r < n; r++ ){
            for(int c = 0; c < m; c++){
                dfs(board, r, c, root, state, wordPositions);
            }
        }
        
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

    final int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private void dfs(char[][] board, int r, int c, TrieNode node, BitSet position, Map<String, List<BitSet>> wordPositions){
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
        }

        for(int[] diff : diffs){            
            dfs(board, r + diff[0], c + diff[1], node, position, wordPositions);
        }

        position.set(index, false);
    }
    
    
    public static void main(String[] args){

        String[][][] inputs = {
            {
                {"a"},
                {"b"},
                {"0"}
            },
            {
                {
                    "doaf",
                    "agai",
                    "dcan"
                },
                {"dog","dad","dgdg","can","again"},
                {"2"}
            },
            {
                {"abce",
                 "sfes",
                 "adee"},
                {"abceseeefs","abceseedasfe"},
                {"1"}
            },
            {
                {"ab", "cd"}, 
                {"a", "b", "cd", "ab", "ac", "abd"},
                {"3"}
            },
            {
                {"b","a","b","b","a"},
                {"babbab","b","a","ba"},
                {"5"}
            },
            {
                {"abc","def","ghi"}, 
                {"abc","defi","gh"},
                {"3"}
            },
            {
                {"aaaa","aaaa","aaaa","aaaa"}, 
                {"a"},
                {"16"}
            }
        };
        
        BoggleGame sv = new BoggleGame();
        
        char[][] matrix;
        for(String[][] input : inputs){
            matrix= Misc.convert(input[0]);
                    
            Misc.printMetrix(matrix);
            System.out.println("words: " + Arrays.toString(input[1]));
            
            Assert.assertEquals(Integer.parseInt(input[2][0]), sv.boggleGame(matrix, input[1]) );
        }
        
    }
    
}
