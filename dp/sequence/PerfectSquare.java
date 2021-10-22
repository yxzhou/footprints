package dp.sequence;

import org.junit.Assert;

/**
 * 
 * Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which sum to n.
 * 
 * For example, 
 * given n = 12, return 3 because 12 = 4 + 4 + 4; 
 * given n = 13, return 2 because 13 = 4 + 9.  
 *
 * 
 * 
 */

public class PerfectSquare {
    
    /**
     *  Time O(n * n),  Space O(n)
     */
    public int smallestOfSquare(int n){
        if(n < 1){
            return 0;
        }

        int squareRoot = (int)Math.ceil(Math.sqrt(n));
        if(squareRoot * squareRoot == n){
            return 1;
        }

        int[] counter = new int[n + 1];
        for(int i = 1; i < squareRoot; i++){
            counter[i * i] = 1;
        }

        for(int i = 2; i <= n; i++){
            if(counter[i] == 1){
                continue;
            }
            counter[i] = n; //init value
            for(int j = 1, half = (i >> 1); j <= half; j++){
                counter[i] = Math.min(counter[i], counter[j] + counter[i - j]);
            }
        }

        return counter[n];
    }
    
    /*  */
    public int smallestOfSquare_dp(int n) {
        if(n < 1){
            return 0;
        }
        
        int[] dp = new int[n+1]; //default all are 0
        
        for(int i = 1; i <= n; i++){
            
            dp[i] = i;
            for(int j = 1, k = 1; k <= i ; k += (j << 1) + 1, j++ ){
                dp[i] = Math.min(dp[i], dp[i - k]);
            }
            
            dp[i]++;
        }
        
        return dp[n];
    }
    
    /**
        1, 4, 9, 16, 25, 36

        41 = 25 + 16,   != 36 + 4 + 1,  so greedy does not work
        42 = 25 + 16 + 1, 

        step 1, get the square array, {1, 4, 9, 16, 25, 36 ... last } ,  last < n  
        step 2, backpack problem
               define f[i] as the perfect square number of i 
     * @param n: a positive integer
     * @return: An integer
     */
    public int numSquares(int n) {
        if(n < 0){
            return 0;
        }else if(n < 2){
            return 1;
        }

        int[] squares = new int[(int)Math.sqrt(n) + 2];
        for(int x = 0, a = 0; x <= n; x += a * 2 + 1, a++){ // x=a*a, (a + 1)*(a + 1) - a*a = a*2 + 1
            squares[a] = x; 
        }

        int[] f = new int[n + 1];

        for(int x = 0, a = 0; x <= n; x++){
            if(x == squares[a]){
                f[x] = 1;
                a++;
                continue;
            }

            f[x] = x;
            for(int i = 1; i < a; i++ ){
                f[x] = Math.min(f[x], f[x - squares[i]] );
            }
            f[x]++;
            
        }

        return f[n];
    }
    
    /*
     * 
     *
     * 四平方和定理 （ four-square theorem） 说明每个正整数均可表示为4个整数的平方和。它是费马多边形数定理和华林问题的特例。
     * 
     */
    public int smallestOfSquare_x(int n) {
        if(n < 1){
            return 0;
        }
        
        while (n % 4 == 0) {
            n /= 4;
        }

        if (n % 8 == 7) {
            return 4;
        }

        for (int a = 0; a * a <= n; ++a) {
            int b = (int) Math.sqrt(n - a * a);

            if (a * a == n || b * b == n) {
                return 1;
            } else if (a * a + b * b == n) {
                return 2;
            }
        }
        
        return 3;
    }
    
    public int smallestOfSquare_x2(int n) {
        if(n < 1){
            return 0;
        }
        
        while ((n & 0b11) == 0) {
            n >>= 2;
        }

        if ((n & 0b111) == 7) {
            return 4;
        }

        for (int a = 0, x = 0, b = 0, y = 0; x <= n; x += (a << 1) + 1, a++) { // x=a*a, (a + 1)*(a + 1) - a*a = a*2 + 1, y=b*b
            y = n - x;
            b = (int) Math.sqrt(y);

            if (x == n || b * b == n) {
                return 1;
            } else if (b * b == y) {
                return 2;
            }
        }
        
        return 3;
    }
    
    public static void main(String[] args){
        PerfectSquare sv = new PerfectSquare();
        
        int[] inputs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 27};
        int[] expects = {1, 2, 3, 1, 2, 3, 4, 2, 1,  2,  3,  3,   2,  3, 4,  1,  3};
        
        for(int i = 0; i < inputs.length; i++){
            Assert.assertEquals(expects[i], sv.smallestOfSquare(inputs[i]));
            Assert.assertEquals(expects[i], sv.smallestOfSquare_dp(inputs[i]));
            Assert.assertEquals(expects[i], sv.smallestOfSquare_x(inputs[i]));
        }
        
        for(int i = 1; i < 1000; i++){
            System.out.println(String.format("%d. \t%d == %d == %d", i, sv.smallestOfSquare(i), sv.smallestOfSquare_dp(i), sv.smallestOfSquare_x(i) ));
        }
        

    }
    
}
