/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easy;

import junit.framework.Assert;

/**
 * 
 * Given a sentence of English, update the first letter of each word to uppercase.
 * 
 * Notes:
 *   The given sentence may not be a grammatical sentence.
 *   The length of the sentence does not exceed 100.
 *   Except for the beginning of the sentence, the rest of the letters are all lowercase
 * 
 * Example1
 * Input: s =  "i want to get an accepted"
 * Output: "I Want To Get An Accepted"
 * 
 * Example2
 * Input: s =  "i jidls    mdijf  i  lsidj  i p l   "
 * Output: "I Jidls    Mdijf  I  Lsidj  I P L   "
 *
 * @author yuanxi
 */
public class CapitalizeFirstLetter {
    
    /**
     * @param s: a string
     * @return a string after capitalizes the first letter
     */
    public String capitalizesFirst(String s) {
        if(s == null || s.isEmpty()){
            return s;
        }

        char[] chars = s.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] -= 32;
        }
        for (int i = 1, n = s.length(); i < n; i++) {
            if (chars[i - 1] == ' ' && chars[i] != ' ') {
                chars[i] -= 32;
            }
        }
        return String.valueOf(chars);
    }
    
    /**
     * @param s: a string
     * @return a string after capitalizes the first letter
     */
    public String capitalizesFirst_2(String s) {
        if(s == null){
            return s;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        sb.append(s);

        for (int i = 0, n = sb.length(); i < n; i++) {
            if (sb.charAt(i) != ' ' && sb.charAt(i - 1) == ' ') {
                sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
            }
        }

        return sb.deleteCharAt(0).toString();
    }
    
    public static void main(String[] args) {
        
        CapitalizeFirstLetter sv = new CapitalizeFirstLetter();
        
        String[][] inputs = {
            //{s, expect}
            {"i want to get an accepted", "I Want To Get An Accepted"},
            {"i jidls    mdijf  i  lsidj  i p l   ", "I Jidls    Mdijf  I  Lsidj  I P L   "},
            {null, null},
            {"", ""},
            {" ", " "},
            {"abc", "Abc"},
            {"abc ", "Abc "},
            {" abc", " Abc"},
        };
        
        for(String[] input : inputs){
            System.out.println(String.format("\ncall capitalizesFirst, s = %s", input[0]));
            
            Assert.assertEquals(input[1], sv.capitalizesFirst(input[0]));
            Assert.assertEquals(input[1], sv.capitalizesFirst_2(input[0]));
        }
        
    }
}
