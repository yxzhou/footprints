package fgafa.dfsbfs.wordSearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * Given a 2D board and a list of words from the dictionary, find all words
 * in the board.
 * 
 * Each word must be constructed from letters of sequentially adjacent cell,
 * where "adjacent" cells are those horizontally or vertically neighboring.
 * The same letter cell may not be used more than once in a word.
 * 
 * For example, Given words = ["oath","pea","eat","rain"] and board =
 * [ ['o','a','a','n'], 
 *   ['e','t','a','e'], 
 *   ['i','h','k','r'],
 *   ['i','f','l','v'] ] 
 *   
 * Return ["eat","oath"]. 
 * 
 * Note: You may assume that all inputs are consist of lowercase letters a-z.
 * 
 * Solutions:
 *   1)  dfs
 *   2)  trie + dfs
 */

public class WordSearchII {

    /**/
    public List<String> findWords(char[][] board, String[] words) {
    	List<String> result = new ArrayList<>();
    	
        // check
        // if(board == null )

        int rows = board.length;
        int cols = board[0].length;
        boolean[][] isUsed = new boolean[rows][cols]; // default it's false;

        out: for(String word : words){
	        for (int row = 0; row < rows; row++) {
	          for (int col = 0; col < cols; col++) {
	            // isUsed[row][col] = false;
	
	            if (exist(board, row, col, word, 0, isUsed)){
	              result.add(word);
	              continue out;
	            }  
	          }
	        }
        }
        
        return result;
    }
	
    /* dfs */
    private boolean exist(char[][] board, int row, int col, String word,
        int start, boolean[][] isUsed) {
      //
      isUsed[row][col] = true;

      if (start == word.length() - 1 && board[row][col] == word.charAt(start))
        return true;

      if (board[row][col] == word.charAt(start)) {

        if (row > 0 && !isUsed[row - 1][col]
            && exist(board, row - 1, col, word, start + 1, isUsed))
          return true;
        if (row < board.length - 1 && !isUsed[row + 1][col]
            && exist(board, row + 1, col, word, start + 1, isUsed))
          return true;
        if (col > 0 && !isUsed[row][col - 1]
            && exist(board, row, col - 1, word, start + 1, isUsed))
          return true;
        if (col < board[0].length - 1 && !isUsed[row][col + 1]
            && exist(board, row, col + 1, word, start + 1, isUsed))
          return true;

      }

      isUsed[row][col] = false;   //this is very important 

      return false;
    }
    
    public List<String> findWords_2(char[][] board, String[] words) {
        ArrayList<String> result = new ArrayList<String>();
     
        int m = board.length;
        int n = board[0].length;
     
        for (String word : words) {
            boolean flag = false;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    char[][] newBoard = new char[m][n];
                    for (int x = 0; x < m; x++){
                        for (int y = 0; y < n; y++){
                            newBoard[x][y] = board[x][y];
                        }
                    }
     
                    if (dfs(newBoard, word, i, j, 0)) {
                        flag = true;
                    }
                }
            }
            if (flag) {
                result.add(word);
            }
        }
     
        return result;
    }
     
    private boolean dfs(char[][] board, String word, int i, int j, int k) {
        int m = board.length;
        int n = board[0].length;
     
        if (i < 0 || j < 0 || i >= m || j >= n || k > word.length() - 1) {
            return false;
        }
     
        if (board[i][j] == word.charAt(k)) {
            char temp = board[i][j];
            board[i][j] = '#';
     
            if (k == word.length() - 1) {
                return true;
            } else if (dfs(board, word, i - 1, j, k + 1)
                    || dfs(board, word, i + 1, j, k + 1)
                    || dfs(board, word, i, j - 1, k + 1)
                    || dfs(board, word, i, j + 1, k + 1)) {
                board[i][j] = temp;
                return true;
            }
     
        } else {
            return false;
        }
     
        return false;
    }
    
    
    public List<String> findWords_trie_n(char[][] board, String[] words) {
        Set<String> result = new HashSet<String>();
 
        Trie trie = new Trie();
        for(String word: words){
            trie.insert(word);
        }
 
        int m=board.length;
        int n=board[0].length;
 
        boolean[][] visited = new boolean[m][n];
 
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
               dfs(board, i, j, new StringBuilder(), visited, trie.root, result);
            }
        }
 
        return new ArrayList<String>(result);
    }
 
    private void dfs(char[][] board, int i, int j, StringBuilder str, boolean[][] visited, TrieNode trie, Set<String> result){
        int m=board.length;
        int n=board[0].length;
 
        if(i<0 || j<0||i>=m||j>=n){
            return;
        }
 
        if(visited[i][j] || !trie.hasChild(board[i][j])){
            return;
        }
 
        str.append(board[i][j]);
        trie = trie.getChild(board[i][j]);

        if(trie.isLeaf){
            result.add(str.toString());
        }
 
        visited[i][j]=true;
        
        dfs(board, i-1, j, str, visited, trie, result);
        dfs(board, i+1, j, str, visited, trie, result);
        dfs(board, i, j-1, str, visited, trie, result);
        dfs(board, i, j+1, str, visited, trie, result);

        visited[i][j]=false;
        str.deleteCharAt(str.length() - 1);
 
    }

    
  //trie Node
    class TrieNode{
        public TrieNode[] children = new TrieNode[26];
        //public String item = "";
        public boolean isLeaf = false;

        public boolean hasChild(char c){
            return hasChild(c - 'a');
        }
        public boolean hasChild(int c){
            return getChild(c) != null;
        }
        public TrieNode getChild(char c){
            return children[c - 'a'];
        }
        public TrieNode getChild(int c){
            return children[c];
        }
    }
     
    //trie
    class Trie{
        public TrieNode root = new TrieNode();
     
        public void insert(String word){
            TrieNode node = root;
            int index;
            for(char c : word.toCharArray()){
                index = c - 'a';
                if(node.children[index] == null){
                    node.children[index]= new TrieNode();
                }
                node = node.children[index];
            }
            //node.item = word;
            node.isLeaf = true;
        }
     
        public boolean search(String word){
            TrieNode node = root;
            int index;
            for(char c: word.toCharArray()){
                index = c - 'a';
                if(node.children[index]==null)
                    return false;
                node = node.children[index];
            }
//            if(node.item.equals(word)){
//                return true;
//            }else{
//                return false;
//            }
            return node.isLeaf;
        }
     
        public boolean startsWith(String prefix){
            TrieNode node = root;
            int index;
            for(char c: prefix.toCharArray()){
                index = c - 'a';
                if(node.children[index]==null)
                    return false;
                node = node.children[index];
            }
            return true;
        }
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
