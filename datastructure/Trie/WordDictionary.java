package fgafa.datastructure.Trie;

/**
 * 
 * Design a data structure that supports the following two operations: addWord(word) and search(word)

	search(word) can search a literal word or a regular expression string containing only letters a-z or ..
	
	A . means it can represent any one letter.
	
	Example
	addWord("bad")
	addWord("dad")
	addWord("mad")
	search("pad")  // return false
	search("bad")  // return true
	search(".ad")  // return true
	search("b..")  // return true
	Note
	You may assume that all words are consist of lowercase letters a-z.
 *
 */

//Your WordDictionary object will be instantiated and called as such:
//WordDictionary wordDictionary = new WordDictionary();
//wordDictionary.addWord("word");
//wordDictionary.search("pattern");

public class WordDictionary {
	TrieNode root = new TrieNode();
	
    // Adds a word into the data structure.
    public void addWord(String word) {
        if(null == word){
        	return;
        }
        
        TrieNode node = root;
        int i;
        for(char c : word.toCharArray()){
        	i = c - 'a';
        	if(null == node.children[i]){
        		node.children[i] = new TrieNode();
        	}
        	
        	node = node.children[i];
        }
        
        node.isWord = true;
    }

    // Returns if the word is in the data structure. A word could
    // contain the dot character '.' to represent any one letter.
    public boolean search(String word) {
        if(null == word){
        	return false;
        }
        
        return search(word, 0, root);
    }
    
    private boolean search(String word, int index, TrieNode node){
    	
    	if(index >= word.length()){
    		return node.isWord;
    	}
    	
    	char c = word.charAt(index);
    	if('.' == c){
    		for(TrieNode child : node.children){
            	if(null != child  && search(word, index+1, child)){
            		return true;
            	}
    		}
    	}else if(null != node.children[c - 'a']){
        	return search(word, index+1, node.children[c - 'a']);
    	}

    	return false;
    }
    
    class TrieNode{
    	TrieNode[] children = new TrieNode[26]; //default all are null
    	boolean isWord = false;
    }
}


