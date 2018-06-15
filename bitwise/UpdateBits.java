package fgafa.bitwise;

public class UpdateBits {

	
    /**
     * Given two 32-bit numbers, N and M, and two bit positions, i and j. 
     * Write a method to set all bits between i and j in N equal to M (e g , M becomes a substring of N located at i and starting at j)
     *
     *Example
     *Given N=(10000000000)2, M=(10101)2, i=2, j=6
     *
     *return N=(10001010100)2
     *
     *Note
     *In the function, the numbers N and M will given in decimal, you should also return a decimal number.
     *
     */
    public int updateBits(int n, int m, int i, int j) {
        //check
        //You can assume that the bits j through i have enough space to fit all of M
        
        //init
        int len = j - i + 1;
        if(len == 32){
        	return m;
        }
        
        int mask = ~(((1 << len) - 1) << i);

        return (n & mask) | (m << i);
    }
    
	public static void main(String[] args) {
		System.out.println("1 << 31: " + (1 << 31)); 
		System.out.println("1 << 30: " + (1 << 30)); 
		
		System.out.println("~(1 << 31): " + ~(1 << 31)); 
		System.out.println("~(1 << 30): " + ~(1 << 30));
	}

}
