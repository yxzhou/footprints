/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp.twosequence;

/**
 *
 * Given two strings S and T, determine if they are both one edit distance apart.
 * 
 * One ediit distance means doing one of these operation:
 *   insert one character in any position of S
 *   delete one character in S
 *   change one character in S to other character
 * 
 * Example 1:
 * Input: s = "aDb", t = "adb" 
 * Output: true
 * 
 * Example 2:
 * Input: s = "ab", t = "ab" 
 * Output: false
 * Explanation: =t ,so they aren't one edit distance apart
 * 
 */
public class EditDistanceOnce {
  /**
   * time O(n) and space O(1)
   */
  public boolean isOneEditDistance(String s, String t) {
      if(null == s || null == t){
          return false;
      }
      
      int diff = s.length() - t.length();
      
      if(0 == diff){ // same length, check if it can be one replace 
          return isOneReplace(s, t);
      }else if(1 == diff){ // 
          return isOneAdd(s, t);
      }else if(-1 == diff){
          return isOneAdd(t, s);
      }
      
      return false;
  }
  
  private boolean isOneReplace(String s, String t){
      boolean once = false; // it's 0 at beginning.
      
      for(int i = 0; i < s.length(); i++){
          if(s.charAt(i) != t.charAt(i)){
              if(once){ // if it has 1 now, return false
                  return false;
              }
              
              once = true; //
          }
      }

      return once; // if s.equals(t), once will be false here.
  }

  
  private boolean isOneAdd(String longer, String shorter){
      boolean once = false;
      for(int i = 0, j = 0; i < shorter.length(); i++, j++){
          if(longer.charAt(i) != shorter.charAt(i)){
              if(once){
                  return false;
              }

              once = true;
              i--;
          }
      }
      
      return once;
  }
}
