package fgafa.array.anagram;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given a word W and a string S, find all starting indices in S which are anagrams of W.
 * For example, given that W is "ab", and S is "abxaba", return 0, 3, and 4.
 *
 * Tags: Google
 */

public class AnagramSlideWindow {

    public List<Integer> slideWindowIndexes(String s, String target){
        List<Integer> indexes = new ArrayList<>();

        if(null == s || null == target || target.length() > s.length() ){
            return indexes;
        }

        Map<Character, Integer> toFinds = new HashMap<>();
        for(char c : target.toCharArray()){
            toFinds.put(c, ( toFinds.containsKey(c) ? toFinds.get(c) : 0 ) + 1);
        }

        int count = 0;
        Map<Character, Integer> found = new HashMap<>();
        for(int right = 0, left = 0; right < s.length(); right++){
            char rightChar = s.charAt(right);

            if(toFinds.containsKey(rightChar)){
                found.put(rightChar, (found.containsKey(rightChar) ? found.get(rightChar) : 0 ) + 1);

                if(found.get(rightChar) == toFinds.get(rightChar)){
                    count++;
                }else if(found.get(rightChar) > toFinds.get(rightChar)){
                    while(left < right && found.get(rightChar) > toFinds.get(rightChar)){
                        char leftChar = s.charAt(left);
                        if(found.get(leftChar) == toFinds.get(leftChar)){
                            count--;
                        }

                        found.put(leftChar,  found.get(leftChar) - 1);
                        left++;
                    }
                }

                if(count == toFinds.size() && right - left + 1 == target.length()){
                    indexes.add(left);
                }

            }else{
                count = 0;
                left = right + 1;
                found = new HashMap<>();
            }
        }

        return indexes;
    }

    public List<Integer> findAnagrams_x(String s, String p) {
        List<Integer> list = new ArrayList<>();
        if (s == null || s.length() == 0 || p == null || p.length() == 0) return list;
        int[] hash = new int[256]; //character hash
        //record each character in p to hash
        for (char c : p.toCharArray()) {
            hash[c]++;
        }
        //two points, initialize count to p's length
        int left = 0, right = 0, count = p.length();
        while (right < s.length()) {
            //move right everytime, if the character exists in p's hash, decrease the count
            //current hash value >= 1 means the character is existing in p
            if (hash[s.charAt(right++)]-- >= 1) count--;

            //when the count is down to 0, means we found the right anagram
            //then add window's left to result list
            if (count == 0) list.add(left);

            //if we find the window's size equals to p, then we have to move left (narrow the window) to find the new match window
            //++ to reset the hash because we kicked out the left
            //only increase the count if the character is in p
            //the count >= 0 indicate it was original in the hash, cuz it won't go below 0
            if (right - left == p.length() && hash[s.charAt(left++)]++ >= 0) count++;
        }
        return list;
    }

    public List<Integer> findAnagrams_x1(String s, String p) {
        List<Integer> list = new ArrayList<>();

        if (s == null || s.length() == 0 || p == null || p.length() == 0) {
            return list;
        }

        int[] hash = new int[256]; //character hash
        for (char c : p.toCharArray()) {
            hash[c]++;
        }

        //two points, initialize count to p's length
        int left = 0;
        int right = 0;
        int count = p.length();
        while (right < s.length()) {
            //move right everytime, if the character exists in p's hash, decrease the count
            //current hash value >= 1 means the character is existing in p
            if (hash[s.charAt(right++)]-- >= 1) count--;

            //when the count is down to 0, means we found the right anagram
            //then add window's left to result list
            if (count == 0) list.add(left);

            //if we find the window's size equals to p, then we have to move left (narrow the window) to find the new match window
            //++ to reset the hash because we kicked out the left
            //only increase the count if the character is in p
            //the count >= 0 indicate it was original in the hash, cuz it won't go below 0
            if (right - left == p.length() && hash[s.charAt(left++)]++ >= 0) count++;
        }
        return list;
    }


    public List<Integer> findAnagrams_n(String s, String p) {
        List<Integer> result = new ArrayList<>();

        if(null == s || null == p || s.length() < p.length()){
            return result;
        }

        int[] need = new int[26];
        for(char c : p.toCharArray()){
            need[c - 'a']++;
        }

        int count = 0;
        for(int i = 0; i < need.length; i++){
            if(need[i] > 0){
                count++;
            }
        }

        char[] chars = s.toCharArray();
        int[] found = new int[26];
        for(int l = 0, r = 0, k = 0, w = p.length(); r < s.length(); l++){
            for(int end = l + w ; r < end ; r++){
                k = chars[r] - 'a';
                if(need[k] > 0){
                    found[k]++;

                    if(need[k] == found[k]){
                        count--;
                    }
                }
            }

            if(count == 0){
                result.add(l);
            }

            k = chars[l] - 'a';
            if(need[k] > 0){
                found[k]--;

                if(need[k] > found[k]){
                    count++;
                }
            }
        }

        return result;
    }

    @Test public void test(){

        String[][] input = {
            {"abxaba", "ab"},
            {"cbaebabacdefcba","abc"}, {"abab", "ab"}
        };

        int[][] expects = {{ 0, 3, 4}, {0, 6, 12}, {0, 1, 2}};

        List<Integer> expect;

        for(int i = 0; i < input.length; i++) {
            expect = Arrays.stream(expects[i])        // IntStream
                    .boxed()        // Stream<Integer>
                    .collect(Collectors.toList());

            //Misc.printList(slideWindowIndexes("abxaba", "ab"));

            //Assert.assertEquals(expect, slideWindowIndexes("abxaba", "ab"));
            Assert.assertEquals(expect, findAnagrams_n(input[i][0], input[i][1]));
        }

    }
}
