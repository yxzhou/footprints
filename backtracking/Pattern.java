package backtracking;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Pattern {

    /**
     *  Given a pattern and a string str, find if str follows the same pattern.
        
        Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in str.
        
        Examples:
        pattern = "abba", str = "dog cat cat dog" should return true.
        pattern = "abba", str = "dog cat cat fish" should return false.
        pattern = "aaaa", str = "dog cat cat dog" should return false.
        pattern = "abba", str = "dog dog dog dog" should return false.
        Notes:
        You may assume pattern contains only lowercase letters, and str contains lowercase letters separated by a single space.
     */
    public boolean match(String pattern, String data){
        Map<Character, String> mapping = new HashMap<>();
        Set<String> words = new HashSet<>();
        
        String[] token = data.split(" ");
        
        if(pattern.length() != token.length){
            return false;
        }
        
        char c;
        for( int i = 0; i < pattern.length(); i++){
            c = pattern.charAt(i);
            
            if(!mapping.containsKey(c)){
                if(words.contains(token[i])){
                    return false;
                }
                
                words.add(token[i]);
                mapping.put(c, token[i]);
            }else if(!token[i].equals(mapping.get(c))){
                return false;
            }
        }
        
        return true;
    }
                
    /**
    2) followup，remove spaces
    pattern = 'abba',    data = 'redbluebluered'        true
    pattern = 'abba',    data = 'redblueyellowred'     false
    pattern = 'aaaa',    data = 'redredredred'           true
    pattern = 'abba',    data = redredredred'           false
    */
    
    /*backtracking*/
    public boolean match_II(String pattern, String data){
        if(null == pattern || null == data || 0 == pattern.length() || pattern.length() > data.length()){
            return false;
        }
        
        String[] mapping = new String[26]; //default all are null
        Set<String> words = new HashSet<>();
        
        return helper(pattern, 0, data, 0, mapping, words);
    }
    
    private boolean helper(String pattern, int i, String data, int j, String[] mapping, Set<String> words){
        if(i == pattern.length() && j == data.length()){
            return true;
        }else if(i == pattern.length() || j == data.length()){
            return false;
        }
        
        int c = pattern.charAt(i) - 'a';
        String word;
        if(null == mapping[c]){
            for(int k = j + 1 ; k <= data.length(); k++){
                word = data.substring(j, k);
                if(words.contains(word)){
                    continue;
                }
                
                mapping[c] = word;
                words.add(word);
                if(helper(pattern, i + 1, data, k, mapping, words)){
                    return true;
                }
                words.remove(word);
                mapping[c] = null;
            }
        }else{
            int length = mapping[c].length();
            
            if(j + length <= data.length()){
                word = data.substring(j, j + length);
                
                if(word.equals(mapping[c])){
                    return helper(pattern, i + 1, data, j + length, mapping, words);
                }
            }
        }

        return false;
    }
    
    public static void main(String[] args){
        Pattern sv = new Pattern();
        
        String[] patterns = {
                    "abba",
                    "abba",
                    "aaaa",
                    "abba"
        };
        
        String[] datas = {
                    "redbluebluered",    // true
                    "redblueyellowred",  // false
                    "redredredred",      // true
                    "redredredred"       // false
                    
        };
        
        boolean[] expects = {
                    true,
                    false,
                    true,
                    false
        };
        
        for(int i = 0; i < datas.length; i++){
            System.out.println(String.format("Input: %s, %s", patterns[i], datas[i]));
            System.out.println(String.format("Output: %s %b", sv.match_II(patterns[i], datas[i]), expects[i]));
        }
    }
}
