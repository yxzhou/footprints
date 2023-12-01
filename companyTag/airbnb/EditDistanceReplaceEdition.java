/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import org.junit.Assert;

/**
 * _https://www.lintcode.com/problem/1411
 *
 * Given two strings s1 and s2, find the least number of operations and make the two strings equal. In this problem, an
 * operation is defined as replace one kind of character to another character indefinitely.
 *
 * Two strings with same length consisting only of lowercase English letters.
 *
 * Example 1:
 * Input: s1 = "abb", s2 = "dad" 
 * Output: 2 
 * Explantion: Then first letters will coincide when we will replace letter 'a' with 'd'. 
 * Second letters will coincide when we will replace 'b' with 'a'. 
 * Third letters will coincide when we will at first replace 'b' with 'a' and then 'a' with 'd'.
 * 
 * Thoughts:
 *    How about s1 = "abd", s2 = "dab"? If it's as same as example #1, it means a->d equals to a<->d.  
 * 
 *   s1. Union Find
 * 
 * 
 */
public class EditDistanceReplaceEdition {
    
    /**
     * 
     * @param s1
     * @param s2
     * @return the least number of operations and make the two strings equal
     */
    public int editDistance(String s1, String s2) {
        int[] parents = new int[26];
        for(int i = parents.length - 1; i >= 0; i-- ){
            parents[i] = i;
        }
        
        int count = 0;
        int c1;
        int c2;
        for(int i = 0, n = s1.length(); i < n; i++){
            c1 = s1.charAt(i) - 'a';
            c2 = s2.charAt(i) - 'a';
                    
            if(find(c1, parents) != find(c2, parents)){
                count++;
                
                union(c1, c2, parents);
            }
        }
        
        return count;
    }
    
    private int find(int c, int[] parents){
        while(c != parents[c]){
            parents[c] = parents[parents[c]];
            c = parents[c];
        }
        
        return c;
    }
    
    private void union(int c1, int c2, int[] parents){
        int p1 = find(c1, parents);
        int p2 = find(c2, parents);
        
        parents[p1] = p2;
    }
    
    public static void main(String[] args){
        String[][] inputs = {
            {
                "abb",
                "dad",
                "2"
            },
            {
                "abd",
                "dab",
                "2"
            }
        };
        
        EditDistanceReplaceEdition sv = new EditDistanceReplaceEdition();
        
        for(String[] input : inputs){
            System.out.println(String.format("\ns1=%s, s2=%s", input[0], input[1]));
            
            Assert.assertEquals("", Integer.parseInt(input[2]), sv.editDistance(input[0], input[1]));
        }
        
    }
    
}
