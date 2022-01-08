package stringmatching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepeatedSequences {

    /**
     *
     * All DNA is composed of a series of nucleotides abbreviated as A, C, G, and T, for example: "ACGAATTCCG". When
     * studying DNA, it is sometimes useful to identify repeated sequences within the DNA.
     *
     * Write a function to find all the 10-letter-long sequences (substrings) that occur more than once in a DNA
     * molecule.
     *
     * For example,
     * Given s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT",
     * Return: ["AAAAACCCCC", "CCCCCAAAAA"].
     * 
     */

    /* Time O(n) Space O(n),   Memory Limit Exceeded*/
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> ret = new ArrayList<>();
        if (null == s || 11 > s.length()) {
            return ret;
        }

        Map<String, Integer> map = new HashMap<>();
        String currWindow;
        for (int i = 0; i <= s.length() - 10; i++) {
            currWindow = s.substring(i, i + 10);
            if (map.containsKey(currWindow)) {
                map.put(currWindow, map.get(currWindow) + 1);
            } else {
                map.put(currWindow, 1);
            }
        }

        for (String key : map.keySet()) {
            if (map.get(key) > 1) {
                ret.add(key);
            }
        }

        return ret;
    }

    public List<String> findRepeatedDnaSequences_X(String s) {
        List<String> result = new ArrayList<>();
        if (null == s || 11 > s.length()) {
            return result;
        }

        Map<Character, Integer> map = new HashMap<>();
        map.put('A', 0); // 00
        map.put('C', 1); // 01
        map.put('G', 2); // 10
        map.put('T', 3); // 11

        Map<Integer, Integer> sumMap = new HashMap<>();
        // if(s == null || s.length() < 10) return result;
        int hashValue = 0;
        for (int i = 0; i < s.length(); i++) {
            // 0xFFFFF is 20 bits, maps to 10 letters.
            hashValue = ((hashValue << 2) + map.get(s.charAt(i))) & 0xFFFFF;
            if (i < 9) {
                continue;
            }

            Integer cnt = sumMap.get(hashValue);
            if (cnt != null && cnt == 1) {
                result.add(s.substring(i - 9, i + 1));
            }
            sumMap.put(hashValue, cnt != null ? cnt + 1 : 1);
        }

        return result;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
