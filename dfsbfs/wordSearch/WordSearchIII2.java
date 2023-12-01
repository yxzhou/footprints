package dfsbfs.wordSearch;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1848/
 * 
 * 
 */


public class WordSearchIII2 {
    /**
     * m1, DFS
     */
    int max = 0;
    TrieNode root;
    public int wordSearchIII(char[][] board, List<String> words) {
        if(board == null || board.length == 0 || words == null){
            return 0;
        }
        
        max = 0; //init
        root = new TrieNode();
        for(String word : words){
            add(root, word);
        }

        dfs(board, new boolean[board.length][board[0].length], 0, 0, root, 0, 0, new HashSet<>());
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
    
    private void dfs(char[][] board, boolean[][] visited, int r, int c, TrieNode node, int i, int j, Set<String> words){                
        if(r < 0 || r >= board.length || c < 0 || c >= board[0].length){
            return;
        }
        
        if(node == root){
            boolean newRow = (j + 1 == board[0].length);
            //System.out.println(String.format(" -- newRow = %b, i = %d, j = %d", newRow, i, j ));
            
            dfs(board, visited, i + (newRow? 1 : 0), newRow? 0 : j + 1, root, i + (newRow? 1 : 0), newRow? 0 : j + 1, deepClone(words));
        }

        node = node.children[board[r][c] - 'a'];
        if( node == null || visited[r][c] ){
            return;
        }
        
        visited[r][c] = true;

        if(node.word != null){
            //System.out.println(String.format(" -- word = %s, count = %d, r = %d, c = %d, i = %d, j = %d", node.word, count, r, c, i, j ));
            HashSet<String> newSet = deepClone(words);
            newSet.add(node.word);
            max = Math.max(max, newSet.size());

            boolean newRow = (j + 1 == board[0].length);
            dfs(board, visited, i + (newRow? 1 : 0), newRow? 0 : j + 1, root, i + (newRow? 1 : 0), newRow? 0 : j + 1, newSet);
        }

        for(int[] diff : diffs){
            dfs(board, visited, r + diff[0], c + diff[1], node, i, j, words);
        }

        visited[r][c] = false;
    }
    
    private HashSet<String> deepClone(Set<String> origin){
        HashSet<String> result = new HashSet<>();
        
        result.addAll(origin);
        return result;
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
        
        WordSearchIII2 sv = new WordSearchIII2();
        
        char[][] matrix;
        for(String[][] input : inputs){
            matrix= Misc.convert(input[0]);
                    
            Misc.printMetrix(matrix);
            System.out.println("words: " + Arrays.toString(input[1]));
            
            Assert.assertEquals(Integer.parseInt(input[2][0]), sv.wordSearchIII(matrix, Arrays.asList(input[1])) );
        }
        
    }
    
    
}
