package array.swapReverseRotate;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * Given a string of words delimited by spaces, reverse the words in string.
 *
 * For example, given "hello world here", return "here world hello"
 *
 *
 * Q1, What constitutes a word?
 *     A sequence of non-space characters constitutes a word and some words have punctuation at the end.
 * Q2, Could the input string contain leading or trailing spaces?
 *     Yes. However, your reversed string should not contain leading or trailing spaces.
 * Q3, How about multiple spaces between two words?
 *     Reduce them to a single space in the reversed string.
 *
 * Follow-up: given a mutable string representation, can you perform this operation in-place?
 *
 *  Tags: google
 *
 */

public class ReverseWords {

    public String reverseWords(String s, String delimiter){
        if(null == s || (s = s.trim()).isEmpty() || null == delimiter){
            return s;
        }

        String[] words = s.split(delimiter);
        String word;
        
        StringBuilder result = new StringBuilder();
        for(int i = words.length - 1; i >= 0; i--){
            word = words[i].trim();
            if(word.isEmpty()){
                continue;
            }
                
            result.append(word).append(delimiter);
        }

        return result.deleteCharAt(result.length() - 1).toString();
    }

    /**
     *  reverse in-place in a mutable string representation
     * 
     * @param words
     * @param delimiter
     * @return 
     */
    public char[] reverseWords(char[] words, char delimiter){
        if(null == words || 0 == words.length){
            return words;
        }
        
        //remove the leading or trailing spaces 
        int n = words.length;
        int end = 0;
        for(int j = 0; j < n; j++){
            if(words[j] != delimiter || (end > 0 && words[end - 1] != delimiter)){
                words[end] = words[j];
                
                end++;
            }
        }
        
        Arrays.fill(words, end, n, ' ');
        if(end > 0 && words[end - 1] == delimiter){
            end-- ;
        }
        
        //System.out.println(String.format("debug: words=[%s], end=%d", String.valueOf(words), end));

        //reverse every char in words
        reverse(words, 0, end - 1);

        //reverse every word in words
        int left = 0;
        for(int right = 0; right < end; right++){
            if(words[right] == delimiter){
                reverse(words, left, right - 1);
                left = right + 1;
            }
        }

        reverse(words, left, end - 1);

        return words;
    }

    private void reverse(char[] chars, int from , int to){
        for(int left = from, right = to; left < right; left++, right--){
            char tmp = chars[left];
            chars[left] = chars[right];
            chars[right] = tmp;
        }
    }
    

    /**
     * @param args
     */
    public static void main(String[] args) {
        ReverseWords sv = new ReverseWords();
        
        
        String[][] inputs = {
            //{s, expect}
            {" ", ""},
            {"hello world here", "here world hello"},
            {"hello  world  here ", "here world hello"},

        };
        
        for(String[] s : inputs){
            System.out.println("\ninput: " + s[0] + "--");
            System.out.println(sv.reverseWords(s[0], " "));
            System.out.println(sv.reverseWords(s[0].toCharArray(), ' '));
            
            Assert.assertEquals(s[1], sv.reverseWords(s[0], " "));

            Assert.assertEquals(s[1], String.valueOf(sv.reverseWords(s[0].toCharArray(), ' ')).trim() );
        }

    }

}
