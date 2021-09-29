package bitwise;

/**
 * Hamming distance is a metric for comparing two binary data strings.
 * While comparing two binary strings of equal length, Hamming distance is the number of bit positions in which the two bits are different.
 *
 * Problem #1
 *   write a function to calculate the hamming distance between two binary numbers 
 * 
 * Problem #2
 *   write a function that takes a list of binary numbers and returns the sum of the hamming distances for each pair 
 *  
 *   Challenge: find a solution that works in O(n) time.
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
    
    public int totalHammingDistance(int[] nums) {
        if(nums == null){
            return 0;
        }

        int result = 0;
        int n = nums.length;
        int count;
        for(int i = 0, k = 1; i < 31; i++, k <<= 1){
            count = 0;
            for(int x : nums){
                if( (x & k) == 0){
                    count++;
                }
            }

            result += count * (n - count);
        }

        return result;
    }
    
    public int totalHammingDistance_2(int[] nums) {
        if(nums == null){
            return 0;
        }

        int[] counts = new int[31];  
        for(int x : nums){
            for(int i = 0; i < 31; i++){
                if( (x & 1) == 0){
                    counts[i]++;
                }
                x >>>= 1;
            }
        }
        
        int result = 0;
        int n = nums.length;
        for(int i = 0; i < 31; i++){
            result += counts[i] * (n - counts[i]);
        }

        return result;
    }
        
    
    /**
     * @param nums, int 
     * @return the sum of the hamming distances for each pair in decimal numbers
     */
    public static int hammingDistance_decimal(String[] nums) {
        int sum = 0;
        boolean[] digits = new boolean[10]; // from 0 to 9

        //todo
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
