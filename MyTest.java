package fgafa;

import fgafa.util.Misc;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MyTest {


/**
 * Intersection of two sorted positive arrays.
 *
 * Input:
 *   arr1 = [1, 2, 3]
 *   arr2 = [1, 3, 5]
 * Output:
 *   [1, 3]
 *
 *   Input:
 *     arr1 = [1, 2, 3]                  (~5 elements)             n
 *     arr2 = [1, 2, 3, ..., 100000000]  (~10,000,000 elements)    m
 *   Output:
 *     [1, 2, 3]

 *    n * logm
 */



    public int[] findIntersection(int[] arr1, int[] arr2){
        if(null == arr1 || null == arr2){
            return new int[0];
        }

        int[] result = new int[Math.min(arr1.length, arr2.length)];
        int k = 0;
        for(int i = 0, j = 0; i < arr1.length && j < arr2.length; ){
            if(arr1[i] == arr2[j]){
                if(k == 0 || (k > 0 && result[k - 1] != arr1[i])){
                    result[k] = arr1[i];
                    k++;
                }

                i++;
                j++;
            }else if (arr1[i] < arr2[j]){
                i++;
            }else{
                j++;
            }
        }

        int[] ret = new int[k];
        System.arraycopy(result, 0, ret, 0, k);

        return ret;

    }

    @Test
    public void testFindIntersection(){
        Misc.printArray_Int(findIntersection(new int[]{1, 2, 3}, new int[]{1, 3, 5}));
    }


    //assume arr1 is smaller,  and arr2 is pretty long
    public int[] findIntersection_binarySearch(int[] arr1, int[] arr2){
        if(null == arr1 || null == arr2){
            return new int[0];
        }

        int[] result = new int[arr1.length];
        int k = 0;

        int left = 0;
        for(int i = 0; i < arr1.length ; i++){

            int right = arr2.length;
            int mid;
            while(left <= right){
                mid = left + (right - left) / 2;

                if(arr2[mid] == arr1[i]){
                    if(k == 0 || (k > 0 && result[k - 1] != arr1[i])){
                        result[k] = arr1[i];
                        k++;
                    }
                    break;
                }else if(arr2[mid] < arr1[i]){
                    left = mid + 1;
                }else{
                    right = mid - 1;
                }
            }

            //left--;
            left = Math.max(0, left - 1);
        }

        int[] ret = new int[k];
        System.arraycopy(result, 0, ret, 0, k);

        return ret;

    }

    @Test
    public void testFindIntersection_binarySearch(){
        Misc.printArray_Int(findIntersection_binarySearch(new int[]{1, 2, 3}, new int[]{1, 3, 5}));
    }

/**
 *
 * Input:
 *   arr: [1, 4, 20, 3, 10, 5]
 *   sum: 33
 * Output:
 *   (2, 4)   sum[4] - sum[1]
 *
 * Thought:
 *
 *    sum[0] 1
 *    sum[1] 1 + 4
 *    sum[2] sum[1] + arr[2]
 *
 *    HashMap  <value, index>
 */

    /* Time O(n)   */
    public int[] find(int[] arr, int target){
        if(null == arr || 0 == arr.length){
            return new int[0];
        }

        int sum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        for(int i = 0; i < arr.length; i++){
            sum += arr[i];

            int diff = sum - target;
            if(map.containsKey(diff)){
                return new int[]{map.get(diff) + 1, i};
            }

            map.putIfAbsent(sum, i);
        }

        return new int[0];
    }

    @Test
    public void testFind(){
        Misc.printArray_Int(find(new int[]{1, 4, 20, 3, 10, 5}, 5));
        Misc.printArray_Int(find(new int[]{1, 4, 20, 3, 10, 5}, 33));
    }

    @Test
    public void testDouble(){
        int x = 2;
        int y = 3;
        double z = (double)x / y;

        System.out.println(x/y);
        System.out.println(z);

    }
}
