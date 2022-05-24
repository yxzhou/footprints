package dfsbfs.wordSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/132
 * 
 * Given a 2D board and a list of words from the dictionary, find all words in the board.
 *
 * Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those
 * horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.
 *
 * Example 1:
 * Input：["doaf","agai","dcan"]，["dog","dad","dgdg","can","again"]
 * Output：["again","can","dad","dog"]
 * Explanation：
 *   d o a f
 *   a g a i
 *   d c a n
 * search in Matrix，so return ["again","can","dad","dog"].
 * 
 * Example 2:
 * Input：["a"]，["b"]
 * Output：[]
 *
 * Note: You may assume that all inputs are consist of lowercase letters a-z.
 * 
 * Solutions:
 *   1)  dfs
 *   2)  trie + dfs
 * 
 */

public class WordSearchII {

    class Node{
        Node[] nexts = new Node[26];

        String word = null;
    }

    /**
     * @param board: A list of lists of character
     * @param words: A list of string
     * @return: A list of string
     */
    public List<String> wordSearchII(char[][] board, List<String> words) {
        if(board == null || board.length == 0 || words == null){
            return Collections.EMPTY_LIST;
        }

        Node root = new Node();

        //init trie 
        for(String word : words){
            add(root, word);
        }

        Set<String> result = new HashSet<>();

        //search
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];

        for(int r = 0; r < m; r++){
            for(int c = 0; c < n; c++){
                dfs(board, r, c, root, visited, result);
            }
        }

        return new ArrayList<>(result);
    }

    int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private void dfs(char[][] board, int r, int c, Node curr, boolean[][] visited, Set<String> result){

        if(r < 0 || r >= board.length || c < 0 || c >= board[0].length || visited[r][c]){
            return;
        }

        curr = curr.nexts[board[r][c] - 'a'];
        if( curr == null ){
            return;
        }
        
        if(curr.word != null){
            result.add(curr.word);
        }

        visited[r][c] = true;

        for(int[] diff : diffs){
            dfs(board, r + diff[0], c + diff[1], curr, visited, result);
        }

        visited[r][c] = false;
    }

    private void add(Node root, String word){
        Node curr = root;
        int p;
        for(int i = 0, n = word.length(); i < n; i++){
            p = word.charAt(i) - 'a';

            if(curr.nexts[p] == null){
                curr.nexts[p] = new Node();
            }

            curr = curr.nexts[p];
        }

        curr.word = word;
    }
    
    public static void main(String[] args){
        String[][][] inputs = {
            {
                {"a"},
                {"b"},
                {}
            },
            {
                {
                    "doaf",
                    "agai",
                    "dcan"
                },
                {"dog","dad","dgdg","can","again"},
                {"again","can","dad","dog"}
            }

        };
        
        WordSearchII sv = new WordSearchII();
        
        char[][] matrix;
        List<String> result;
        for(String[][] input : inputs){
            matrix= Misc.convert(input[0]);
                    
            Misc.printMetrix(matrix);
            System.out.println("words: " + Arrays.toString(input[1]));
            
            result = sv.wordSearchII(matrix, Arrays.asList(input[1]));
            Collections.sort(result);
            Assert.assertArrayEquals(input[2], result.toArray(new String[0]) );
            
        }
        
    }

}
