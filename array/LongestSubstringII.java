package array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * _https://www.lintcode.com/problem/928
 * 
 * Given a string, find the length of the longest substring T that contains at most 2 distinct characters.
 * 
 * Input: “eceba” Output: 3
 * Explanation:  T is "ece" which its length is 3.
 * 
 * Example 2
 * Input: “aaa” Output: 3
 * 
 */

public class LongestSubstringII {

    /**
     * Time O(n),  Space O(1)
     * 
     * @param s: a string
     * @return the length of the longest substring T that contains at most 2 distinct characters
     */
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if(s == null){
            return 0;
        }

        int[] p = new int[256]; // p[i] is the last position of character i
        int c1 = -1; // store the distinct characters
        int c2 = -1; // store the distinct characters

        int max = 0; 

        for(int c, n = s.length(), base = -1, i = 0; i < n; i++){
            c = s.charAt(i);

            if(c1 != c && c2 != c){
                if(c1 == -1){
                    c1 = c;
                }else if(c2 == -1){
                    c2 = c;
                }else{
                    if(p[c1] < p[c2]){
                        base = p[c1];
                        c1 = c;
                    }else{
                        base = p[c2];
                        c2 = c;
                    }
                }
            }

            p[c] = i;
            max = Math.max(max, i - base);
        }

        return max;
    }
    
    /* Time O(n),  Space O(1)*/
    public int lengthOfLongestSubstringTwoDistinct_2(String s) {
        if(null == s){
            return 0;
        }
         
        int max = 0; //result
        
        Map<Character, Integer> indexs = new HashMap<>(); //<the character, the last index of the character >
        Character curr;
        Character last = null;
        for(int right = 0, left = -1; right < s.length(); right++){
            curr = s.charAt(right);
            
            if(indexs.size() == 2 && !indexs.containsKey(curr)){
                
                //find the left-most occurrence character
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
            max = Math.max(max, right - left);
        }
        
        return max;
    }

    /**
     * Time O(n),  Space O(1)
     */
    public int lengthOfLongestSubstringTwoDistinctI_12(String s) {
        if(null == s){
            return 0;
        }
        
        int result = 0;
        
        Map<Character, Integer> counts = new HashMap<>();//map<the character, the occurrence number of the character>
        char c;
        for(int left = 0, right = 0; right < s.length(); right++ ){
            c = s.charAt(right);
            counts.put(c, counts.getOrDefault(c, 0) + 1);

            while(counts.size() == 3){
                c = s.charAt(left);
                left++;
                
                if(counts.get(c) > 1){
                    counts.put(c, counts.get(c) - 1);
                }else{
                    counts.remove(c);
                }
            }
            
            result = Math.max(result, right - left + 1);
        }
        
        return result;
    }

}
