package array.strobogrammatic;

import java.util.ArrayList;
import java.util.List;

import util.Misc;

/**
 * 
 *A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down). 
 *For example, the numbers "69", "88", and "818" are all strobogrammatic.

    Find all strobogrammatic numbers that are of length = n.
    
    For example,
    Given n = 2, return ["11","69","88","96"].
 *
 */

public class StrobogrammaticII {
    
    int[] middleDigits = {0, 1, 8}; 
    int[] validDigits = {0, 1, 6, 8, 9}; 
    char[] map = {'0', '1', '#', '#', '#', '#', '9', '#', '8', '6'};
    
    public List<String> findStrobogrammatic(int n) {

        List<String> result = new ArrayList<>();
        
        //check
        if(1 > n){
            return result;
        }else if(1 == n){
            for(int i = 0; i < validDigits.length; i++){
                result.add(String.valueOf(validDigits[i]));
            }
            return result;
        }
        
        helper(n, result, new StringBuilder(n), true);
        
        return result;
    }
    
    private void helper(int n, List<String> result, StringBuilder str, boolean isFirst){
        if(n < 1){
            int size = str.length();
            
            for(int i = size - 1 + n; i >= 0; i--){  //n = 0 or n = -1
                str.append(map[str.charAt(i) - '0']);
            }
            
            result.add(str.toString());
            str.delete(size, str.length());
        }else if(n == 1){
            for(int i = 0; i < middleDigits.length; i++){  //**
                str.append(middleDigits[i]);
                
                helper(n - 2, result, str, false);
                
                str.deleteCharAt(str.length() - 1);
            }
        }else if(n > 1){
            for(int i = ( isFirst ? 1 : 0); i < validDigits.length; i++){  //**
                str.append(validDigits[i]);
                
                helper(n - 2, result, str, false);
                
                str.deleteCharAt(str.length() - 1);
            }
        }
    }
    
    public static void main(String[] args){

        StrobogrammaticII sv = new StrobogrammaticII();
        
        for(int i = 0; i < 9; i++){
            System.out.println(String.format("Input %d ", i)); 
            Misc.printArrayList(sv.findStrobogrammatic(i));
        }
    }
}
