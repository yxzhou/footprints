package sorting;

import util.Misc;

/**
 * 
 * Given an unsorted integer array, find the first missing positive integer.
 * 
 * For example,
 * Given [1,2,0] return 3,
 * Given [3,4,-1,1] return 2.
 * 
 * Your algorithm should run in O(n) time and uses constant space.
 * 
 *
 */

public class FindMissingFirstPositive {

    /**
     * try to make the A in order,  
     *   index  0,  1,   2,  3,  --
     *   value      1,  2,   3,  ---
     * the point is to put the right value in the right position
     *
     * @param A
     * @param n
     * @return
     */
    public int firstMissingPositive(int A[]) {
        if (null == A || 0 == A.length) {
            return 1;
        }

        int n = A.length;
        for (int i = 0; i < n; ) {
            // swap when A[i] is a "right value", and it's in the right position, and it's not equal to value on the right position.
            if (A[i] > 0 && A[i] < A.length  && A[A[i]] != A[i] ) {
                swap(A, i, A[i]);
            } else {
                i++;
            }
        }

        for (int i = 1; i < n; i++) {
            if (A[i] != i) {
                return i;
            }
        }
        return n + 1;
    }

    /**
     * swap i_th and j_th in the array. 
     */
    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;

    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        FindMissingFirstPositive sv = new FindMissingFirstPositive();

        int[][] inputs = {//null, 
            {}, 
            {1}, 
            {2}, //, {1,0,1,0}
            {0}, 
            {1, 1}, 
            {3, 2}, 
            {1, 3}, 
            {1, 2, 4}, 
            {0, 3, 2}, 
            {1, 2, 0}, 
            {3, 4, -1, 1}
        };
        
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("\n input: " + Misc.array2String(inputs[i]));
            //System.out.println("Miss:   " + service.findMissedMinUInt_qickSort(ints[i]));

            //System.out.println("output: "+ Misc.array2String(ints[i]));

            System.out.println("Miss:   " + sv.firstMissingPositive(inputs[i]));
        }

    }

}
