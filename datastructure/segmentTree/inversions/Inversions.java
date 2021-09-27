package datastructure.segmentTree.inversions;

import util.Misc;

import java.util.Arrays;

/**
 *
 * We can determine how "out of order" an array A is by counting the number of inversions it has. Two elements A[i] and A[j] form an inversion if A[i] > A[j] but i < j.
 * That is, a smaller element appears after a larger element.
 *
 * Given an array, count the number of inversions it has. Do this faster than O(N^2) time.
 *
 * You may assume each element in the array is distinct.
 *
 * For example,
 *    A sorted list has zero inversions.
 *    The array [2, 4, 1, 3, 5] has three inversions: (2, 1), (4, 1), and (4, 3).
 *    The array [5, 4, 3, 2, 1] has ten inversions: every distinct pair forms an inversion.
 *
 */

public class Inversions {


    public int countInversions(int[] nums){
        if(null == nums || nums.length < 2){
            return 0;
        }

        int length = nums.length;
        int[] ordered = Arrays.copyOf(nums, length);
        Arrays.sort(ordered);

        int[] bigger = new int[length]; //default all are 0
        int[] equal = new int[length]; //default all are 0

        int count = 0;
        for(int num : nums){
            //
            for(int left = 0, right = length - 1; left <= right; ){
                int mid = left + (right - left) / 2;

                if(ordered[mid] == num){
                    count += bigger[mid];
                    equal[mid]++;
                    break;
                }else if(ordered[mid] < num){
                    bigger[mid]++;
                    left = mid + 1;
                }else{
                    count += bigger[mid] + equal[mid];
                    right = mid - 1;
                }
            }
        }

        return count;
    }

    public static void main(String[] args){
        int[][] cases = {
                //{2, 4, 1, 3, 5},
                {5, 4, 3, 2, 1},
                {1, 2, 3, 4},
                {5, 4, 3, 2},
                {5, 4, 3},
                {5, 4}
        };

        int[] expects = {
            3,
            10,
            0
        };

        Inversions sv = new Inversions();

        for(int i = 0; i < cases.length; i++){
            int result = sv.countInversions(cases[i]);

            System.out.println(String.format("\n%d: {%s}\t %b", i, Misc.array2String(cases[i]), result == expects[i]));
            if(result != expects[i]){
                System.out.println(String.format("result: %d  -  %d", result, expects[i]));
            }
        }

    }

}
