/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.anagram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.Misc;

/**
 * Give a String list, group them by isomorphic
 *
 *
 * @author yuanxi
 */
public class IsomorphicGroup {

    public List<List<String>> isIsomorphic(String[] ss) {
        List<List<String>> result = new ArrayList<>();

        if (ss == null || ss.length == 0) {
            return result;
        }

        Map<String, List<String>> map = new HashMap<>();
        for (String s : ss) {
            String code = encode(s);

            map.putIfAbsent(code, new ArrayList<>());
            map.get(code).add(s);
        }

        return new ArrayList<>(map.values());
    }

    private String encode(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        int id = 0;
        Map<Character, Integer> map = new HashMap<>();
        StringBuilder result = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (!map.containsKey(c)) {
                map.put(c, id);
                id++;
            }

            result.append(map.get(c));
            result.append(',');
        }

        return result.toString();
    }
    
    public static void main(String[] args) {
        IsomorphicGroup sv = new IsomorphicGroup();

        String[] ss = {"egg", "foo", "paper", "ab", "ab"};
        String[] tt = {"add", "bar", "title", "aa", "ca"};

        String[] sstt = new String[ss.length + tt.length];
        System.arraycopy(ss, 0, sstt, 0, ss.length);
        System.arraycopy(tt, 0, sstt, ss.length, tt.length);

        Misc.printListList(sv.isIsomorphic(sstt));

    }
}
