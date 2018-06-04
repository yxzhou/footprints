package fgafa.array.wordDistance;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Q1: Given a string, find the length of the longest substring T that contains at most 2 distinct characters.

    For example, Given s = “eceba”,
    
    T is "ece" which its length is 3.
    
    
 * Q2: Given a string, find the length of the longest substring T that contains at most k distinct characters.
 */

public class MaximumWindowSubstring {

    /* Time O(n),  Space O(1)*/
    public int lengthOfLongestSubstringTwoDistinctI_12(String s) {
        int result = 0;
        
        //check
        if(null == s || s.length() < 2){
            return result;
        }
        
        // Key: letter; value: the occurrence number. 
        Map<Character, Integer> counts = new HashMap<>();
        Character c;
        for(int left = 0, right = 0; right < s.length();  ){
            if(counts.size() < 3){
                result = Math.max(result, right - left);
                
                c = s.charAt(right);
                right++;
                
                if(counts.containsKey(c)){
                    counts.put(c, counts.get(c) + 1);
                }else{
                    counts.put(c, 1);
                }
            }
            
            while(counts.size() == 3){
                c = s.charAt(left);
                left++;
                
                if(counts.get(c) > 1){
                    counts.put(c, counts.get(c) - 1);
                }else{
                    counts.remove(c);
                }
            }
        }
        
        return result;
    }
    
    public int lengthOfLongestSubstringTwoDistinctI_1(String s) {
        int result = 0;
        
        //check
        if(null == s || s.length() < 2){
            return result;
        }
        
        // Key: letter; value: the occurrence number. 
        Map<Character, Integer> counts = new HashMap<>();
        Character c;
        for(int right = 0, left = 0; right < s.length(); right++ ){
            c = s.charAt(right);
            
            if(counts.containsKey(c)){
                counts.put(c, counts.get(c) + 1);
            }else{
                counts.put(c, 1);
                
                while(counts.size() > 2){
                    c = s.charAt(left);
                    left++;
                    
                    if(counts.get(c) > 1){
                        counts.put(c, counts.get(c) - 1);
                    }else{
                        counts.remove(c);
                    }
                }
            }
            
            result = Math.max(result, right - left + 1);
        }
        
        return result;
    }
    
    /* Time O(n),  Space O(1)*/
    public int lengthOfLongestSubstringTwoDistinctI_2(String s) {
        int result = 0;
        
        //check
        if(null == s || s.length() < 2){
            return result;
        }
        
        // Key: letter; value: the index of the last occurrence. 
        Map<Character, Integer> indexs = new HashMap<>();
        Character curr;
        Character last = ' ';
        for(int right = 0, left = -1; right < s.length(); right++){
            curr = s.charAt(right);
            
            if(indexs.size() == 2 && !indexs.containsKey(curr)){
                
                //the character with the leftmost last occurrence
                left = right;
                for(Map.Entry<Character, Integer> entry : indexs.entrySet()){
                    if(entry.getValue() < left){
                        left = entry.getValue();
                        last = entry.getKey();
                    }
                }
                
                indexs.remove(last);
            }
            
            indexs.put(curr, right);
            result = Math.max(result, right - left);
        }
        
        return result;
    }
    
    
    /* Time O(n),  Space O(k)*/
    public int lengthOfLongestSubstringTwoDistinctII(String s, int k) {
        int result = 0;
        
        //check
        if(null == s || s.length() < 2){
            return result;
        }
        
        // Key: letter; value: the occurrence number. 
        Map<Character, Integer> counts = new HashMap<>();
        Character c;
        for(int right = 0, left = 0; right < s.length(); right++ ){
            c = s.charAt(right);
            
            if(counts.containsKey(c)){
                counts.put(c, counts.get(c) + 1);
            }else{
                counts.put(c, 1);
                
                while(counts.size() > k){
                    c = s.charAt(left);
                    left++;
                    
                    if(counts.get(c) > 1){
                        counts.put(c, counts.get(c) - 1);
                    }else{
                        counts.remove(c);
                    }
                }
            }
            
            result = Math.max(result, right - left + 1);
        }
        
        return result;
    }
}
