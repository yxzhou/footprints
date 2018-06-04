package fgafa.dp.sequence;

/**
 * 
 * Given a positive integer n, break it into the sum of at least two positive integers and maximize the product of those integers. Return the maximum product you can get.

    For example, given n = 2, return 1 (2 = 1 + 1); given n = 10, return 36 (10 = 3 + 3 + 4).
    
    Note: you may assume that n is not less than 2.
 *
 */

public class IntegerBreak {

    //dp
    public int integerBreak(int n) {
        //check
        if(n < 2){
            return 0;
        }else if(n == 2 || n == 3){
            return n - 1;
        }
        
        //example dp[10] = 36
        int[] dp = new int[n + 1]; //default all are 0
        for(int i = 1; i < 4; i++){
            dp[i] = i;
        }
        
        for(int i = 4; i <= n; i++){
            for(int x = (i + 1) / 2; x < i; x++ ){
                dp[i] = Math.max(dp[i], dp[x] * dp[i - x]);
            }
        }
        
        return dp[n];
    }
    
    public static void main(String[] args){
        
        IntegerBreak sv = new IntegerBreak();

        for(int i = 1; i < 11; i++){
            System.out.println(String.format("%d \t integerBreak: %d", i, sv.integerBreak(i)));
        }
    }
    
}
