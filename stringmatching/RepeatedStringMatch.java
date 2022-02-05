/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringmatching;

import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/718
 *
 * Given two strings A and B, find the minimum number of times A has to be repeated such that B is a substring of it. If
 * no such solution, return -1.
 *
 * Notes: 
 *   The length of A and B will be between 1 and 10000.
 * 
 * Example 1:
 * Input : A = "a"     B = "b".
 * Output : -1
 * 
 * Example 2:
 * Input : A = "abcd"     B = "cdabcdab".
 * Output :3
 * Explanation : because by repeating A three times (“abcdabcdabcd”), B is a substring of it; and B is not a substring 
 * of A repeated two times ("abcdabcd").
 * 
 */
public class RepeatedStringMatch {
    /**
     * @param A: string A to be repeated
     * @param B: string B
     * @return the minimum number of times A has to be repeated
     */
    public int repeatedStringMatch(String A, String B) {
        if(A == null || B == null || A.isEmpty() || B.isEmpty()){
            return -1;
        }
                
        int n = A.length();
        int m = B.length();

        StringBuilder sb = new StringBuilder(A);

        while( sb.length() < m ){
            sb.append(A);
        }

        for(int k = 0; k < 2; k++){
            if(sb.indexOf(B) != -1 ){
                return sb.length() / n ;
            }

            sb.append(A);
        }

        return -1;
    }
    
    /**
     * @param A: string A to be repeated
     * @param B: string B
     * @return the minimum number of times A has to be repeated
     */
    public int repeatedString(String A, String B) {
        if(A == null || B == null || A.isEmpty() || B.isEmpty()){
            return -1;
        }

        int n = A.length();
        int m = B.length();

        int count = 0;
        int l = B.indexOf(A);

        if(l == -1){
            if( n >= m && A.contains(B) ){
                return 1;
            }

            A += A;
            if( A.contains(B) ){
                return 2;
            }

            return -1;
        }

        //l != -1
        if(l > 0){
            if(A.endsWith(B.substring(0, l))){
                count++;
            }else{
                return -1;
            }
        }

        while( B.startsWith(A, l) ){
            l += n;
            count++;          
        }


        if(l < m){
            if(A.startsWith(B.substring(l, m))){
                count++;
                l = m;
            }else{
                return -1;
            }
        }

        return l >= m? count : -1;
    }
    
    
    public static void main(String[] args){
        
        String[][] inputs = {
            {"","a","-1"},
            {"abcd", "abcd", "1"},
            {"abcd", "abc", "1"},
            {"abcd", "cd", "1"},
            {"abcd", "cdab", "2" },
            {"ac", "cc", "-1" },
            {"abcd", "cdabcdab", "3" },
        };
        
        RepeatedStringMatch sv = new RepeatedStringMatch();
        
        for(String[] input : inputs){
            System.out.println(String.format("\nA: %s, B: %s, expect: %s", input[0], input[1], input[2] ));
            
            Assert.assertEquals(Integer.parseInt(input[2]), sv.repeatedString(input[0], input[1]));
            Assert.assertEquals(Integer.parseInt(input[2]), sv.repeatedStringMatch(input[0], input[1]));
        }
        
    }
    
}
