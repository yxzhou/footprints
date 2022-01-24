/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stringmatching;

import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/811/
 * 
 * In a string composed of 'L', 'R', and 'X' characters, like "RXXLRXRXL", a move consists of either replacing one
 * occurrence of "XL" with "LX", or replacing one occurrence of "RX" with "XR". Given the starting string start and the
 * ending string end, return True if and only if there exists a sequence of moves to transform one string to the other.
 * 
 * 1 <= len(start) = len(end) <= 10000.
 * Both start and end will only consist of characters in {'L', 'R', 'X'}.
 * 
 * Example 1:
 * Input: start = "RXXLRXRXL", end = "XRLXXRRLX", 
 * Output: true
 * Explanation:
 * We can transform start to end following these steps:
 *   RXXLRXRXL ->
 *   XRXLRXRXL ->
 *   XRLXRXRXL ->
 *   XRLXXRRXL ->
 *   XRLXXRRLX
 * 
 * Example 2:
 * Input:start = "X", end = "L"
 * Ouput: false
 * 
 * Thoughts:
 *   Think about the following cases:
 *   case #1
 *      "XXXL" 
 *      "LXXX"
 *
 *   case #2
 *      "RXXX" 
 *      "XXXR"
 *
 *   case #3
 *      "LXR" 
 *      "XLR" 
 * 
 *   case #4
 *      "XXLRXX"    --1st
 *      "LXXXXR"    --2nd
 * 
 *   Because the rule is: "XL" to "LX",  "RX" to "XR".  
 *   So like the above case #4, the LR related position cannot be changed, it means
 *     found_1: ignore with 'X', the 1st equals to 2nd. 
 *    like the above case #3
 *     found_2: found_1 is not enough, because "LX" cannot be changed to "XL"
 * 
 */
public class LRStringSwapAdjacent {
    
    /**
     * 
     * @param start: the start
     * @param end: the end
     * @return is there exists a sequence of moves to transform one string to the other
     */
    public boolean canTransform(String start, String end) {
        assert start.length() != end.length();

        int n = start.length();

        char[] s = start.toCharArray();
        int leftR = n;
        int leftX = n;
        for(int i = n - 1; i >= 0; i--){
            if(s[i] != end.charAt(i)){
                if( s[i] == 'X' && end.charAt(i) == 'R' ){
                    
                    leftR = Math.min(leftR, i - 1);
                    while(leftR >= 0 && s[leftR] == 'X'){
                        leftR--;
                    }
                    if(leftR == -1 || s[leftR] != 'R'){
                        return false;
                    }
                    
                    s[leftR] = 'X';
                }else if(s[i] == 'L' && end.charAt(i) == 'X' ) {
                    
                    leftX = Math.min(leftX, i - 1);
                    while(leftX >= 0 && s[leftX] == 'L'){
                        leftX--;
                    }
                    if(leftX == -1 || s[leftX] == 'R'){
                        return false;
                    }
                    
                    s[leftX] = 'L';
                }else{
                    return false;
                }
           
            }
            //else s[i] == end.charAt(i) continue 
        }

        return true;
    }

    
    
    /**
     * @param start: the start
     * @param end: the end
     * @return is there exists a sequence of moves to transform one string to the other
     */
    public boolean canTransform_n(String start, String end) {        
        if (!start.replace("X", "").equals(end.replace("X", ""))) {
            return false;
        }

        int l = 0, r = 0;
        for (int i = 0; i < start.length(); i++) {
            if (start.charAt(i) == 'R') {
                r++;
            }
            if (end.charAt(i) == 'L') {
                l++;
            }
            if (start.charAt(i) == 'L') {
                l--;
            }
            if (end.charAt(i) == 'R') {
                r--;
            }
            
            //if ((l < 0 || r != 0) && (l != 0 || r < 0)) {  
            if( l < 0 || r < 0 || (r != 0 && l != 0) ){
                return false;
            }
        }

        return l == 0 && r == 0;
    }
    
    public static void main(String[] args){
        System.out.println("--main--");
        
        String[][] inputs = {
            {"RXXLRXRXL", "XRLXXRRLX", "true"},
            {"XXXL", "LXXX", "true"},
            {"RXXX", "XXXR", "true"},
            {"LXR", "XLR", "false"},
            {"LXR", "LRX", "false"},
            {"LX", "XL", "false"},
            {"XR", "RX", "false"},
            {"XLR", "LXR", "true"},
            {"LRX", "LXR", "true"},
            {"XL", "LX", "true"},
            {"RX", "XR", "true"},
            {"XXLRXX", "LXXXXR", "true"}
        };  
         
        LRStringSwapAdjacent sv = new LRStringSwapAdjacent();
        
        for(String[] input : inputs){
            System.out.println(String.format("\nFrom %s to %s ", input[0], input[1]));
            
            Assert.assertEquals(Boolean.parseBoolean(input[2]), sv.canTransform(input[0], input[1]));
            Assert.assertEquals(Boolean.parseBoolean(input[2]), sv.canTransform_n(input[0], input[1]));
        }
        
    }

}
