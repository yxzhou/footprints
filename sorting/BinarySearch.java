/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sorting;

/**
 *
 * Pay attention to #1, overflow
 *   mid = (left + right) / 2, when left = 1, right = Integer.MAX_VALUE 
 *   better to:
 *   mid = left + (right - left) / 2;
 *   
 * Pay attention to  2, missing
 *   [left, right] vs [left, right) vs (left, right]
 *   what is it at beginning? keep it in the loop
 * 
 * Pay attention to  3, dead loop
 *   Example: when arr = [2, 5],  target = 1
 *  
 *    int left = 0;
 *    int right = 1;
 *    int mid;
 *    while(left < right){
 *       mid = left + (right - left) / 2;
 * 
 *       if(arr[mid] < target){
 *           right = mid - 1;
 *       }else{
 *           left = mid;
 *       }
 *    }
 *   
 *   In fact, mid is Math.floor(left + right)
 *   In this case, mid always equals left, dead loop. 
 * 
 *  cases: 
 *   find any element in sorted arr which equals to the target.
 *   find the first element in sorted arr which equals to the target
 *  
 *   search insert position
 * 
 *   find smallest number greater than target
 *   
 * 
 */
public class BinarySearch {
    
    /**
     * template #1
     * 
     */
    public int find(int[] arr, int target){
        int left = 0; 
        int right = arr.length - 1;
        
        int mid;
        while(left <= right){
            mid = left + (right - left) / 2;
            
            if(arr[mid] == target){
                return mid;
            }else if(arr[mid] > target){
                right = mid - 1;  // arr[mid - 1] is possible, arr[mid] is excluded
            }else{ // 
                left = mid + 1; // arr[mid + 1] is possible, arr[mid] is excluded
            }
        }
        
        return -1;
    }
        
    /**
     * template #2
     * 
     */
    public int binarySearch_2(int[] arr, int target){
        int left = 0; 
        int right = arr.length - 1;
        
        int mid;
        while(left + 1 <= right){
            mid = left + (right - left) / 2;
            
            if(arr[mid] <= target){
                left = mid;
            }else{ // 
                right = mid; 
            }
        }
        
        if(arr[left] == target){
            return left;
        }else if(arr[right] == target){
            return right;
        }
        return -1;
    }
    
    
}
