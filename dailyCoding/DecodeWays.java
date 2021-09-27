package dailyCoding;

import java.util.*;

import org.junit.Assert;
import org.junit.Test;

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



    public int decodeWays_dp(String message){
        //assume it's decodable, every character is in [0, 9] and there is no break point ( such as 000)
        if(null == message || message.trim().isEmpty()){
            return 0;
        }

        char[] chars = message.toCharArray();
        int size = chars.length;
        int[] dp = new int[size + 1]; //default all are 0
        dp[0] = 1;
        dp[1] = 1;

        for(int i = 2; i <= size; i++){
            if(chars[i - 1] != '0'){
                dp[i] += dp[i - 1];
            }

            if(chars[i - 2] == '1' || ( chars[i - 2] == '2' && chars[i - 1] < '7')){
                dp[i] += dp[i - 2];
            }
        }

        return dp[size];
    }

    public int decodeWays_dp_n(String message){
        //assume it's decodable, every character is in [0, 9] and there is no break point ( such as 000)
        if(null == message || message.trim().isEmpty()){
            return 0;
        }

        char[] chars = message.toCharArray();
        int countOne = 1;
        int countTwo = 1;

        for(int i = 2; i <= message.length(); i++){
            int ways = 0;
            if(chars[i - 1] != '0'){
                ways += countOne;
            }

            if(chars[i - 2] == '1' || ( chars[i - 2] == '2' && chars[i - 1] < '7')){
                ways += countTwo;
            }

            countTwo = countOne;
            countOne = ways;
        }

        return countOne;
    }

    @Test public void test(){
        String[] str = {"", "-1", "ab", "01"
                , "1", "10", "11", "21", "28", "1211", "111221"
                , "110", "230", "301", "1090", "10034", "12034", "1200", "1203", "12834", "834", "1280", "12801"
                ,"6065812287883668764831544958683283296479682877898293612168136334983851946827579555449329483852397155"
                ,"4757562545844617494555774581341211511296816786586787755257741178599337186486723247528324612117156948"
                ,"1159314227869675749153973158896359637455398771636981264557866779635662185364345272665484523344457179"};
        int[] n = {0, 0, 0, 0
                , 1, 1, 2, 2, 1, 5, 13
                , 1, 0, 0, 0, 0, 1, 0, 1, 2, 1, 0, 0
                , 0, 589824, 6912};

        for(int i=4; i<str.length; i++){
            //System.out.println(i);

            Assert.assertEquals(n[i], decodeWays_dp(str[i]));
            Assert.assertEquals(n[i], decodeWays_dp_n(str[i]));

        }
    }
}
