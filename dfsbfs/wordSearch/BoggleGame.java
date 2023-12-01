/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dfsbfs.wordSearch;

import java.util.Arrays;
import org.junit.Assert;
import util.Misc;

/**
 * 
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
 * 1 compare to WordSearchIII, here it can reuse the words.
 *   
 * 2 trie + dfs. 
 *   2.1 To every cell in the 2D matrix, there are 2 options. one is to find a word start with this cell. the other is not.
 *   2.2 To every cell, there are 4 directory options to dfs. 
 *   2.3 When found a word, it need "find" from the cell next to the start, instead of the end. 
 *   2.4 When found a word, it needn't continue to find the longer word. It would be better to leave more space for more words.
 *   
 * 3 
 *    
 * 
 */
public class BoggleGame {
    
    final int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1} };
    TrieNode root;
    int max = 0;
    
    public int boggleGame(char[][] board, String[] words) {
        if(board == null || board.length == 0 || words == null){
            return 0;
        }
        
        max = 0; //make BoggleGame as stateless
        root = new TrieNode();
        for(String w : words){
            add(root, w);
        }
        
        boolean[][] visited = new boolean[board.length][board[0].length]; //default all are false
        find(board, 0, 0, root, visited, 0, 0, 0);
        
        return max;
    }
    
    /**
     * dfs
     *
     */
    private void find(char[][] board, int r, int c, TrieNode node, boolean[][] visited, int count, int startR, int startC){
        if(r < 0 || r >= board.length || c < 0 || c >= board[0].length){
            return;
        }
        
        //if this is not start to find a word yet, it can pass this cell, 
        //if it alreday found a word, restart to find from the cell next to the start
        if(node == root){
            boolean needNewRow = (startC == board[0].length - 1);
            int newStartR = startR + (needNewRow ? 1 : 0);
            int newStartC = needNewRow ? 0 : startC + 1;
            find(board, newStartR, newStartC, root, visited, count, newStartR, newStartC);
        }
        
        node = node.sons[board[r][c] - 'a'];
        if(node == null || visited[r][c]){
            return; //it will restart the finding from the cell next to the start
        }
        
        visited[r][c] = true;
        
        if(node.isWord){
            //When found a word, it need "find" from the cell next to the start, instead of the end. 
            //And it needn't continue to find the longer word. 
            count++;
            max = Math.max(max, count);
            //System.out.println(String.format(" -- word = %s, count = %d, max = %d, r = %d, c = %d, startR = %d, startC = %d", node.word, count, max, r, c, startR, startC ));            
            
            boolean needNewRow = (startC == board[0].length - 1);
            int newStartR = startR + (needNewRow ? 1 : 0);
            int newStartC = needNewRow ? 0 : startC + 1;
            find(board, newStartR, newStartC, root, visited, count, newStartR, newStartC);
        }else{
            //continue try 4 directions
            for(int[] diff : diffs){
                find(board, r + diff[0], c + diff[1], node, visited, count, startR, startC);
            }
        }
        
        visited[r][c] = false;
        
    }
    
    private void add(TrieNode root, String word){
        TrieNode node = root;
        
        int c;
        for(int i = 0; i < word.length(); i++){
            c = word.charAt(i) - 'a';
            
            if(node.sons[c] == null){
                node.sons[c] = new TrieNode();
            }
            
            node = node.sons[c];
        }
        
        node.isWord = true;
        //node.word = word;
    }
    
    class TrieNode{
        TrieNode[] sons = new TrieNode[26];
        boolean isWord = false;
        //String word = null;
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
                {
                    "abce",
                    "sfes",
                    "adee"
                },
                {"abceseeefs","abceseedasfe"},
                {"1"}
            },
            {
                {
                    "ab",
                    "cd"
                },
                {"a", "b", "cd", "ab", "ac", "abd"},
                {"3"}
            },
            {
                {"b","a","b","b","a"},
                {"babbab","b","a","ba"},
                {"5"}
            },
            {
                {
                    "abc",
                    "def",
                    "ghi"
                }, 
                {"abc","defi","gh"},
                {"3"}
            },
            {
                {
                    "aaaa",
                    "aaaa",
                    "aaaa",
                    "aaaa"
                }, 
                {"a", "aa"},
                {"16"}
            },
            {
                {
                    "aaaa",
                    "aaaa",
                    "aaaa",
                    "aaaa"
                }, 
                {"aa", "aaa"},
                {"8"}
            }
                
        };
        
        BoggleGame sv = new BoggleGame(); //
        BoggleGame3 sv3 = new BoggleGame3(); // BoggleGame3 is stateless
        
        char[][] matrix;
        for(String[][] input : inputs){
            matrix= Misc.convert(input[0]);
                    
            Misc.printMetrix(matrix); 
            System.out.println("words: " + Arrays.toString(input[1]));
            
            //BoggleGame sv = new BoggleGame();
            Assert.assertEquals("sv ", Integer.parseInt(input[2][0]), sv.boggleGame(matrix, input[1]) );
            
            Assert.assertEquals("sv3 ", Integer.parseInt(input[2][0]), sv3.boggleGame(matrix, input[1]) );
        }
        
    }
    
}
