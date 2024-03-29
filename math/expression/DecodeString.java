package math.expression;

import org.junit.Assert;

import java.util.Stack;

/**
 * _https://www.lintcode.com/problem/575
 * 
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

    /**
     * @param s: an expression includes numbers, letters and brackets
     * @return a string
     */
    public String expressionExpand(String s) {

        Stack<StringBuilder> strs = new Stack<>();
        Stack<Integer> nums = new Stack<>();

        char c;
        int num = 0;
        StringBuilder inner = new StringBuilder();
        StringBuilder outer;
        for(int i = 0, n = s.length() ; i < n; i++){
            c = s.charAt(i);

            if(Character.isDigit(c) ){
                num = num * 10 + (c - '0');
            }else if(Character.isLetter(c) ){
                inner.append(c);
            }else if(c == '['){
                strs.add(inner);
                inner = new StringBuilder();

                nums.add(num);
                num = 0;
            }else{ // c == ']'
                num = nums.pop();
                outer = strs.pop();

                for( ; num > 0; num--){
                    outer.append(inner);
                }

                inner = outer;
            }
        }

        return inner.toString();
    }
    
    public String decodeString(String s) {

        Stack<StringBuilder> strs = new Stack<>();
        Stack<Integer> nums = new Stack<>();

        StringBuilder curr = new StringBuilder();
        StringBuilder pre;
        int num = 0;
        char c;
        for(int i = 0, n = s.length(); i < n;  ){
            c = s.charAt(i);
            
            if(c == '['){ // from k to encoded_string
                strs.add(curr);
                nums.add(num);
                
                curr = new StringBuilder();
                i++;
            }else if(c == ']'){
                pre = strs.pop();

                for( num = nums.pop(); num > 0; num--){
                    pre.append(curr);
                }

                curr = pre;
                i++;
            }else if( Character.isDigit(c) ){
                num = 0;
                for( ; i < n && Character.isDigit( c = s.charAt(i) ); i++){
                    num = num * 10 + (c - '0');
                }
                
            }else{ // [a, z]
                for( ; i < n && Character.isLetter( c = s.charAt(i) ); i++ ){
                    curr.append(c);
                }

            }
        }

        return curr.toString();
    }


   
    public static void main(String[] args){
        String[][] inputs = {
            {"abc3[a]", "abcaaa"},
            {"3[a]2[bc]", "aaabcbc"},
            {"3[a2[c]]", "accaccacc"},
            {"2[abc]3[cd]ef", "abcabccdcdcdef"},
            {"3[2[ad]3[pf]]xyz", "adadpfpfpfadadpfpfpfadadpfpfpfxyz"}
        };
        
        DecodeString sv = new DecodeString();

        for(String[] input : inputs){
            System.out.println(String.format("\ns = %s, expect: %s", input[0], input[1]));
            
            Assert.assertEquals(input[1], sv.decodeString(input[0]));
            
            Assert.assertEquals(input[1], sv.expressionExpand(input[0]));
        }

    }

}
