package dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * Given n and k, return the k-th permutation of the dictionary order in the full permutation of n. (1 <= n <= 9)
 * 
 * Example
 * For n = 3, all permutations are listed as follows:
 * "123"
 * "132"
 * "213"
 * "231"
 * "312"
 * "321"
 * If k = 4, the fourth permutation is "231"
 * 
 * 
 * Challenge
 *   O(n*k) in time complexity is easy, can you do it in O(n^2) or less?
 *
 */

public class PermutationSequence {
	
    /**
     * 康拓编码
     * 
     * @param n: n
     * @param k: the kth permutation
     * @return: return the k-th permutation
     */
    public String getPermutation(int n, int k) {
        if (n < 1 || n > 9 || k < 1) {
            return null;
        }

        int[] f = new int[n + 1]; //fractorial
        f[0] = 1;
        char[] result = new char[n];
        for(int i = 1; i <= n; i++){
            f[i] = f[i - 1] * i;

            result[i - 1] = (char)(i + '0');
        }

        k--;
        k = k % f[n];

        int p;
        char c;
        for(int i = n - 1, j = 0; i >= 0; i--, j++){
            p = k / f[i];
            k = k % f[i];

            c = result[j + p];
            System.arraycopy(result, j, result, j + 1, p);
            result[j] = c;
        }

        return new String(result);
    }
    

    public static void main(String[] args) {
        PermutationSequence sv = new PermutationSequence();

        for (int n = 4, k = 1; k < 32; k++) {
            System.out.println(sv.getPermutation(n, k));
        }

    }

}
