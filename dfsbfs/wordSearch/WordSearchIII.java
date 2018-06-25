package fgafa.dfsbfs.wordSearch;

import java.util.ArrayList;
import java.util.List;


public class WordSearchIII {

    /**
     * Given a bag of characters and a dictionary, find longest string that can
     * be constructed.
     */
    
    public List<String> findWords_trie_n(char[] board, String[] words) {
        //make sure there is no duplicted char in board 
        
        List<String> result = new ArrayList<String>();
 
        Trie trie = new Trie();
        for(String word: words){
            trie.insert(word);
        }
 
        for(int i=0; i<board.length; i++){
            dfs(board, new StringBuilder(), i, trie.root, result);
        }
        
        return result;
    }
 
    private void dfs(char[] board, StringBuilder str, int i, TrieNode trie, List<String> result){

        if(!trie.hasChild(board[i])){
            return;
        }
 
        str.append(board[i]);
        trie = trie.getChild(board[i]);

        if(trie.isLeaf){
            if (result.isEmpty() || result.get(0).length() <= str.length()) {

                if (result.get(0).length() < str.length()) {
                    result.clear();
                }

                result.add(str.toString());
            }
            
        }
 
        for(int j=0; j<board.length; j++){
            dfs(board, str, j, trie, result);
        }

        str.deleteCharAt(str.length() - 1);
    }

    
  //trie Node
    class TrieNode{
        public TrieNode[] children = new TrieNode[26];

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
    
    
}
