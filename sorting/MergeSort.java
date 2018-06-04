package fgafa.sorting;



public class MergeSort {

    public void mergeSort(int[] nums){
        mergeSort(nums, 0, nums.length / 2, nums.length);
    }
    
    private void mergeSort(int[] nums, int start, int delimit, int end){
        if(start + 1 < delimit){
            mergeSort(nums, start, (delimit - start) / 2, delimit);
        }
        
        if(delimit + 1 < end){
            mergeSort(nums, delimit, (end - delimit) / 2, end);
        }
        
        merge(nums, start, delimit, end);
    }
    
    /**
     * 2 sorted parts in array, one is from nums[0] to nums[i - 1], the other is from nums[i] to nums[nums.length - 1]
     * do merge sort in place
     * 
     * @return 
     */
    private void merge(int[] nums, int start, int delimit, int end){
        //check
        if(null == nums || start < 0 || end > nums.length || delimit <= start || delimit >= end ){
            return;
        }
        
        boolean needMerge = false;
        
        do{

            int second = delimit;
            needMerge = false;
            
            while(start < second){
                if(nums[start] > nums[second] ){
                    swap(nums, start, second);
                    start++;
                    
                }else{ // <=
                    start++;
                }
            }
            
        }while(needMerge);
    }
    
    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    
    public static void main(String[] args) {
        int[][] input = {
                    {1,3,5,2,4,6,8},
                    {1,4,5,6,2,3,7}
        };
        
        int[] index = {
                    3,
                    4
        };

        MergeSort sv = new MergeSort();
        for(int i = 0; i < input.length; i++){
            //System.out.println(String.format("\n Input: %s, %d", Misc.));
            
            //System.out.println(String.format("", ));
        }
        
    }

}
