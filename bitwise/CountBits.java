package fgafa.bitwise;

import java.util.ArrayList;
import java.util.List;

import fgafa.util.Misc;

/**
 * 
 * Given a non negative integer number num. For every numbers i in the range 0 ≤ i ≤ num calculate the number of 1's in their binary representation and return them as an array.

    Example:
    For num = 5 you should return [0,1,1,2,1,2].
    
    Follow up:
    
    It is very easy to come up with a solution with run time O(n*sizeof(integer)). But can you do it in linear time O(n) /possibly in a single pass?
    Space complexity should be O(n).
    Can you do it like a boss? Do it without using any builtin function like __builtin_popcount in c++ or in any other language.
 *
 */
public class CountBits {

    public int[] countBits(int num) {
        //check
        if(num < 0){
            return new int[0];
        }
        
        List<Integer> list = new ArrayList<>();
        
        list.add(0);
        if(num == 0){
            return list2Array(list);
        }
        
        list.add(1);
        if(num == 1){
            return list2Array(list);
        }
        
        int x = 4;
        for( ; x <= num; x <<= 1){
            for(int n = list.size(), i = 0; i < n; i++){
                list.add(1 + list.get(i));
            }
        }
        
        x >>= 1;
        for(int i = 0; x <= num; x++, i++ ){
            list.add(1 + list.get(i));
        }
        
        return list2Array(list);
    }
    
    private int[] list2Array(List<Integer> list){
        int[] arr = new int[list.size()];
        
        for(int i = 0; i < list.size(); i++){
            arr[i] = list.get(i);
        }
        
        return arr;
    }
    
    
    public static void main(String[] args){
        CountBits sv = new CountBits();
        
        for(int num = -1; num < 11; num++){
            System.out.println(String.format("countBits(%d), %s", num, Misc.array2String(sv.countBits(num))));
            
        }
    }
}
