package fgafa.easy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fgafa.util.Misc;

public class Isomorphic {

	/**
	 * Given two strings s and t, determine if they are isomorphic.
	 * 
	 * Two strings are isomorphic if the characters in s can be replaced to get t.
	 * 
	 * All occurrences of a character must be replaced with another character
	 * while preserving the order of characters. No two characters may map to
	 * the same character but a character may map to itself.
	 * 
	 * For example, 
	 * Given "egg", "add", return true.
	 * Given "foo", "bar", return false.
	 * Given "paper", "title", return true.
	 * 
	 * Note: You may assume both s and t have the same length.
	 */
	
    public boolean isIsomorphic(String s, String t) {
        //check input
    	if(null == s || null == t || s.length() != t.length()){
    		return false;
    	}
    	
    	Map<Character, Character> mapping1 = new HashMap<>();
    	Map<Character, Character> mapping2 = new HashMap<>();
    	
    	for(int i = 0;i<s.length(); i++){
    		if( (mapping1.containsKey(s.charAt(i)) && !mapping1.get(s.charAt(i)).equals(t.charAt(i))) || 
    				(mapping2.containsKey(t.charAt(i)) && !mapping2.get(t.charAt(i)).equals(s.charAt(i)))){
    			return false;
    		}
    		
    		if(!mapping1.containsKey(s.charAt(i))){
    			mapping1.put(s.charAt(i), t.charAt(i));
    		}
    		
    		if(!mapping2.containsKey(t.charAt(i))){  
    			mapping2.put(t.charAt(i), s.charAt(i));
    		}
    	}
    	
    	return true;
    }
    
    /**
     * Give a String list,  group them by isomorphic
     * 
     */
    public List<List<String>> isIsomorphic(String[] ss) {
        List<List<String>> result = new ArrayList<>();
        
        if(ss == null || ss.length == 0){
            return result;
        }
        
        Map<String, List<String>> map = new HashMap<>();
        for(String s : ss){
            String code = encode(s);
            
            if(!map.containsKey(code)){
                map.put(code, new ArrayList<>());
            }
            
            map.get(code).add(s);
        }
        
        return new ArrayList<>(map.values()); 
    }
    
    private String encode(String s){
        if(s == null || s.length() == 0){
            return "";
        }
        
        int count = 0;
        Map<Character, Integer> map = new HashMap<>();
        StringBuilder result = new StringBuilder();
        
        for(char c : s.toCharArray()){
            if(!map.containsKey(c)){
                map.put(c, count);
                count++;
            }
            
            result.append(map.get(c));
            result.append(',');
        }
        
        return result.toString();
    }
	
	public static void main(String[] args) {
		Isomorphic sv = new Isomorphic();
		
		String[] ss = {"egg", "foo", "paper", "ab", "ab"};
		String[] tt = {"add", "bar", "title", "aa", "ca"};
		boolean[] expects = {true, false, true, false, true};

		for(int i = 0; i< ss.length; i++){
			System.out.println(ss[i] + "->" + tt[i] + " , " + expects[i] + "-" + sv.isIsomorphic(ss[i], tt[i]));
		}
		
		String[] sstt = new String[ss.length + tt.length];
		System.arraycopy(ss, 0, sstt, 0, ss.length);
		System.arraycopy(tt, 0, sstt, ss.length, tt.length);
		
		Misc.printListList(sv.isIsomorphic(sstt));
		
	}

}
