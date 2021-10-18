package array.anagram;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.
 * If s is an anagram of p, then s is a permutation of p.
 * Strings consists of lowercase English letters only and the length of both strings s and p will not be larger than 40,000.
 * The order of output does not matter.
 * 
 * Example 1:
 * Input : s =  "cbaebabacd", p = "abc"
 * Output : [0, 6]
 * Explanation : 
 *   The substring with start index = 0 is "cba", which is an anagram of "abc".
 *   The substring with start index = 6 is "bac", which is an anagram of "abc".
 * 
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
        if(null == s || null == p || s.length() < p.length()){
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

    
    public List<Integer> findAnagrams_n2(String s, String p) {
        if(s == null || s.isEmpty() || p == null || p.isEmpty() || s.length() < p.length()){
            return Collections.EMPTY_LIST;
        }

        //int[] counts = new int[26]; //default all are 0
        int[] found = new int[26]; //default all are 0. int[256] is better than int[26], beause it avoid a minus operation. 
        boolean[] visited = new boolean[26]; //default all are false
        int count = 0;

        int m = p.length();
        int j;
        for(int i = 0; i < m; i++){
            j = p.charAt(i) - 'a';
            
            visited[j] = true;
            count -=(found[j] == 0 ? 1 : 0);
            found[j]--;
        }

        List<Integer> result = new ArrayList<>();

        for(int i = 0; i < s.length(); i++){
            j = s.charAt(i) - 'a';
            if(visited[j]){
                found[j]++;
                count +=(found[j] == 0 ? 1 : 0);
            }

            if(i >= m){
                j = s.charAt(i - m) - 'a';
                if(visited[j]){
                    count -=(found[j] == 0 ? 1 : 0);
                    found[j]--;
                }
            }

            if(count == 0){
                result.add(i - m + 1);
            }
        }

        return result;
    }


    public static void main(String[] args){
        AnagramSlideWindow sv = new AnagramSlideWindow();
        
        String[][] input = {
            {"abxaba", "ab"},
            {"cbaebabacdefcba","abc"}, 
            {"abab", "ab"},
            {"abcba", "bc"}
        };

        int[][] expects = {{ 0, 3, 4}, {0, 6, 12}, {0, 1, 2}, {1,2}};

        List<Integer> expect;

        for(int i = 0; i < input.length; i++) {
            expect = Arrays.stream(expects[i])        // IntStream
                    .boxed()        // Stream<Integer>
                    .collect(Collectors.toList());

            //Misc.printList(slideWindowIndexes("abxaba", "ab"));

            Assert.assertEquals(expect, sv.slideWindowIndexes(input[i][0], input[i][1]));
            Assert.assertEquals(expect, sv.findAnagrams_n(input[i][0], input[i][1]));
            Assert.assertEquals(expect, sv.findAnagrams_x(input[i][0], input[i][1]));
            Assert.assertEquals(expect, sv.findAnagrams_n2(input[i][0], input[i][1]));
        }

    }
}
