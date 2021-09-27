package array.swapReverseRotate;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Write a function that takes a string as input and reverse only the vowels of a string.

    Example 1:
    Given s = "hello", return "holle".
    
    Example 2:
    Given s = "leetcode", return "leotcede".
 *
 */

public class ReverseVowels {
    
    Set<Character> vowels = new HashSet<>();

    public ReverseVowels(){
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
    }
    
    
    public String reverseVowels(String s) {
        if(null == s || s.isEmpty()){
            return s;
        }
        
        char[] str = s.toCharArray();
        
        int left = 0;
        int right = 0;
        while(left < right){
            swap(str, left, right);
            
            while(left < right && !isVowel(str[left])){
                left++;
            }
            
            while(left < right && !isVowel(str[right])){
                right--;
            }
            
        }
        
        return String.valueOf(str);
    }
       
    
    private boolean isVowel(char c){
        return this.vowels.contains(Character.toLowerCase(c));
    }
    
    private void swap(char[] str, int left, int right){
        char tmp = str[left];
        str[left] = str[right];
        str[right] = tmp;
    }


}
