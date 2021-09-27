package array;

/**
 * 
 * Find the contiguous subarray within an array (containing at least one number) which has the largest product.
 * 
 * For example, given the array [2,3,-2,4], the contiguous subarray [2,3] has the largest product = 6.
 *
 */
public class LargestSubProduct {

	/**
	 * Time O(n)
	 */
	public int maxProduct(int[] A) {
		if(null == A || 0 == A.length)
			return 0;

		int maxProduct = A[0];
		int max_temp = A[0];
		int min_temp = A[0];

		for (int i = 1; i < A.length; i++) {
			int a = A[i] * max_temp;
			int b = A[i] * min_temp;
			max_temp = Math.max(Math.max(a, b), A[i]);
			min_temp = Math.min(Math.min(a, b), A[i]);
			maxProduct = Math.max(maxProduct, max_temp);
		}
		return maxProduct;
	}

    /**
     * @param nums: an array of integers
     * @return: an integer
     */
    public int maxProduct_n(int[] nums) {
    	if(null == nums || 0 == nums.length){
    		return -1; // error
    	}
    	
    	int subMax = nums[0];
    	int subMin = nums[0];
    	int max = nums[0];
    	
    	int size = nums.length;
    	int a;
    	int b;
    	for(int i = 1; i < size; i++){
    		a = subMax * nums[i];
    		b = subMin * nums[i];
    				
    		subMax = Math.max(Math.max(a, b), nums[i]);
    		subMin = Math.min(Math.min(a, b), nums[i]);
    		
    		max = Math.max(max, subMax);
    	}
    	
    	return max;
    }

	public int maxProduct_x(int[] nums) {
		int result = Integer.MIN_VALUE;
		int max = 1;
		int min = 1;
		int tmp;

		for(int x : nums){
			if(x > 0){
				max = Math.max(x, max * x);
				min = Math.min(x, min * x);
			}else{
				tmp = max * x;
				max = Math.max(x, min * x);
				min = Math.min(x, tmp);
			}

			result = Math.max(result, max);
		}

		return result;
	}
	
	/*wrong*/
	public int maxProduct_wrong(int[] A) {
		if(null == A || 0 == A.length)
			return 0;
		
		int max = 0;
		for(int i=0, j=0; i< A.length; i = j+1){
			while(i<A.length && 0 == A[i++]);
			
			int countOfNegative = 0;
			for( j=i; j<A.length && 0 != A[j]; j++){
				if(A[j] < 0){
					countOfNegative++;
				}
			}
			
			max = Math.max(max, maxProduct(A, i, j+1, countOfNegative));
			i = j + 1;
		}
		return max;
	}
	
	private int maxProduct(int[] A, int i, int j, int countOfNegative){
		if(i == j)
			return 0;
		
		int left = 1;
		int right = 1;
		int middle = 1;
		if( (countOfNegative & 1) == 1 ){
			while(i<j && A[i] > 0 )
				left *= A[i++];
		
			if(countOfNegative > 1)	
			    left *= A[i++];
			else
				i++;
		}
		
		if(countOfNegative > 1)	{
			while(i<j && A[i] > 0)
				middle *= A[i++];
		}
		
		if( (countOfNegative & 1) == 1 ){	
			if(countOfNegative > 1)	
			    right *= A[i++];
			else
				i++;
			
			while(i<j && A[i] > 0)
				right *= A[i++];
		}
		
		return Math.max(right* middle, left* middle);
	}
	
	public static void main(String[] args) {
		int[][] inputs = {{-4, -3, -2}};


		LargestSubProduct sv = new LargestSubProduct();

		for(int i = 0; i < inputs.length; i++){
			sv.maxProduct_x(inputs[i]);
		}


	}

}
