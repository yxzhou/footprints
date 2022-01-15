/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.palindrome;

/**
 *
 * Given a string which consists of lowercase or uppercase letters, find the length of the longest palindromes that can be built with those letters.
 * 
 * This is case sensitive, for example "Aa" is not considered a palindrome here.
 * 
 * Assume the length of given string will not exceed 100000.
 * 
 * Example 1:
 * Input : s = "abccccdd"
 * Output : 7
 * Explanation : One longest palindrome that can be built is "dccaccd", whose length is `7`.
 * 
 * Solution:
 *   check every characters in the input s, 
 *      if it has even number, all them can be built. 
 *      if it has odd number, x, only the x-1 can be built
 *   In the final output, anyone character can be built in the middle.
 *   
 */
public class LongestPalindrome {
    /**
     * @param s: a string which consists of lowercase or uppercase letters
     * @return: the length of the longest palindromes that can be built
     */
    public int longestPalindrome(String s) {
        if(s == null || s.isEmpty() ){
            return 0;
        }

        int n = s.length();
        int[] counts = new int[128];
        for(int i = 0; i < n; i++){
            counts[s.charAt(i)]++;
        }

        boolean foundOdd = false;
        for(int x : counts){
            if( (x & 1) == 1 ){
                n--;
                foundOdd = true;
            }
        }

        return foundOdd? n + 1 : n; //anyone character can be put in the middle. 
    }
    
   /**
     * @param s: a string which consists of lowercase or uppercase letters
     * @return the length of the longest palindromes that can be built
     */
    public int longestPalindrome_n(String s) {
        if(s == null || s.isEmpty() ){
            return 0;
        }

        int n = s.length();
        boolean[] isOdd = new boolean[128]; //default all are false
        for(char c : s.toCharArray()){
            isOdd[c] = !isOdd[c];
            //isOdd[c] ^= true;
        }

        boolean foundOdd = false;
        for(boolean x : isOdd){
            if( x ){
                n--;
                foundOdd = true;
            }
        }

        return foundOdd? n + 1 : n;
    }
}
