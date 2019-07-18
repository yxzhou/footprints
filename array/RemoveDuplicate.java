package fgafa.array;

import fgafa.util.Misc;

import java.util.HashSet;
import java.util.Set;

/*
 * Design an algorithm and write code to remove the duplicate characters in a string without using any 
 * additional buffer. NOTE: One or two addition variable are fine. An extra copy of the array is not.
 */

public class RemoveDuplicate {

  /*
   * remove the duplicate characters in a string with using one or two additional variable. 
   * 
   * Time  O(n*n), Space  O(1) 
   */
    public static int removeDuplicates(char[] str) {
        if (str == null || str.length == 0) {
            return 0;
        }

//        if (str.length > Integer.MAX_VALUE) {
//            throw new IllegalArgumentException("Can't support so big array size !");
//        }

        int len = str.length;
        int tail = 1;

        for (int i = 1; i < len; i++) {
            int j = 0;
            for (j = 0; j < tail; j++) {
                if (str[i] == str[j]) {
                    break;
                }
            }

            if (j == tail) {
                str[tail] = str[i];
                tail++;
            }
        }

        return tail;
    }
  
  /*
   * remove the duplicate characters in a string with using a additional buffer, Hashtable. 
   *  Time  O(n), Space  O(n)
   */
    public static int removeDuplicates_hashtable(char[] str) {
        if (str == null || str.length == 0) {
            return 0;
        }

        if (str.length > Integer.MAX_VALUE) {
            throw new IllegalArgumentException( "Can't support so big array size !");
        }

        int len = str.length;
        int tail = 1;
        Set<String> ht = new HashSet<String>();

        ht.add(String.valueOf(str[0]));
        for (int i = 1; i < len; i++) {
            if (!ht.contains(String.valueOf(str[i]))) {
                str[tail] = str[i];
                tail++;

                ht.add(String.valueOf(str[i]));
            }
        }

        return tail;
    }  
  
  /**
   * Remove Duplicates from Sorted Array
   * Given a sorted array, remove the duplicates in place such that each element appear only once and return the new length.
   * 
   * Do not allocate extra space for another array, you must do this in place with constant memory.
   * 
   * For example,
   * Given input array A = [1,1,2],
   * Your function should return length = 2, and A is now [1,2].
   */
  /* Time O(n), Space O(1) */
    public int removeDuplicatesInSortedArray(int[] nums) {
        if (nums == null) {
            return 0;
        }

        int length = nums.length;
        if (length < 2) {
            return length;
        }

        int i = 0;
        for (int j = 1; j < length; j++) {
            if (nums[i] != nums[j]) {
                i++;
                nums[i] = nums[j];
            }
        }

        return i + 1;
    }
  
  /**
   * Follow up for "Remove Duplicates":
   * What if duplicates are allowed at most twice?
   * 
     For example,
   * Given sorted array A = [1,1,1,2,2,3],
   * 
   * Your function should return length = 5, and A is now [1,1,2,2,3].
   * 
   */
    /* Time O(n), Space O(1) */
    public int removeDuplicatesInSortedArrayII(int[] nums) {
        if (nums == null) {
            return 0;
        }

        int length = nums.length;
        if (length < 2) {
            return length;
        }

        int i = 2;
        for (int j = 2; j < length; j++) {
            if (nums[i - 2] != nums[j]) {
                nums[i] = nums[j];
                i++;
            }
        }

        return i;
    }
  
  
  public static void main(String[] args) {

    System.out.println("------------Start-------------- " );
    
    String[] str = {null,"","a", "aa","aaa", "abc","abaac","abaaabb", "ababab"}; 
    int newlen = 0;
    
    for(String s: str){
      System.out.println("Remove duplicate from " + s );
      char[] chars; 
      if(s == null){
        chars = null;
      }else {
        chars = s.toCharArray();
      }
      
      newlen = removeDuplicates(chars);
      System.out.println("\t Result: " + Misc.array2String(chars, newlen) );
      
    }
    
    System.out.println("------------method 2-------------- " );
    
    for(String s: str){
      System.out.println("Remove duplicate from " + s );
      char[] chars; 
      if(s == null)
        chars = null;
      else
        chars = s.toCharArray();
      
      newlen = removeDuplicates_hashtable(chars);
      System.out.println("\t Result: " + Misc.array2String(chars, newlen) );
      
    }
    
    System.out.println("------------End-------------- " );
  }
  
}
