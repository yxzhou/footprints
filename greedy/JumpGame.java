package fgafa.greedy;

import fgafa.util.Misc;

/**
 * 
 * Given an array of non-negative integers, you are initially positioned at the first index of the array.<br>
 * Each element in the array represents your maximum jump length at that position.<br>
 * Determine if you are able to reach the last index.<br>
 * For example:<br>
 * A = [2,3,1,1,4], return true.<br>
 * A = [3,2,1,0,4], return false.<br>
 * 
 *
 */

public class JumpGame
{

  /*
   * Time O(n) Space O(1) 
   */
  
  public boolean canJump_x(int[] A) {
      //check
      if(null == A || 0 == A.length){
          return false;
      }
      
      int limit = 0;
      for(int i = 0; i <= limit && limit < A.length; i++){
          limit = Math.max(limit, i + A[i]);
      }
      
      return limit >= A.length - 1;
  }
  
  public boolean canJump_n(int[] A) {
      //check
      if(null == A || 0 == A.length){
          return false;
      }
      
      int i = 0;
      int size =  A.length;
      for( int max = A[i]; i <= max && i < size; i++ ){
          max = Math.max(max, i + A[i]);
      }
      
      return i >= size;
  }


  /**
   * @param args
   */
  public static void main(String[] args) {
    JumpGame sv = new JumpGame();
    int[][] A = {null,{},{0},{0,1}, {1,2}, {2,0}, {2,0,0}, {0,1,1,1}, {2,3,1,1,4},{3,2,1,0,4}};
    boolean[] exp = {false, false, true, false, true, true, true, false, true, false};
    
    for(int i=0; i< A.length; i++){
      System.out.println("\n -"+i+"- Input:"+ Misc.array2String( A[i] ));

      System.out.println("Output:"+sv.canJump_x(A[i]) + "\t" + exp[i]);
      System.out.println("Output:"+sv.canJump_n(A[i]) + "\t" + exp[i]);
    }

  }

}
