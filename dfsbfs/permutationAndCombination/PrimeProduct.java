/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfsbfs.permutationAndCombination;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * 
 * Given a non-repeating prime array arr, and each prime number is used at most once, find all the product without duplicate and sort them from small to large.
 *
 * Notes:
 *  2 <= |arr| <= 9
 *  2 <= arr[i] <= 23

 * 
 * Example 1:
	Input: arr = [2,3,5]
	Output:  [6,10,15,30]
	
	Explanation:
	2*3 = 6
	2*5 = 10
	3*5 = 15
	2*3*5 = 30

 * Example 2:
	Input: arr = [2,3]
	Output:  [6]
	
	Explanation:
	2*3 = 6.

 * 
 */
public class PrimeProduct {

    /**
     * @param arr: The prime array
     * @return: Return the array of all of prime product
     */
    public int[] getPrimeProduct_dfs(int[] arr) {
        List<Integer> list = new LinkedList<>();
        helper(arr, 0, 0, 1, list);

        Collections.sort(list);

        return list.stream().mapToInt(i->i).toArray();
    }

    private void helper(int[] arr, int p, int count, int product, List<Integer> result){
        if(count > 1){
            result.add(product);
        }

        count++;
        for( ; p < arr.length; p++){
            helper(arr, p + 1, count, product * arr[p], result);
        }
    }
    
    public int[] getPrimeProduct_dfs_2(int[] arr) {
        List<Integer> list = new LinkedList<>();
        helper(arr, 0, 0, 1, list);

        Collections.sort(list);

        return list.stream().mapToInt(i->i).toArray();
    }

    private void helper_2(int[] arr, int p, int count, int product, List<Integer> result){
        if(p >= arr.length){
            if(count > 1){
                result.add(product);
            }
            return;
        }

        helper(arr, p + 1, count, product, result);
        helper(arr, p + 1, count + 1, product * arr[p], result);
    }
    
    public int[] getPrimeProduct_iterative(int[] arr) {
        List<Integer> list = new LinkedList<>();

        int count;
        int product;
        int p;

        for(int x = 3, y = 3, all = (1 << arr.length); x < all; x++){
            count = 0;
            product = 1;
            p = 0;

            y = x;
            while(y > 0){
                if( (y & 1) == 1 ){
                    count++;
                    product *= arr[p];
                }

                y >>= 1;
                p++;
            }

            if(count > 1){
                list.add(product);
            }
        }

        Collections.sort(list);

        return list.stream().mapToInt(i->i).toArray();
    }
}
