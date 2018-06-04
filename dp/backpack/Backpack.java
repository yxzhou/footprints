package fgafa.dp.backpack;


/**
 * 
 * Given n items with size Ai, an integer m denotes the size of a backpack. How full you can fill this backpack?

	Example
	If we have 4 items with size [2, 3, 5, 7], 
	if the backpack size is 11, we can select [2, 3, 5], so that the max size we can fill this backpack is 10. 
	If the backpack size is 12. we can select [2, 3, 7] so that we can fulfill the backpack.
	
	You function should return the max size we can fill in the given backpack.
	
	Note
	You can not divide any item into small pieces.
	Every item can be filled multiple times.
	
	Challenge
	O(n x m) time and O(m) memory.
	
	O(n x m) memory is also acceptable if you do not know how to optimize memory.
 *
 */
public class Backpack {
    /**
     * @param m: An integer m denotes the size of a backpack
     * @param A: Given n items with size A[i]
     * @return: The maximum size
     */
    /* Time O(m * n)  Space O(m)*/
    public int backPack(int m, int[] A) {
    	int result = 0;
        //check
    	if(m < 1 || null == A || 0 == A.length){
    		return result;
    	}
    	
    	boolean[] size = new boolean[m+1]; //default all are false
    	int index;
    	for(int a : A){
    		if(a > m){
    			continue;
    		}

    		//a just can be used once
    		for(int i = m; i > 0; i--){
    			index = a + i;
    			if(size[i] && index <= m ){
    				size[index] = true;
    			}
    		}                                              
    		
    		size[a] = true;
    	}
    	
    	for(int i = m; i > 0; i--){
    		if(size[i]){
    			return i;
    		}
    	}
    	return result;
    }
    
    public static void main(String[] args){
    	int[] A = {12,3,7,4,5,13,2,8,4,7,6,5,7};
    	int m = 90;
    	
    	Backpack sv = new Backpack();
    	System.out.println(sv.backPack(m, A));
    	
    }
}
