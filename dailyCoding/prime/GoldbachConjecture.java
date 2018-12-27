package fgafa.dailyCoding.prime;

import java.util.ArrayList;
import java.util.List;

import fgafa.util.Misc;
import org.junit.Test;

/**
 *
 * Given an even number (greater than 2), return two prime numbers whose sum will be equal to the given number.

 A solution will always exist. See Goldbach’s conjecture.

 Example:

 Input: 4
 Output: 2 + 2 = 4
 If there are more than one solution possible, return the lexicographically smaller solution.

 If [a, b] is one solution with a <= b, and [c, d] is another solution with c <= d, then

 [a, b] < [c, d]
 If a < c OR a==c AND b < d.
 *
 * tags: alibaba
 *
 */

public class GoldbachConjecture {

    public int[] goldbachConjecture(int n){
        assert n>2;
        assert (n&1) == 0;

        boolean[] isPrimes = new boolean[n]; //default all are false
        List<Integer> primes = new ArrayList<>();

        for(int i = 2; i < n; i++){
            if(isPrimes[i]){
                continue;
            }

            primes.add(i);

            for(int j = i; j < n; j+=i){
                isPrimes[j] = true;
            }
        }

        for(int i = 0, j = primes.size() - 1; i <= j; ){
            int sum = primes.get(i) + primes.get(j);
            if(sum == n){
                return new int[]{primes.get(i), primes.get(j)};
            }else if(sum < n){
                i++;
            }else{
                j--;
            }
        }

        return null; //this will not be supposed to happened.
    }

    @Test public void test(){
        for(int n = 4; n < 101; n += 2){
            System.out.println(n + " = " + Misc.array2String(goldbachConjecture(n)));

        }
    }
}
