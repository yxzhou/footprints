package fgafa.math;

/**
 * Leetcode #357
 *
 * Given a non-negative integer n, count all numbers with unique digits, x, where 0 ≤ x < 10n.

    Example:
    Given n = 2, return 91. (The answer should be the total numbers in the range of 0 ≤ x < 100, excluding [11,22,33,44,55,66,77,88,99])
 *
 */

public class NumbersWithUniqueDigits {

    /**
     * Mathematics:
     *
     * when one digit number, the unique will be 0, 1, --, 9
     * . It's P(10, 1) = 10
     * when two digit number, the first digit is from [1, 9], the second is [0, 9] exclude the first pickup.
     *   It's P(9, 1) * P(9, 1) = 9 * 9
     * when three digit number,
     *   It's P(9, 1) * P(9, 2) = 9 * 9 * 8
     */
    public int countNumbersWithUniqueDigits(int n) {
        if(n == 0){
            return 1;
        }

        n = Math.min(n, 10);

        int m = 9;
        int curr = 10;

        for(int i = 0; i < n - 1; i++){
            m *= 9 - i;
            curr += m;
        }

        return curr;
    }

    public static void main(String[] args) {
        NumbersWithUniqueDigits sv = new NumbersWithUniqueDigits();

        for(int i = -1; i < 13; i++){
            System.out.println(String.format("%d, %d", i, sv.countNumbersWithUniqueDigits(i)));
        }
    }

}
