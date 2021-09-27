package leetcode.facebook;

import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

/**
 * Given an encoded string, return it's decoded string.
 *
 * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated
 * exactly k times. Note that k is guaranteed to be a positive integer.
 *
 * You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.
 *
 * Furthermore, you may assume that the original data does not contain any digits and that digits are only for those
 * repeat numbers, k. For example, there won't be input like 3a or 2[4].
 *
 * Examples:
 * s = "3[a]2[bc]", return "aaabcbc".
 * s = "3[a2[c]]", return "accaccacc".
 * s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
 *
 *
 *  Solution:
 *     the format would be:  str + num + [ + str + num + ] + str
 *     example:  ab23[cd45[ef]gh]
 *
 *     " 3[a]2[bc]"
 *     " 3[a2[c]]"
 *     " 2[abc]3[cd]ef"
 */

public class DecodeString {


    public String decodeString(String s) {

        Stack<StringBuilder> strs = new Stack<>();
        Stack<Integer> nums = new Stack<>();

        StringBuilder sb = new StringBuilder();
        int n = 0;

        for(char c : s.toCharArray()){
            if(c == '['){ // from k to encoded_string
                strs.add(sb);
                sb = new StringBuilder();

                nums.add(n);
                n = 0;
            }else if(c == ']'){
                StringBuilder subResult = strs.pop();

                for(int i = nums.pop(); i > 0; i--){
                    subResult.append(sb);
                }

                sb = subResult;
            }else if(c >= '0' && c <= '9' ){
                n = n * 10 + (c - '0');

            }else{ // [a, z]
                sb.append(c);

            }
        }

        return sb.toString();
    }


    @Test
    public void test(){

        Assert.assertEquals("aaabcbc", decodeString("3[a]2[bc]"));
        Assert.assertEquals("accaccacc", decodeString("3[a2[c]]"));
        Assert.assertEquals("abcabccdcdcdef", decodeString("2[abc]3[cd]ef"));

    }

}
