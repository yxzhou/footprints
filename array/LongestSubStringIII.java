package array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Given an integer k and a string s, find the length of the longest substring that contains at most k distinct characters.
 * 
 * Example 1:
 * Input: S = "eceba" and k = 3
 * Output: 4
 * Explanation: T = "eceb"
 * 
 * Example 2:
 * Input: S = "WORLD" and k = 4
 * Output: 4
 * Explanation: T = "WORL" or "ORLD"
 *
 * Example 3:
 * Input: "abcba" and k = 2
 * Output: 3 
 * Explanation: T = "bcb"
 * 
 * Challenge O(n), n is the size of the string s.
 * 
 * Tags: Amazon
 */
public class LongestSubstringIII {

    /**
     * define n as the length of string
     * Time O(n * k), worst case example for "abcabc", k = 3 
     * 
     * @param s
     * @param k
     * @return 
     */
    public int longestSubString_n(String s, int k){
        if(null == s ){
            return 0;
        }

        int max = 0; //the result

        Map<Character, Integer> positions = new HashMap<>(k); //<character, last position of the character>
        char c;
        for(int n = s.length(), base = -1, i = 0; i < n; i++){
            c = s.charAt(i);
            
            if(positions.size() == k && !positions.containsKey(c)){
                
                //find the left-most occurrence character
                int min = n;
                Character keyOfMin = null;
                for(Map.Entry<Character, Integer> position : positions.entrySet()){
                    if(position.getValue() < min){
                        min = position.getValue();
                        keyOfMin = position.getKey();
                    }
                }

                base = min;
                positions.remove(keyOfMin);
            }
            
            positions.put(c, i);
            max= Math.max(max, i - base);
        }

        return max;
    }

    /**
     * slide window
     * Time O(n) because the left and right of slide window both moved n times at mostly
     * 
     * @param s
     * @param k
     * @return 
     */
    public int longestSubString_2(String s, int k){
        if(null == s){
            return 0;
        }
        
        int result = 0;
        
        Map<Character, Integer> counts = new HashMap<>();//map<the character, the occurrence number of the character>
        char c;
        for(int left = 0, right = 0; right < s.length(); right++ ){
            c = s.charAt(right);
            counts.put(c, counts.getOrDefault(c, 0) + 1);

            while(counts.size() == k){
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
