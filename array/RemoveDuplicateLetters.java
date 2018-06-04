package fgafa.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * Given a string which contains only lowercase letters, remove duplicate letters so that every letter appear once and only once. 
 * You must make sure your result is the smallest in lexicographical order among all possible results.

    Example:
    Given "bcabc"
    Return "abc"
    
    Given "cbacdcbc"
    Return "acdb"
 *
 */

public class RemoveDuplicateLetters {

    public String removeDuplicateLetters(String s) {
        //check
        if(null == s || 0 == s.length()){
            return "";
        }
        
        Map<Integer, List<Integer>> positions = new HashMap<>(26); //chars[0] is the positions of 'a'
        for(int i = 0, c = 0; i < s.length(); i++){
            c = s.charAt(i) - 'a';
            
            if(!positions.containsKey(c)){
                positions.put(c, new LinkedList<Integer>());
            }
            
            positions.get(c).add(i + 1);
        }
        
        StringBuilder result = new StringBuilder();
        int curr = 0;
        int pre = 0;
        for(int k = positions.size(); k > 0; k--){
            for(int i = 0; i < 26; i++){
                if(positions.containsKey(i)){
                    for(int p : positions.get(i)){
                        if(p > pre){
                            curr = p;
                            break;
                        }
                    }
                    
                    if(validToStartWith(i, positions, curr)){
                        pre = curr;
                        
                        result.append((char)(i + 'a'));
                        positions.remove(i);
                        
                        break;
                    }
                }
            }
        }

        return result.toString();
    }
    
    private boolean validToStartWith(int currKey, Map<Integer, List<Integer>> positions, int base){
        List<Integer> list;
        for(int key : positions.keySet()){
            if(key != currKey){
                list = positions.get(key);
                if(base > list.get(list.size() - 1)){
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        String[] input = {
                    "bcabc",  //Return "abc"
                    "cbacdcbc", //Return "acdb"
                    "cbabc", // abc
                    "fceadb", // fceadb 
                    "cbaddabaa" //cadb
        };

        RemoveDuplicateLetters sv = new RemoveDuplicateLetters();
        
        for(String s : input){
            System.out.println(String.format("%s -> %s", s, sv.removeDuplicateLetters(s)));
        }
    }

}
