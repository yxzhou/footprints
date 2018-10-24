package fgafa.bitwise;

import fgafa.util.Misc;

public class SingleNumber {

	/**
	 * 
	 * Given an array of integers, every element appears twice except for one.
	 * Find that single one.
	 * 
	 * Note: Your algorithm should have a linear runtime complexity. Could you
	 * implement it without using extra memory?
	 */
    public int singleNumber(int[] A) {
        if (null == A | 0 == A.length) {
            throw new IllegalArgumentException("Can't find the single number from a NULL or Empty array.");
        }

        int ret = 0;
        for (int i = 0; i < A.length; i++) {
            ret ^= A[i];
        }

        return ret;
    }
	
	/**
	 * 
	 * Given an array of integers, every element appears three times except for
	 * one. Find that single one.
	 * 
	 * Note: Your algorithm should have a linear runtime complexity. Could you
	 * implement it without using extra memory?
	 */
    public int singleNumberII(int[] A) {
        if (null == A | 0 == A.length) {
            throw new IllegalArgumentException("Can't find the single number from a NULL or Empty array.");
        }

        int bit = 0;
        int ret = 0;
        for (int i = 0; i < 32; i++) {
            bit = 0;
            for (int num : A) {
                bit += (num >> i) & 1;
                bit %= 3; // for case, the count is out of range
            }

            ret += (bit << i);
        }

        return ret;
    }
    
	/**
	 * @param nums : An integer array
	 * @return : An integer 
	 */
    public int singleNumberII_n(int[] nums) {
        //check
        if(null == nums || 0 == nums.length){
            throw new IllegalArgumentException("The input array should not be null or empty.");
        }
        
        //main
        int ret = 0;
        for(int i = 0; i < 32; i++){
            int bit = 0;
            int x = (1 << i);
            for(int n : nums){
                bit += n & x;
                bit %= 3;
            }
            
            if(1 == bit){
                ret |= (bit << i);
            }
        }
        
        return ret;
    }
    
	/**
	 * Given an array of integers, every element appears twice except for two.
	 * Find that two.
	 * 
	 * Note: Your algorithm should have a linear runtime complexity. Could you
	 * implement it without using extra memory?
	 */

    public int[] twoNumber_n(int[] nums) {
        if(null == nums || 2 > nums.length){ 
            return new int[0];  //exception, error code
        }
        
        //main
        //1 get the xor
        int xor = 0;
        for(int n : nums){
            xor ^= n;
        }
        
        //2 from right to left, get the first 1 in the xor
        int i = 0;
        for( ; i < 32; i++){
            if(((xor >> i)& 1) == 1 ){
                break;
            }
        }
        
        //3 with the 1, it can diff with the special two, and make A in 2 group
        int[] ret = new int[2]; //default both are 0
        for(int n : nums){
            if(((n >> i)& 1) == 1){
                ret[0] ^= n;
            }else{
                ret[1] ^= n;
            }
        }
        
        return ret;
    }
	
	public static void main(String[] args) {
		//
        System.out.println("<<: " + (1 << 3) + " " + (1 << 31));
		System.out.println("&: " + (2 & 2) + " " + (1 & 2));
		System.out.println("|: " + (2 | 2) + " " + (1 | 2));
		System.out.println("^: " + (2 ^ 2) + " " + (1 ^ 2));
		
		SingleNumber sv = new SingleNumber();
		
		int[][] input = {
				{2,2,1},
				{0xFFFFFFF0, 0x0000000F, 0x0000000F},
		};

		for(int[] nums : input){
			System.out.println("\nInput: " + Misc.array2String(nums));
			System.out.println("Output: " + sv.singleNumber(nums));
		}
		
		
		System.out.println("\n=========================");
		int[][] input2 = {
				{2,2,2,1},
				{2,2,2,1,1,1,4}
		};
		for(int[] nums : input2){
			System.out.println("\nInput: " + Misc.array2String(nums));
			System.out.println("Output: " + sv.singleNumberII(nums) + " " + sv.singleNumberII_n(nums));
		}
		
		System.out.println("\n=========================");
	}

}
