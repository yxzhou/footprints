package fgafa.bitwise;

/**
 * 
 * Given a positive integer, output its complement number. The complement strategy is to flip the bits of its binary representation.

Note:
The given integer is guaranteed to fit within the range of a 32-bit signed integer.
You could assume no leading zero bit in the integer’s binary representation.
Example 1:
Input: 5
Output: 2
Explanation: The binary representation of 5 is 101 (no leading zero bits), and its complement is 010. So you need to output 2.
Example 2:
Input: 1
Output: 0
Explanation: The binary representation of 1 is 1 (no leading zero bits), and its complement is 0. So you need to output 0.
 *
 */

public class NumberComplement {
    
    public int findComplement(int num) {
        
        int result = 0;
        long i = 1; //**long, avoid overflow when i <<= 1
        
        while(num > i){
            if((num & i) == 0){
                result |= i;  // result += i;
            }
            
            i <<= 1;
        }
        
        return result;
    }
        
        

    public static void main(String[] args) {
        int n = 5;
        
        System.out.println(~n);
        
        System.out.println(~n & (n - 1));
        
        System.out.println(1 << 30);
        System.out.println(1 << 31);
        System.out.println(1 << 32);
        
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MAX_VALUE - 10);
        
        System.out.println();
        
        long m = Integer.MAX_VALUE;
        System.out.println( m << 1 );
        
        System.out.println();
        
        NumberComplement sv = new NumberComplement();

        for(int start = 0, i = start; i <= 16; i++){
            System.out.println(String.format("Input: %s, Output: %s", Integer.toUnsignedString(i, 2), Integer.toUnsignedString(sv.findComplement(i), 2)));
        }
        
        System.out.println();
        
        for(int start = Integer.MAX_VALUE - 10, i = start; i <= Integer.MAX_VALUE; i++){
            //System.out.println(i);
            System.out.println(String.format("Input: %s, Output: %s", Integer.toUnsignedString(i, 2), Integer.toUnsignedString(sv.findComplement(i), 2)));
       
            if(i == Integer.MAX_VALUE){
                break;
            }
        }
        
    }

}
