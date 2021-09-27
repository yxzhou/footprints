package dailyCoding;

/**
 *
 * A number is considered perfect if its digits sum up to exactly 10.

 Given a positive integer n, return the n-th perfect number.

 For example, given 1, you should return 19. Given 2, you should return 28.
 *
 * Tags: ms
 *
 */

public class PerfectNumber {

    public String perfectNumber(int n){
        if(n < 0){
            throw new IllegalArgumentException("The input should be positive integer.");
        }

        int sum = 0;
        int x = n;
        while(x > 0){
            sum += x % 10;
            sum %= 10;

            x /= 10;
        }

        return n + "" + (sum == 0 ? "" : 10 - sum);
    }

}
