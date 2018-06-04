package fgafa.array;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Given two strings, find the longest common substring.

	Return the length of it.
	
	Example
	Given A = "ABCD", B = "CBCE", return 2.
	
	Note
	The characters in substring should occur continuously in original string. This is different with subsequence.
	
	Challenge
	O(n * m) time and memory.
 */

public class LongestCommonSubStringII {

    /**
     * @param strs: string array.
     * @return: the length of the longest common substring.
     */
    public int longestCommonSubstring(String[] strs) {
        //check
        if(null == strs || 0 == strs.length){
            return 0;
        }
        
        int size = strs.length;
        if(1 == size){
        	return strs[0].length();
        }
        
        Trie main = new Trie().buildTrie(strs[0]);

        for(int i = strs.length - 1; i > 1; i--){
        	Trie other = new Trie().buildTrie(strs[i]);
        	main.merge(main.root, other.root);
        }
        
    	int max = 0;
    	char[] word = strs[1].toCharArray();
    	for(int s = 0, e = strs[1].length(); s < e; s++){
    		max = Math.max(max, main.getPrefix(word, s, e));
    	}
    	
    	return max;
    }
    

    
    class TrieNode{
        Map<Character, TrieNode> children = new HashMap<Character,TrieNode>(); 
        int count = 0;
        
        public boolean hasChild(char c){
            return children.containsKey(c);
        }
        
        public TrieNode getChild(char c){
            return children.get(c);
        }
        
        public void addChild(char c){
            children.put(c, new TrieNode());
        }
        
        public void deleteChild(char c){
        	children.remove(c);
        }
    }
    
    class Trie{
        TrieNode root = new TrieNode();
        
        public void insert(char[] word, int s, int e){
            if(null == word){
                return;
            }
            
            TrieNode node = root;
            char c;
            for(int i = s; i < e; i++){
            	c = word[i];
                if(!node.hasChild(c)){
                    node.addChild(c);
                }
                
                node = node.getChild(c);
            }
        }
        
        public int getPrefix(char[] word, int s, int e){
            if(null == word){
                return 0;
            }
            
            int i = s;
            char c;
            TrieNode node = root;
            for( ; i < e; i++){
                c = word[i];
                if(!node.hasChild(c)){
                	break;
                }
                
                node = node.getChild(c);
            }
            
            return i - s;
        }
        
        /**
         * only keep the common part
         * @param other
         */
        public void merge(TrieNode main, TrieNode other){

        	for(Character c : other.children.keySet()){
        		if(!main.hasChild(c)){
        			main.deleteChild(c);
        		}else{
        			merge(main.getChild(c), other.getChild(c));
        		}
        	}

        }
        
        public Trie buildTrie(String str){
            Trie trieTree = new Trie();
            char[] word = str.toCharArray();
            for(int s = 0, e = str.length(); s < e; s++){
                trieTree.insert(word, s, e);
            }
            
            return trieTree;
        }
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
