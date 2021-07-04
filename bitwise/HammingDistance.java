package fgafa.bitwise;

/**
 * Hamming distance is a metric for comparing two binary data strings.
 * While comparing two binary strings of equal length, Hamming distance is the number of bit positions in which the two bits are different.
 *
 * (a) first, write a function to calculate the hamming distance between two binary numbers 
 * (b) write a function that takes a list of binary numbers and returns the sum of the hamming distances for each pair 
 * (c) find a solution for (b) that works in O(n) time.
 */

public class HammingDistance {

    /**
     * @param a, a binary number
     * @param b, a binary number
     * @return the hamming distance between two binary numbers
     */
    public static int hammingDistance(int a, int b) {
        int xor = a ^ b;
        int distance = 0;
        while (xor != 0) { //note, it's not xor > 0
            xor &= xor - 1;
            distance++;
        }
        return distance;
    }

    /**
     * @param array, int
     * @return the sum of the hamming distances for each pair in binary numbers
     */
    public static int hammingDistance_binary(int[] nums) {
        int sum = 0;
        for (int i = 0; i < 32; i++) {
            int mask = 1 << i;
            int numOf0 = 0;
            int numOf1 = 0;
            for (int value : nums) {
                if ((value & mask) != 0) {
                    numOf1++;
                } else {
                    numOf0++;
                }
            }
            sum += numOf0 * numOf1;
        }
        return sum;
    }
    
    
    /**
     * @param nums, int 
     * @return the sum of the hamming distances for each pair in decimal numbers
     */
    public static int hammingDistance_decimal(String[] nums) {
        int sum = 0;
        boolean[] digits = new boolean[10]; // from 0 to 9

        return sum;
    }
    

    /**
     * given K files and N machines, N is much larger than K . given function
     * long sum(int fileID,int machineID) which use particular machine calculate
     * the sum of file. Question:write function which calculate the sum of all
     * files
     */
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
