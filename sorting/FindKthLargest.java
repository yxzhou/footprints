package fgafa.sorting;

import java.util.ArrayList;
import java.util.Random;

public class FindKthLargest {

    public int find2ndLargest(int[] nums){
        //check
        if(null == nums || 2 > nums.length){
            return -1;
        }
        
        int[] largests = new int[2];
        largests[0] = nums[0] > nums[1] ? 0 : 1;
        largests[1] = nums[0] > nums[1] ? 1 : 0;
        for(int i = 2, end = nums.length; i < end; i++){
            
            if(nums[i] > nums[largests[0]]){
                largests[1] = largests[0];
                largests[0] = i;
            }else if(nums[i] > nums[largests[1]]){
                largests[1] = i;
            }
            
        }
        
        return largests[1];
    }
    
    public int findkthLargest(int[] nums, int k){
        //check
        if(null == nums || k < 1 || k > nums.length){
            return -1;
        }
        
        Random random = new Random();
        int low = 0;
        int high = nums.length - 1;
        int pivot;
        k--;
        while(low < high){
            pivot = low + random.nextInt(high - low + 1);
            
            pivot = quickSort(nums, low, high, pivot);
            
            if(k == pivot){
                return nums[pivot];
            }else if(k < pivot){
                high = pivot - 1;
            }else{
                low = pivot + 1;
            }
        }
        
        return nums[low];
    }
    
    private int quickSort(int[] nums, int low, int high, int pivot){
        swap(nums, low, pivot);
        pivot = high;
        high--;
        while(low <= high){
            if(nums[low] < nums[pivot]){
                swap(nums, low, high);
                high--;
            }else{
                low++;
            }
        }
        
        swap(nums, low, pivot);
        return low;
    }
    
    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    
    public int kthLargestElement(int k, ArrayList<Integer> numbers) {
        //check
        if(null == numbers || 1 > k || k > numbers.size()){
            return Integer.MIN_VALUE;
        }
        
        int low = 0;
        int high = numbers.size() - 1;
        int pivot;
        k--;
        Random random = new Random();
        while(low < high){
        	pivot = low + random.nextInt(high - low + 1);
            pivot = quicksort(numbers, low, high, pivot);
            
            if(k == pivot ){
                return numbers.get(k);
            }else if(k < pivot){
                high = pivot - 1;
            }else{
                low = pivot + 1;
            }
        }
        
        return numbers.get(low);
    }
    
    private int quicksort(ArrayList<Integer> numbers, int low, int high, int pivot){
        swap(numbers, high, pivot);
        pivot = high;
        high--;
        while(low <= high){
            if(numbers.get(low) >= numbers.get(pivot)){
                low++;
            }else{
                swap(numbers, low, high);
                high--;
            }
        }

        swap(numbers, low, pivot);
        return low;
    }
    
    private void swap(ArrayList<Integer> numbers, int i, int j){
        Integer tmp = numbers.get(i);
        numbers.set(i, numbers.get(j));
        numbers.set(j, tmp);
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
