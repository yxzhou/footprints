package easy;

import java.util.ArrayList;
import java.util.List;

import util.Misc;

/**
 * Given an array A, output another array B such that B[k]=product  of all elements in A but A[k]. 
 * 
 * e.g. 
 *  input: {4, 3, 2, 1, 2}
 * output: {12, 16, 24, 48, 24}
 * 
 * Solutions:
 * #1 division. need pay attention to 0 in input, and overflow issue  
 * #2 B[i]= A[0] * ... * A[i-1] * A[i+1] * ... * A[n-1]
 */

public class ProductOf
{

  /**
   * @param args
   */
  public static void main(String[] args) {
    int[][] input = {null, {}, {4}, {1,2,3,4}, {4, 3, 2, 1, 2}, {0,1,2,3,4}, {0,1,2,0,3,4}};
    ProductOf s = new ProductOf();
    
    for(int i=0; i < input.length; i++){
      int[] arr = input[i];
      System.out.println("\n input:" + Misc.array2String(arr) );
      
      Misc.printList(s.productExcludeItself(arr));
      Misc.printList(s.productExcludeItself_division(arr));
    }
    
  }
  
    /**
     * @param A: Given an integers array A
     * @return: A Long array B and B[i]= A[0] * ... * A[i-1] * A[i+1] * ... * A[n-1]
     */
    //Time, O(n);  Space O(1)
    public List<Long> productExcludeItself(int[] A) {
        List<Long> result = new ArrayList<>();
                
        // check
        if(null == A){
            return result;
        }
        
        long tmp = 1;
        for(int i = 0; i < A.length; i++){
            result.add(tmp);
            tmp *= A[i];
        }
        
        tmp = 1;
        for(int i = A.length - 1; i >= 0; i--){
            result.set(i, result.get(i) * tmp );
            tmp *= A[i];
        }
        
        return result;
    }
    
    
    //Time, O(n);  Space O(1)
    public List<Long> productExcludeItself_division(int[] A) {
        List<Long> result = new ArrayList<>();
                
        // check
        if(null == A){
            return result;
        }
        
        int countOfZero = 0;
        long productOfAll = 1;
        for(int num : A){
            if( 0 == num ){
                countOfZero++;
                
                if(2 == countOfZero){
                    break;
                }
                
            }else{
                productOfAll *= num;
            }
        }

        if(2 == countOfZero){
            for(int i = 0; i < A.length; i++){
                result.add(0L);
            }
        }else if(1 == countOfZero){
            for(int i = 0; i < A.length; i++){
                if(0 == A[i]){
                    result.add(productOfAll);
                }else{
                    result.add(0L);
                }
            }
        }else{
            for(int i = 0; i < A.length; i++){
                result.add(productOfAll / A[i]);
            }
        }
        
        return result;
    }
}
