package easy;

import util.Misc;

import java.util.LinkedList;
import java.util.List;

public class PascalTriangle
{

  /**
   * _https://www.lintcode.com/problem/768/?_from=ladder&fromId=190
   * Yang Hui Triangle, Pascal's triangle
   *
   * Problem #1: Given numRows, generate the first numRows of Pascal's triangle.
   * 
   * For example, given numRows = 5,
   * Return
   * 
   * [
   *      [1],
   *     [1,1],
   *    [1,2,1],
   *   [1,3,3,1],
   *  [1,4,6,4,1]
   * ]
   *
   *
   * Problem #2: Given an index k, return the kth row of the Pascal's triangle.
   *
   * For example, given k = 3,
   * Return [1,3,3,1].
   *
   * Note:
   * Could you optimize your algorithm to use only O(k) extra space?
   *
   */

    /**
     *  Problem #1
     */
  public List<List<Integer>> generate(int n) {
      List<List<Integer>> result = new LinkedList<>();

      if(n < 1){
          return result;
      }

      List<Integer> pre = new LinkedList<>();
      List<Integer> curr;
      int l = 0;
      while(n-- > 0){
          curr = new LinkedList<>();
          l = 0;

          for(int x : pre){
              curr.add(l + x);
              l = x;
          }

          curr.add(1);

          result.add(curr);
          pre = curr;
      }

      return result;
  }

  
  /**
   *  Problem #2
   */
  
  public List<Integer> getRow_x(int rowIndex) {
	  List<Integer> ret = new LinkedList<>();
      if( rowIndex < 0){
    	  return ret;
      }

      ret.add(1);
	  for(int i = 1; i <= rowIndex; i++){
    	  ret.add(1);
    	  for( int j = i - 1; j>0 ; j--){
    		  ret.set(j, ret.get(j) + ret.get(j-1));
    	  }
	  }
	  
	  return ret;
  }
  

  public static void main(String[] args) {
    PascalTriangle sv = new PascalTriangle();
    
    for(int rowIndex = -1; rowIndex < 6; rowIndex ++){
    	System.out.println("\n Input: " + rowIndex);
    	
    	Misc.printListList(sv.generate(rowIndex));
        Misc.printArrayList_Integer(sv.getRow_x(rowIndex));
    }

    
  }

}
