package easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * There are a large number of leaves eaten by caterpillars. There are 'K' caterpillars which jump onto the leaves in a pre-determined sequence.
    All caterpillars start at position 0 and jump onto the leaves at positions  1,2,3...,N. Note that there is no leaf at position 0. 
    Each caterpillar has an associated 'jump-number'. Let the jump-number of the  i-th caterpillar be A [i]. 
    A caterpillar with jump number and keeps eating all leaves on A[i] * x,  x is [1, 2, --, N/A[i]] . 
    Given a set A of K element， K<=15， N <=109, we need to determine the number of uneaten leaves.
    
    Input Format: 
    N -number of leaves 
    A - Given array of integers 
    
    Output Format: 
    An integer denoting the number of uneaten leaves. 
    
    Sample Input: . From 1point 3acres bbs
    N = 10 
    A = [2,4,5] 
    
    Sample Output: 
    4 
 *
 */

/**
 * 
 * Solution:
 *   1) Define boolean[] isEated = new boolean[N + 1];
 *   for each insect, i is [0, A.length)
 *      for each leaf, j = A[i]; j <= N, j +=A[i]
 *          isEated[j] = true;
 *   
 *   count isEated is false;
 *   
 *   Time O(N * A.lenght), Memory O(A.length)
 *   
 *   2) if N << A.length; 
 *   for each subsets s in array [1, N]
 *     calculate least common multipler of elements in s, let be lcm
 *         eatedLeaves = N / lcm
 *         if the length of s is odd
 *            totalEatedLeaves -= eatedLeaves;
 *         else 
 *            totalEatedLeaves += eatedLeaves;
 *  
 *   Time O(2^N), Memory O(1)
 *
 */

public class UneatedLeaves {
    
    public int unEattedLeaves_1(int N, int[] A){
        //check ignore
        
        boolean isEated[] = new boolean[N+1]; //default all are false
        
        for(int factor : A){
            for(int i = factor; i <= N; i += factor ){
                isEated[i] = true;
            }
        }
        
        int remain = 0;
        for(int i = 1; i < isEated.length; i++){
            if(!isEated[i]){
                remain++;
            }
        }
        
        return remain;
    }
    
    private List<Integer> getPrimes(int[] A){
        Arrays.sort(A);
        
        List<Integer> result = new ArrayList<>();
        //result.add(A[0]);
        for(int i = 1, size = A.length; i < size; i++){
            if(0 >= A[i]){
                continue;
            }
            
            for(int j = i + 1; j < size; j++){
                if(0 >= A[j]){
                    continue;
                }else if( (A[j] % A[i]) == 0){
                    A[j] = 0;
                }
            }
        }
        
        for(int num : A){
            if(num > 0){
                result.add(num);
            }
        }
        
        return result;
    }
    

    private static long lcm(long a,
                    long b) {
        return a * b / gcd(a, b);
    }

    private static long gcd(long a,
                    long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

}
