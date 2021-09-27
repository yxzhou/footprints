package datastructure.trie;

/**
 * 
 * Implement a trie with insert, search, and startsWith methods.

	Example
	Note
	You may assume that all inputs are consist of lowercase letters a-z.
 *
 *  Your trie object will be instantiated and called as such:
     trie trie = new trie();
     trie.insert("somestring");
     trie.search("key");
 *
 */

public class Trie {
    private TrieNode root;

    public TrieNode getRoot() {
        return root;
    }

    public void setRoot(TrieNode root) {
        this.root = root;
    }

    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        //check
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
        //check
        if(null == word){
            return false;
        }
        
        TrieNode node = locate(word);
        
        return null != node && node.isWord;
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        //check
        if(null == prefix){
            return false;
        }
        
        TrieNode node = locate(prefix);
        
        return null != node;
    }
    
    private TrieNode locate(String word) {
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

	public class TrieNode {
	    TrieNode[] children = new TrieNode[26]; //default all are NULL
	    boolean isWord = false;
	    
	    public TrieNode() { }
	}
}
