package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;

import util.Misc;

/**
 * _https://www.lintcode.com/problem/1310
 * 
 * Given an array A, output another array B such that B[k]=product of all elements in A but A[k]. 
 * 
 * e.g. 
 *  input: {4, 3, 2, 1, 2}
 * output: {12, 16, 24, 48, 24}
 * 
 * Solutions:
 * #1 division. need pay attention to 0 in input, and overflow issue  
 * #2 B[i]= A[0] * ... * A[i-1] * A[i+1] * ... * A[n-1]
 * 
 * 
 */

public class ProductOf {

    /**
     * Time, O(n);  Space O(1)
     * 
     * @param nums: Given an integers array A
     * @return A Long array B and B[i]= A[0] * ... * A[i-1] * A[i+1] * ... * A[n-1]
     */
    public List<Long> productExcludeItself(int[] nums) {                
        if(null == nums){
            return Collections.EMPTY_LIST;
        }
        
        int n = nums.length;
        List<Long> result = new ArrayList<>(n);
                
        long p = 1; //product
        for(int i = 0; i < n; i++){
            result.add(p);
            p *= nums[i];
        }
        
        p = 1;
        for(int i = n - 1; i >= 0; i--){
            result.set(i, result.get(i) * p );
            p *= nums[i];
        }
        
        return result;
    }
    
    public int[] productExceptSelf(int[] nums) {
        if(nums == null){
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n];
        
        if(n == 1){
            return result;
        }
        
        Arrays.fill(result, 1);

        int left = 1;
        int right = 1;
        for(int i = 0, j = n - 1; i < n; i++, j--){
            result[i] *= left;
            result[j] *= right;

            left *= nums[i];
            right *= nums[j];
        }

        return result;
    }
    
    /**
     * @param nums: an array of integers
     * @return the product of all the elements of nums except nums[i].
     */
    public int[] productExceptSelf_2(int[] nums) {
        if(nums == null || nums.length < 1){
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n];
        
        if(n == 1){
            return result;
        }

        result[n - 1] = 1;
        for(int i = n - 1; i > 0; i--){
            result[i - 1] =  result[i] * nums[i];
        }

        int p = 1; //product
        for(int i = 0; i < n; i++){
            result[i] *= p;
            p *= nums[i];
        }

        return result;
    }
    
    //Time, O(n);  Space O(1)
    public int[] productExceptSelf_division(int[] nums) {
        if(nums == null){
            return new int[0];
        }
        
        int n = nums.length;
        int[] result = new int[n];
        
        if(n == 1){
            return result;
        }
        
        int[] zeros = new int[2]; //zero positions
        int index = 0;  
        
        long p = 1; // product of all non-zero
        for(int i = 0; i < n; i++){
            if( 0 == nums[i] ){
                zeros[index++] = i;
                                
                if(2 == index){
                    break;
                }
            }else{
                p *= nums[i];
            }
        }

        if(index == 1){ 
            result[zeros[0]] = (int)p;         
        }else if(index == 0){
            for(int i = 0; i < nums.length; i++){
                result[i] = (int)(p / nums[i]);
            }
        }
        
        return result;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        int[][][] inputs = {
            {null, {}}, 
            {{}, {}},
            {{4}, {0}},
            {{1, 2, 3, 4}, {24, 12, 8, 6}},
            {{4, 3, 2, 1, 2}, {12, 16, 24, 48, 24}},
            {{0, 1, 2, 3, 4}, {24, 0, 0, 0, 0}},
            {{0, 1, 2, 0, 3, 4}, {0, 0, 0, 0, 0, 0}}
        };

        ProductOf sv = new ProductOf();

        for (int[][] input : inputs) {
            System.out.println(String.format("\n %s \n%s", Misc.array2String(input[0]), Misc.array2String(input[1]) ));

            Assert.assertArrayEquals(input[1], sv.productExceptSelf(input[0]) );
            Assert.assertArrayEquals(input[1], sv.productExceptSelf_2(input[0]) );
            Assert.assertArrayEquals(input[1], sv.productExceptSelf_division(input[0]) );

        }

    }
    
}
