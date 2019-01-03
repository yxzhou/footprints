package fgafa.array.anagram;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fgafa.util.Misc;
import org.junit.Assert;
import org.junit.Test;

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

    @Test public void test(){

        int[] arr = { 0, 3, 4};

        List<Integer> expect = Arrays.stream(arr)		// IntStream
                .boxed()  		// Stream<Integer>
                .collect(Collectors.toList());

        //Misc.printList(slideWindowIndexes("abxaba", "ab"));

        Assert.assertEquals(expect, slideWindowIndexes("abxaba", "ab"));
    }
}
