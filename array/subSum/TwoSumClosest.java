package fgafa.array.subSum;

import java.util.Arrays;

public class TwoSumClosest {
    
    /*Time O(nlogn) + O(n), Space O(1)*/
    public int[] twoSumClosest(int[] numbers, int target) {
        //initial
        int[] result = new int[2];
        
        //check
        if(numbers == null || numbers.length == 0){
            return result;
        }
        //sorted
        Arrays.sort(numbers);
      
        //
        int left = 0;
        int right = numbers.length - 1;
        int tmp;
        int min = Integer.MAX_VALUE;

        while(left < right){
            tmp = numbers[left] + numbers[right] - target; 

            if( Math.abs(tmp) < min) {
                  result[0] = numbers[left];
                  result[1] = numbers[right];

                  min = Math.abs(tmp);
            }

            if(tmp == 0){
                return result;
            }else if (tmp <0 ){
                left ++;
            }else{  //sumTmp > target
                right --;
            }

         }
         return result;
     }

    /*Time O(nlogn) + O(n^2), Space O(1)*/
    public int twoSumBiggerThan(int[] numbers, int target) {
        //initial
        int count = 0;
        
        //check
        if(numbers == null || numbers.length == 0){
            return count;
        }
        //sorted
        Arrays.sort(numbers);
      
        //
        int left = 0;
        int right = numbers.length - 1;
        int sum;

        while(left < right){
            sum = numbers[left] + numbers[right]; 

            if (sum > target){
                count += 1;
                
                right--;
            } else {  //sum <= target
                left++;
                
                right = numbers.length - 1;
            }
         }

         return count;
     }
    
    /*Time O(nlogn) + O(n), Space O(1)*/
    public int twoSumBiggerThan_n(int[] numbers, int target) {
        //initial
        int count = 0;
        
        //check
        if(numbers == null || numbers.length == 0){
            return count;
        }
        //sorted
        Arrays.sort(numbers);
      
        //
        int left = 0;
        int right = numbers.length - 1;
        int sum;

        while(left < right){
            sum = numbers[left] + numbers[right]; 

            if (sum > target){
                count += right - left;
                
                right--;
            } else {  //sum <= target
                left++;
            }
         }

         return count;
     }


    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
