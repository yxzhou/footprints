package dailyCoding;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Given an integer k and a string s, find the length of the longest substring that contains at most k distinct characters.

 For example, given s = "abcba" and k = 2, the longest substring with k distinct characters is "bcb".
 *
 * Tags: Amazon
 */
public class LongestSubString {

    public int longestSubString(String s, int k){
        if(null == s || s.isEmpty()){
            return 0;
        }

        int result = 0;

        Map<Character, Integer> lastPositions = new HashMap<>(k); //<character, last position of the character>
        int i = 0;
        int j = 0;
        for( ; i < s.length(); i++){
            char c = s.charAt(i);
            if(lastPositions.size() <= k){
                lastPositions.put(c, i);
            }else{
                result= Math.max(result, i - j);

                int min = s.length();
                Character keyOfMin = null;
                for(Map.Entry<Character, Integer> position : lastPositions.entrySet()){
                    if(position.getValue() < min){
                        min = position.getValue();
                        keyOfMin = position.getKey();
                    }
                }

                j = min;
                lastPositions.remove(keyOfMin);
            }
        }

        result = Math.max(result, i - j);

        return result;
    }


    public int longestSubString_2(String s, int k){
        if(null == s || s.isEmpty()){
            return 0;
        }

        int result = 0;

        Map<Character, Integer> lastPositions = new HashMap<>(k); //<character, numbers of the character>
        int i = 0;
        int j = 0;
        for( ; i < s.length(); i++){
            char c = s.charAt(i);
            if(lastPositions.size() <= k){
                if(lastPositions.containsKey(c)){
                    lastPositions.put(c, lastPositions.get(c) + 1);
                }else{
                    lastPositions.put(c, 1);
                }
            }else{
                result= Math.max(result, i - j);

                while(j < i){
                    c = s.charAt(j);

                    if(lastPositions.get(c) == 1){
                        lastPositions.remove(c);
                    }else{
                        lastPositions.put(c, lastPositions.get(c) - 1);
                    }
                }
            }
        }

        result = Math.max(result, i - j);

        return result;
    }
}
