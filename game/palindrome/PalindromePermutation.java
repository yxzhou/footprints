package game.palindrome;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Misc;

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
      -- If the length is odd. Only one character could appear odd times. 
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
    
    /**
     * @param s: the given string
     * @return if a permutation of the string could form a palindrome
     */
    public boolean canPermutePalindrome_n(String s) {
        int count = 0;
        boolean[] flag = new boolean[256]; //default all are false
        int i; 
        for(char c : s.toCharArray()){
            i = (int)c;
            flag[i] = !flag[i];

            if(flag[i]){
                count++;
            }else{
                count--;
            }
        }

        return count < 2;
    }
    public boolean canPermutePalindrome_n2(String s) {
        int count = 0;
        
        BitSet bits = new BitSet();
        int i; 
        for(char c : s.toCharArray()){
            i = (int)c;
            bits.flip(i);

            if(bits.get(i)){
                count++;
            }else{
                count--;
            }
        }

        return count < 2;
    }
    public boolean canPermutePalindrome_nx(String s) {
        
        BitSet bits = new BitSet();
        for(char c : s.toCharArray()){
            bits.flip(c);
        }

        return bits.cardinality() < 2;
    }
    
    public List<String> generatePalindromes_n(String s) {
        List<String> result = new ArrayList<>();
        
        //check input
        if(null == s || s.length() < 1){
            return result;
        }
        
        //count characters
        Map<Character, Integer> counts = new HashMap<>();
        for(char c : s.toCharArray()){
            if(counts.containsKey(c)){
                counts.put(c, counts.get(c) + 1);
            }else{
                counts.put(c, 1);
            }
        }
       
        Character middle = null;
        List<Character> list = new ArrayList<>(s.length() / 2);
        for(Character key : counts.keySet()){
            if(( counts.get(key) & 1 ) == 1){
                if(middle == null){
                    middle = key;
                }else{
                    return result;
                }
            }
            
            for(int i = 0; i < counts.get(key) / 2; i++){
                list.add(key);
            }
        }
        
        dfs(list, 0, middle, result);

        return result;
    }
    
    
    private void dfs(List<Character> list, int i, Character middle, List<String> result){
        
        if(i == list.size()){
            result.add(build(list, middle));
            return;
        }
        
        for(int j = i; j < list.size(); j++){
            if(j > i && list.get(j) == list.get(j - 1)){
                continue;
            }
            
            swap(list, i, j);
            
            dfs(list, i+1, middle, result);
            
            swap(list, i, j);
        }
    }
    
    private String build(List<Character> list, Character middle){
        int size = list.size() * 2;
        if(null != middle){
            size++;
        }
        char[] result = new char[size];
        
        for(int i = 0; i < list.size(); i++){
            result[i] = list.get(i);
            result[--size] = list.get(i);            
        }
        
        if(null != middle){
            result[list.size()] = middle;
        }
        
        return String.valueOf(result);
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
        
//        boolean isOdd = (s.length() & 1) == 1;
//        if( odds.size() != (isOdd? 1 : 0)){
//            return result;
//        }
        if(odds.size() > 1){
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
        BitSet bits = new BitSet();
        bits.flip(3);
        bits.flip(3);
        bits.flip(2);
        System.out.println(bits.cardinality());
        
        PalindromePermutation sv = new PalindromePermutation();
        
        String[] input = {"a", "aa", "aab", "abc", "aabb", "aaab", "abca", "aabbc", "carerac"};
        
        for(String s : input){
            System.out.println(String.format("Input: %s  -- canPermutePalindrome : %b", s, sv.canPermutePalindrome(s)));
            Misc.printArrayList(sv.generatePalindromes(s));
        }

    }
}
