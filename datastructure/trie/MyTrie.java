package datastructure.trie;

/**
 * 
 * Implement a Trie with insert, search, and startsWith methods.
 * 
 * Note
 *   You may assume that all inputs are consist of lowercase letters a-z.
 * 
 * Example
 * Input:
 *   insert("lintcode")
 *   search("code")
 *   startsWith("lint")
 *   startsWith("linterror")
 *   insert("linterror")
 *   search("lintcode“)
 *   startsWith("linterror")
 * Output:
 *   false
 *   true
 *   false
 *   true
 *   true
 */

public class MyTrie {
    /**
     * Predefined class
     */
    public class TrieNode {
        TrieNode[] children = new TrieNode[26]; //default all are NULL
        boolean isWord = false;

        public TrieNode() { }
    }
        
    public TrieNode getRoot() {
        return this.root;
    } 
    public void setRoot(TrieNode root) {
        this.root = root;
    }
    
    private TrieNode root;

    public MyTrie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        if(null == word){
            return;
        }
        
        TrieNode curr = root;
        int index;
        for(char c : word.toCharArray()){
            index = c - 'a';
            if(null == curr.children[index]){
                curr.children[index] = new TrieNode();
            }
            curr = curr.children[index];
        }
        curr.isWord = true;
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {        
        TrieNode node = locate(word);
        
        return null != node && node.isWord;
    }

    // Returns if there is any word in the trie that starts with the given prefix.
    public boolean startsWith(String prefix) {        
        TrieNode node = locate(prefix);
        
        return null != node;
    }
    
    private TrieNode locate(String word) {
        if(null == word){
            return null;
        }
                
        TrieNode curr = root;
        int index;
        for(char c : word.toCharArray()){
            index = c - 'a';
            if(null == curr.children[index]){
                return null;
            }
            curr = curr.children[index];
        }
        
        return curr;
    }

}
