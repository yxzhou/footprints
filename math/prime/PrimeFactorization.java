package math.prime;

import java.util.LinkedList;
import java.util.List;

/**
 * _https://www.lintcode.com/problem/235/?_from=ladder&fromId=190
 *
 * Prime factorize a given integer.
 *
 * You should sort the factors in ascending order.
 *
 * Example
 * Example 1:
 * Input: 10
 * Output: [2, 5]
 *
 * Example 2:
 * Input: 660
 * Output: [2, 2, 3, 5, 11]
 *
 */

public class PrimeFactorization {

    public List<Integer> primeFactorization(int num) {
        int n = num;
        List<Integer> factors = new LinkedList<>();
        for(int i = 2; i * i <= n; i++){
            while(n % i == 0){
                n /= i;

                factors.add(i);
            }
        }

        if(n > 1){
            factors.add(n);
        }

        return factors;
    }

    public List<Integer> primeFactorization_2(int num) {
        boolean[] v = new boolean[ (int)Math.sqrt(num) + 1 ]; //default all are false

        List<Integer> factors = new LinkedList<>();
        for(int i = 2; i <= Math.sqrt(num); i++){
            if(!v[i]){
                while(num % i == 0){
                    num /= i;

                    factors.add(i);
                }

                for(int j = i, end = (int)Math.sqrt(num); j <= end; j+=i){
                    v[j] = true;
                }
            }
        }

        if(num != 1){
            factors.add(num);
        }

        return factors;
    }

}
