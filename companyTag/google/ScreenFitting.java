/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.google;

import java.util.Arrays;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/889
 *
 * Given a rows x cols screen and a sentence represented by a list of non-empty words, find how many times the given
 * sentence can be fitted on the screen.
 *
 * Constraints: 
 *   A word cannot be split into two lines. 
 *   The order of words in the sentence must remain unchanged. 
 *   Two consecutive words in a line must be separated by a single space. 
 *   Total words in the sentence won't exceed 100. 
 *   Length of each word is greater than 0 and won't exceed 10. 
 *   1 ≤ rows, cols ≤ 20,000.
 * 
 * Example 1:
	Input: rows = 4, cols = 5, sentence = ["I", "had", "apple", "pie"]
	Output: 1
	
	Explanation:
	I-had
	apple
	pie-I
	had--
	
	The character '-' signifies an empty space on the screen.
	
 * Example 2:
	Input:  rows = 2, cols = 8, sentence = ["hello", "world"]
	Output:  1
	
	Explanation:
	
	hello---
	world---
	
	The character '-' signifies an empty space on the screen.

 * Example 3:
	Input: rows = 3, cols = 6, sentence = ["a", "bcd", "e"]
	Output:  2
	
	Explanation:
	a-bcd-
	e-a---
	bcd-e-

	The character '-' signifies an empty space on the screen.
 * 
 * 
 */
public class ScreenFitting {
    /**
        ? how to do if a word's length is greater than the cols

     * @param sentence: a list of string
     * @param rows: an integer
     * @param cols: an integer
     * @return an integer, denote times the given sentence can be fitted on the screen
     */
    public int wordsTyping(String[] sentence, int rows, int cols) {
        int result = 0; // count of times

        int n = sentence.length;
        
        int i = 0;
        int w = cols + 1;
        int len;
        for(int r = 0; r < rows; ){
            if(i == n){
                i = 0;
                result++;
            }

            len = sentence[i].length();

            if(w > len ){
                w -= len + 1;
                i++;
            }else{ 
                w = cols + 1;    
                r++;
            }

        }

        return result;
    }
    
    /**
     * @param sentence: a list of string
     * @param rows: an integer
     * @param cols: an integer
     * @return an integer, denote times the given sentence can be fitted on the screen
     */
    public int wordsTyping_2(String[] sentence, int rows, int cols) {
        if(sentence == null || sentence.length == 0 ){
            return 0;
        }

        int n = sentence.length;
        int[] lengths = new int[n];
        for(int i = 0; i < n; i++){
            if(sentence[i].length() > cols){
                return 0;
            }

            lengths[i] = sentence[i].length() + 1;
        }

        int count = 0;
        int i = 0;

        for(int r = 0; r < rows; r++){
            for(int w = cols + 1; w >= lengths[i]; ){
                w -= lengths[i];

                i++;
                if(i == n){
                    count++;
                    i = 0;
                }
            }            
        }

        return count;
    }
    
    
    public static void main(String[] args){
        
        String[][][] inputs = {
            //{ sentence, {rows, cols}, {expect} }
            {
                {"I", "had", "apple", "pie"},
                {"4", "5"},
                {"1"}
            },
            {
                {"hello", "world"},
                {"2", "8"},
                {"1"}
            },
            {
                {"a", "bcd", "e"},
                {"3", "6"},
                {"2"}
            }
        };
        
        ScreenFitting sv = new ScreenFitting();
        
        for(String[][] input : inputs){
            Assert.assertEquals(String.format("--sentence: %s, rows= %s, cols= %s", Arrays.toString(input[0]), input[1][0], input[1][1]), 
                    Integer.parseInt(input[2][0]), sv.wordsTyping(input[0], Integer.parseInt(input[1][0]), Integer.parseInt(input[1][1])));
            
            Assert.assertEquals(String.format("--sentence: %s, rows= %s, cols= %s", Arrays.toString(input[0]), input[1][0], input[1][1]), 
                    Integer.parseInt(input[2][0]), sv.wordsTyping_2(input[0], Integer.parseInt(input[1][0]), Integer.parseInt(input[1][1])));
        }
        
    }
    
}
