package stringmatching;

import junit.framework.Assert;

/**
 * 
 * refer to java.lang.String
 *
 */
public class String2 {

    /*
     * Write a function to find the longest common prefix string amongst an array of strings.
     */
    public String longestCommonPrefix(String[] strs) {
        if (null == strs || 0 == strs.length) {
            return "";
        }

        StringBuilder ret = new StringBuilder();
        try {
            for (int i = 0; i < strs[0].length(); i++) {
                for (int j = 1; j < strs.length; j++) {
                    if (strs[0].charAt(i) != strs[j].charAt(i)) {
                        return ret.toString();
                    }
                }
                ret.append(strs[0].charAt(i));
            }
        }
        catch (IndexOutOfBoundsException e) {
            // ignore
        }

        return ret.toString();
    }

    public String longestCommonPrefix_2(String[] strs) {
        if (null == strs || 0 == strs.length) {
            return "";
        }

        StringBuilder ret = new StringBuilder();
        char c;
        for (int i = 0; i < strs[0].length(); i++) {
            c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || c != strs[j].charAt(i)) {
                    return ret.toString();
                }
            }
            ret.append(strs[0].charAt(i));
        }

        return ret.toString();
    }


    /**
     * Returns the index within this string of the first occurrence of the specified substring
     *
     * @param source the characters being searched.
     * @param sourceOffset offset of the source string.
     * @param sourceCount count of the source string.
     * @param target the characters being searched for.
     * @param targetOffset offset of the target string.
     * @param targetCount count of the target string.
     * @param fromIndex the index to begin searching from.
     */
    private int indexOf(char[] source, int sourceOffset, int sourceCount,
            char[] target, int targetOffset, int targetCount, int fromIndex) {
        if (fromIndex >= sourceCount) {
            return (targetCount == 0 ? sourceCount : -1);
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (targetCount == 0) {
            return fromIndex;
        }

        char first = target[targetOffset];
        int max = sourceOffset + (sourceCount - targetCount);

        for (int i = sourceOffset + fromIndex; i <= max; i++) {
            /* Look for first character. */
            if (source[i] != first) {
                while (++i <= max && source[i] != first);
            }

            /* Found first character, now look at the rest of v2 */
            if (i <= max) {
                int j = i + 1;
                int end = j + targetCount - 1;
                for (int k = targetOffset + 1; j < end && source[j] == target[k]; j++, k++);

                if (j == end) {
                    /* Found whole string. */
                    return i - sourceOffset;
                }
            }
        }
        return -1;
    }

    /*Time O(n^2)*/
    public int indexOf(char[] src, char[] target) {
        if (src == null || target == null) {
            return -1;
        }

        int n = src.length;
        int m = target.length;
        if (n < m ) {
            return -1;
        }
        if (m == 0) {
            return 0;
        }

        //main
        int i = 0, j = 0;

        while (i < n - m) {
            while (i < n - m && src[i] != target[0]) {
                i++;
            }

            if (src[i] == target[0]) {
                while (i < n && j < m && src[i + j] == target[j]) {
                    j++;
                }

                if (j == m) {
                    return i;
                }

                j = 0;
            }
            i++;

        }
        return -1;
    }

    /**
     * Returns a index to the first occurrence of target in source, or -1 if target is not part of source.
     *
     * @param source string to be scanned.
     * @param target string containing the sequence of characters to match.
     * @return
     */
    public int strStr(String source, String target) {
        if (null == source || null == target || source.length() < target.length() ) {
            return -1;
        }
//        if(target.isEmpty()){
//            return 0;
//        }

        for (int i = 0, limit = source.length() - target.length(); i <= limit ; i++) {
            if(found(source, i, target)){
                return i;
            }
        }

        return -1;
    }
    
    private boolean found(String source, int i, String target){
        int n = source.length();
        int m = target.length();
        
        int j = 0;
        for(; i < n && j < m; i++, j++){
            if(source.charAt(i) != target.charAt(j)){
                return false;
            }
        }
        
        return j == m;
    }


    public int strStr_n(String haystack, String needle) {
        if (null == haystack || null == needle || haystack.length() < needle.length()) {
            return -1;
        }

        int n = needle.length();
        long target = hash(needle);

        long curr = hash(haystack.substring(0, n));

        for (int i = 0, end = haystack.length() - n; i <= end; i++) {
            if (curr == target && haystack.substring(i, i + n).equals(needle)) {
                return i;
            }

            if (i < end) {
                curr = rehash(curr, n, haystack.charAt(i), haystack.charAt(i + n));
            }
        }

        return -1;
    }

    private long hash(String s) {
        long hash = 0;

        for (char c : s.toCharArray()) {
            hash += (hash << 5) + c;
        }

        return hash;
    }

    private long rehash(long hash, int length, char removeChar, char addChar) {
        long remove = removeChar;
        while (length > 1) {
            remove += (remove << 5);
            length--;
        }

        hash -= remove;
        hash += (hash << 5) + addChar;

        return hash;
    }

    /**
     * Replace all occurrence of the given pattern to ‘X’.For example, given that the pattern=”abc”, replace “abcdeffdfegabcabc” with “XdeffdfegX”.Note that multiple occurrences of abc’s that are contiguous will be replaced with only one ‘X' 
     *
     *
     * @param src
     * @param pattern
     * @param replacement
     * @return 
     */
    public String replaceAll(String src, String pattern, String replacement) {
        if (src == null || pattern == null || replacement == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        char[] s = src.toCharArray();
        char[] p = pattern.toCharArray();
        int sLen = s.length;
        int pLen = p.length;
        int i = 0;
        int j;

        while (i < sLen) {
            j = 0;

            while (i < sLen && s[i] != p[0]) {
                sb.append(s[i]);
                i++;
            }

            if (i < sLen && s[i] == p[0]) {
                while (i < sLen && j < pLen && s[i] == p[j]) {
                    i++;
                    j++;
                }

                if (j == pLen) {
                    sb.append(replacement);
                } else {
                    sb.append(s[i - j]);
                    i = i - j + 1;
                }
            }
        }

        while (i < sLen) {
            sb.append(s[i++]);
        }

        return sb.toString();
    }
    
    /**
     * convert the uppercase letters in the string to lowercase letters, and then return the new string.
     * 
     * From ASCII table, 
     *   A - Z is [65, 90] 
     *   a - z is [97, 122]
     * 
     * check every character c in s, if it's uppercase, c = c + 32; 
     * because [65, 90] is [0b01000001, 0b01011010], 32 is 0b00100000, so c + 32 == c | 32;
     * 
     * Time Complexity O(n), n is the length of s, Space O(1)
     * 
     * @param s
     * @return 
     */
    public String toLowerCase(String s) {
        char[] chars = s.toCharArray();

        for(int i = chars.length - 1; i > -1; i--){
            if(chars[i] >= 'A' && chars[i] <= 'Z'){
                chars[i] |= 32;
            }
        }

        return String.valueOf(chars);
        
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < s.length(); ++i) {
//            char ch = s.charAt(i);
//            if (ch >= 65 && ch <= 90) {
//                ch |= 32;
//            }
//            sb.append(ch);
//        }
//        return sb.toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        String2 sv = new String2();

        String[][] inputs = {
            //{source, target, expect}
            {"", "", "0"},
            {"abc", "", "0"},
            {"abc", "a", "0"},
            {"abc", "ab", "0"},
            {"abc", "b", "1"},
            {"abc", "bc", "1"},
            {"abc", "c", "2"},
            {"abcd", "cd", "2"},
            {"abc", "bcd", "-1"},
            {"mississippi","issip", "4"},
        };
        int r;
        for (String[] input : inputs) {
            System.out.print(String.format("\nInput: source = %s, target = %s, expect = %s", input[0], input[1], input[2]));  
            
            r = sv.indexOf(input[0].toCharArray(), input[1].toCharArray());
            System.out.print(String.format("\n--indexOf(\"%s\",\"%s\") is: %d", input[0], input[1], r ));            
            Assert.assertEquals(Integer.parseInt(input[2]), r);

            r = sv.strStr(input[0], input[1]);
            System.out.print(String.format("\n--strStr(\"%s\",\"%s\") is: %d", input[0], input[1], r )); 
            Assert.assertEquals(Integer.parseInt(input[2]), r);

            r = sv.strStr_n(input[0], input[1]);
            System.out.print(String.format("\n--strStr_n(\"%s\",\"%s\") is: %d", input[0], input[1], r )); 
            Assert.assertEquals(Integer.parseInt(input[2]), r);
        }

        String[] strs = {
            "a, a = X",
            "aa, aa = X",
            "aa, a = X",
            "aa, aaa = aa",
            "abc, abc = X",
            "abcabc, abc = X",
            "abcabcabc, abc = X",
            "abcaabcaabc, abc = XaXaX",
            "abcaaabcaaabca, abc = XaaXaaXa",
            "abcabcabababcabc, abc = XababX",
            "abcabcabababcabcab, abc = XababXab",
            "aabbaabbaaabbbaabb, aabb = XaXbX",
            "aabbaabbaaabbbaabb, aaabb = aabbaabbXbaabb",
            "aabbaabbaaabbbaaabb, aaabb = aabbaabbXbX",
            "aabbaabbaaabbbaaabc, aaabb = aabbaabbXbaaabc",
            "abcdeffdfegabcabc, abc = XdeffdfegX",
            "abcdeffdfegabcabc, ab = XcdeffdfegXcXc",
            "abcdeffdfegabcabc, a = XbcdeffdfegXbcXbc",
            "abcdeffdfegabcab, abc = XdeffdfegXab",
            "abcdeffdfegabcabcab, abc = XdeffdfegXab",
            "abcdeffdfegabcaabcab, abc = XdeffdfegXaXab",
            "abcdeffdfegabcaaaabcab, abc = XdeffdfegXaaaXab",
            "aaaaaa, a = X",
            "aaaaaa, aa = X",
            "aaaaaa, aaaaaa = X",
            "aaaaaa, aaaaaaa = aaaaaa",
            "aabaababaaab, a = XbXbXbXb",
            "aabaababaaa, a = XbXbXbX",
            "aaaab, a = Xb",
            "baaa, a = bX",
            "aabaaabaab, aaa = aabXbaab",
            "aabaaabaab, aa = XbXabXb",
            "aabaaabaa, aa = XbXabX"
        };

        System.out.println("");

        int commaId, equalId;
        String sc, pattern, replacement, result;
        for (String str : strs) {
            commaId = str.indexOf(',');
            equalId = str.indexOf('=');
            sc = str.substring(0, commaId);
            pattern = str.substring(commaId + 2, equalId - 1);
            replacement = str.substring(equalId + 2);
            System.out.println("\n\"" + str + "\", ==>" + "src=" + sc + ", pattern=" + pattern + ", replacement=" + replacement);
            result = sv.replaceAll(sc, pattern, replacement);
            System.out.print("==>" + result);
            System.out.println("   " + result.equals(sc.replaceAll(pattern, replacement)));
        }

        String s1 = "1";
        String s2 = "";

        s2 += s1.charAt(0);

    }
}
