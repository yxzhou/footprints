/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graph.eulerPathAndCircuit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/1050
 * 
 * There is a box protected by a password. The password is n digits, where each letter can be one of the first k digits
 * 0, 1, ..., k-1.
 *
 * You can keep inputting the password, the password will automatically be matched against the last n digits entered.
 *
 * For example, assuming the password is "345", I can open it when I type "012345", but I enter a total of 6 digits.
 *
 * Please return any string of minimum length that is guaranteed to open the box after the entire string is inputted.
 *
 * Notes:
 *   n will be in the range [1, 4]. 
 *   k will be in the range [1, 10]. 
 *   k^n will be at most 4096.
 *
 * Example 1:
 * Input: n = 1, k = 2 
 * Output: "01" 
 * Note: "10" will be accepted too. Example 2:
 *
 * Input: n = 2, k = 2 
 * Output: "00110" 
 * Note: "01100", "10011", "11001" will be accepted too.
 *
 * Thoughts:
 * To case: n = 2, k = 2 
 *   k=2 means all possible digits are {0, 1}
 *   n=2 means all possible passwords are {00, 01, 10, 11}
 * 
 * To case: n = 3, k = 2 
 *   k=2 means all possible digits are {0, 1}
 *   n=2 means all possible passwords are {000, 001, 011, 100, 101, 111}
 * 
 * It's guarantee there is a such string to include the password. 
 * 
 * m1) traversal all possible passwords. To make sure the minimum length. If the curr password is "abc", the next 
 * password is "bcd". 
 * 
 */
public class CrackingTheSafe {
    /**
     * 
     * @param n: n
     * @param k: k
     * @return: Cracking the Safe
     */
    public String crackSafe(int n, int k) {
        Set<String> visited = new HashSet<>();
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < n; i++){
            result.append('0');
        }
        visited.add(result.toString());

        dfs(k, result.substring(1), visited, (int)Math.pow(k, n), result);

        return result.toString();
    }

    private boolean dfs(int k, String curr, Set<String> visited, int fullSize, StringBuilder result){
        if( visited.size() == fullSize ){
            return true;
        }

        char c;
        String str;
        for(int num = 0; num < k; num++ ){
            c = (char)('0' + num);
            str = curr + c;
            
            if(!visited.contains(str) ){
                visited.add(str);
                result.append(c);

                if(dfs(k, str.substring(1), visited, fullSize, result)){
                    return true;
                }

                result.deleteCharAt(result.length() - 1);
                visited.remove(str);
            }
        }

        return false;
    }
    
    /**
     * 
     * @param n: n
     * @param k: k
     * @return: Cracking the Safe
     */
    public String crackSafe_n(int n, int k) {
        Set<String> visited = new HashSet<>();
        StringBuilder result = new StringBuilder();

        StringBuilder start = new StringBuilder();
        for(int i = 1; i < n; i++){
            start.append('0');
        }

        dfs(k, start.toString(), visited, result);
        result.append(start);

        return result.toString();
    }

    private void dfs(int k, String curr, Set<String> visited, StringBuilder result){

        char c;
        String str;
        for(int num = 0; num < k; num++ ){
            c = (char)('0' + num);
            str = curr + c;
            
            if(!visited.contains(str) ){
                visited.add(str);
                
                dfs(k, str.substring(1), visited, result);

                result.append(c);
            }
        }
    }
    
    public static void main(String[] args){
        
        String[][][] inputs = {
            //{"n", "k", expect}
            {{"1", "2"}, {"01", "10"}},
            {{"2", "2"}, {"00110", "01100"}},
            {{"3", "2"}, {"0001011100", "0011101000"}}, //  
            {{"3", "3"}, {"00010020110120210221112122200", "00222121112201202101102001000"}}, //
            {{"2", "3"}, {"0010211220", "0221120100"}} //
        };
        
        CrackingTheSafe sv = new CrackingTheSafe();
        
        Set<String> expects;
        for(String[][] input : inputs){
            System.out.println(String.format("\nn = %d, k = %d", Integer.parseInt(input[0][0]), Integer.parseInt(input[0][1])));
            
            expects = new HashSet<>(Arrays.asList(input[1]));
            
            Assert.assertTrue( expects.contains(sv.crackSafe(Integer.parseInt(input[0][0]), Integer.parseInt(input[0][1]))));
            Assert.assertTrue( expects.contains(sv.crackSafe_n(Integer.parseInt(input[0][0]), Integer.parseInt(input[0][1]))));
            
            Assert.assertEquals(input[1][1], sv.crackSafe_n(Integer.parseInt(input[0][0]), Integer.parseInt(input[0][1])));

        }

    }
}
