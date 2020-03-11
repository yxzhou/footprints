package fgafa.dp.arithmetic;

/**
 * Leetcode #413
 *
 * A sequence of number is called arithmetic if it consists of at least three elements and if the difference between any two consecutive elements is the same.
 *
 * For example, these are arithmetic sequence:
 *
 * 1, 3, 5, 7, 9
 * 7, 7, 7, 7
 * 3, -1, -5, -9
 * The following sequence is not arithmetic.
 *
 * 1, 1, 2, 5, 7
 *
 * A zero-indexed array A consisting of N numbers is given. A slice of that array is any pair of integers (P, Q) such that 0 <= P < Q < N.
 *
 * A slice (P, Q) of array A is called arithmetic if the sequence:
 * A[P], A[p + 1], ..., A[Q - 1], A[Q] is arithmetic. In particular, this means that P + 1 < Q.
 *
 * The function should return the number of arithmetic slices in the array A.
 *
 *
 * Example:
 *
 * A = [1, 2, 3, 4]
 *
 * return: 3, for 3 arithmetic slices in A: [1, 2, 3], [2, 3, 4] and [1, 2, 3, 4] itself.
 *
 */

public class ArithmeticSlices {

    public int numberOfArithmeticSlices(int[] A) {
        if(A == null || A.length < 3){
            return 0;
        }

        int n = A.length;
        int result = 0;

        int count = 2;
        int diff = A[1] - A[0];
        for(int i = 1, j = 2; j < n; i++, j++){
            if( A[j] - A[i] == diff ){
                count += 1;
            }else{
                count -= 2;
                result += count * (count + 1) / 2;

                count = 2;
                diff = A[j] - A[i];
            }
        }

        count -= 2;
        result += count * (count + 1) / 2;

        return result;
    }

    public int numberOfArithmeticSlices_2(int[] A) {
        if(A == null || A.length < 3){
            return 0;
        }

        int n = A.length;
        int result = 0;

        int rank = 0;
        int diff = A[1] - A[0];
        for(int i = 1, j = 2; j < n; i++, j++){
            if( A[j] - A[i] == diff ){
                rank += 1;

                result += ( rank > 0 ? rank : 0) ;

            }else{
                rank = 0;
                diff = A[j] - A[i];
            }
        }

        return result;
    }




}
