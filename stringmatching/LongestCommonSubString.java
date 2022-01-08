package stringmatching;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Given two strings, find the longest common substring.
 * Return the length of it.
 * 
 * Example
 * Given A = "ABCD", B = "CBCE", return 2.
 * 
 *  Note
 *  The characters in substring should occur continuously in original string. This is different with subsequence.
 * 
 *  Challenge O(n * m) time and memory.
 */

public class LongestCommonSubString {

    /**
     * @param A, B: Two string.
     * @return: the length of the longest common substring.
     */
    public int longestCommonSubstring(String A, String B) {
        if(null == A || 0 == A.length() || null == B || 0 == B.length()){
            return 0;
        }
        
        Trie trieTree = new Trie();
        char[] word = A.toCharArray();
        for(int s = 0, e = A.length(); s < e; s++){
            trieTree.insert(word, s, e);
        }
        
    	int max = 0;
    	word = B.toCharArray();
    	for(int s = 0, e = B.length(); s < e; s++){
    		max = Math.max(max, trieTree.getPrefix(word, s, e));
    	}
    	
    	return max;
    }
    
    class TrieNode{
        Map<Character, TrieNode> children = new HashMap<Character,TrieNode>(); 
        boolean isLeaf = false;
        
        public boolean hasChild(char c){
            return children.containsKey(c);
        }
        
        public TrieNode getChild(char c){
            return children.get(c);
        }
        
        public void addChild(char c){
            children.put(c, new TrieNode());
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
            
            node.isLeaf = true;
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
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
