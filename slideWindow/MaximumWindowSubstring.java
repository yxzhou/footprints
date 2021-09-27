package slideWindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
    public int lengthOfLongestSubstringTwoDistinctI_n(String s) {
        int maxLength = 0;
        
        if(null == s || 0 == s.length()){
            return maxLength;
        }
        
        Map<Character, Interval> intervals = new HashMap<>();

        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i); 
            
            if(intervals.size() < 2 && !intervals.containsKey(c)){
                intervals.put(c, new Interval(c, i, i));
            }else if(intervals.size() == 2 && !intervals.containsKey(c)){
                List<Interval> list = new ArrayList<>(intervals.values());
                Collections.sort(list);
                
                int diff = list.get(1).right - Math.min(list.get(0).left, list.get(1).left);
                
                maxLength = Math.max(maxLength, diff);                
                intervals.remove(list.get(0).value);
            }else{
                intervals.get(c).right = i;
            }
        }
        
        if(intervals.size() < 2){
            maxLength = s.length() - 1;
        }
        
        return maxLength + 1;
    }
    
    class Interval implements Comparable<Interval>{
        char value;
        int left;
        int right;

        Interval(char value, int left, int right){
            this.value = value;
            this.left = left;
            this.right = right;
        }
        
        @Override
        public int compareTo(Interval o) {
            return this.right - o.right;
        }
    }
    
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
    public int lengthOfLongestSubstringTwoDistinctII_n(String s, int k) {
        int maxLength = 0;

        if(null == s || 0 == s.length()){
            return maxLength;
        }

        Map<Character, Interval> indexes = new HashMap<>();
        int left = 0;
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);

            if(!indexes.containsKey(c)){
                if(indexes.size() < k){
                    indexes.put(c, new Interval(c, i, i));
                }else{ // indexes.size() == k
                    List<Interval> list = new ArrayList<>(indexes.values());
                    Collections.sort(list);

                    maxLength = Math.max(maxLength, list.get(k - 1).right - left + 1);
                    left = list.get(0).right + 1;

                    indexes.remove(list.get(0).value);
                }
            }else{
                indexes.get(c).right = i;
            }
        }

        if(indexes.size() < k){
            maxLength = s.length();
        }

        return maxLength;
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
