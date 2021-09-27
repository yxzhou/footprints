package datastructure.trie;

/**
 * 
 * Implement a trie with insert, search, and startsWith methods.

	Example
	Note
	You may assume that all inputs are consist of lowercase letters a-z.
 *
 */

    class TrieIII{
        public TrieNode root = new TrieNode();
     
        public void insert(String word){
            TrieNode node = root;
            for(char c : word.toCharArray()){
                if(node.children[c-'a'] == null){
                    node.children[c-'a']= new TrieNode();
                }
                node = node.children[c-'a'];
            }
            node.item = word;
        }
     
        public boolean search(String word){
            TrieNode node = root;
            for(char c: word.toCharArray()){
                if(node.children[c-'a']==null)
                    return false;
                node = node.children[c-'a'];
            }
            if(node.item.equals(word)){
                return true;
            }else{
                return false;
            }
        }
     
        public boolean startsWith(String prefix){
            TrieNode node = root;
            for(char c: prefix.toCharArray()){
                if(node.children[c-'a']==null)
                    return false;
                node = node.children[c-'a'];
            }
            return true;
        }
        
        //trie Node
        class TrieNode{
            public TrieNode[] children = new TrieNode[26];
            public String item = "";
        }
    }
    

