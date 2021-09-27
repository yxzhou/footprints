package array.anagram;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 *
 * Given two strings s and t, write a function to determine if t is an anagram of s.

    For example,
    s = "anagram", t = "nagaram", return true.
    s = "rat", t = "car", return false.

    Note:
    You may assume the string contains only lowercase alphabets.

    Follow up:
    What if the inputs contain unicode characters? How would you adapt your solution to such case?


    Solutions:
        #1 string to char[] and sort and compare
        #2 count and compare, int[256] if it's ASCII
           Hashtable<Character, Integer> if it's unicode character.
 */

public class IsAnagram
{

    /*
     * check if the two string have identical counts for each unique char with Hashtable / Array
     *
     * @ return boolean,  true means they are anagrams each other.
     */
    public boolean isAnagrams(String s1, String s2){
        //check input. They are not anagrams if they are null, or size is not same.
        if(s1 == null || s2 == null || s1.length() != s2.length())
            return false;

        //init
        int[] letters = new int[256];  // ??
        for(int i : letters ){
            letters[i] = 0;
        }

        int uniqueCharsNum = 0;

        //check if the two string have identical counts for each unique char with Hashtable, (or Array)
        //check s1
        for(char c : s1.toCharArray()){
            if(letters[(int)c] == 0 )
                uniqueCharsNum ++;

            letters[(int)c] ++;
        }

        //check s2
        for(char c : s2.toCharArray()){
            //if not found, return false
            if(letters[(int)c] == 0 )
                return false;

            //if found, --
            letters[(int)c] -- ;

            //if it's 0 after --
            if(letters[(int)c] == 0)
                uniqueCharsNum --;
        }

        //return
        return 0 == uniqueCharsNum;
    }


    public boolean isAnagram_n(String s, String t) {
        //check input. They are not anagrams if they are null, or size is not same.
        if(null == s || null == t || s.length() != t.length()){
            return false;
        }

        int[] counts = new int[256]; //default all are 0
        for(int c : s.toCharArray()){
            counts[c]++;
        }

        for(int c : t.toCharArray()){
            counts[c]--;
        }

        for(int c : counts){
            if( 0 != c){
                return false;
            }
        }

        return true;
    }


    public boolean isAnagram_n2(String s, String t) {
        //check input. They are not anagrams if they are null, or size is not same.
        if(null == s || null == t || s.length() != t.length()){
            return false;
        }

        Map<Character, Integer> counts = new HashMap<>(s.length());
        for(char c : s.toCharArray()){
            if(counts.containsKey(c)){
                counts.put(c, counts.get(c) + 1);
            }else{
                counts.put(c, 1);
            }
        }

        for(char c : t.toCharArray()){
            if(counts.containsKey(c)){
                if( 0 == counts.get(c)){
                    return false;
                }else{
                    counts.put(c, counts.get(c) - 1);
                }
            }else{
                return false;
            }
        }

        return true;
    }

    /*
     * Arrays.sort and String.equals
     * @ return boolean,  true means they are anagrams each other.
     */
    public boolean isAnagrams3(String s1, String s2){
        //check input. They are not anagrams if they are null, or size is not same.
        if(s1 == null || s2 == null || s1.length() != s2.length())
            return false;

        char[] newStr = s1.toCharArray();
        Arrays.sort(newStr);
        s1 = new String(newStr);

        newStr = s2.toCharArray();
        Arrays.sort(newStr);
        s2 = new String(newStr);

        return s1.equals(s2);
    }

    /*
     * WRONG !!!
     * WRONG !!!
     *
     * XOR does not work,  eg. isAnagrams2("abab", "bcbc") true, it's wrong
     *
     * check if the two string have identical counts for each unique char without big additional space, such as Hashtable
     *
     * @ input
     * @ return boolean,  true means they are anagrams each other.
     */
    private boolean isAnagrams_wrong(String s1, String s2){
        //check input. They are not anagrams if they are null, or size is not same.
        if(s1 == null || s2 == null || s1.length() == 0 || s1.length() != s2.length())
            return false;

        //init
        int ret = 0;


        //check if the two string have identical counts for each unique char with XOR
        for(char c : s1.toCharArray()){
            ret = ret ^ (int) c;  //  x xor 0 = x, x xor x = 0
        }
        for(char c : s2.toCharArray()){
            ret = ret ^ (int) c;
        }

        //return
        return ret == 0;
    }



    @Test public void test() {

        //    String s = "aa";
        //    int ret = 0;
        //    for(char c : s.toCharArray()){
        //      ret = ret ^ (int) c;  //  x xor 0 = x, x xor x = 0
        //   }
        //    System.out.println("test " + isAnagrams2("abab", "bcbc") );


        System.out.println("------------Start-------------- " );

        //init
        String[] strs1 = {null, "acb", "abab", "abc", "abcde", "abcdef", "abcbcae"};
        String[] strs2 = {null, "bac", "bcbc", "abcd", "bdcea", "abcdge", "abaebcb"};

        for(int i = 0; i<strs1.length; i++){
            System.out.println("check if string are anagrams: s1=" + strs1[i] + ", s2=" + strs2[i]);
            System.out.println("\t Result -isAnagrams:" + isAnagrams(strs1[i], strs2[i]));

            System.out.println("\t Result -isAnagram_n:" + isAnagram_n(strs1[i], strs2[i]));

            System.out.println("\t Result -isAnagrams2:" + isAnagrams_wrong(strs1[i], strs2[i]));

            System.out.println("\t Result -isAnagrams3:" + isAnagrams3(strs1[i], strs2[i]));
        }

        System.out.println("------------End-------------- " );

    }

}
