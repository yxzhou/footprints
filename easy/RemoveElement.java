package fgafa.easy;
/**
 * 
 * Remove Element
 * Given an array and a value, remove all instances of that value in place and return the new length.
 * 
 * The order of elements can be changed. It doesn't matter what you leave beyond the new length.
 */
public class RemoveElement {

    /*
     * in place, The order of elements can be changed
     * Time O(n) Space O(1)
     */
      public int removeElement(int[] A,
                                int target) {
          if (A == null || A.length == 0) {
              return 0;
          }

          int len = A.length;

          for (int i = 0; i < len;) {
              if (A[i] == target) {
                  len--;
                  A[i] = A[len];
              } else {
                  i++;
              }
          }

          return len;
      }
    
      /*
       * The order of elements can NOT be changed
       * Time O(n) Space O(1)
       */
    public int removeElementII(int[] A,
                               int target) {
        if (A == null || A.length == 0) {
            return 0;
        }

        int index = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] == target) {
                A[index++] = A[i];
            }
        }

        return index;
    }
}
