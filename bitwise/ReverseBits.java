package fgafa.bitwise;


/**
 * Reverse bits of a given 32 bits unsigned integer.
 * 
 * For example, 
 * given input 43261596 (represented in binary as 0000 0010 1001 0100 0001 1110 1001 1100),
 *     return 964176192 (represented in binary as 0011 1001 0111 1000 0010 1001 0100 0000).
 * 
 * Follow up: If this function is called many times, how would you optimize it?
 * 
 * Solutions:
 * 1  int -> unsigned binary String -> reverse with StringBuilder -> int
 * 2) like the Reverse Integer, do bitwise shift
 * 
 * If it's called many times, we can store all mapping in DB or cache, totally it's 2^31 * 4 bytes = 8G
 * 
 */

public class ReverseBits {


	/**       */
	public void reverseBits_searchDB(int n) {
		//search DB
	}
	public void reverseBits_searchMemory(int n) {
		//load in memory,  it's  2^31 * 4 bytes = 8G, 
	}
	
	  /**
	   * Reverse bits of an unsigned integer,  (Java doesn't include support for unsigned integers)
	   * 
	   * eg.
	   * 0110 1011 => 1110 1010 => 1110 1010 => 1100 1110 => 1101 0110
	   * 
	   * Time O(the size of bits)
	   */
	  public static int reverse_swap(int bits){
	    if(bits < 0 ) {
			return -1;   // it's an unsigned
		}
	    
	    int length = 30;  // a Java int is 32 bits in all JVMs and on all platforms
	    while((bits & (1 << length )) == 0 ) {
			length--;
		}

	    for(int i=0, j = length; i < j; i++, j--){
	      bits = swapBits(bits, i, j);
	    }
	    
	    return bits;
	  }
	  
	  private static int swapBits(int n, int i, int j){
	    //System.out.println("swapBits between "+i+" with "+j + " in " + Integer.toBinaryString(n));
	    
	    boolean b1 = getBit(n, i);
	    boolean b2 = getBit(n, j);
	    
	    n = setBit(n, i, b2);
	    n = setBit(n, j, b1);
	    
	    return n;
	  }
	  
	  /*
	   * get the bit in the index position of n
	   * 
	   * @return: false means 0; true means 1  
	   * 
	   */
	  private static boolean getBit(int n, int index) {
	    return (n & (1 << index)) > 0;

	  }
	  
	  /*
	   * set the bit in the index position of n
	   * 
	   * @boolean b, true means to set 1, false means to set 0
	   * @return: the new int  
	   * 
	   */
	  private static int setBit(int n, int index, boolean b) {
	    if(b)
	      return (n | (1 << index));
	    else
	      return (n & ~(1 << index));
	    
	  }

	public static int reverseBits_x(int n) {
		int res = 0;

		for(int i = 0; i < 32; i++){
			res = (res << 1) | (n & 1);

			n >>= 1;
		}
		return res;
	}
	  
	  /**
	   * 
	   * The divide and conquer approach:
	   * Remember how merge sort works? Let us use an example of n == 8 (one byte) to see how this works:
	   * 
	   *       01101001
	   *     /         \
	   *    0110      1001
	   *   /   \     /   \
	   *  01   10   10   01
	   *  /\   /\   /\   /\
	   * 0 1  1 0  1 0  0 1     
	   * The first step is to swap all odd and even bits. After that swap consecutive pairs of bits, and so on...
	   * Therefore, only a total of log(n) operations are necessary.
	   * The below code shows a specific case where n == 32, but it could be easily adapted to larger n's as well.
	   * 
	   * 0x55555555 = 01010101 01010101 01010101 01010101   0xAAAAAAAA = 10101010 10101010 10101010 10101010
	   * 0x33333333 = 00110011 00110011 00110011 00110011   0xCCCCCCCC = 11001100 11001100 11001100 11001100
	   * 0x0F0F0F0F = 00001111 00001111 00001111 00001111   0xF0F0F0F0 = 11110000 11110000 11110000 11110000
	   * 0x00FF00FF = 00000000 11111111 00000000 11111111   0xFF00FF00 = 11111111 00000000 11111111 00000000
	   * 0x0000FFFF = 00000000 00000000 11111111 11111111   0xFFFF0000 = 11111111 11111111 00000000 00000000
	   * 
	   * eg.
	   * 0110 1011  => 1001 0111  => 0110 1101  => 1101 0110
	   *
	   *
	   * 0b1111111111111111111111111111101;
	   *
	   * 0111 1111 1111 1111 1111 1111 1111 1101  (1)
	   * 1011 1111 1111 1111 1111 1111 1111 1110  (2)
	   * 1110 1111 1111 1111 1111 1111 1111 1011  (4)
	   * 1111 1110 1111 1111 1111 1111 1011 1111  (8)
	   * 1111 1111 1111 1110 1011 1111 1111 1111  (16)
	   * 1011 1111 1111 1111 1111 1111 1111 1110
	   *
	   *
	   * 
	   * @param bits
	   * @return
	   */
	  
