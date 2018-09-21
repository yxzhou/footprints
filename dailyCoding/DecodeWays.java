package fgafa.dailyCoding;

import java.util.*;

/**
 *
 * Given the mapping a = 1, b = 2, … z = 26, and an encoded message, count the number of ways it can be decoded.
 *
 * For example, the message ‘111’ would give 3, since it could be decoded as ‘aaa’, ‘ka’, and ‘ak’.
 *
 * You can assume that the messages are decodable. For example, ‘001’ is not allowed.
 *
 *  Tags: Facebook, Memorized-search,  DP
 */

public class DecodeWays {

    public int decodeWays(int message){

        //ignore check
        //assume it's decodable

        String m = String.valueOf(message);
        return helper(m, 0, new HashMap<String, Integer>(m.length()));

    }

    private int helper(String message, int index, Map<String, Integer> cache){
        if(index >= message.length()){
            return 1;
        }

        String currMessage = message.substring(index);
        if(cache.containsKey(currMessage)){
            return cache.get(currMessage);
        }

        int result = 0;
        int currOne = message.charAt(index) - '0';
        if(currOne > 0 && currOne < 10){
            result += helper(message, index + 1, cache);
        }

        if(index + 1 < message.length()){
            int currTwo = currOne * 10 + message.charAt(index + 1) - '0';
            if(currTwo > 9 && currTwo < 27){
                result += helper(message, index + 2, cache);;
            }
        }

        cache.put(currMessage, result);
        return result;
    }


    //todo
    public int decodeWays_dp(int message){
        //ignore check
        //assume it's decodable

        char[] chars = String.valueOf(message).toCharArray();

        int countOne;
        int countTwo;
        int curr = 0;
        for(int i = chars.length - 1; i >= 0; i--){
            curr = curr * 10 + chars[i] - '0';

            if(curr > 0 && curr < 9){

            }
        }

        return -1;
    }
}
