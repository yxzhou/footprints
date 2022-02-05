package sorting;

import util.Misc;

public class PartitionArrayII {

    /**
     * 
     * Given an array with positive and negative integers. Re-range it to interleaving with positive and negative integers.

        Example
        Given [-1, -2, -3, 4, 5, 6], after re-range, it will be [-1, 5, -2, 4, -3, 6] or any other reasonable answer.

        Note
        You are not necessary to keep the original order of positive integers or negative integers.

        Challenge
        Do it in-place and without extra memory.
     */
    public void rerange(int[] A) {

        if (null == A || 2 > A.length) {
            return;
        }

        int size = A.length;
        int positiveNum = 0;
        for (int num : A) {
            if (num > 0) {
                positiveNum++;
            }
        }

        int f = 1;
        if ((positiveNum << 1) < size) {
            f = -1;
        }

        for (int i = 0, j = 1; i < size & j < size;) {
            if (A[i] * f > 0) {
                i += 2;
            } else if (A[j] * f < 0) {
                j += 2;
            } else {
                swap(A, i, j);
                i += 2;
                j += 2;
            }
        }
    }
	
    private void swap(int[] A, int i, int j){
    	int tmp = A[i];
    	A[i] = A[j];
    	A[j] = tmp;
    }

    public static void main(String[] args) {
        PartitionArrayII sv = new PartitionArrayII();

        int[][] inputs = {
            {-1, -2, -3, 4, 5, 6},
            {-1, -2, -3, 4, 5, 6, 7},
            {-1, -2, -3, 4, 5, 6, -7},
            {1, 2},
            {1, 2, 3},
            {1, 2, 3, -4},};

        for (int[] input : inputs) {
            System.out.println(String.format("Input: %s", Misc.array2String(input)));

            sv.rerange(input);

            System.out.println(String.format("Output: %s", Misc.array2String(input)));
        }
    }

}
