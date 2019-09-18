package fgafa.bitwise;

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
    
    public boolean isPowerOfFour(int num) {
        while(num > 1){
            if((num & 3) == 0){
                num >>= 2;
            }else{
                return false;
            }
        }
        
        return num == 1;
    }

	/**
	 * from
	 *
	 *
	 */
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
