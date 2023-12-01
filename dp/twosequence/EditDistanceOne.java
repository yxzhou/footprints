/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp.twosequence;

import java.util.Arrays;
import junit.framework.Assert;

/**
 * __https://www.lintcode.com/problem/640
 *
 * Given two strings S and T, determine if they are both one edit distance apart.
 * 
 * One ediit distance means doing one of these operation:
 *   insert one character in any position of S
 *   delete one character in S
 *   change one character in S to other character
 * 
 * Example 1:
 * Input: s = "aDb", t = "adb" 
 * Output: true
 * 
 * Example 2:
 * Input: s = "ab", t = "ab" 
 * Output: false
 * Explanation: =t ,so they aren't one edit distance apart
 * 
 */
public class EditDistanceOne {

    /**
     * one-pass
     * 
     * time complexity O(n) and space complexity O(1)
     * 
     * @param s
     * @param t
     * @return true if they are both one edit distance apart, else false
     */
    public boolean isOneEditDistance(String s, String t) {
        if (null == s || null == t) {
            return false;
        }

        int diff = s.length() - t.length();

        if (0 == diff) { // same length, check if it can be one replace 
            return isOneReplace(s, t);
        } else if (1 == diff) { // 
            return isOneAdd(s, t);
        } else if (-1 == diff) {
            return isOneAdd(t, s);
        }//else diff > 1 || diff < -1

        return false;
    }

    private boolean isOneReplace(String s, String t) {
        int count = 0; 

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != t.charAt(i)) {
                if (count > 1) { // if it has 1 now, return false
                    return false;
                }

                count++; //
            }
        }

        return count == 1; // if s.equals(t), return false.
    }

    private boolean isOneAdd(String longer, String shorter) {
        int offset = 0;
        for (int i = 0; i < shorter.length(); i++) {
            if (longer.charAt(i + offset) != shorter.charAt(i)) {
                if (offset > 1) {
                    return false;
                }

                offset++;
                i--;
            }
        }

        return true; //offset < 2
    }
    
    
    /**
     * two pass
     * time complexity O(n) and space complexity O(1)
     * 
     * @param s
     * @param t
     * @return true if they are both one edit distance apart, else false
     */
    public boolean isOneEditDistance_TwoPass(String s, String t) {
        if (null == s || null == t) {
            return false;
        }

        int diff = s.length() - t.length();
        
        if(diff > 1 || diff < -1){
            return false;
        }

        int min = diff <= 0? s.length() : t.length();
        
        char[] arrS = s.toCharArray();
        char[] arrT = t.toCharArray();
        for(int i = 0; i < min; i++ ){
            if( arrS[i] != arrT[i]){
                if(diff == 1){
                    return Arrays.equals(arrS, i + 1, arrS.length, arrT, i, arrT.length);
                }else if(diff == -1){
                    return Arrays.equals(arrS, i, arrS.length, arrT, i + 1, arrT.length);
                }else{ // diff == 0
                    return Arrays.equals(arrS, i + 1, arrS.length, arrT, i + 1, arrT.length);
                }
            }
        }

        return diff == 1 || diff == -1; // if s.equals(t), return false.
    }
    
    /**
     * one-pass
     * 
     * time complexity O(n) and space complexity O(1)
     * 
     * @param s
     * @param t
     * @return true if they are both one edit distance apart, else false
     */
    public boolean isOneEditDistance_n(String s, String t) {
        if (null == s || null == t) {
            return false;
        }

        int n = s.length();
        int m = t.length();
        int diff = n - m;
        
        if(diff > 1 || diff < -1){
            return false;
        }
        
        for(int i = 0, min = (diff <= 0? n : m) ; i < min; i++ ){
            if( s.charAt(i) != t.charAt(i)){
                if(diff == 0){
                    return isEquals(s, i + 1, n, t, i + 1, m);
                }else if(diff == 1){
                    return isEquals(s, i + 1, n, t, i, m);
                }else{ // diff == -1
                    return isEquals(s, i, n, t, i + 1, m);
                }
            }
        }

        return diff == 1 || diff == -1; // if s.equals(t), diff == 0, return false.
    }
    
    private boolean isEquals(String s, int sStart, int sEnd, String t, int tStart, int tEnd){
        
        assert sEnd - sStart == tEnd - tStart;
        for( int i = sStart, j = tStart; i < sEnd; i++, j++){
            if(s.charAt(i) != t.charAt(j)){
                return false;
            }
        }
        
        return true;
    }
    
    
    
    public static void main(String[] args){
        
        String[][][] inputs = {
            {
                {"aDb", "adb"},
                {"true"}
            },
            {
                {"ab", "ab"},
                {"false"}
            },      
            {
                {"ab", "a"},
                {"true"}
            },      
            {
                {"a", "ab"},
                {"true"}
            },      
            {
                {"ab", "b"},
                {"true"}
            },      
            {
                {"b", "ab"},
                {"true"}
            }
            
        };
        
        EditDistanceOne sv = new EditDistanceOne();
                
        for(String[][] input : inputs){
            System.out.println(String.format("\n s = %s, \tt = %s", input[0][0], input[0][1]));
         
            Assert.assertEquals("isOneEditDistance ", input[1][0].equals("true"), sv.isOneEditDistance(input[0][0], input[0][1]));
            Assert.assertEquals("isOneEditDistance_TwoPass ", input[1][0].equals("true"), sv.isOneEditDistance_TwoPass(input[0][0], input[0][1]));
            
            Assert.assertEquals("isOneEditDistance_n ", input[1][0].equals("true"), sv.isOneEditDistance_n(input[0][0], input[0][1]));
        }
    }
    
}
