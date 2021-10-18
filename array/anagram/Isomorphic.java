package array.anagram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Misc;



public class Isomorphic {

    /**
     * Given two strings s and t, determine if they are isomorphic.
     *
     * Two strings are isomorphic if the characters in s can be replaced to get t.
     *
     * All occurrences of a character must be replaced with another character while preserving the order of characters.
     * No two characters may map to the same character but a character may map to itself.
     *
     * For example, Given "egg", "add", return true. Given "foo", "bar", return false. Given "paper", "title", return
     * true.
     *
     * Note: You may assume both s and t have the same length.
     */
    public boolean isIsomorphic(String s, String t) {
        //check input
        if (null == s || null == t || s.length() != t.length()) {
            return false;
        }

        Map<Character, Character> mapping1 = new HashMap<>();
        Map<Character, Character> mapping2 = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            if ((mapping1.containsKey(s.charAt(i)) && !mapping1.get(s.charAt(i)).equals(t.charAt(i)))
                    || (mapping2.containsKey(t.charAt(i)) && !mapping2.get(t.charAt(i)).equals(s.charAt(i)))) {
                return false;
            }

            if (!mapping1.containsKey(s.charAt(i))) {
                mapping1.put(s.charAt(i), t.charAt(i));
            }

            if (!mapping2.containsKey(t.charAt(i))) {
                mapping2.put(t.charAt(i), s.charAt(i));
            }
        }

        return true;
    }
    
    public boolean isIsomorphic_n(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }

        // No two characters may map to the same character, a character may map to itself
        char[] mirror1 = new char[256];
        char[] mirror2 = new char[256];
        char sc, tc;
        for (int i = 0; i < s.length(); i++) {
            sc = s.charAt(i);
            tc = t.charAt(i);

            if (mirror1[sc] == '\u0000') {
                mirror1[sc] = tc;
            } else if (mirror1[sc] != tc) {
                return false;
            }//else mirror1[sc] == tc, pass

            if (mirror2[tc] == '\u0000') {
                mirror2[tc] = sc;
            } else if (mirror2[tc] != sc) {
                return false;
            }//else mirror2[tc] == sc, pass
        }

        return true;
    }
    


    public static void main(String[] args) {
        Isomorphic sv = new Isomorphic();

        String[] ss = {"egg", "foo", "paper", "ab", "ab"};
        String[] tt = {"add", "bar", "title", "aa", "ca"};
        boolean[] expects = {true, false, true, false, true};

        for (int i = 0; i < ss.length; i++) {
            System.out.println(ss[i] + "->" + tt[i] + " , " + expects[i] + "-" + sv.isIsomorphic(ss[i], tt[i]));
        }

    }

}
