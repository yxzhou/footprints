package dfsbfs.permutationAndCombination;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import util.Misc;

/**
 * 
 * Given a permutation which contains no repeated number, find its index in all
 * the permutations of these numbers, which are ordered in lexicographical
 * order. The index begins at 1.
 * 
 * Example Given [1,2,4], return 1.
 *
 */
public class PermutationIndex {
    /**
     * @param A an integer array
     * @return a long integer
     */
    public long permutationIndex(int[] A) {
        //check
        if(null == A || 2 > A.length){
            return 1;
        }
        
        long[] factorial = new long[A.length];
        factorial[0] = 1;
        for(int i = 1; i < A.length; i++){
            factorial[i] = factorial[i - 1] * i;
        }
        
        int[] B = Arrays.copyOf(A, A.length);
        Arrays.sort(B);
        Map<Integer, Integer> rank = new HashMap<>();
        for(int i = 0; i < B.length; i++){
        	rank.put(B[i], i);
        }
        
        long result = 1;
        for(int i = 0; i < A.length - 1; i++){
        	result += rank.get(A[i]) * factorial[A.length - 1 - i];
        	
        	for(Integer key : rank.keySet()){
        		if(key > A[i]){
        			rank.put(key, rank.get(key) - 1);
        		}
        	}
        }
        
        return result;
    }
    
    public long permutationIndex_2(int[] A) {
        //check
        if(null == A || 2 > A.length){
            return 1;
        }
        
        long[] factorial = new long[A.length];
        factorial[0] = 1;
        for(int i = 1; i < A.length; i++){
            factorial[i] = factorial[i - 1] * i;
        }
        
        long result = 1;
        int rank = 0;
        for(int i = 0; i < A.length - 1; i++){
        	rank = 0;
        	for(int j = i + 1; j < A.length; j++){
        		if(A[i] > A[j]){
        			rank++;
        		}
        	}
        	
        	result += rank * factorial[A.length - 1 - i];
        }
        
        return result;
    }
    
    public long permutationIndex_n(int[] A) {
        //check
        if(null == A || 2 > A.length){
            return 1;
        }
        
        long factorial = 1;
        long result = 1;
        int rank = 0;
        for(int i = A.length - 2, k = 2; i >= 0; i--, k++){
        	rank = 0;
        	for(int j = i + 1; j < A.length; j++){
        		if(A[i] > A[j]){
        			rank++;
        		}
        	}
        	
        	result += rank * factorial;
        	factorial = factorial * k;
        }
        
        return result;
    }
    
    
    
    /**
     * 
     * Given a permutation which may contain repeated numbers, find its index in all
     * the permutations of these numbers, which are ordered in lexicographical
     * order. The index begins at 1.
     * 
     * Example Given the permutation [1, 4, 2, 2], return 3.
     *
     */
    public long permutationIndexII(int[] A) {
        //check
        if(null == A || 2 > A.length){
            return 1;
        }
        
        long[] factorial = new long[A.length];
        factorial[0] = 1;
        
        long result = 1;
        int rank = 0;
        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
        count.put(A[A.length - 1], 1);
        for(int i = A.length - 2, k = 1; i >= 0; i--, k++){
        	rank = 0;
        	for(int j = i + 1; j < A.length; j++){
        		if(A[i] > A[j]){
        			rank++;
        		}
        	}
        	
        	if(count.containsKey(A[i])){
        		count.put(A[i], count.get(A[i]) + 1);
        	}else{
        		count.put(A[i], 1);
        	}
        	
        	factorial[k] = factorial[k - 1] * k;
        	
        	if(rank > 0){
            	result += rank * factorial[k] / getDuplication(count, factorial);
        	}
        }
        
        return result;
    }
    
    private long getDuplication(Map<Integer, Integer> count, long[] factorial){
        long dup = 1;
        for (int val : count.values()) {
            dup *= factorial[val];
        }

        return dup;
    }
        
    public static void main(String[] args){
    	int[][] input_unique = {
    			{1},
    			{1,2},
    			{2,1},
    			{1,2,3},
    			{1,3,2},
    			{2,1,3},
    			{2,3,1},
    			{3,1,2},
    			{3,2,1},
    			{1,2,3,4},
    			{1,2,4,3},
    			{2,1,3,4},
    			{4,3,2,1},	
    	};
    	
    	int[][] input_duplicate = {
    			{1, 2, 2, 4},
    			{1, 2, 4, 2},
    			{1, 4, 2, 2},
    			{2, 1, 2, 4},
    			{2, 1, 4, 2},
    			{2, 2, 1, 4},
    			{2, 2, 4, 1},
    			{2, 4, 1, 2},
    			{2, 4, 2, 1},
    			{4, 1, 2, 2},
    			{4, 2, 1, 2},
    			{4, 2, 2, 1},
    			
    			{2, 2, 3, 3, 4},
    			{2, 2, 3, 4, 3},
    			{2, 2, 4, 3, 3},
    			{2, 3, 2, 3, 4},
    			{2, 3, 2, 4, 3},
    			{2, 3, 3, 2, 4},
    			{2, 3, 3, 4, 2},
    			{2, 3, 4, 2, 3},
    			{2, 3, 4, 3, 2},
    			{2, 4, 2, 3, 3},
    			{2, 4, 3, 2, 3},
    			{2, 4, 3, 3, 2},	
    	};
    	
    	PermutationIndex sv = new PermutationIndex();
    	
    	for(int[] input : input_unique){
    		System.out.println("\nInput:" + Misc.array2String(input) + " " + sv.permutationIndex_n(input));
    		
    		System.out.println("\nInput:" + Misc.array2String(input) + " " + sv.permutationIndexII(input));
    	}

    	for(int[] input : input_duplicate){
    		System.out.println("\nInput:" + Misc.array2String(input) + " " + sv.permutationIndexII(input));
    	}
    }
}
