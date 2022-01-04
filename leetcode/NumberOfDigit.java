package leetcode;

/**
 * 
 * Count the number of k's between 0 and n. k can be 0 - 9.

	Example
	if n=12, k=1 in [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12], we have FIVE 1's (1, 10, 11, 12)
 *
 */

public class NumberOfDigit {
    /*
     * param k : As description.
     * param n : As description.
     * return: An integer denote the count of digit k in 1..n
     * 
     * ?? the return maybe long
     */
    public int digitCounts(int k, int n) {
        //check
        if (n < 0 || k < 0 || k > 9) {
            return 0;
        }
        if (n == 0 && k == 0) {
            return 1;
        }

        int count = 0;

        int highDigits = 0;
        int currDigit = 0;
        int lowDigits = 0;

        for (long factor = 1; n >= factor; factor *= 10) {
            lowDigits = n % (int) factor;

            highDigits = n / (int) factor;
            currDigit = highDigits % 10;
            highDigits = highDigits / 10;

            if (k == 0) {
                highDigits--;
            }

            if (currDigit == k) {
                count += highDigits * factor + lowDigits + 1;
            } else if (currDigit > k) {
                count += (highDigits + 1) * factor;
            } else { // currDigit < k
                count += highDigits * factor;
            }
        }

        return k == 0 ? count + 1 : count;
    }
    
    
    public static void main(String[] args) {
        NumberOfDigit sv = new NumberOfDigit();
        NumberOfDigitOne svOne = new NumberOfDigitOne();

        int[] input = {
            -1,
            0,
            1,
            2,
            9,
            10,
            11,
            12,
            20,
            21,
            22,
            99,
            109,
            110,
            111,
            112,
            120,
            121,
            122,
            199,
            999,
            1000,
            9999,
            99999,
            1410065408
        };

        for (int n : input) {
            System.out.println(String.format("Input: %d - %d - %d - %d", n, sv.digitCounts(0, n), sv.digitCounts(1, n), svOne.countDigitOne(n)));
        }
    }
}
