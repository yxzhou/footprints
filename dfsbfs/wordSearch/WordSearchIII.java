package dfsbfs.wordSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1848/
 * 
 * Given a matrix of lower alphabets and a dictionary. Find maximum number of words in the dictionary that can be found
 * in the matrix in the meantime. A word can start from any position in the matrix and go left/right/up/down to the
 * adjacent position. One character only be used once in the matrix. No same word in dictionary
 *
 * Example 1:
 * Input: ["doaf","agai","dcan"]，["dog","dad","dgdg","can","again"]
 * Output：2
 * Explanation：
 *   d o a f
 *   a g a i
 *   d c a n
 * Explanation： search in Matrix, you can find `dog` and `can` in the meantime.
 * 
 * Example 2:
 * Input： ["a"]，["b"]
 * Output： 0
 *
 * 
 * Thoughts: 
 * Example 3: 
 * Input: ["ab", "cd"],  ["a", "b", "cd", "ab", "ac", "abd"]
 * 
 * In fact it's to get the combination of words that can be filled in the board. 
 * The combinations can be:
 *  ["a", "b", "cd"], 
 *  ["ab", "cd"]
 *  ["b", "cd"]
 * 
 * The combination with maximum number of words is ["a", "b", "cd"].
 * 
 * the state:  boolean[][] matirx = new boolean[][]; It can be stored as a BitSet
 * init: S = {an empty matix} = {new BitSet()}
 * 
 * m1) dfs 
 * Define f(state, r, c)   
 * f(s0, 0, 0) = max( f(s0, 0, 1), f(s1, 0, 1) when found word1 start from (0, 0), f(s2, 0, 1) when found word2 start from (0, 0), --- )
 *    
 * 
 * m2) It's a backpack problem.
 * 
 * Because no same word in dictionary, and it's to find the combination with maximum number of words. 
 * A word only need be filled in matrix once. 
 * 
 * for each word, 
 *   for each existing state
 *      search all matrix, find out the fill-able sections  
 *          if s0 and section are not used any same character, add newS = s0 + section in S.
 * 
 *   
 * Time complexity analysis:
 *   Define n as the numbers of characters in board, m as the number of words, k as the average size of words.
 * 
 *                          Time    -       Space
 *   Build TrieTree,        O(m * k)        O(26^k) 
 *   Find word's positions  O(n * k)        O(n * n/64)
 *   Find the combination   O(m * n)        O(n)
 * 
 *   So the time complexity is O(m*n + m*k + n*k), Space complexity is O(26^k + n*n)
 * 
 */


public class WordSearchIII {
    /**
     * m2, DP
     */

    public int wordSearchIII(char[][] board, List<String> words) {
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
        dp.put(new BitSet(), 0);

        BitSet next;
        int x;
        Map<BitSet, Integer> tmp;
        for(String word : wordPositions.keySet()){
            
            tmp = new HashMap<>();
            for(BitSet curr : dp.keySet()){
                for(BitSet toAdd : wordPositions.get(word)){
                    if(!curr.intersects(toAdd) ){
                        next = (BitSet)curr.clone();
                        next.or(toAdd);
                        
                        if(( x = dp.get(curr) + 1) > dp.getOrDefault(next, 0) && x > tmp.getOrDefault(next, 0) ){
                            tmp.put(next, x);
                            
                            max = Math.max(max, x);
                        }
                    }
                }
            }
            
            for(BitSet curr : tmp.keySet()){
                dp.put(curr, tmp.get(curr));
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
    private void dfs(char[][] board, int r, int c, TrieNode node, BitSet position, Map<String, List<BitSet> > wordPositions){
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
    
//    private void dfs(char[][] board, int r, int c, TrieNode node, BitSet position, Map<String, List<BitSet> > wordPositions){
//        int n = board.length;
//        int m = board[0].length;
//        int index = r * m + c;
//                
//        if(position.get(index)){
//            return;
//        }
//
//        node = node.children[board[r][c] - 'a'];
//        if( node == null ){
//            return;
//        }
//        
//        position.set(index);
//        
//        if(node.word != null){
//            wordPositions.computeIfAbsent(node.word, k -> new ArrayList<>()).add((BitSet)position.clone());
//        }
//
//        int nr;
//        int nc;
//        for(int[] diff : diffs){
//            nr = r + diff[0];
//            nc = c + diff[1];
//            
//            if(nr < 0 || nr >= n || nc < 0 || nc >= m){
//                continue;
//            }
//            
//            dfs(board, nr, nc, node, position, wordPositions);
//        }
//
//        position.set(index, false);
//    }
    
//    private BitSet deepClone(BitSet origin){
//        BitSet result = new BitSet(); 
//                
//        for (int i = origin.nextSetBit(0); i >= 0; i = origin.nextSetBit(i+1)) {
//            
//            result.set(i);
//            
//            if (i == Integer.MAX_VALUE) {
//                break; // or (i+1) would overflow
//            }
//        }
//        
//        return result;
//    }
    
    public static void main(String[] args){
        
        /** Test BitSet clone  */
        BitSet origin = new BitSet();
        origin.set(3);
        
        BitSet cloned = (BitSet)origin.clone();
        
        Assert.assertTrue(cloned.equals(origin));
        cloned.set(1);
        Assert.assertFalse(cloned.equals(origin));
        
        /** Test BitSet as key in HashMap  */
        BitSet bitset1 = new BitSet();
        bitset1.set(3);
        
        BitSet bitset2 = new BitSet();
        bitset2.set(3);
        
        BitSet bitset3 = new BitSet();
        bitset3.set(1);
        
        Map<BitSet, Integer> map = new HashMap<>();
        
        map.put(bitset1, 1);
        map.put(bitset2, 2);
        map.put(bitset3, 3);
        
        Assert.assertEquals(2, map.size());
        
        map.clear();
        
        map.put(bitset1, map.getOrDefault(bitset1, 0) + 1);
        map.put(bitset2, map.getOrDefault(bitset2, 0) + 1);
        
        System.out.println(" --- " + map.get(bitset2));
        
//        if(true){
//            return;
//        }
        /**   */
        
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
                {"abc","def","ghi"}, 
                {"abc","defi","gh"},
                {"3"}
            },
            {
                {"aaaa","aaaa","aaaa","aaaa"}, 
                {"a"},
                {"1"}
            }
        };
        
        WordSearchIII sv = new WordSearchIII();
        
        char[][] matrix;
        for(String[][] input : inputs){
            matrix= Misc.convert(input[0]);
                    
            Misc.printMetrix(matrix);
            System.out.println("words: " + Arrays.toString(input[1]));
            
            Assert.assertEquals(Integer.parseInt(input[2][0]), sv.wordSearchIII(matrix, Arrays.asList(input[1])) );
        }
        
    }
    
    
}
