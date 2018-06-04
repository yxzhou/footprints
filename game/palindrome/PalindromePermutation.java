package fgafa.game.palindrome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fgafa.util.Misc;

/**
 * 
 * Q1, Given a string, determine if a permutation of the string could form a palindrome.
    For example,
    "code" -> False, "aab" -> True, "carerac" -> True.
 *
 *
 * Q2, Given a string s, return all the palindromic permutations (without duplicates) of it. 
 * Return an empty list if no palindromic permutation could be form.
    For example:
    Given s = "aabb", return ["abba", "baab"].
    Given s = "abc", return [].
 */

public class PalindromePermutation {

    /*
     * The problem can be easily solved by count the frequency of each character using a hash map. 
     * The only thing need to take special care is consider the length of the string to be even or odd. 
      -- If the length is even. Each character should appear exactly times of 2, e.g. 2, 4, 6, etc..
      -- If the length is odd. One and only one character could appear odd times. 
     */
    public boolean canPermutePalindrome(String s) {
        //check input
        if(null == s || s.length() < 2){
            return true;
        }
        
        Map<Character, Integer> counts = new HashMap<>();
        for(char c : s.toCharArray()){
            if(counts.containsKey(c)){
                counts.put(c, counts.get(c) + 1);
            }else{
                counts.put(c, 1);
            }
        }
        
        int oddNum = 0;
        for(int count : counts.values()){
            if( (count & 1) == 1 ){
                oddNum++;
            }
        }
        
        boolean isOdd = (s.length() & 1) == 1;
        return oddNum == (isOdd ? 1 : 0 );
    }
    
    
    public List<String> generatePalindromes(String s) {
        List<String> result = new ArrayList<>();
        
        //check input
        if(null == s || s.length() < 1){
            return result;
        }
        
        Map<Character, Integer> counts = new HashMap<>();
        for(char c : s.toCharArray()){
            if(counts.containsKey(c)){
                counts.put(c, counts.get(c) + 1);
            }else{
                counts.put(c, 1);
            }
        }
        
        List<Character> odds = new ArrayList<>();
        List<Character> evens = new ArrayList<>();
        int count;
        for(Map.Entry<Character, Integer> entry : counts.entrySet()){
            count = entry.getValue();
            if( ( count & 1) == 1 ){
                odds.add(entry.getKey());
                count--;
            }
            
            for( ; count > 0 ; count -= 2){
                evens.add(entry.getKey());
            }
        }
        
        boolean isOdd = (s.length() & 1) == 1;
        if( odds.size() != (isOdd? 1 : 0)){
            return result;
        }
        
        //
        helper(evens, 0, new StringBuilder(), odds.size() == 0 ? null : odds.get(0), result);
        
        return result;
    }
    
    private void helper(List<Character> chars, int index, StringBuilder path, Character middle, List<String> result){
        if(index == chars.size()){
            StringBuilder tmp = new StringBuilder(path);
            
            if(null != middle){
                tmp.append(middle);
            }
            
            tmp.append(path.reverse());
            result.add(tmp.toString());
            return;
        }
        
        for(int i = index; i < chars.size(); i++){
            if(!containsDuplicate(chars, index, i)){
                swap(chars, index, i);
                path.append(chars.get(index));
                
                helper(chars, index + 1, path, middle, result);
                
                path.deleteCharAt(path.length() - 1);
                swap(chars, index, i);
            }
        }
        
    }
    
    private void swap(List<Character> chars, int i, int j){
        if(i != j){
            Character tmp = chars.get(i);
            chars.set(i, chars.get(j));
            chars.set(j, tmp);           
        }
    }
    
    private boolean containsDuplicate(List<Character> chars, int i, int j){
        for( ; i < j ; i++){
            if(chars.get(i).equals(chars.get(j))){
                return true;
            }
        }
        
        return false;
    }
    
    public static void main(String[] args){
        PalindromePermutation sv = new PalindromePermutation();
        
        String[] input = {"a", "aa", "aab", "abc", "aabb", "aaab", "abca", "aabbc", "carerac"};
        
        for(String s : input){
            System.out.println(String.format("Input: %s  -- canPermutePalindrome : %b", s, sv.canPermutePalindrome(s)));
            Misc.printArrayList(sv.generatePalindromes(s));
        }

    }
}
