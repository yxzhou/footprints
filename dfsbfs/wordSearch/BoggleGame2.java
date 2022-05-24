/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dfsbfs.wordSearch;

import java.util.Arrays;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/635
 * 
 * 
 */
public class BoggleGame2 {
    /*
     * compare to WordSearchIII2, here it can reuse the words.
     *
     * @param board: a list of lists of character
     * @param words: a list of string
     * @return: an integer
     *
     */
    int max = 0;
    TrieNode root;
    public int boggleGame(char[][] board, String[] words) {
        if(board == null || board.length == 0 || words == null){
            return 0;
        }
        
        max = 0; //init
        root = new TrieNode();
        for(String word : words){
            add(root, word);
        }

        dfs(board, new boolean[board.length][board[0].length], 0, 0, root, 0, 0, 0);
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
    
    private void dfs(char[][] board, boolean[][] visited, int r, int c, TrieNode node, int i, int j, int count){                
        if(r < 0 || r >= board.length || c < 0 || c >= board[0].length){
            return;
        }
        
        if(node == root){
            boolean newRow = (j + 1 == board[0].length);
            //System.out.println(String.format(" -- newRow = %b, i = %d, j = %d", newRow, i, j ));
            
            dfs(board, visited, i + (newRow? 1 : 0), newRow? 0 : j + 1, root, i + (newRow? 1 : 0), newRow? 0 : j + 1, count);
        }

        node = node.children[board[r][c] - 'a'];
        if( node == null || visited[r][c] ){
            return;
        }
        
        visited[r][c] = true;

        if(node.word != null){
            //System.out.println(String.format(" -- word = %s, count = %d, r = %d, c = %d, i = %d, j = %d", node.word, count, r, c, i, j ));
            max = Math.max(max, count + 1);

            boolean newRow = (j + 1 == board[0].length);
            dfs(board, visited, i + (newRow? 1 : 0), newRow? 0 : j + 1, root, i + (newRow? 1 : 0), newRow? 0 : j + 1, count + 1);
        }

        for(int[] diff : diffs){
            dfs(board, visited, r + diff[0], c + diff[1], node, i, j, count);
        }

        visited[r][c] = false;
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
        
        BoggleGame2 sv = new BoggleGame2();
        
        char[][] matrix;
        for(String[][] input : inputs){
            matrix= Misc.convert(input[0]);
                    
            Misc.printMetrix(matrix);
            System.out.println("words: " + Arrays.toString(input[1]));
            
            Assert.assertEquals(Integer.parseInt(input[2][0]), sv.boggleGame(matrix, input[1]) );
        }
        
    }
    
}
