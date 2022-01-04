package array.swapReverseRotate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * _https://www.lintcode.com/problem/1282
 * Write a function that takes a string as input and reverse only the vowels of a string.

    Example 1:
    Given s = "hello", return "holle".
    
    Example 2:
    Given s = "leetcode", return "leotcede".
 *
 */

public class ReverseVowels {
    
    /**
     * @param s: a string
     * @return: reverse only the vowels of a string
     */
    Set<Character> vowels = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));

    public String reverseVowels(String s) {
        if(s == null){
            return s;
        }

        char[] arr = s.toCharArray();
        char c;
        for(int l = 0, r = arr.length - 1; l < r; ){
            while(l < r && !vowels.contains(arr[l])){
                l++;
            }
            while(l < r && !vowels.contains(arr[r])){
                r--;
            }

            if(l < r){
                //swap
                c = arr[r];
                arr[r] = arr[l];
                arr[l] = c;
                
                l++;
                r--;
            }
        }

        return String.valueOf(arr);
    }


}
