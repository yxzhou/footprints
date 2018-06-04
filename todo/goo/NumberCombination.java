package fgafa.todo.goo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fgafa.util.Misc;

/**
 *  Google phone interview
 *  
 *  A set digits, find all the numbers combination which smaller than target.
 *  
 *  example, 
 *  Given  {1, 3, 5} and target 300, 
 *  return {1,3,5,11,13,15,31,33,35,111,113,115,131---}
 *
 */

public class NumberCombination {

    public List<Integer> getNumbersCombination(Set<Integer> digits, int target){
        List<Integer> result = new ArrayList<>();

        //check the digits one by one,  [0,9] 
        
        int[] sortedDigits = new int[digits.size()];
        int i = 0;
        for(int n : digits){
            sortedDigits[i++] = n;
        }
        Arrays.sort(sortedDigits);
        
        Map<Integer, Integer> digit2Index = new HashMap<>();
        for(i = 0; i < sortedDigits.length; i++){
            digit2Index.put(sortedDigits[i], i);
        }
        
        int curr  = sortedDigits[0];
        
        while(curr < target){
            result.add(curr);
            curr = next(sortedDigits, digit2Index, curr);
        }
            
        return result;
    }
    
    private int next(int[] sortedDigits, Map<Integer, Integer> digit2Index, int curr){
        
        String nums = String.valueOf(curr);
        
        int maxDigit = sortedDigits[sortedDigits.length - 1];
        
        //from right to left, find the first index when nums[index] < maxDigit
        int i = nums.length() - 1;
        for( ; i >= 0 && nums.charAt(i) - '0' == maxDigit ; i-- );
        
        StringBuilder next = new StringBuilder();
        
        if(i > -1){
            next.append(nums.substring(0, i));
            next.append(sortedDigits[digit2Index.get(nums.charAt(i) - '0') + 1]);
        }else{
            next.append(sortedDigits[0]);
        }
        
        for( i++; i < nums.length(); i++){
            next.append(sortedDigits[0]);
        }
        return Integer.valueOf(next.toString());
    }
    
    public List<Integer> getNumbersCombination_n(Set<Integer> digits, int target){
        List<Integer> result = new ArrayList<>();

        //check the digits one by one,  [0,9] 
        
        int[] sortedDigits = new int[digits.size()];
        int i = 0;
        for(int n : digits){
            sortedDigits[i++] = n;
        }
        Arrays.sort(sortedDigits);
        
        int size = sortedDigits.length;
        for(i = 0; i < size; i++){
            if(sortedDigits[i] < target){
                result.add(sortedDigits[i]);
            }else{
                return result;
            }
            
            sortedDigits[i] *= 10;
        }
        
        int start = 0;
        int end = size - 1;
        for(i = 0; sortedDigits[i] + result.get(end) < target; i++ ){
            for(int j = start; j <= end; j++){
                result.add(sortedDigits[i] + result.get(j));
            }
            
            sortedDigits[i] *= 10;
            
            if(i == size - 1){
                start = end + 1;
                end = result.size() - 1;
                i = -1;
            }
        }
        
        target -= sortedDigits[i];
        
        int low = start;
        int high = end;
        while(low + 1 < high){
            int mid = low + (high - low) / 2;
            if(result.get(mid) >= target){
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        end = result.get(high) < target ? high : low;
        end = result.get(end) < target ? end : end - 1;
        
        for(int j = start; j <= end; j++){
            result.add(sortedDigits[i] + result.get(j));
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        NumberCombination sv = new NumberCombination();
        
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(3);
        set.add(5);

        int target = 300;
        
        System.out.println(String.format("Input set: %s, target： %d", set.toString(), target));
        
        Misc.printList(sv.getNumbersCombination(set, target));
        Misc.printList(sv.getNumbersCombination_n(set, target));
        
    }

}
