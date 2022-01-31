package array.anagram;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 *
 * Given two strings s and t, write a function to determine if t is an anagram of s.
 * 
 * For example,
 *  s = "anagram", t = "nagaram", return true.
 *  s = "rat", t = "car", return false.
 * 
 *  Note:
 *  You may assume the string contains only lowercase alphabets.
 * 
 *  Follow up:
 *  Can you do it with O(n) time, O(1) extra space ?
 *  What if the inputs contain Unicode characters? How would you adapt your solution to such case?
 * 
 *   Solutions: 
 *     #1 string to char[] and sort and compare,  
 *     Time O(nlogn) Space O(n)
 *
 *     #2 count and compare, 
 *         int[26] if the input string contains only lowercase alphabets   
 *         int[256] if it's ASCII code
 *         Hashtable<Character, Integer> if it's Unicode character, char is 2 bytes in Java. 
 *     
 * 
 *     #3 xor and hash code,  --WRONG 
 *        
 *   
 *   Note:  s.toCharArray() takes O(n) space.  it created a new char[]; 
 */

public class Anagram {

    /*
     * check if the two string have identical counts for each unique char with Hashtable / Array
     *
     * @ return boolean,  true means they are anagrams each other.
     */
    public boolean isAnagrams(String s1, String s2) {
        //check input. They are not anagrams if they are null, or size is not same.
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }

        //init
        int[] letters = new int[256];  // ??
        for (int i : letters) {
            letters[i] = 0;
        }

        int uniqueCharsNum = 0;

        //check if the two string have identical counts for each unique char with Hashtable, (or Array)
        //check s1
        for (char c : s1.toCharArray()) {
            if (letters[(int) c] == 0) {
                uniqueCharsNum++;
            }

            letters[(int) c]++;
        }

        //check s2
        for (char c : s2.toCharArray()) {
            //if not found, return false
            if (letters[(int) c] == 0) {
                return false;
            }

            //if found, --
            letters[(int) c]--;

            //if it's 0 after --
            if (letters[(int) c] == 0) {
                uniqueCharsNum--;
            }
        }

        return 0 == uniqueCharsNum;
    }


    public boolean isAnagram_ASCII_n(String s, String t) {
        //check input. They are not anagrams if they are null, or size is not same.
        if(null == s || null == t || s.length() != t.length()){
            return false;
        }

        int[] counts = new int[256]; //default all are 0
        //for(char c : s.toCharArray()){ // s.toCharArray() takes O(n) space 
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            counts[c]++;
        }

        for(int i = 0; i < t.length(); i++){
            char c = t.charAt(i);
            counts[c]--;
        }

        for(int x : counts){
            if( 0 != x){
                return false;
            }
        }

        return true;
    }


    public boolean isAnagram_Unicode_n(String s, String t) {
        //check input. They are not anagrams if they are null, or size is not same.
        if(null == s || null == t || s.length() != t.length()){
            return false;
        }

        Map<Character, Integer> counts = new HashMap<>(s.length());
        //for(char c : s.toCharArray()){ // s.toCharArray() takes O(n) space 
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }

        for(int i = 0; i < t.length(); i++){
            char c = t.charAt(i);
            
            if(!counts.containsKey(c) || 0 == counts.get(c)){
                return false;
            }else{
                counts.put(c, counts.get(c) - 1);
            }
        }

        return true;
    }

    /*
     * Arrays.sort and String.equals
     * @ return boolean,  true means they are anagrams each other.
     */
    public boolean isAnagrams_sort(String s1, String s2) {
        //check input. They are not anagrams if they are null, or size is not same.
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }

        char[] newStr = s1.toCharArray();
        Arrays.sort(newStr);
        s1 = new String(newStr);

        newStr = s2.toCharArray();
        Arrays.sort(newStr);
        s2 = new String(newStr);

        return s1.equals(s2);
    }

    /*
     * WRONG !!!
     * WRONG !!!  for case ("aba", "ccb" )
     *
     * XOR does not work,  eg. isAnagrams2("abab", "bcbc") true, it's wrong
     *
     * check if the two string have identical counts for each unique char without big additional space, such as Hashtable
     *
     * @ input
     * @ return boolean,  true means they are anagrams each other.
     */
    private boolean isAnagrams_xor_wrong(String s1, String s2) {
        //check input. They are not anagrams if they are null, or size is not same.
        if (s1 == null || s2 == null || s1.length() == 0 || s1.length() != s2.length()) {
            return false;
        }

        //init
        int ret = 0;

        //check if the two string have identical counts for each unique char with XOR
        for (char c : s1.toCharArray()) {
            ret = ret ^ (int) c;  //  x xor 0 = x, x xor x = 0
        }
        for (char c : s2.toCharArray()) {
            ret = ret ^ (int) c;
        }

        //return
        return ret == 0;
    }

    /**
     * WRONG !!!
     * WRONG !!!  for case ("ABAB", "tutu") and ("ahah", "nunu")
     * 
     * @param s
     * @param t
     * @return 
     */
    public boolean isAnagrams_xorAndChecksum_wrong(String s, String t) {
        if (s == null || t == null || s.length() == 0 || s.length() != t.length()) {
            return false;
        }
        
        int cor = 0, hash = 0;
        for (char  c : s.toCharArray()) {
            cor ^= c;
            hash += c * c % 26;
        }
         for (char  c : t.toCharArray()) {
            cor ^= c;
            hash -= c * c % 26;
        }
        return cor == 0 && hash == 0;
    }

    public static void main(String[] args) {
        Anagram sv = new Anagram();

        System.out.println("------------ simple try -------------- ");
        char[] chars = new char[5];
        System.out.println(" the default value of char[] is: " + chars[0] + " " + (chars[0] == '\u0000') + " " + (chars[0] == 0) + " " + (chars[0] == 0x0) );
        
        if(true){
            return;
        }
        
        String s = "aa";
        int ret = 0;
        for (char c : s.toCharArray()) {
            ret ^= c;  //  x xor 0 = x, x xor x = 0
        }
        System.out.println("test1 " + sv.isAnagrams_xor_wrong("abab", "bcbc"));

        for(char c = 'A'; c <= 'z'; c++){
            //System.out.println("  c = " + c);
            
            int hash = (int)(c * c) % 26;
            System.out.println(String.format("--char c is: %c \t\t  = %d", c, c * c % 26 ));
        }

        System.out.println("------------Start-------------- " );


        //init
        String[] strs1 = {null, "acb", "abab", "abc", "abcde", "abcdef", "abcbcae", "ABAB", "ahah"};
        String[] strs2 = {null, "bac", "bcbc", "abcd", "bdcea", "abcdge", "abaebcb", "tutu", "nunu"};

        for(int i = 0; i<strs1.length; i++){
            System.out.println("check if string are anagrams: s1=" + strs1[i] + ", s2=" + strs2[i]);
            System.out.println("\t Result -isAnagrams:" + sv.isAnagrams(strs1[i], strs2[i]));

            System.out.println("\t Result -isAnagram_n:" + sv.isAnagram_ASCII_n(strs1[i], strs2[i]));

            System.out.println("\t Result -isAnagrams2:" + sv.isAnagrams_xor_wrong(strs1[i], strs2[i]));

            System.out.println("\t Result -isAnagrams3:" + sv.isAnagrams_sort(strs1[i], strs2[i]));
            
            System.out.println("\t Result -isAnagrams_xorAndChecksum:" + sv.isAnagrams_xorAndChecksum_wrong(strs1[i], strs2[i]));
            
        }

        System.out.println("------------End-------------- " );

    }

}
