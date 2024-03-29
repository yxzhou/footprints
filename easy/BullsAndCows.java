package easy;

import java.util.HashMap;
import java.util.Map;

/**
 * _https://www.lintcode.com/problem/1299
 * 
 * You are playing the following Bulls and Cows game with your friend: You write down a number and ask your friend to
 * guess what the number is. Each time your friend makes a guess, you provide a hint that indicates how many digits in
 * said guess match your secret number exactly in both digit and position (called "bulls") and how many digits match the
 * secret number but locate in the wrong position (called "cows"). Your friend will use successive guesses and hints to
 * eventually derive the secret number.

    For example:
    Secret number:  "1807"
    Friend's guess: "7810"
    Hint: 1 bull and 3 cows. (The bull is 8, the cows are 0, 1 and 7.)
    
    Write a function to return a hint according to the secret number and friend's guess, 
    use A to indicate the bulls and B to indicate the cows. In the above example, your function should return "1A3B".
    
    Please note that both secret number and friend's guess may contain duplicate digits, for example:
    Secret number:  "1123"
    Friend's guess: "0111"
    In this case, the 1st 1 in friend's guess is a bull, the 2nd or 3rd 1 is a cow, and your function should return "1A1B".
    You may assume that the secret number and your friend's guess only contain digits, and their lengths are always equal.
 *
 */

public class BullsAndCows {

    public String getHint(String secret, String guess) {
        if(null == secret || null == guess || secret.length() != guess.length()){
            return "";
        }
        
        //get bulls and Map<Character, Integer/*position*/>
        Map<Character, Integer> countsOfSecret = new HashMap<>();
        Map<Character, Integer> countsOfGuess = new HashMap<>();
        int bullNum = 0;
        Character c;
        for(int i = 0; i < secret.length(); i++){
            
            if(guess.charAt(i) == secret.charAt(i)){
                bullNum ++;
            }else{
                
                c = secret.charAt(i);
                if(countsOfSecret.containsKey(c)){
                    countsOfSecret.put(c, countsOfSecret.get(c) + 1);
                }else{
                    countsOfSecret.put(c, 1);
                }
                
                c = guess.charAt(i);
                if(countsOfGuess.containsKey(c)){
                    countsOfGuess.put(c, countsOfGuess.get(c) + 1);
                }else{
                    countsOfGuess.put(c, 1);
                }

            }
        }
        
        int cowNum = 0;
        for(Character digit : countsOfGuess.keySet()){
            if(countsOfSecret.containsKey(digit)){
                cowNum += Math.min(countsOfGuess.get(digit), countsOfSecret.get(digit));
            }
        }
        
        return String.format("%dA%dB", bullNum, cowNum);
    }
    
    public String getHint_2(String secret, String guess) {
        if(null == secret || null == guess || secret.length() != guess.length()){
            return "";
        }
        
        //get bulls and Map<Character, Integer/*count*/>
        int[] countsOfSecret = new int[10]; //default all are 0
        int[] countsOfGuess = new int[10];
        int bullNum = 0;
        for(int i = 0; i < secret.length(); i++){
            
            if(guess.charAt(i) == secret.charAt(i)){
                bullNum ++;
            }else{
                countsOfSecret[secret.charAt(i) - '0']++;
                countsOfGuess[guess.charAt(i) - '0']++;
            }
        }
        
        int cowNum = 0;
        for(int i = 0; i < countsOfGuess.length; i++){
            cowNum += Math.min(countsOfGuess[i], countsOfSecret[i]);
        }
        
        return String.format("%dA%dB", bullNum, cowNum);
    }
    
    /**
     * @param secret: An string
     * @param guess: An string
     * @return xAyB, x bulls and y cows
     */
    public String getHint_n(String secret, String guess) {
//        if(null == secret || null == guess || secret.length() != guess.length()){
//            return "";
//        }
        assert secret == null || guess == null || secret.length() != guess.length();
        

        int[] counts = new int[10]; // index for number 0 to 9

        int n = secret.length();
        for(int i = 0; i < n; i++){
            counts[secret.charAt(i) - '0']++;
        }

        int bull = 0;
        int cow = 0;
        char c;
        int x;
        for(int i = 0; i < guess.length(); i++){
            c = guess.charAt(i);
            x = c - '0';
            if( c == secret.charAt(i)){
                bull++;
                counts[x]--;
            }else if(counts[x] > 0){
                cow++;
                counts[x]--;
            }
        }

        return bull + "A" + cow + "B";
    }
    
    public String getHint_n2(String secret, String guess) {
        
        int[] count = new int[10];
        int bull = 0;
        int cow = 0;
        
        int s, g;
        for (int i = 0; i < secret.length(); i++) {
            s = secret.charAt(i) - '0';
            g = guess.charAt(i) - '0';
            if (s == g) {
                bull++;
            } else {
                if (count[s]++ < 0) {
                    cow++;
                }
                if (count[g]-- > 0) {
                    cow++;
                }
            }
        }
        return bull + "A" + cow + "B";
    }
    
}