	  public static int reverseMask(int bits){
	    
	    bits = ((bits & 0x55555555) << 1) | ((bits & 0xAAAAAAAA) >>> 1);    //swap all odd and even bits,  1 and 1
	    bits = ((bits & 0x33333333) << 2) | ((bits & 0xCCCCCCCC) >>> 2);    //swap                      ,  2 and 2
	    bits = ((bits & 0x0F0F0F0F) << 4) | ((bits & 0xF0F0F0F0) >>> 4);    //swap                      ,  4 and 4
	    bits = ((bits & 0x00FF00FF) << 8) | ((bits & 0xFF00FF00) >>> 8);    //swap                      ,  8 and 8
	    bits = ((bits & 0x0000FFFF) << 16) | ((bits & 0xFFFF0000) >>> 16);  //swap                      , 16 and 16
	    
	    return bits;
	  }

	/**
	 *
	 *
	 */

	public static int reverseBits_bitshift(int n) {
		//System.out.println( Integer.toUnsignedString(n, 2) );
		n = ((n & 0xFFFF0000) >>> 16 ) | ((n & 0x0000FFFF) << 16);
		//System.out.println( Integer.toUnsignedString(n, 2) );
		n = ((n & 0xFF00FF00) >>> 8 ) | ((n & 0x00FF00FF) << 8);
		//System.out.println( Integer.toUnsignedString(n, 2) );
		n = ((n & 0xF0F0F0F0) >>> 4 ) | ((n & 0x0F0F0F0F) << 4);
		//System.out.println( Integer.toUnsignedString(n, 2) );
		n = ((n & 0xCCCCCCCC) >>> 2 ) | ((n & 0x33333333) << 2);
		//System.out.println( Integer.toUnsignedString(n, 2) );
		n = ((n & 0xAAAAAAAA) >>> 1 ) | ((n & 0x55555555) << 1);
		//System.out.println( Integer.toUnsignedString(n, 2) );

		return n;
	}

	public static void main(String[] args) {
		ReverseBits sv = new ReverseBits();


	    /* test bit reverse  */
	    int[] bits = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 313, 1881,
				43261596,
				964176192,
				0b1111111111111111111111111111101,
				0b11111111111111111111111111111101 // this is not an unsign int
	    };


	    for(int i=0; i<bits.length; i++){
			System.out.println("Reverse \t\t\t"+ Integer.toBinaryString(bits[i]) + " to " + new StringBuilder(Integer.toBinaryString(bits[i])).reverse());

			int rev2 = reverse_swap(bits[i]);
			System.out.println("reverse_swap: \t\t"+ Integer.toBinaryString(bits[i]) + " to " + Integer.toBinaryString(rev2));

			int rev0 = reverseBits_x(bits[i]);
			System.out.println("reverseBits_x: \t\t"+ Integer.toBinaryString(bits[i]) + " to " + Integer.toBinaryString(rev0));

			int rev1 = reverseMask(bits[i]);
			System.out.println("reverseMask: \t\t"+ Integer.toBinaryString(bits[i]) + " to " + Integer.toBinaryString(rev1));


			int rev3 = reverseBits_bitshift(bits[i]);
			System.out.println("reverse_bitshift \t"+ Integer.toBinaryString(bits[i]) + " to " + Integer.toBinaryString(rev3));

			System.out.println();

	    }

	    int x = 43261596;
	    System.out.println("\n" + ( x & 0xaaaaaaaa )  + "\t"+ (x & 0xAAAAAAAA));


		int n = 0b111111111111111111111111111101; //111 1111 1111 1111 1111 1111 1111 1101
//		         11111111111111111111111111111101
		System.out.println(Integer.toBinaryString(n));

//	    y = ((y & 0x55555555) << 1) | ((y & 0xAAAAAAAA) >>> 1);
//		System.out.println(Integer.toBinaryString(y));

		n = ((n & 0xFFFF0000) >>> 16) | ((n & 0x0000FFFF) << 16);
		System.out.println(Integer.toBinaryString(n));

		n = ((n & 0xFF00FF00) >>> 8 ) | ((n & 0x00FF00FF) << 8);
		System.out.println(Integer.toBinaryString(n));

		n = ((n & 0xF0F0F0F0) >>> 4 ) | ((n & 0x0F0F0F0F) << 4);
		System.out.println(Integer.toBinaryString(n));

		n = ((n & 0xCCCCCCCC) >>> 2 ) | ((n & 0x33333333) << 2);
		System.out.println(Integer.toBinaryString(n));

		n = ((n & 0xAAAAAAAA) >>> 1 ) | ((n & 0x55555555) << 1);
		System.out.println(Integer.toBinaryString(n));

		
	}

}
