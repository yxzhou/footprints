package fgafa.math.prime;

import java.lang.Math;

/*
 * Output all prime numbers up to a specified integer n.
 * 
 */
public class Prime
{

    public boolean isPrime(int n){
        if(n < 2){
            return false;
        }
        
        for(int i = 2; i < n; i++){
            if(n % i == 0){
                return false;
            }
        }
        
        return true;
    }
    

  /* 
   * Generate a prime list from 0 up to n, using The Sieve of Erantosthenes
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
    public boolean[] isPrime_sieve(int n){
        if(n < 0){
            return new boolean[0];
        }
        
        boolean[] result = new boolean[n + 1];//default all are true ??
        // 0 and 1 is not prime and composite
        result[0] = false;
        result[1] = false;
        for(int i = 2; i < result.length; i++){
            result[i] = true;
        }
        
        int limit = (int)Math.sqrt(n);  //**
        for (int i = 2; i <= limit; i++) {
            if(result[i]){
                for(int k = i*i; k < n; k += i){
                    result[k] = false;
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
      //check input
	  if(n < 2){ //0 and 1 is not prime and composite
		  return 0;
	  }
	  
	  //init, 
	  boolean[] isPrime = new boolean[n];
	  isPrime[0] = false;
	  isPrime[1] = false;
	  for(int i=2; i<n; i++){
		  isPrime[i] = true;
	  }
	 
	  //using The Sieve of Erantosthenes
	  int limit = (int) Math.sqrt(n);
	  for(int i=2; i<= limit; i++){
		  if(isPrime[i]){
			  for(int j= i*i; j<n; j+=i){ //start from i*i
				  isPrime[j] = false;
			  }
		  }
	  }
	  
	  //count how many isPrime[] is true;
	  int count = 0;
	  for(int i=2; i<n; i++){
		  if(isPrime[i]){
			  count++;
		  }
	  }
	  
	  return count;
  }
  
  public static void main(String[] args) {

    System.out.println("====start====");
    
    
    int n = 120; 
    
        
    Prime sv = new Prime();
    boolean[] prime  = sv.isPrime_sieve(n);
    
    for(int i=0; i<n; i++){
      
      if(prime[i])
        System.out.print(i + " ");
        
    }
    
    System.out.println();
    System.out.println(sv.countPrimes(n));
    
    System.out.println("====end====");
  }

}
