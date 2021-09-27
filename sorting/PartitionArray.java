package sorting;

public class PartitionArray {

    /**
     * Partition an integers array into odd number first and even number second.
     * 
     * Example
     * Given [1, 2, 3, 4], return [1, 3, 2, 4]
     * 
     * Challenge
     * Do it in-place.
     * 
     * @param nums: an array of integers
     * @return: nothing
     */
	
	
    /**
     * Partition an integers array into odd number first and even number second.
     * 
     * Example
     * Given [4, 2, 1, 3], return [1, 4, 3, 2] or [1, 2, 3, 4]
     * 
     * Challenge
     * Do it in-place.
     * 
     * @param nums: an array of integers
     * @return: nothing
     */
    public void partitionArray(int[] nums) {
        // check
        if(null == nums || 2 > nums.length){
            return;
        }
        
        for(int i = 0, j = 1; i + j < nums.length; ){
            if(isEven(i + nums[i])){ //no need worry about overflow
                swap(nums, i, i+j);
                j++;
            }else{
                i++;
                j = 1;
            }
        }
    }
    
    public void partitionArray_n(int[] nums) {
        // check
        if(null == nums || 2 > nums.length){
            return;
        }
        
        for(int i = 0, j = 1, size = nums.length; i < size & j < size; ){
            if(isOdd(nums[i])){ 
                i += 2;
            }else if(isEven(nums[j])){
                j += 2;
            }else{
            	swap(nums, i, j);
            	i += 2;
            	j += 2;
            }
        }
    }
    
    private boolean isOdd(int num){
        return (num & 1) == 1;
    }
    
    private boolean isEven(int num){
        return (num & 1) == 0;
    }
    
    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    
	public static void main(String[] args) {
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Integer.MAX_VALUE + 1);
		
		PartitionArray sv = new PartitionArray();
		
		System.out.println(sv.isOdd(Integer.MAX_VALUE));
		System.out.println(sv.isOdd(Integer.MAX_VALUE + 1));	
		
		
	}

}
