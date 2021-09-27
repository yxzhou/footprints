package dp;

/**
 *
 * This problem was asked by Quantcast.
 *
 * You are presented with an array representing a Boolean expression. The elements are of two kinds:
 *
 * T and F, representing the values True and False.
 * &, |, and ^, representing the bitwise operators for AND, OR, and XOR.
 * Determine the number of ways to group the array elements using parentheses so that the entire expression evaluates to True.
 *
 * For example, suppose the input is ['F', '|', 'T', '&', 'T']. In this case, there are two acceptable groupings: (F | T) & T and F | (T & T).
 *
 *
 *
 */

public class BooleanExpressions {

    public int getTrue(char[] chars){
        if(null == chars || 0 == chars.length){
            return 0;
        }

        int n = chars.length;
        int half = (n >> 1);

        int[][] dp = new int[half + 1][half + 1]; //default all are 0
        for(int hi = 0, i = 0; hi <= half; hi++) {
            dp[hi][hi] = (chars[i] == 'T' ? 1 : 0);
        }

        int[] f = new int[half];
        f[0] = 1;
        f[1] = 1;
        for(int i = 2; i < half; i++){
            for(int j = 1; j < i; j++){
                f[i] += f[j] * f[i - j];
            }
        }

        for(int k = 1; k <=half; k++){
            for(int hi = 0, hj = hi + k; hj <= half; hi++, hj++){
                for(int h = hi, i = h * 2 + 1; h < hj; h++, i += 2){
                    if(chars[i] == '&'){
                        dp[hi][hj] = dp[hi][h] * dp[h+1][hj];
                    }else{ // '|'
                        dp[hi][hj] = dp[hi][h] * f[hj - h - 1] + f[h - hi] * dp[h+1][hj];
                    }
                }
            }
        }

        return dp[0][half];
    }

}
