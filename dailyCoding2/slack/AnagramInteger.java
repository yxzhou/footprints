package dailyCoding2.slack;

import static junit.framework.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * This problem was asked by Slack.
 *
 * You are given a string formed by concatenating several words corresponding to the integers zero through nine and then anagramming.
 *
 * For example, the input could be 'niesevehrtfeev', which is an anagram of 'threefiveseven'. Note that there can be multiple instances of each integer.
 *
 * Given this string, return the original integers in sorted order. In the example above, this would be 357.
 *
 *
 * Thoughts:
 *   when i got "niesevehrtfeev", how to get the "threefiveseven" ?
 *
 *   let's see the integers zero through nine.
 *
 *              1st-the unique character      2nd-the unique character    3rd-the unique character
 *   zero           z
 *   one                                         o
 *   two            w
 *   three                                       r
 *   four           u
 *   five                                        f
 *   six            x
 *   seven                                       s
 *   eight          g
 *   nine                                                                   i
 *
 *
 *    0 1 2 3 4 5 6 7 8 9
 *    a b c d e f g h i j
 *    k l m n o p q r s t
 *    u v w x y z
 *
 *   //z - zero
 *   if (chars[25] != 0) {
 *       integers[0] = chars[25];
 *       decrease(chars, "zero", integers[0]);
 *   }
 */

public class AnagramInteger {

    //char[] uniqueChars = {'z', 'w', 'u', 'x', 'g', 'o', 'r', 'f', 's', 'i'};
    int[] uniqueChars = {25, 22, 20, 23, 6, 14, 17, 5, 18, 8};
    String[] uniqueChar2String = {"zero", "two", "four", "six", "eight", "one", "three", "five", "seven", "nine"};
    int[] string2Integer = {0, 2, 4, 6, 8, 1, 3, 5, 7, 9};

//    String[] integer2String = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
//    Map<String, Integer> string2Integer = new HashMap<>();
//    {
//        for(int i = 0; i < integer2String.length; i++){
//            string2Integer.put(integer2String[i], i);
//        }
//    }

    public String anagramInteger(String anagram){
        if(anagram == null || anagram.trim().isEmpty()){
            return "";
        }

        int[] chars = new int[26]; //
        for(char c : anagram.toCharArray()){
            chars[c - 'a']++;
        }

        int[] integers = new int[10];

        for(int i=0; i < uniqueChars.length; i++){
            if(chars[uniqueChars[i]] != 0){
                integers[string2Integer[i]] = chars[uniqueChars[i]];
                decrease(chars, uniqueChar2String[i], chars[uniqueChars[i]]);
            }

        }

        StringBuilder result = new StringBuilder();
        for(int i = 0; i < integers.length; i++){
            if(integers[i] != 0){
                for(int j = 0; j < integers[i]; j++){
                    result.append(i);
                }
            }
        }

        return result.toString();
    }

    private void decrease(int[] charsCount, String chars, int n ){
        for(char c : chars.toCharArray()){
            int i = c - 'a';
            charsCount[i] -= n;

            if(charsCount[i] < 0){
                throw new IllegalArgumentException("");
            }
        }
    }


    @Test
    public void test() {
        assertEquals("", "357", anagramInteger("niesevehrtfeev"));
    }

}
