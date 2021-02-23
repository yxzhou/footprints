package fgafa.array.subSum;

import java.util.Map;
import java.util.TreeMap;

/**
 * From Lintcode #1797
 *
 * Give two sorted arrays. To take a number from each of the two arrays, the sum of the two numbers needs to be less than or equal to k, and you need to find the index combination with the largest sum of the two numbers. Returns a pair of indexes containing two arrays. If you have multiple index answers with equal sum of two numbers, you should choose the index pair with the smallest index of the first array. On this premise, you should choose the index pair with the smallest index of the second array.
 *
 * The sum of the two numbers <= k
 * The sum is the biggest
 * Both array indexes are kept to a minimum
 * Example
 * Example1
 *
 * A = [1, 4, 6, 9], B = [1, 2, 3, 4], K = 9
 * return [2, 2]
 * Example2:
 *
 * Input:
 * A = [1, 4, 6, 8], B = [1, 2, 3, 5], K = 12
 * Output:
 * [2, 3]
 *
 */

public class OptimalUtilization {

    /**
     * @param A: a integer sorted array
     * @param B: a integer sorted array
     * @param K: a integer
     * @return: return a pair of index
     */
    public int[] optimalUtilization(int[] A, int[] B, int K) {
        if(A == null || A.length == 0 || B == null || B.length == 0 || A[0] + B[0] > K){
            return new int[0];
        }

        // if(A.length > B.length){
        //     return optimalUtilization(B, A, K);
        // }

        TreeMap<Integer, Integer> tree = new TreeMap<>();
        for(int i = 0; i < B.length; i++){
            // if(i > 0 && B[i] == B[i - 1]){
            //     continue;
            // }

            if(!tree.containsKey(B[i])){
                tree.put(B[i], i);
            }
        }

        int[] result = new int[2];

        Map.Entry<Integer,Integer> entry;
        int diff;
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < A.length; i++){
            entry = tree.floorEntry(K - A[i]);

            if(entry == null){
                break;
            }

            diff = K - A[i] - entry.getKey();
            if(min > diff){
                min = diff;

                result[0] = i;
                result[1] = entry.getValue();

                if(min == 0){
                    break;
                }
            }
        }

        return result;
    }

}
