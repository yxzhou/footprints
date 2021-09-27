package dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import util.Misc;

/**
 * Numbers can be regarded as product of its factors. For example, 8 = 2 x 2 x 2; = 2 x 4. 
 * Write a function that takes an integer n and return all possible combinations of its factors.
    
    Note:
    Each combination’s factors must be sorted ascending, for example: The factors of 2 and 6 is [2, 6], not [6, 2].
    You may assume that n is always positive.
    Factors should be greater than 1 and less than n.
    
    Examples:
    input: 1 output: []
    input: 37 output: []
    input: 12 output: [ [2, 6], [2, 2, 3], [3, 4] ]
    input: 32 output: [ [2, 16], [2, 2, 8], [2, 2, 2, 4], [2, 2, 2, 2, 2], [2, 4, 4], [4, 8] ]
 *
 */

public class FactorCombinations {

    public List<List<Integer>> factorCombinations(int n) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();

        //check
        if(n < 2){
            return result;
        }
        
        //get all prime factors
        List<Integer> factors = new ArrayList<>();
        boolean[] isPrime = new boolean[n]; //default all are false
        for(int i = 2; i < n; i++){
            isPrime[i] = true;
        }
        
        for(int i = 2, end = (int)Math.sqrt(n); i <= end; i++){
            if(isPrime[i]){
                factors.add(i);
                
                for(int j = i*i; j < n; j += i){
                    isPrime[j] = false;
                }
            }
        }
        
        //combination
        helper(result, factors, 0, n, new ArrayList<Integer>());
        
        return result;
    }
    
    private void helper(List<List<Integer>> result, List<Integer> factors, int start, int n, List<Integer> path){
        
        int factor;
        int remain;
        for(int i = start; i < factors.size(); i++){
            factor = factors.get(i);
                        
            if(n % factor == 0){
                remain = n / factor;
                
                if(remain >= factor){
                    List<Integer> tmp = new ArrayList<>(path);
                    tmp.add(factor);
                    tmp.add(remain);
                    result.add(tmp);
                }
                
                if( remain > factor ){
                    path.add(factor);
                    helper(result, factors, i, remain, path);
                    path.remove(path.size() - 1);
                }

            }
        }
    }
    
    public List<List<Integer>> factorCombinations_2(int n) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        
        if(n > 1){
            helper(ans, new LinkedList<Integer>(), n, n / 2);
        }
        
        return ans;
    }

    private void helper(List<List<Integer>> ans, LinkedList<Integer> path, int num, int biggestFactor) {
        if (num == 1) {
            ans.add(new ArrayList<Integer>(path));
            return;
        }
      
        for (int i = Math.min(biggestFactor, num); i >= 2; i--) {
            if (num % i == 0) {
                path.addFirst(i);
                helper(ans, path, num / i, i);
                path.removeFirst();
            }
        }
    }
    
    public List<List<Integer>> factorCombinations_n(int n) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        
        if(n > 1){
            helper(ans, new LinkedList<Integer>(), n, 2, n / 2);
        }
        
        return ans;
    }

    private void helper(List<List<Integer>> ans, LinkedList<Integer> path, int n, int start, int end) {
        if (n == 1) {
            ans.add(new ArrayList<Integer>(path));
            return;
        }
      
        for (int i = start; i <= end; i++) {
            if (n % i == 0) {
                path.addLast(i);
                helper(ans, path, n / i, i, n / i);
                path.removeLast();
            }
        }
    }
    
    public static void main(String[] args){
        int[] input = {1, 12, 18, 25, 30, 32, 36, 37, 4*9*25};
        
        FactorCombinations sv = new FactorCombinations();
        
        for(int n : input){
            System.out.println(String.format(" Input %d", n));
            Misc.printListList(sv.factorCombinations_2(n));
            System.out.println();
            Misc.printListList(sv.factorCombinations_n(n));
        }
    }
}
