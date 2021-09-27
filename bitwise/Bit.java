package bitwise;

import org.junit.Test;

public class Bit {

    /*
     * get the bit in the index position of n
     * 
     * @return: false means 0; true means 1  
     * 
     */
    public static boolean getBit(int n, int index) {
      return (n & (1 << index)) > 0;

    }
    /*
     * set the bit in the index position of n
     * 
     * @boolean b, true means to set 1, false means to set 0
     * @return: the new int  
     * 
     */
    public static int setBit(int n, int index, boolean b) {
      if(b)
        return (n | (1 << index));
      else
        return (n & ~(1 << index));
      
    }
    
	/*
	 * Even == the last bit is 0
	 */
	public static boolean isEven(int num) {
		if (num < 0)
			return false;

		return (num & 1) == 0;
	}

	/**
	 * Odd == the last bit is 1
	 * 
	 */
	public static boolean isOdd(int num) {
		if (num < 0)
			return false;

		return (num & 1) == 1;
	}

	public static boolean isOdd(long num) {
		if (num < 0)
			return false;

		return (num & 1) == 1;
	}

	/**
	 * check if num is a power of 2, num = 2^k ?
	 * 
	 * (a power of 2) == only have one bit 1
	 * 
	 */
	/*Time O(1)*/
	public static boolean isPowerOfTwo_n(int n) {
		// return (num & (num-1)) == 0; // how to do if num == 0; 0 is not a
		// power of 2.
		return (n > 0) && ((n & (n - 1)) == 0);
	}
	
	/*Time O(logn)*/
	public static boolean isPowerOfTwo(int n) {
		while(n > 0){
			n >>= 1;
		}
			
		return n == 1;
	}
		
    public boolean isPowerOfThree(int n) {
        return (Math.log10(n) / Math.log10(3)) % 1 == 0;
    }



	private static boolean isPowerOfFour(int n){
		return (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
	}

    public boolean isPowerOfFour_1(int num) {
		if(num < 1){
			return false;
		}

		boolean flag = true;

		while(num != 1){
			num >>= 1;
			flag = !flag;
		}

        return flag;
    }

	public boolean isPowerOfFour_2(int num) {
		while(num > 1){
			if((num & 3) == 0){
				num >>= 2;
			}else{
				return false;
			}
		}

		return num == 1;
	}

	public boolean isPowerOfFour_x(int num) {
		return num > 0 && ( (num & (num - 1)) == 0 ) && ( (num & 0xaaaaaaaa) == 0 );
	}


	/**
	 *  Input a positive long, N, find the largest number that:
	 *     1 smaller or equals to N
	 *     2 it's a power of 2
	 *
	 */

	public int largestPower (int N) {
		if(N < 1){
			return 0;
		}
		if(N > (1 << 30)){
			return (1 << 30);
		}

		//change all right side bits to 1
		N |= (N >> 1);
		N |= (N >> 2);
		N |= (N >> 4);
		N |= (N >> 8);
		N |= (N >> 16); // stop when it's integer,
		//N |= (N >> 32);

		return (N + 1) >> 1;
	}


	/**
	 * get the rightmost 1-bit diff between x and y
	 *
	 * It's as same as lowBit(), in BinaryIndexedTree
	 *
	 */
	@Test
	public void rightMostDiff(){
		for(int x = 0; x < 19; x++){
			//for(int y = x; y < 15; y++){

				System.out.println(Integer.toBinaryString(x));
				System.out.println(Integer.toBinaryString(-x));
				System.out.println(Integer.toBinaryString(x & (-x)));
				System.out.println();
			//}
		}
	}

	@Test public void test(){
		int a = 0b00111100;
		int b = 0b00001101;

		System.out.println(Integer.toBinaryString(a&b));
		System.out.println(Integer.toBinaryString(a|b));
		System.out.println(Integer.toBinaryString(a&~b));

		/** */
		System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
		System.out.println(Integer.toBinaryString(1 << 31));
		System.out.println(Integer.toBinaryString(largestPower(Integer.MAX_VALUE)));

		/** */

		System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));
		System.out.println(Integer.toBinaryString(Integer.MIN_VALUE - 1));

		int x = Integer.MIN_VALUE;
		System.out.println(x & (x - 1));

	}

	/**
	 * from
	 *
	 *
	 */

	public static void main(String[] args) {

		System.out.println(0b11111111111111111111111111111101);

		System.out.println(Integer.toBinaryString(~(1 << 3)));
		System.out.println(Integer.toBinaryString(~0b00111100));

		for(int i = 8; i < 16; i++){

			System.out.println(Integer.toBinaryString(i));
			System.out.println(Integer.toBinaryString(-i));

			System.out.println(Integer.toBinaryString(i & (-i)));

			System.out.println();
		}




	}

}
