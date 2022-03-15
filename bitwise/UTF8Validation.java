/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bitwise;

import java.util.Arrays;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/1262
 *
 * A character in UTF-8 can be from 1 to 4 bytes long, subjected to the following rules:
 *
 * For 1-byte character, the first bit is a 0, followed by its unicode code. For n-bytes character, the first n-bits are
 * all one's, the n+1 bit is 0, followed by n-1 bytes with most significant 2 bits being 10. This is how the UTF-8
 * encoding would work:
 *
 * Char. number range  | UTF-8 octet sequence  
 * (hexadecimal)       | (binary)
 * --------------------+--------------------------------------------- 
 * 0000 0000-0000 007F | 0xxxxxxx 
 * 0000 0080-0000 07FF | 110xxxxx 10xxxxxx 
 * 0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx 
 * 0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx 
 * Given an array of integers representing the data, return whether it is a valid UTF-8 encoding.
 *
 *
 * The input is an array of integers. Only the least significant 8 bits of each integer is used to store the data. This
 * means each integer represents only 1 byte of data.
 *
 * Example1
 * Input: [197, 130, 1] 
 * Output: true 
 * Explanation: [197, 130, 1] represents the octet sequence: 11000101 10000010
 * 00000001. It is a valid utf-8 encoding for a 2-bytes character followed by a 1-byte character. 
 * 
 * Example2
 * Input: [235, 140, 4] 
 * Output: false 
 * Explanation: [235, 140, 4] represented the octet sequence: 11101011 10001100
 * 00000100. The first 3 bits are all one's and the 4th bit is 0 means it is a 3-bytes character. The next byte is a
 * continuation byte which starts with 10 and that's correct. But the second continuation byte does not start with 10,
 * so it is invalid.
 * 
 * 
 * Thoughts:
 *   The rule is:
 *   
 * 
 */
public class UTF8Validation {
    /**
     * @param data: an array of integers
     * @return: whether it is a valid utf-8 encoding
     */
    public boolean validUtf8(int[] data) {
        if (data == null || data.length == 0) {
            return false;
        }

        int m = 0;
        int curr;
        
        for (int i = 0, n = data.length; i < n; i++) {
            curr = data[i];

            if (m == 0) {
                for (int x = 7; ((curr >> x) & 1) == 1; x--) {
                    m++;
                }

                if (m == 1 || m > 4) {
                    return false;
                } else if (m > 1) {
                    m--;
                }

            } else if ((curr >> 6) == 0b10) {
                m--;
            } else {
                return false;
            }
        }

        return true;
    }
    
    public static void main(String[] args){
        int[][][] inputs = {
            {
                {197, 130, 1}, 
                {1} //true
            },
            {
                {235, 140, 4},  
                {0} //false
            }
        };
        
        UTF8Validation sv = new UTF8Validation();
        
        for(int[][] input : inputs){
            System.out.println( Arrays.toString(input[0]) );
            Assert.assertEquals(input[1][0], sv.validUtf8(input[0]) ? 1 : 0);
        }
    } 
}
