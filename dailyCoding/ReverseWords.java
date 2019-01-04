package fgafa.dailyCoding;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * Given a string of words delimited by spaces, reverse the words in string. For example, given "hello world here", return "here world hello"
 * Follow-up: given a mutable string representation, can you perform this operation in-place?
 *
 *  Tags: google
 *
 */

public class ReverseWords {

    public String reverseWords(String words, String delimiter){
        if(null == words || 0 == words.length() || null == delimiter){
            return words;
        }

        String[] wordsArray = words.split(delimiter);
        StringBuilder result = new StringBuilder();
        for(int i = wordsArray.length - 1; i > 0; i--){
            result.append(wordsArray[i]).append(delimiter);
        }

        return result.append(wordsArray[0]).toString();
    }

    /**
     *  reverse in-place in a mutable string representation
     */
    public char[] reverseWords(char[] words, char delimiter){
        if(null == words || 0 == words.length){
            return words;
        }

        //reverse every char in words
        reverse(words, 0, words.length - 1);

        //reverse every word in words
        int left = 0;
        for(int right = 0; right < words.length; right++){
            if(words[right] == delimiter){
                reverse(words, left, right - 1);
                left = right + 1;
            }
        }

        reverse(words, left, words.length - 1);

        return words;
    }

    private void reverse(char[] chars, int from , int to){
        for(int left = from, right = to; left < right; left++, right--){
            char tmp = chars[left];
            chars[left] = chars[right];
            chars[right] = tmp;
        }
    }

    @Test public void test(){
        Assert.assertEquals("here world hello", reverseWords("hello world here", " "));

        //System.out.println(reverseWords("hello world here".toCharArray(), ' '));

        Assert.assertArrayEquals("here world hello".toCharArray(), reverseWords("hello world here".toCharArray(), ' '));
    }

}
