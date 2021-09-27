package dp.sequence;

/**
 * 
 * Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which sum to n.
 * 
 * For example, 
 * given n = 12, return 3 because 12 = 4 + 4 + 4; 
 * 
 * given n = 13, return 2 because 13 = 4 + 9.  
 *
 */

public class PerfectSquare {
    
    /*  */
    public int numSquares(int n) {
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
    
    /*
     * 四平方和定理 （ four-square theorem） 说明每个正整数均可表示为4个整数的平方和。它是费马多边形数定理和华林问题的特例。
     * 
     */
    public int numSquares_x(int n) {
        if(n < 1){
            return 0;
        }
        
        while (n % 4 == 0){
            n /= 4;
        }
        
        if (n % 8 == 7){
            return 4;
        }
        
        for (int a = 0; a * a <= n; ++a) {
            int b = (int) Math.sqrt(n - a * a);
            if (a * a + b * b == n) {
                return 2;
            }else if( a * a == n || b * b == n ){
                return 1;
            }
        }
        
        return 3;
    }
    
    public static void main(String[] args){
        PerfectSquare sv = new PerfectSquare();
        
        for(int i = 12; i < 1000; i++){
            System.out.println(String.format("%d - %d == %d", i, sv.numSquares(i), sv.numSquares_x(i) ));
        }
    }
    
}
