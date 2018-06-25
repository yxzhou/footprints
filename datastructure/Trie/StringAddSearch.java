package fgafa.datastructure.trie;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


/**
 * 
 * Design a data structure that supports the following two operations:
 * 
 * void addWord(word) 
 * bool search(word) 
 * 
 * search(word) can search a literal word or a regular expression string containing only letters a-z or .. 
 * A . means it can represent any one letter.
 * 
 * For example:
 * addWord("bad") 
 * addWord("dad") 
 * addWord("mad") 
 * search("pad") -> false
 * search("bad") -> true 
 * search(".ad") -> true 
 * search("b..") -> true 
 * 
 * Note: You may assume that all words are consist of lowercase letters a-z.
 * 
 */
public class StringAddSearch {
	private final static char DOT = '.';
	//private final char END_CHARACTER = '$';
	
	Map<Character, Map> roots = new HashMap<>(); 
	
	public StringAddSearch(){
	}
		
	public void addWord(String word){
		//check
		if(null == word){
			return;
		}
		
		Map<Character, Map> node = roots;
		for(int i=0; i<word.length(); i++ ){
			char c = word.charAt(i);
			if(!node.containsKey(c)){
				node.put(c, new HashMap<Character, Map>());
			}
			
			node = node.get(c);
		}
	}
	
	/* bfs */
	public boolean search(String word){
		Queue<Map<Character, Map>> queue = new LinkedList<>();
		queue.offer(roots);
		
		boolean isFound = false;
		for(int i=0 ; i<word.length(); i++){
			char c = word.charAt(i);
			isFound = false;
			
			for(int j=queue.size(); j>0; j--){
				Map<Character, Map> node = queue.poll();
				
				if(DOT == c || node.containsKey(c)){
					queue.add(node.get(c));
					isFound = true;
				}
			}
		}
			
		return isFound;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
