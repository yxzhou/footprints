/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package array.anagram;

/**
 * _https://www.lintcode.com/problem/1052
 *
 * Find the minimum length word from a given dictionary words, which has all the letters from the string licensePlate.
 * Such a word is said to complete the given string licensePlate
 *
 * Here, for letters we ignore case. For example, "P" on the licensePlate still matches "p" on the word.
 *
 * It is guaranteed an answer exists. If there are multiple answers, return the one that occurs first in the array.
 *
 * The license plate might have the same letter occurring multiple times. For example, given a licensePlate of "PP", the
 * word "pair" does not complete the licensePlate, but the word "supper" does.
 *
 * Constraints: 
 *   licensePlate will be a string with length in range [1, 7]. 
 *   licensePlate will contain digits, spaces, or letters (uppercase or lowercase). 
 *   words will have a length in the range [10, 1000]. 
 *   Every words[i] will consist of lowercase letters, and have length in range [1, 15].
 *
 * Example 1: 
 * Input: licensePlate = "1s3 PSt", words = ["step", "steps", "stripe", "stepple"] 
 * Output: "steps"
 * Explanation: The smallest length word that contains the letters "S", "P", "S", and "T". Note that the answer is not
 * "step", because the letter "s" must occur in the word twice. Also note that we ignored case for the purposes of
 * comparing whether a letter exists in the word.
 *
 * Example 2: 
 * Input: licensePlate = "1s3 456", words = ["looks", "pest", "stew", "show"] 
 * Output: "pest"
 * Explanation: There are 3 smallest length words that contains the letters "s". We return the one that occurred first.
 *
 */
public class ShortestCompletingWord {
    /**
     * @param licensePlate: a string
     * @param words: List[str]
     * @return return a string
     */
    public String shortestCompletingWord(String licensePlate, String[] words) {
        
        int[] targets = new int[26];
        int count = 0;
        int c;
        for(int i = 0; i < licensePlate.length(); i++ ){
            c = Character.toLowerCase(licensePlate.charAt(i)) - 'a';

            if(c >= 0 && c < 26){
                targets[c]++;

                if(targets[c] == 1){
                    count++;
                }
            }
        } 

        String r = null; // the result
        int[] curr;
        int state;
        for (String word : words) {
            
            curr = new int[26];
            state = count;
            for(int j = 0; j < word.length(); j++ ){
                c = word.charAt(j) - 'a';
                if(targets[c] > curr[c]){
                    curr[c]++;

                    if(curr[c] == targets[c]){
                        state--;
                    }
                }
            }
            
            if(state == 0 && (r == null || word.length() < r.length()) ){
                r = word;
            }
        }

        return r;
    }
}
