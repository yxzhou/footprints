package binarysearch;

/*
 * From lintcode:
 * 
 * Given n pieces of wood with length L[i] (integer array). 
 * Cut them into small pieces to guarantee you could have equal or more than k pieces with the same length. 
 * What is the longest length you can get from the n pieces of wood? Given L & k, 
 * return the maximum length of the small pieces.

     Notice
    You couldn't cut wood into float length.
    
    Example
    For L=[232, 124, 456], k=7, return 114.
    
    Challenge
    O(n log Len), where Len is the longest length of the wood.
 */
public class WoodCut {

    /** 
     *@param L: Given n pieces of wood with length L[i]
     *@param k: An integer
     *return: The maximum length of the small pieces.
     */
    public int woodCut_binarysearch(int[] L, int k) {
        //check
        if(null == L || 0 == L.length || 1 > k){
            return 0;
        }
        
        int maxLength = 0;
        long total = 0;
        for(int length : L){
            maxLength = Math.max(maxLength, length);
            total += length;
        }
        
        int high = (int)Math.min(total / k, maxLength);
        int low = maxLength / k;
        int mid;
        while(true){
            mid = low + ((high - low) >> 1);
            if(valid(L, mid, k)){
                if(valid(L, mid + 1, k)){
                    low = mid + 1;
                }else{
                    return mid;
                }
            }else{
                high = mid - 1;
            }
        }
    }
    
    private boolean valid(int[] L, int unitLength, int k){
        if(0 == unitLength){
            return true;
        }
        
        int count = 0;
        for(int length : L){
            count += length / unitLength;
        }
        
        return count >= k;
    }
    
	public static void main(String[] args) {
		int[][] LL = {
				{}, 
				{}};
		int[] kk = {};
		
		WoodCut sv = new WoodCut();

	}

}
