package dp.backpack;


/**
 * _https://www.lintcode.com/problem/125
 * 
 * Given n items with size A[i] and value V[i], and a backpack with size m. 
 * What's the maximum value can you put into the backpack?
 * 
 * Note
 * You cannot divide item into small pieces
 * The total size of items you choose should smaller or equal to m.
 * Each item can only be picked up once.
 * 
 * Example 1
 * Input: A = [2, 3, 5, 7] and V = [1, 5, 2, 4], m = 10.
 * Output: 9
 * Explanation: Put A[1] and A[3] into backpack, get the max value V[1] + V[3] = 9
 * 
 * Example 2
 * Input: A = [2, 3, 8] and V = [2, 5, 8], m = 10.
 * Output: 10
 * Explanation: Put A[0] and A[2] into backpack, get the max value V[0] + V[2] = 10
 *
 *
 * Challenge
 * O(n x m) memory is acceptable, can you do it in O(m) memory?
 *
 */
public class BackpackII {
    /**
     * @param m: An integer m denotes the size of a backpack
     * @param A & V: Given n items with size A[i] and value V[i]
     * @return: The maximum value
     */
    /* Time O(m * n)  Space O(m)*/
    public int backPackII(int m, int[] A, int[] V) {
        if(m < 1 || A == null || V == null ){
            return 0;
        }

        int[] max = new int[m + 1];// max[i] is the max value with size i
        max[0] = 1;

        int size;
        for(int i = 0, end = A.length; i < end; i++){ // each size of item
            for(int j = m - A[i]; j >= 0; j--){
                if( max[j] > 0 ){
                    size = j + A[i];
                    max[size] = Math.max(max[size], max[j] + V[i]) ;
                }
            }
        }

        int result = 0;
        for( ; m > 0; m--){
            result = Math.max(result, max[m]);
        }

        return result == 0? 0 : result - 1;
    }
    
    public static void main(String[] args){
    	int[] A = {2, 3, 5, 7};
    	int[] V = {1, 5, 2, 4};
    	int m = 10;
    	
    	BackpackII sv = new BackpackII();
    	System.out.println(sv.backPackII(m, A, V));
    	
    }
}
