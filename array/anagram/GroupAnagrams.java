package array.anagram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Misc;
import org.junit.Test;

/**
 *
Given an array of strings, group anagrams together.

For example, given: ["eat", "tea", "tan", "ate", "nat", "bat"],
Return:
    [
        ["ate", "eat","tea"],
        ["nat","tan"],
        ["bat"]
    ]

 * Note: All inputs will be in lower-case.
 *
 */

public class GroupAnagrams {

    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();

        if (null == strs || 0 == strs.length) {
            return result;
        }

        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            if (s == null) {
                continue;
            }

            char[] newArray = s.toCharArray();
            Arrays.sort(newArray);
            String key = String.valueOf(newArray);

            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<String>());
            }

            map.get(key).add(s);
        }

//        for (List<String> subList : map.values()) {
//            result.add(subList);
//        }
        result.addAll(map.values());

        return result;
    }

    private String anagramCode(String str){
        char[] codes = str.toCharArray();
        Arrays.sort(codes);
        return String.valueOf(codes);
    }

    @Test public void test() {

        System.out.println("------------Start-------------- ");


        String[] strs = { "tea", "and", "ate", "eat", "dan", "eat", "abc" };

        List<List<String>> result = groupAnagrams(strs);

        Misc.printListList(result);

        System.out.println("------------End-------------- ");

    }

}
