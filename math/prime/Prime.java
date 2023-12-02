package math.prime;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import junit.framework.Assert;

/*
 * Output all prime numbers up to a specified integer n.
 * 
 */
public class Prime {

    public boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }

        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }
    

  /* 
   * Generate a prime list from 0 up to n, using The Sieve of Erantosthenes,
   * param n The upper bound of the prime list (including n)
   * param prime[] An array of truth value whether a number is prime
   * 
   * 1)Create a list of consecutive integers from 2 to n: (2, 3, 4, ..., n).
   * 2)Initially, let p equal 2, the first prime number.
   * 3)Starting from p, count up in increments of p and mark each of these numbers greater than p itself in the list. 
   * These numbers will be 2p, 3p, 4p, etc.; 
   * note that some of them may have already been marked.
   * 4)Find the first number greater than p in the list that is not marked. 
   *   If there was no such number, stop. 
   *   Otherwise, let p now equal this number (which is the next prime), and repeat from step 3.
   * 
   * 
  */
    public boolean[] isPrime_sieve(int n) {
        if (n < 0) {
            return new boolean[0];
        }

        boolean[] isPrime = new boolean[n + 1];//default all are false
        Arrays.fill(isPrime, 2, isPrime.length, true);// 0 and 1 is not prime and composite

        int limit = (int) Math.sqrt(n);  //**
        for (int i = 2; i <= limit; i++) {
            if (isPrime[i]) {
                for (int k = i * i; k <= n; k += i) {
                    isPrime[k] = false;
                }
            }
        }

        return isPrime;
    }

    public List<Integer> prime(int n) {
        if (n < 2) {
            return Collections.EMPTY_LIST;
        }

        boolean[] isPrime = new boolean[n + 1]; //default all are false
        Arrays.fill(isPrime, true);
        
        List<Integer> result = new LinkedList<>();

        for (int i = 2; i <= n; i++) {
            if (!isPrime[i]) {
                result.add(i);

                //for (int j = (i << 1); j <= n; j += i) {
                for (int j = i * i; j <= n; j += i) {    
                    isPrime[j] = false;
                }
            }
        }

        return result;
    }
  
  /*
   * 
   * Count the number of prime numbers less than a non-negative number, n
   */
    public int countPrimes(int n) {
        if (n < 2) { //example, 0 and 1 are not prime and composite
            return 0;
        }

        boolean[] isPrime = new boolean[n + 1];//default all are false

        //using The Sieve of Erantosthenes
        int count = 0; 
        for (int i = 2; i <= n; i++ ) {
            if (!isPrime[i]) {
                count++;
                
                for (int j = i * i; j <= n; j += i) { //start from i*i
                    isPrime[j] = true;
                }
            }
        }

        return count;
    }
  
    public static void main(String[] args) {

        System.out.println("====start====");

        int[] input = {113, 120};

        Prime sv = new Prime();
        
        for(int n : input){
            System.out.println("\n====input: " + n);
            
            boolean[] prime = sv.isPrime_sieve(n);

            int count = 0;
            for (int i = 0; i < prime.length; i++) {
                if (prime[i]) {
                    count++;

                    System.out.print(i + " ");
                }
            }

            int result = sv.countPrimes(n);
            System.out.println();
            System.out.println(result);

            Assert.assertEquals(result, count);
        }

        System.out.println("====end====");
    }

}
