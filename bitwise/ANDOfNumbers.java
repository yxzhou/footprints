package bitwise;

/**
 * Given a range [m, n] where 0 <= m <= n <= 2147483647, return the bitwise AND of all numbers in this range, inclusive.
 * 
 * For example, given the range [5, 7], you should return 4.
 *   
 * solutions:
 * 1) Do bitwise AND from m, m+1, m+2, --- to n,  
 * Time complexity O(n - m + 1)
 *
 * 2) Think about the number in [m, n], 
 *   if it has 2, (x and x+1), for sure, ( x & (x+1) ) & 0x1 == 0, 
 *   if it has 4, (x, x+1, x+2 and x+3), ( x & (x+1) & (x+2) & (x+3) ) & 0x11 == 0
 *   ---  
 * Time complexity O( log(n - m) )
 */

public class AndOfNumbers {
    public int rangeBitwiseAnd_n(int m, int n) {
        assert m > 0 && n > 0 && m <= n;

        int diff = n - m;
        long mask = 1; //**
        int count = 0;
        while(mask <= diff){
            mask <<= 1;
            count++;
        }

        //if(m == n){
        //  return m;
        //}
        //int count = (int)( Math.log(n - m) / Math.log(2)) + 1;

        return m & n & (0xffffffff << count);
        
    }
    
    
    public int rangeBitwiseAnd_bruteforce(int m, int n) {
        int result = 0xffffffff;
        for(int i = m; i <= n; i++){
            result &= i;
        }
        
        return result;
    }
    
    public static void main(String[] args){

        for(int diff = 0; diff < 50; diff++){

            long mask = 1; //**
            int count = 0;
            while(mask <= diff){
                mask <<= 1;
                count++;
            }

            System.out.println( count + " " +  (int)( Math.log(diff ) / Math.log(2)) );


        }

        System.out.println();

        System.out.println( Integer.toBinaryString(0x7FFFFFFF << 2)  );
        
        
        System.out.println( Integer.toBinaryString(0x7FFFFFFF << 0)  );
        
        System.out.println( Integer.toBinaryString(1 & 33 )  );
        
        
        int[][] input = {
                    {0, 0},
                    {1, 1},
                    {0, 2},
                    {1, 2},
                    {1, 3},
                    {3, 6},
                    {5, 6},
                    {8, 9},
                    {8, 10},
                    {8, 11},
                    {8, 12},
                    {1, 32},
                    {1, 33},
                    {2, 33},
                    {2, 0x7FFFFFFF}
        };
        
        AndOfNumbers sv = new AndOfNumbers();
        
        for(int i = 0; i < input.length; i++){
            System.out.println(String.format("Input: [%d, %d]", input[i][0], input[i][1]));
            
            System.out.println(String.format("Output: %d = %d", sv.rangeBitwiseAnd_bruteforce(input[i][0], input[i][1]), sv.rangeBitwiseAnd_n(input[i][0], input[i][1])));
        }
        
    }
}
