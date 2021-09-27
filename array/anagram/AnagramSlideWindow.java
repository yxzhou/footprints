package array.anagram;

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

    public List<Integer> slideWindowIndexes(String s, String p){
        List<Integer> result = new ArrayList<>();
        if(null == s || null == p || p.length() > s.length() ){
            return result;
        }

        Map<Character, Integer> m = new HashMap<>();
        for(char c : p.toCharArray()){
            m.put(c, m.getOrDefault(c, 0) + 1);
        }

        for(int right = 0, left = 0, count = m.size(); right < s.length(); ){
            char c = s.charAt(right++);

            if(m.containsKey(c)) {
                if (m.get(c) == 1) {
                    count--;
                }

                m.put(c, m.get(c) - 1);
            }

            if(right > p.length()) {
                c = s.charAt(left++);

                if(m.containsKey(c)) {
                    if(m.get(c) == 0) {
                        count++;
                    }

                    m.put(c, m.get(c) + 1);
                }
            }

            if(count == 0) {
                result.add(left);
            }
        }

        return result;
    }


    public List<Integer> findAnagrams_n(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if(null == s || null == p || s.length() < p.length()){
            return result;
        }

        int[] m = new int[256];
        for(char c : p.toCharArray()){
            m[c]++;
        }

        int count = 0;
        for(int i = 0; i < m.length; i++){
            if(m[i] > 0){
                count++;
            }
        }

        int[] found = new int[256];
        for(int l = 0, r = 0, k = 0, w = p.length(); r < s.length(); l++){
            for(int end = l + w ; r < end ; r++){
                k = s.charAt(r);
                if(m[k] > 0){
                    found[k]++;

                    if(m[k] == found[k]){
                        count--;
                    }
                }
            }

            if(count == 0){
                result.add(l);
            }

            k = s.charAt(l);
            if(m[k] > 0){
                found[k]--;

                if(m[k] > found[k]){
                    count++;
                }
            }
        }

        return result;
    }


    public List<Integer> findAnagrams_x(String s, String p) {
        List<Integer> list = new ArrayList<>();
        if (s == null || s.length() == 0 || p == null || p.length() == 0) {
            return list;
        }

        int[] hash = new int[256]; //character hash
        //record each character in p to hash
        for (char c : p.toCharArray()) {
            hash[c]++;
        }

        //two points, initialize count to p's length
        for ( int left = 0, right = 0, w = p.length(), count = w; right < s.length(); ) {
            //move right everytime, if the character exists in p's hash, decrease the count
            //current hash value >= 1 means the character is existing in p
            if (hash[s.charAt(right++)]-- >= 1) {
                count--;
            }

            //when the count is down to 0, means we found the right anagram then add window's left to result list
            if (count == 0) {
                list.add(left);
            }

            //if we find the window's size equals to p, then we have to move left (narrow the window) to find the new match window
            //++ to reset the hash because we kicked out the left
            //only increase the count if the character is in p
            //the count >= 0 indicate it was original in the hash, cuz it won't go below 0
            if (right - left == w && hash[s.charAt(left++)]++ >= 0) {
                count++;
            }
        }
        return list;
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

            Assert.assertEquals(expect, slideWindowIndexes(input[i][0], input[i][1]));
            Assert.assertEquals(expect, findAnagrams_n(input[i][0], input[i][1]));
            Assert.assertEquals(expect, findAnagrams_x(input[i][0], input[i][1]));
        }

    }
}
