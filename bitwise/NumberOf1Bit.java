package bitwise;

/**
 * Write a function that takes an unsigned integer and returns the number of ’1'
 * bits it has (also known as the Hamming weight).
 * 
 * For example, the 32-bit integer ’11' has binary representation
 * 00000000000000000000000000001011, so the function should return 3.
 */
public class NumberOf1Bit {

	/*Wrong !!*/
    public int hammingWeight(int n) {
        int count = 0;
        
        if(n < 0){
            n ^= (1 << 31) ;
            count ++;
        }
        
        while( n > 0 ){
            if((n & 1) == 1){
                count ++;
            }
            
            n = (n >> 1);
        }
        
        return count;
    }
    
    /*Time O(n), where n is the size of input, 32 */
    public int hammingWeight_n(int n) {  
        
        int count = 0;  
        for(int i = 0; i < 32;i ++) {
            count += (n >> i) & 1;  
        }
        
        return count;  
    }      
    
    /*
     * count how many 1 in a 32 bit int
     * 
     * e.g. 
     * 0110 & 0101 => 0100 & 0010 = > 0000
     * 
     */
    public static int bitCount_2(int input) {

      int count = 0;
      while (input != 0) { // tmp maybe negative
    	  input &= (input - 1);
    	  count++;
      }

      return count;
    }
    
    /**
     * Jdk java.lang.Integer.bitCount 
     * 
     * refer to _http://graphics.stanford.edu/~seander/bithacks.html#CountBitsSetParallel
     * 
     * 
     * Returns the number of one-bits in the two's complement binary
     * representation of the specified <tt>int</tt> value.  This function is
     * sometimes referred to as the <i>population count</i>.
     *
     * 0x55555555 = 01010101 01010101 01010101 01010101   
     * 0x33333333 = 00110011 00110011 00110011 00110011   
     * 0x0F0F0F0F = 00001111 00001111 00001111 00001111   
     * 0x00FF00FF = 00000000 11111111 00000000 11111111   
     * 0x0000FFFF = 00000000 00000000 11111111 11111111   
     *
     * @return the number of one-bits in the two's complement binary
     *     representation of the specified <tt>int</tt> value.
     *     
     *     
     * @since 1.5
     */
    public static int bitCount(int i) {
        // HD, Figure 5-2
  //  i = i - ((i >>> 1) & 0x55555555);
  //  i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
  //  i = (i + (i >>> 4)) & 0x0f0f0f0f;
  //  i = i + (i >>> 8);
  //  i = i + (i >>> 16);
  //  return i & 0x3f;

      i = i - ((i >> 1) & 0x55555555);                    // reuse input as temporary
      i = (i & 0x33333333) + ((i >> 2) & 0x33333333);     // temp
      i = ((i + (i >> 4) & 0xF0F0F0F) * 0x1010101) >> 24; // count
      
      return i;
    }
    
    /*
     * The parity of a binary word is 1 if the number of 1s in the word is odd; otherwise, it is 0.
     * Example, the parity of 1011 is 1, and the parity of 10001000 is 0.
     * 
     * short parity(long x)
     * 
     * Solutions:
     * 1, brute force, check the value of each bit, count and store the result modulo 2
     *   Time O(n), where n is the size of x
     * 2, count the bit whose value is 1, 
     *   Time O(k), where k is the number of 1s
     * 3, xor 
     *   Time O(logn), where n is the size of x
     * 
     */
    /*Time O(n), where n is the size of x*/
    public static short parity_1(long x) {

      short result = 0;
      while (x != 0) { 
          result  ^= (x & 1);
          x >>>= 1;
      }

      return result;
    }
    
    /*Time O(k), where k is the number of 1s*/
    public static short parity_2(long x) {

        short result = 0;
        while (x != 0) { 
            result ^= 1;
            x &= (x - 1); // drops the lowest set bit of x
        }

        return result;
      }    
    
    /*Time O(logn), where n is the size of x*/
    public static short parity_3(long x) {

        for(int i = 32; i > 0; i >>= 1){
            x ^= x >>> i;
        }
        
        return (short)(x & 1);
      }    
    
    
	public static void main(String[] args) {
		NumberOf1Bit sv = new NumberOf1Bit();
		
		int[] input = {//0, 1, 2, 3,4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
				//Long.parseUnsignedLong(Integer.toBinaryString(Integer.MAX_VALUE)), //
				Integer.MAX_VALUE,
				Integer.MIN_VALUE,
				-1,
				1<<31,
				1<<32 //2147483648//10000000000000000000000000000000
				//2147483648L, //10000000000000000000000000000000
				//4294967295L// (11111111111111111111111111111111)
				};
		
		String[] input2 = {
				//"00000000000000000000000000000000",
				//"00000000000000000000000000000001",
				"00000000000000000000000000000011",
				"01000000000000000000000000000001",
				"11000000000000000000000000000000",
				"11100000000000000000000000000000",
				"11111111111111111111111111111111"

		};
		
		//System.out.println(Integer.parseUnsignedInt("4294967295"));
		for(int n : input){
			System.out.println(String.format("\n input: %d, %s", n, Integer.toBinaryString(n)));
			System.out.println(String.format("Number of bit-1: %d, %d, %d, %d", sv.hammingWeight(n), sv.hammingWeight_n(n), sv.bitCount_2(n), sv.bitCount(n)));
		}
		/*
		for(String n2 : input2){
			System.out.println("\n input:" + n2);
			//System.out.println(sv.hammingWeight(Long.parseUnsignedLong(n2)));
			System.out.println(sv.hammingWeight(Integer.valueOf(n2)));
			//System.out.println(sv.hammingWeight(Integer.parseUnsignedInt(n2)));
			System.out.println(sv.hammingWeight_n(Integer.valueOf(n2)));
		}
		*/
	}

}
