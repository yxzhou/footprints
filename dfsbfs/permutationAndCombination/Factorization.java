/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * _https://www.lintcode.com/problem/652
 *
 * A non-negative numbers can be regarded as product of its factors. Write a function that takes an integer n and return
 * all possible combinations of its factors.
 *
 *Constraints:
 *   Elements in a combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak). 
 *   The solution set must not contain duplicate combination.
 *
 * Example1 
 * Input: 8 
 * Output: [[2,2,2],[2,4]] 
 * Explanation: 8 = 2 x 2 x 2 = 2 x 4
 *
 * Example2 
 * Input: 1 
 * Output: []
 * 
 */
public class Factorization {
    
    /**
     * @param n: An integer
     * @return a list of combination
     */
    public List<List<Integer>> getFactors_dfs(int n) {
        LinkedList<List<Integer>> result = new LinkedList<>();
        int[] factors = new int[(int)(Math.log(n)/Math.log(2)) + 1];

        dfs(n,  factors, 0, result);

        result.removeLast();

        return result;
    }

    private void dfs(int n, int[] factors, int m,  List<List<Integer>> result){
        for(int x = (m == 0? 2 : factors[m - 1]), end = (int)Math.sqrt(n); x <= end; x++ ){
            if( n % x == 0){
                factors[m] = x;
                dfs( n / x, factors, m + 1, result);
            }
        }

        List<Integer> list = new ArrayList<>(m);
        for(int i = 0; i < m; i++){
            list.add(factors[i]);
        }
        list.add(n);

        result.add(list);
    }
    
    
    public List<List<Integer>> getFactors(int n) {
        LinkedList<List<Integer>> result = new LinkedList<>();
        
        List<Integer> primes = primeFactorization(n);
        if(primes.isEmpty()){
            return result;
        }
        
        int[] factors = new int[primes.size()];
        factors[0] = primes.get(0);
        
        dfs(n, factors, 0, primes, result);

        result.removeLast();

        return result;
    }
    
    private void dfs(int n, int[] factors, int m, List<Integer> primes, List<List<Integer>> result){
        //todo
    }
    
    private List<Integer> primeFactorization(int num){
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
