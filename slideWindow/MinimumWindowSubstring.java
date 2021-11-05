package slideWindow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


/**
 * similar with string matching.ShortestExcerpt.java
 * 
 * Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).
 * 
 * For example,
 * S = "ADOBECODEBANC"
 * T = "ABC"Rus
 * Minimum window is "BANC".
 * 
 * Note:
 * If there is no such window in S that covers all characters in T, return the empty string "".
 * 
 * If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.
 * 
 * Followup:
 *   In the above example, how about to find the minimum window that includes 'A', 'B' and 'C' in order
 *
 */

public class MinimumWindowSubstring {

    /**
     * 
     * @param source
     * @param target
     * @return 
     */
    public String minWindow(String source , String target) {
        if(source == null || target == null || target.length() == 0 || source.length() < target.length()  ){
            return "";
        }

        boolean[] focus = new boolean[256];
        int[] found = new int[256];
        int status = 0;
        int c;
        for(int i = 0; i < target.length(); i++ ){
            c = target.charAt(i);

            if(!focus[c]){
                focus[c] = true;
                status++;
            }

            found[c]++;
        }

        int n = source.length();
        int min = Integer.MAX_VALUE;
        int minR = 0;
        for(int l = 0, r = 0; r <= n;  ){
            if(status > 0){
                if(r == n){
                    break;
                }

                c = source.charAt(r++);
                if(focus[c]){
                    found[c]--;

                    if(found[c] == 0){
                        status--;
                    }
                }

            }else{ //count == 0,  no possible of count < 0
                if(min > r - l){
                    min = r - l;
                    minR = r;
                }

                c = source.charAt(l++);
                if(focus[c]){
                    if(found[c] == 0){
                        status++;
                    }

                    found[c]++;
                }

            }
        }

        return min == Integer.MAX_VALUE? "" : source.substring(minR - min, minR);
  
    }
    

    public String minWindow_4(String s, String t) {
        if (null == s || null == t || t.isEmpty() || s.length() < t.length()) {
            return "";
        }

        Map<Character, Integer> need = new HashMap<>();
        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        int status = need.size();
        int[] min = new int[]{-1, s.length()}; // the min slide window. m[0], the start point; m[1], the size
        char c;
        for (int l = 0, r = 0; r < s.length(); r++) {
            c = s.charAt(r);
            if (need.containsKey(c)) {
                need.put(c, need.get(c) - 1);

                if (need.get(c) == 0) {
                    status--;
                }
            }

            if (status == 0) {
                while (status == 0) {
                    c = s.charAt(l++);
                    if (need.containsKey(c)) {
                        need.put(c, need.get(c) + 1);

                        if (need.get(c) > 0) {
                            status++;
                        }
                    }
                }

                if (min[1] > r - l) {
                    min[0] = l - 1;
                    min[1] = r - l;
                }
            }
        }

        return min[0] == -1 ? "" : s.substring(min[0], min[0] + min[1] + 2);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String[] s = {"a", "a", "ab", "a", "aa", "bba", "bbaa", "bdab", "ADOBECODEBANC", "ADOBECODEBANCA"};
        String[] t = {"a", "b", "a", "ab", "aa", "ab", "aba", "ab", "ABC", "ABCA"};

        MinimumWindowSubstring sv = new MinimumWindowSubstring();
        for (int i = 0; i < s.length; i++) {

            System.out.println("\nInput:" + s[i] + " " + t[i]);

            System.out.println("minWindow_x: " + sv.minWindow(s[i], t[i]));
            System.out.println("minWindow_x4: " + sv.minWindow_4(s[i], t[i]));
        }

    }

}
