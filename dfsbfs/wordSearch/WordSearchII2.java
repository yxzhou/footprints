package dfsbfs.wordSearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordSearchII2 {

    /**
     * @param board: A list of lists of character
     * @param words: A list of string
     * @return: A list of string
     */
    public List<String> wordSearchII(char[][] board, List<String> words) {
        if(words == null || null == board || 0 == board.length || 0 == board[0].length ){
            return new ArrayList<>();
        }

        Trie trie = new Trie();
        for(String word : words){
            trie.add(word);
        }

        Set<String> set = new HashSet<>();
        boolean[][] visited = new boolean[board.length][board[0].length];

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                dfs(board, i, j, trie.root, visited, set);
            }
        }

        return new ArrayList(set);
    }

    int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private void dfs(char[][] board, int i, int j, Node node, boolean[][] visited, Set<String> set){
        if(i < 0 || i >= board.length || j < 0 || j >= board[0].length || visited[i][j] ){
            return;
        }

        char c = board[i][j];
        int index = c - 'a';
        if(node.next[index] == null){
            return;
        }
        node = node.next[index];

        if(node.word != null){
            set.add(node.word);
        }

        visited[i][j] = true;

        for(int d = 0; d < diffs.length; d++){
            dfs(board, i + diffs[d][0], j + diffs[d][1], node, visited, set);
        }

        visited[i][j] = false;
    }


    class Node{
        Node[] next = new Node[26];

        String word = null;
    }

    class Trie{
        Node root = new Node();

        void add(String word){
            Node curr = root;

            for(char c : word.toCharArray()){
                int index = c - 'a';

                if(curr.next[index] == null){
                    curr.next[index] = new Node();
                }

                curr = curr.next[index];
            }

            curr.word = word;
        }
    }

}
