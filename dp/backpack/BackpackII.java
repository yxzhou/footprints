package fgafa.dp.backpack;


/**
 * 
Given n items with size Ai and value Vi, and a backpack with size m. What's the maximum value can you put into the backpack?


Example
Given 4 items with size [2, 3, 5, 7] and value [1, 5, 2, 4], and a backpack with size 10. The maximum value is 9.

Note
You cannot divide item into small pieces and the total size of items you choose should smaller or equal to m.

Challenge
O(n x m) memory is acceptable, can you do it in O(m) memory?
 *
 */
public class BackpackII {
    /**
     * @param m: An integer m denotes the size of a backpack
     * @param A & V: Given n items with size A[i] and value V[i]
     * @return: The maximum value
     */
    /* Time O(m * n)  Space O(m)*/
    public int backPackII(int m, int[] A, int V[]) {
    	int result = 0;
        //check
    	if(m < 1 || null == A || 0 == A.length || null == V || A.length != V.length){
    		return result;
    	}
    	
    	
    	int[] valueSum = new int[m + 1];//default all are 0
    	
    	int size;
    	int index;
    	final int len = A.length;
    	for(int k = 0; k < len; k++){
    		size = A[k];
    		if(size > m){
    			continue;
    		}

    		for(int i = m; i > 0; i--){
    			index = size + i;
    			if(valueSum[i] > 0 && index <= m ){
    				valueSum[index] = Math.max(valueSum[index], valueSum[i] + V[k]);
    			}
    		}                                              
    		
    		valueSum[size] = V[k];
		}

    	for(int i = m; i > 0; i--){
    		result = Math.max(result, valueSum[i]);
    	}
    	return result;
    }
    
    public static void main(String[] args){
    	int[] A = {2, 3, 5, 7};
    	int[] V = {1, 5, 2, 4};
    	int m = 10;
    	
    	BackpackII sv = new BackpackII();
    	System.out.println(sv.backPackII(m, A, V));
    	
    }
}
