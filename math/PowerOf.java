package fgafa.math;

import fgafa.util.Misc;

public class PowerOf {
    /*
     * calculate m to the power of n with * operation, ( m to the nth power )  --m^n
     *
     * the iteration method, the result P(n) will be m * m^2 * m ^4 ...
     * 
     * e.g.   x^7
     * n          result(1)            factor(x)
     * 7          1 * x                x^2
     * 3(n>>=1)   1 * x * x^2          x^4
     * 1(n>>=1)   1 * x * x^2 * x^4    x^8
     * 0(n>>=1) 
     *   
     * Time O(logn)  
     * 
     * Testcases: 
     * 1 how to do if m is negative?  
     * 2 n is negative ?      
     * 3 n is Integer.MIN_VALUE,  
     * 4 n is Integer.MAX_VALUE 
     * 5 how to check if the calculated result is bigger or smaller than the type maxValue or minValue.
     * 
     * 
     * test if Integer.MIN_VALUE < 0: true
     * test if -Integer.MIN_VALUE < 0: true   //
     * test if Integer.MAX_VALUE > 0: true
     * test if -Integer.MAX_VALUE > 0: false  //
     * 
     */

    public double pow(int x, int n){
      //return power((double) x, (long) n);
      return pow((double) x, (int) n);
    }
    public double pow(double x, int n){
      //check
      //when n is negative and n is Integer.MIN_VALUE
      if(n < 0){
//        if(n == Integer.MIN_VALUE)
//          n = (n^(n>>31)) - (n>>31);   // abs(n) when n is Integer.MIN_VALUE,  -n doesn't work. 
//        else 
          n = -n;  // abs(n)=n>0? n:-n when casting int to long,  
      
        //System.out.println("==="+n);
        
        return 1.0 / pow(x, n);  
      }
      
      double result = 1;
      double factor = x;
      
      for (; n > 0; n >>= 1 ) {
        
        if((n&1)==1){  //isOdd(n)
            result *= factor ;  
            
            if(Double.isInfinite(result)){
              return (x>0)? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
            }
              
        }
        
        factor *= factor;

      }
      
      return result;
    }
    
    /**
     * Your task is to calculate a^b mod 1337 where a is a positive integer and b is an extremely large positive integer given in the form of an array.

        Example1:
        
        a = 2
        b = [3]
        
        Result: 8
        Example2:
        
        a = 2
        b = [1,0]
        
        Result: 1024
     */
    final static int BASE = 1337;
    public int superPow_wrong(int a, int[] b) {
        //a is a positive integer and b is an extremely large positive integer
        if(a <= 0 || null == b || 0 == b.length){
            throw new IllegalArgumentException();
        }
        
        int result = 1;
        int factor = a % BASE;
        for(int i = b.length - 1; i >= 0; i--){
            result =  ( result * (int)( Math.pow(factor, b[i]) % BASE ) ) % BASE;
                        
            //factor = Math.pow(factor, 10) % BASE;  // wrong
            factor = (int)( (Math.pow(factor, 3) % BASE) * (Math.pow(factor, 2) % BASE) % BASE );
            factor = (int)(Math.pow(factor, 2) % BASE);
            
        }
        
        return (int) result;
    }
    
    public int superPow_n(int a, int[] b) {
        //a is a positive integer and b is an extremely large positive integer
        if(a <= 0 || null == b || 0 == b.length){
            throw new IllegalArgumentException();
        }
        
        int result = 1;
        int factor = a % BASE;
        for(int i = b.length - 1; i >= 0; i--){
            result =  result * superPowHelp(factor, b[i]) % BASE;
                        
            factor = superPowHelp(factor, 10) % BASE;
        }
        
        return result;
    }
    
    private int superPowHelp(int a, int b){
        int result = 1;
        int factor = a;
        
        for (; b > 0; b >>= 1 ) {
            if((b & 1) == 1){
                result = result * factor % BASE;
            }
            
            factor = factor * factor % BASE;
        }
        
        return result;
    }
    
    /**
     * @param x the base number
     * @param n the power number
     * @return the result
     */
    public double pow_n(double x, int n) {
        assert (!(x == 0 && n < 0));

        // check
        if (x == 0) {
            return 0;
        } else if (n == 0) {
            return 1;
        } else if (n < 0) {
            return 1 / pow_n(x, 0 - n);
        }

        double result = 1;
        double factor = x;

        for (; n > 0; n >>= 1) {
            if ((n & 1) == 1) {
                result *= factor;

                if (Double.isInfinite(result)) { // **
                    return (x > 0) ? Double.POSITIVE_INFINITY
                                : Double.NEGATIVE_INFINITY;
                }
            }

            factor *= factor;
        }

        return result;
    }

    /**
     * the recursive method, calculate m to the power of n, ( m to the nth power )  --m^n
     *
     * define P(n) = m^n
     * P(n) = P(n/2) * P(n/2)
     * 
     * not recommend
     */
    protected long power_R(int m, int n){
      long result = 1;
      
      if( 0 == n)
        return result;
      
      long tmpsquare_root = power_R(m, n/2);
      
      result = tmpsquare_root * tmpsquare_root;
        
      if((n&1)==1) result *= m;  //isOdd(n)
      
      return result;
    }
    

    /**
     * calculate m to the power of n, ( m to the nth power )  --m^n
     * 
     * similar to pow(m, n)
     * 
     */
    protected long power_3(int m, int n) {
      if (n == 0)
        return 1;

      //
      int flag = n;  
      for (int value = n; (value &= (value - 1)) > 0;)
        flag = value;

      //System.out.println(" n - "+ n+" || flag - "  + flag );
      
      long result = m;
      while ((flag >>= 1) > 0) {
        result *= result;
        if ((n & flag) > 0)
          result *= m;
      }
      return result;

    }
    
    /**
     * cubed == m to power of three == m^3
     * 
     */
    public long powerOfThree(int m){
      
      int c = 0, d = 1, e = 6;
      while(m-- > 0){
          c += d;
          d += e;
          e += 6;
          
          //m--;
          
          //System.out.println(" m - "+ m );
      }

      return c;
    }
    
    public boolean isPowerOfTwo(int n) {
        if (n <= 0) {
            return false;
        }
         
        return (n & (n - 1)) == 0;
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
    
    public static void main(String[] args){
        
        PowerOf sv = new PowerOf();
        
        /* test pow
        int[] m = {2, -2, -2,  2, -2, 2, 100, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE};
        int[] n = {3,  3,  4, -2, -2, 100, 2, 2, 2, -2, -2};

        for(int i= 0; i< m.length; i++){
          System.out.println("\n calculate "+m[i]+" to the power of "+n[i]+": " );
          System.out.println("\n sv.power(): \t" + sv.power(m[i], n[i]));
          System.out.println(" sv.power_R(): \t" + sv.power_R(m[i], n[i]));
          System.out.println(" sv.power3():  \t" + sv.power_3(m[i], n[i]));      
        }
        for(int i= 0; i< m.length; i++){
          System.out.println("\n calculate "+n[i]+" to the power of "+m[i]+": " );
          System.out.println(" sv.power():   \t" + sv.power(n[i], m[i]));
          System.out.println(" sv.power_R(): \t" + sv.power_R(n[i], m[i]));
          System.out.println(" sv.power3():  \t" + sv.power_3(n[i], m[i]));      
        }    
        */  
        
        /* test power */  
        for(int p = 0; p < 33; p++ ){
            
            System.out.println(String.format("%d \t powerOf2: %b, powerOf3: %b, powerOf4: %b ", p, sv.isPowerOfTwo(p), sv.isPowerOfThree(p), sv.isPowerOfFour(p)));
        }
     
        /* test super pow */ 
        System.out.println(Math.pow(BASE, 10));
        System.out.println(Math.pow(BASE, 10) % BASE);
        
        int[] aa = {2, 2, 2147483647, 2147483647, 2147483647, 2147483647, 1331};
        int[][] bb = {{3}, {1,0}, {1}, {2}, {3}, {2,0,0}, {2,3,4,5,6,7,8,9}};
        for(int p = 0; p < aa.length; p++ ){
            
            System.out.println(String.format(" super pow: %d, %s, Output: %d, %d", aa[p], Misc.array2String(bb[p]), sv.superPow_wrong(aa[p], bb[p]), sv.superPow_n(aa[p], bb[p])));
        }
         
    }
}
